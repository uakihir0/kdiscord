package work.socialhub.kdiscord.apis

import kotlinx.coroutines.test.runTest
import work.socialhub.kdiscord.AbstractTest
import work.socialhub.kdiscord.api.request.guilds.GuildsListRequest
import kotlin.test.Ignore
import kotlin.test.Test

/**
 * Live REST smoke tests. Require a valid DISCORD_USER_TOKEN.
 * Ignored by default; remove @Ignore to run against a real account.
 */
class GuildsTest : AbstractTest() {

    @Test
    @Ignore
    fun testGetCurrentUserGuilds() = runTest {
        val response = discord().guilds().getCurrentUserGuilds(GuildsListRequest())
        println("=== Current User Guilds ===")
        response.data.forEach { println("  ${it.id} > ${it.name}") }
    }
}
