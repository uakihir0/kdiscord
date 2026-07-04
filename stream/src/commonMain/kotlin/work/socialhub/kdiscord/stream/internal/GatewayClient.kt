package work.socialhub.kdiscord.stream.internal

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import work.socialhub.khttpclient.websocket.WebsocketRequest
import work.socialhub.kdiscord.Discord
import work.socialhub.kdiscord.DiscordFactory
import work.socialhub.kdiscord.entity.gateway.GatewayOpcode
import work.socialhub.kdiscord.entity.gateway.GatewayPayload
import work.socialhub.kdiscord.entity.gateway.HelloData
import work.socialhub.kdiscord.entity.gateway.IdentifyPayload
import work.socialhub.kdiscord.entity.gateway.ResumePayload
import work.socialhub.kdiscord.entity.gateway.event.GuildCreateEvent
import work.socialhub.kdiscord.entity.gateway.event.MessageCreateEvent
import work.socialhub.kdiscord.entity.gateway.event.MessageDeleteGatewayEvent
import work.socialhub.kdiscord.entity.gateway.event.MessageUpdateEvent
import work.socialhub.kdiscord.entity.gateway.event.PresenceUpdateGatewayEvent
import work.socialhub.kdiscord.entity.gateway.event.ReadyEvent
import work.socialhub.kdiscord.entity.gateway.event.UnknownEvent
import work.socialhub.kdiscord.internal.InternalUtility
import work.socialhub.kdiscord.stream.DiscordStream
import work.socialhub.kdiscord.stream.DiscordStreamListener
import kotlin.concurrent.Volatile
import kotlin.random.Random

/**
 * Discord Gateway client implementing the connection lifecycle:
 * Hello -> Heartbeat (+ jitter) -> Identify -> Ready -> Resume, with
 * Reconnect / Invalid Session handling and exponential-backoff reconnect.
 *
 * v1 uses uncompressed JSON transport (no zlib-stream).
 */
class GatewayClient(
    private val apiHost: String,
    private val token: String,
) : DiscordStream {

    companion object {
        private const val GATEWAY_VERSION = "10"
        private const val INITIAL_RECONNECT_DELAY_MS = 1000L
        private const val MAX_RECONNECT_DELAY_MS = 30000L
    }

    private val discord: Discord = DiscordFactory.instance(token, apiHost)
    private val listeners = mutableListOf<DiscordStreamListener>()

    @Volatile
    private var websocket: WebsocketRequest? = null

    @Volatile
    private var isConnected: Boolean = false

    @Volatile
    private var isIntentionallyClosed: Boolean = false

    // Gateway session state
    @Volatile
    private var heartbeatIntervalMs: Long = 0

    @Volatile
    private var lastSequence: Int? = null

    @Volatile
    private var sessionId: String? = null

    @Volatile
    private var resumeGatewayUrl: String? = null

    @Volatile
    private var heartbeatAcked: Boolean = true

    private var heartbeatJob: Job? = null
    private var reconnectJob: Job? = null
    private var frameConsumerJob: Job? = null

    private var currentReconnectDelay: Long = INITIAL_RECONNECT_DELAY_MS
    private var scope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    // Single-consumer channel to preserve frame ordering (khttpclient dispatches
    // each received frame in a separate coroutine, so ordering is not guaranteed).
    private var frames = Channel<String>(Channel.UNLIMITED)

    override fun addEventListener(listener: DiscordStreamListener) {
        listeners.add(listener)
    }

    override fun removeEventListener(listener: DiscordStreamListener) {
        listeners.remove(listener)
    }

    override fun isConnected(): Boolean = isConnected

    override suspend fun start() {
        if (isConnected) return
        isIntentionallyClosed = false
        scope = CoroutineScope(Dispatchers.Default + SupervisorJob())
        frames = Channel(Channel.UNLIMITED)
        currentReconnectDelay = INITIAL_RECONNECT_DELAY_MS

        // Single consumer that processes frames in receive order.
        frameConsumerJob = scope.launch {
            for (raw in frames) {
                try {
                    handleFrame(raw)
                } catch (e: Exception) {
                    dispatchError(e)
                }
            }
        }

        connect(resume = false)
    }

    override fun stop() {
        isIntentionallyClosed = true
        reconnectJob?.cancel()
        heartbeatJob?.cancel()
        frameConsumerJob?.cancel()
        try {
            websocket?.close()
        } catch (e: Exception) {
            // ignore
        }
        scope.cancel()
        isConnected = false
        listeners.forEach { it.onClose() }
    }

    private suspend fun connect(resume: Boolean) {
        if (isIntentionallyClosed) return
        try {
            val base = if (resume) {
                resumeGatewayUrl ?: fetchGatewayUrl()
            } else {
                fetchGatewayUrl()
            }
            openWebSocket(withQuery(base), resume)
        } catch (e: Exception) {
            dispatchError(e)
            scheduleReconnect(resume)
        }
    }

    private suspend fun fetchGatewayUrl(): String {
        val response = discord.gateway().getGateway()
        return response.data.url
            ?: throw IllegalStateException("Gateway URL is null in response")
    }

    private fun withQuery(base: String): String {
        val trimmed = base.trimEnd('/')
        return "$trimmed/?v=$GATEWAY_VERSION&encoding=json"
    }

    private fun openWebSocket(wsUrl: String, resume: Boolean) {
        val ws = WebsocketRequest()
        ws.url(wsUrl)
        ws.textListener = { message ->
            // Enqueue only; ordered handling happens in the single consumer.
            frames.trySend(message)
        }
        ws.onOpenListener = {
            // WebSocket transport is open; the Gateway handshake (Hello/Identify)
            // still needs to complete before we consider the session connected.
            resumeRequested = resume
        }
        ws.onCloseListener = {
            isConnected = false
            heartbeatJob?.cancel()
            if (!isIntentionallyClosed) {
                scheduleReconnect(resume = sessionId != null)
            } else {
                listeners.forEach { it.onClose() }
            }
        }
        ws.onErrorListener = { error ->
            dispatchError(error)
        }
        websocket = ws
        scope.launch {
            try {
                ws.open()
            } catch (e: Exception) {
                dispatchError(e)
                if (!isIntentionallyClosed) scheduleReconnect(resume = sessionId != null)
            }
        }
    }

    @Volatile
    private var resumeRequested: Boolean = false

    private suspend fun handleFrame(raw: String) {
        val payload = InternalUtility.json.decodeFromString<GatewayPayload>(raw)

        // Track the sequence number in receive order before any dispatch.
        payload.s?.let { lastSequence = it }

        when (payload.op) {
            GatewayOpcode.HELLO -> {
                val hello = decodeData<HelloData>(payload.d)
                heartbeatIntervalMs = hello?.heartbeatInterval ?: 0
                startHeartbeat()
                if (resumeRequested && sessionId != null && lastSequence != null) {
                    sendResume()
                } else {
                    sendIdentify()
                }
            }

            GatewayOpcode.HEARTBEAT -> {
                // Server requested an immediate heartbeat.
                sendHeartbeat()
            }

            GatewayOpcode.HEARTBEAT_ACK -> {
                heartbeatAcked = true
            }

            GatewayOpcode.RECONNECT -> {
                // Server asks us to reconnect and resume.
                closeSocketForReconnect()
                scheduleReconnect(resume = true)
            }

            GatewayOpcode.INVALID_SESSION -> {
                // d is a boolean indicating whether the session is resumable.
                val resumable = (payload.d as? JsonPrimitive)?.content?.toBoolean() ?: false
                if (!resumable) {
                    sessionId = null
                    lastSequence = null
                }
                closeSocketForReconnect()
                scheduleReconnect(resume = resumable)
            }

            GatewayOpcode.DISPATCH -> {
                dispatch(payload)
            }

            else -> {
                // Ignore unhandled opcodes.
            }
        }
    }

    private fun dispatch(payload: GatewayPayload) {
        val type = payload.t ?: return

        if (type == "READY") {
            val ready = decodeData<work.socialhub.kdiscord.entity.gateway.ReadyData>(payload.d)
            if (ready != null) {
                sessionId = ready.sessionId
                resumeGatewayUrl = ready.resumeGatewayUrl
                isConnected = true
                currentReconnectDelay = INITIAL_RECONNECT_DELAY_MS
                listeners.forEach { it.onReady(ready) }
            }
            return
        }

        if (type == "RESUMED") {
            isConnected = true
            currentReconnectDelay = INITIAL_RECONNECT_DELAY_MS
            return
        }

        when (val event = GatewayEventSerializer.decode(type, payload.d)) {
            is ReadyEvent -> listeners.forEach { it.onReady(event.data) }
            is MessageCreateEvent -> listeners.forEach { it.onMessageCreate(event.message) }
            is MessageUpdateEvent -> listeners.forEach { it.onMessageUpdate(event.message) }
            is MessageDeleteGatewayEvent -> listeners.forEach { it.onMessageDelete(event.data) }
            is GuildCreateEvent -> listeners.forEach { it.onGuildCreate(event.guild) }
            is PresenceUpdateGatewayEvent -> listeners.forEach { it.onPresenceUpdate(event.data) }
            is UnknownEvent -> listeners.forEach { it.onUnknownEvent(event.type, event.raw) }
            else -> {}
        }
    }

    private fun startHeartbeat() {
        heartbeatJob?.cancel()
        heartbeatAcked = true
        heartbeatJob = scope.launch {
            // First heartbeat after a jittered fraction of the interval (per spec).
            delay((heartbeatIntervalMs * Random.nextDouble()).toLong())
            while (isActive) {
                if (!heartbeatAcked) {
                    // Zombied connection: reconnect and resume.
                    closeSocketForReconnect()
                    scheduleReconnect(resume = true)
                    break
                }
                heartbeatAcked = false
                sendHeartbeat()
                delay(heartbeatIntervalMs)
            }
        }
    }

    private suspend fun sendHeartbeat() {
        val seq: JsonElement = lastSequence?.let { JsonPrimitive(it) } ?: JsonNull
        val json = buildJsonObject {
            put("op", JsonPrimitive(GatewayOpcode.HEARTBEAT))
            put("d", seq)
        }
        websocket?.sendText(json.toString())
    }

    private suspend fun sendIdentify() {
        val identify = IdentifyPayload(token = token)
        val json = buildJsonObject {
            put("op", JsonPrimitive(GatewayOpcode.IDENTIFY))
            put("d", InternalUtility.json.encodeToJsonElement(identify))
        }
        websocket?.sendText(json.toString())
    }

    private suspend fun sendResume() {
        val resume = ResumePayload(
            token = token,
            sessionId = sessionId!!,
            seq = lastSequence!!,
        )
        val json = buildJsonObject {
            put("op", JsonPrimitive(GatewayOpcode.RESUME))
            put("d", InternalUtility.json.encodeToJsonElement(resume))
        }
        websocket?.sendText(json.toString())
    }

    private fun closeSocketForReconnect() {
        isConnected = false
        heartbeatJob?.cancel()
        try {
            websocket?.close()
        } catch (e: Exception) {
            // ignore
        }
    }

    private fun scheduleReconnect(resume: Boolean) {
        if (isIntentionallyClosed) return
        reconnectJob?.cancel()
        reconnectJob = scope.launch {
            delay(currentReconnectDelay)
            currentReconnectDelay = minOf(currentReconnectDelay * 2, MAX_RECONNECT_DELAY_MS)
            if (isIntentionallyClosed) return@launch
            connect(resume)
        }
    }

    private inline fun <reified T> decodeData(element: JsonElement?): T? {
        if (element == null) return null
        return try {
            InternalUtility.json.decodeFromJsonElement<T>(element)
        } catch (e: Exception) {
            null
        }
    }

    private fun dispatchError(e: Exception) {
        listeners.forEach { it.onError(e) }
    }
}
