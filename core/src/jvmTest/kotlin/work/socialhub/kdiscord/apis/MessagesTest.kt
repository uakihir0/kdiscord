package work.socialhub.kdiscord.apis

import kotlinx.coroutines.test.runTest
import work.socialhub.kdiscord.AbstractTest
import work.socialhub.kdiscord.api.request.messages.MessagesListRequest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertTrue

/**
 * Live REST smoke tests. Require DISCORD_USER_TOKEN and DISCORD_CHANNEL_ID.
 * Ignored by default; remove @Ignore to run against a real account.
 */
class MessagesTest : AbstractTest() {

    @Test
    @Ignore
    fun testListMessages() = runTest {
        val channelId = checkNotNull(CHANNEL_ID) { "DISCORD_CHANNEL_ID is not set" }
        val messages = discord().messages()

        val response = messages.list(
            MessagesListRequest(channelId).also { it.limit = 5 }
        )
        println("=== Messages (first page) ===")
        response.data.forEach { println("  ${it.id} ${it.author?.username}: ${it.content}") }
        assertTrue(response.data.size <= 5)

        // Exercise pagination via the `before` cursor.
        response.data.lastOrNull()?.id?.let { before ->
            val older = messages.list(
                MessagesListRequest(channelId).also {
                    it.limit = 5
                    it.before = before
                }
            )
            println("=== Messages (before $before) ===")
            older.data.forEach { println("  ${it.id}: ${it.content}") }
        }
    }
}
