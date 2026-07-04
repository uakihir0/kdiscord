package work.socialhub.kdiscord.apis

import kotlinx.coroutines.test.runTest
import work.socialhub.kdiscord.AbstractTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * Live REST smoke tests. Require DISCORD_USER_TOKEN and DISCORD_CHANNEL_ID.
 * Ignored by default; remove @Ignore to run against a real account.
 */
class ChannelsTest : AbstractTest() {

    @Test
    @Ignore
    fun testGetChannel() = runTest {
        val channelId = checkNotNull(CHANNEL_ID) { "DISCORD_CHANNEL_ID is not set" }
        val response = discord().channels().getChannel(channelId)
        println(response.json)
        assertNotNull(response.data.id)
    }

    @Test
    @Ignore
    fun testListDmChannels() = runTest {
        val response = discord().channels().listDmChannels()
        println("=== DM Channels ===")
        response.data.forEach { println("  ${it.id} > ${it.name ?: it.recipients?.firstOrNull()?.username}") }
    }
}
