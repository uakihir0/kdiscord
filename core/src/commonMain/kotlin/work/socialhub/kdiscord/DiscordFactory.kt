package work.socialhub.kdiscord

import work.socialhub.kdiscord.internal.DiscordImpl
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
object DiscordFactory {

    /**
     * Create a Discord client instance with a user token.
     */
    fun instance(
        token: String,
    ): Discord {
        return DiscordImpl(Discord.DEFAULT_API_HOST, token)
    }

    /**
     * Create a Discord client instance with a user token and a custom API host.
     */
    @JsName("instanceWithApiHost")
    fun instance(
        token: String,
        apiHost: String,
    ): Discord {
        return DiscordImpl(apiHost, token)
    }
}
