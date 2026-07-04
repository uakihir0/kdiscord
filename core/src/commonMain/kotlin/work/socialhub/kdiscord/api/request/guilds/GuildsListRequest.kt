package work.socialhub.kdiscord.api.request.guilds

import kotlin.js.JsExport

/**
 * Request for GET /users/@me/guilds (Get Current User Guilds).
 * https://discord.com/developers/docs/resources/user#get-current-user-guilds
 *
 * The before/after parameters are mutually exclusive.
 */
@JsExport
class GuildsListRequest {
    /** Get guilds before this guild id (snowflake). */
    var before: String? = null

    /** Get guilds after this guild id (snowflake). */
    var after: String? = null

    /** Max number of guilds to return (1-200, default 200). */
    var limit: Int? = null

    /** Include approximate member and presence counts in the response. */
    var withCounts: Boolean? = null
}
