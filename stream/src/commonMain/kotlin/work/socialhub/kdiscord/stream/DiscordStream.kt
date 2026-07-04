package work.socialhub.kdiscord.stream

import kotlin.js.JsExport

/**
 * Discord Gateway (WebSocket) client.
 * Obtain an instance via [DiscordStreamFactory].
 */
@JsExport
interface DiscordStream {

    /** Register an event listener. */
    fun addEventListener(listener: DiscordStreamListener)

    /** Remove a previously registered event listener. */
    fun removeEventListener(listener: DiscordStreamListener)

    /**
     * Connect to the Gateway and start receiving events.
     * Suspends until the connection is established (the socket then runs in the background).
     */
    suspend fun start()

    /** Disconnect from the Gateway and stop receiving events. */
    fun stop()

    /** Whether the Gateway connection is currently established. */
    fun isConnected(): Boolean
}
