package work.socialhub.kdiscord

import kotlinx.serialization.json.Json
import java.io.File
import kotlin.test.BeforeTest

open class AbstractTest {

    companion object {
        var API_HOST: String = Discord.DEFAULT_API_HOST
        var USER_TOKEN: String? = null
        var CHANNEL_ID: String? = null
        var GUILD_ID: String? = null
    }

    protected val json = Json {
        ignoreUnknownKeys = true
    }

    fun discord(): Discord {
        checkNotNull(USER_TOKEN) {
            "DISCORD_USER_TOKEN is not set. " +
                "Copy secrets.json.default to secrets.json and fill in the credentials, " +
                "or set the DISCORD_USER_TOKEN environment variable."
        }
        return DiscordFactory.instance(USER_TOKEN!!, API_HOST)
    }

    @BeforeTest
    fun setupTest() {

        try {
            // Get credentials from environment variables / system properties.
            USER_TOKEN = System.getenv("DISCORD_USER_TOKEN")
                ?: System.getProperty("DISCORD_USER_TOKEN")
            CHANNEL_ID = System.getenv("DISCORD_CHANNEL_ID")
                ?: System.getProperty("DISCORD_CHANNEL_ID")
            GUILD_ID = System.getenv("DISCORD_GUILD_ID")
                ?: System.getProperty("DISCORD_GUILD_ID")
            System.getenv("DISCORD_API_HOST")?.let { API_HOST = it }
        } catch (_: Exception) {
        }

        if (USER_TOKEN == null) {
            try {
                // Get credentials from secrets.json file.
                readTestProps()?.get("discord")?.let {
                    USER_TOKEN = it["DISCORD_USER_TOKEN"]
                    CHANNEL_ID = it["DISCORD_CHANNEL_ID"]
                    GUILD_ID = it["DISCORD_GUILD_ID"]
                    it["DISCORD_API_HOST"]?.let { host -> if (host.isNotEmpty()) API_HOST = host }
                }
            } catch (_: Exception) {
            }
        }
    }

    /**
     * Read Test Properties
     */
    private fun readTestProps(): Map<String, Map<String, String>>? {
        return try {
            val jsonStr = File("../secrets.json").readText()
            json.decodeFromString<Map<String, Map<String, String>>>(jsonStr)
        } catch (e: Exception) {
            null
        }
    }
}
