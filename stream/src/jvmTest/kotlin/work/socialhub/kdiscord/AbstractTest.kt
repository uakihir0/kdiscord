package work.socialhub.kdiscord

import kotlinx.serialization.json.Json
import java.io.File
import kotlin.test.BeforeTest

open class AbstractTest {

    companion object {
        var API_HOST: String = Discord.DEFAULT_API_HOST
        var USER_TOKEN: String? = null
    }

    protected val json = Json {
        ignoreUnknownKeys = true
    }

    @BeforeTest
    fun setupTest() {

        try {
            USER_TOKEN = System.getenv("DISCORD_USER_TOKEN")
                ?: System.getProperty("DISCORD_USER_TOKEN")
            System.getenv("DISCORD_API_HOST")?.let { API_HOST = it }
        } catch (_: Exception) {
        }

        if (USER_TOKEN == null) {
            try {
                readTestProps()?.get("discord")?.let {
                    USER_TOKEN = it["DISCORD_USER_TOKEN"]
                    it["DISCORD_API_HOST"]?.let { host -> if (host.isNotEmpty()) API_HOST = host }
                }
            } catch (_: Exception) {
            }
        }
    }

    private fun readTestProps(): Map<String, Map<String, String>>? {
        return try {
            val jsonStr = File("../secrets.json").readText()
            json.decodeFromString<Map<String, Map<String, String>>>(jsonStr)
        } catch (e: Exception) {
            null
        }
    }
}
