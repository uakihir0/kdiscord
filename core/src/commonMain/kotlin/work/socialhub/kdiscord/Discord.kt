package work.socialhub.kdiscord

import work.socialhub.kdiscord.api.ChannelsResource
import work.socialhub.kdiscord.api.GatewayResource
import work.socialhub.kdiscord.api.GuildsResource
import work.socialhub.kdiscord.api.MessagesResource
import work.socialhub.kdiscord.api.ReactionsResource
import work.socialhub.kdiscord.api.UsersResource
import kotlin.js.JsExport

/**
 * Discord API client.
 * Discord API クライアント。
 *
 * Obtain an instance via [DiscordFactory].
 */
@JsExport
interface Discord {

    /** REST API host (e.g. https://discord.com/api/v10). */
    val apiHost: String

    /** User token used for authentication. */
    val token: String

    /** Users resource. */
    fun users(): UsersResource

    /** Channels resource. */
    fun channels(): ChannelsResource

    /** Messages resource. */
    fun messages(): MessagesResource

    /** Reactions resource. */
    fun reactions(): ReactionsResource

    /** Guilds resource. */
    fun guilds(): GuildsResource

    /** Gateway resource. */
    fun gateway(): GatewayResource

    companion object {
        /** Default Discord REST API endpoint. */
        const val DEFAULT_API_HOST: String = "https://discord.com/api/v10"
    }
}
