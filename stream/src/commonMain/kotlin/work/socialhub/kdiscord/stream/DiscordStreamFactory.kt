package work.socialhub.kdiscord.stream

import work.socialhub.kdiscord.Discord
import work.socialhub.kdiscord.stream.internal.GatewayClient
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
object DiscordStreamFactory {

    /**
     * Create a Discord Gateway client with a user token.
     */
    fun instance(
        token: String,
    ): DiscordStream {
        return GatewayClient(Discord.DEFAULT_API_HOST, token)
    }

    /**
     * Create a Discord Gateway client with a user token and a custom API host.
     */
    @JsName("instanceWithApiHost")
    fun instance(
        token: String,
        apiHost: String,
    ): DiscordStream {
        return GatewayClient(apiHost, token)
    }
}
