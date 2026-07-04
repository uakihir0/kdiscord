package work.socialhub.kdiscord.apis

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import work.socialhub.kdiscord.AbstractTest
import work.socialhub.kdiscord.entity.Message
import work.socialhub.kdiscord.entity.gateway.ReadyData
import work.socialhub.kdiscord.stream.DiscordStreamFactory
import work.socialhub.kdiscord.stream.DiscordStreamListener
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Live Gateway test. Requires a valid DISCORD_USER_TOKEN.
 * Ignored by default; remove @Ignore to connect to the real Gateway.
 */
class StreamTest : AbstractTest() {

    @Test
    @Ignore
    fun testConnectAndReady() = runBlocking {
        val stream = DiscordStreamFactory.instance(USER_TOKEN!!, API_HOST)
        val ready = CompletableDeferred<Boolean>()

        stream.addEventListener(object : DiscordStreamListener {
            override fun onReady(data: ReadyData) {
                println("=== READY ===")
                println("  User    > ${data.user?.username}")
                println("  Session > ${data.sessionId}")
                if (!ready.isCompleted) ready.complete(true)
            }

            override fun onMessageCreate(message: Message) {
                println("MESSAGE_CREATE ${message.author?.username}: ${message.content}")
            }

            override fun onUnknownEvent(type: String, raw: String) {
                println("UNKNOWN EVENT $type")
            }

            override fun onError(error: Exception) {
                println("ERROR: $error")
            }
        })

        launch { stream.start() }
        withTimeout(30_000) { assertTrue(ready.await()) }
        stream.stop()
    }
}
