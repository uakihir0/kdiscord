package work.socialhub.kdiscord.apis

import kotlinx.coroutines.runBlocking
import work.socialhub.kdiscord.AbstractTest
import work.socialhub.kdiscord.api.request.messages.MessagesCreateRequest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * Live reaction tests. Require DISCORD_USER_TOKEN and DISCORD_CHANNEL_ID.
 * Ignored by default; remove @Ignore to run against a real account.
 */
class ReactionsTest : AbstractTest() {

    @Test
    @Ignore
    fun testAddAndRemoveReaction() = runBlocking {
        val channelId = checkNotNull(CHANNEL_ID) { "DISCORD_CHANNEL_ID is not set" }
        val discord = discord()

        // Post a message to react to.
        val message = discord.messages().create(
            MessagesCreateRequest(channelId).also { it.content = "reaction test" }
        )
        val messageId = message.data.id
        assertNotNull(messageId)

        // Add a unicode reaction, then remove it.
        val emoji = "👍" // 👍
        discord.reactions().createReaction(channelId, messageId, emoji)
        println("=== Added reaction $emoji to $messageId ===")

        val reactors = discord.reactions().getReactions(channelId, messageId, emoji)
        println("  reactors: ${reactors.data.map { it.username }}")

        discord.reactions().deleteOwnReaction(channelId, messageId, emoji)
        println("=== Removed reaction ===")
    }
}
