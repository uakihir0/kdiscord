package work.socialhub.kdiscord

import kotlin.js.JsExport

/**
 * Discord API client.
 * Discord API クライアント。
 */
@JsExport
interface Discord {

    /** REST API host (e.g. https://discord.com/api/v10). */
    val apiHost: String

    /** User token used for authentication. */
    val token: String
}
