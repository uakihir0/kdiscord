package work.socialhub.kdiscord

import work.socialhub.kdiscord.stream.DiscordStream
import work.socialhub.kdiscord.stream.DiscordStreamFactory
import kotlin.js.JsExport
import kotlin.js.JsName

/**
 * Aggregate facade for the `all` distribution (CocoaPods / SwiftPackage / JS library).
 * Delegates to [DiscordFactory] and [DiscordStreamFactory].
 */
@JsExport
object KdiscordFactory {

    /** Create a Discord REST client with a user token. */
    fun instance(token: String): Discord {
        return DiscordFactory.instance(token)
    }

    /** Create a Discord REST client with a user token and a custom API host. */
    @JsName("instanceWithApiHost")
    fun instance(token: String, apiHost: String): Discord {
        return DiscordFactory.instance(token, apiHost)
    }

    /** Create a Discord Gateway (WebSocket) client with a user token. */
    fun streamInstance(token: String): DiscordStream {
        return DiscordStreamFactory.instance(token)
    }

    /** Create a Discord Gateway (WebSocket) client with a user token and a custom API host. */
    @JsName("streamInstanceWithApiHost")
    fun streamInstance(token: String, apiHost: String): DiscordStream {
        return DiscordStreamFactory.instance(token, apiHost)
    }
}
