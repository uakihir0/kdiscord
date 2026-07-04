package work.socialhub.kdiscord

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

    companion object {
        /** Default Discord REST API endpoint. */
        const val DEFAULT_API_HOST: String = "https://discord.com/api/v10"
    }
}
