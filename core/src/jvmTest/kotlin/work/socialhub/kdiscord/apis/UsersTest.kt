package work.socialhub.kdiscord.apis

import kotlinx.coroutines.test.runTest
import work.socialhub.kdiscord.AbstractTest
import kotlin.test.Ignore
import kotlin.test.Test
import kotlin.test.assertNotNull

/**
 * Live REST smoke tests. Require a valid DISCORD_USER_TOKEN in secrets.json /
 * environment. Ignored by default so CI stays offline; remove @Ignore to run.
 */
class UsersTest : AbstractTest() {

    @Test
    @Ignore
    fun testGetMe() = runTest {
        val response = discord().users().getMe()
        println(response.json)
        assertNotNull(response.data.id)
        println("=== Current User ===")
        println("  ID       > ${response.data.id}")
        println("  Username > ${response.data.username}")
    }
}
