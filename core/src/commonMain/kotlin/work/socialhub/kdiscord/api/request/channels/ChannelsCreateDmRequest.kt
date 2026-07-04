package work.socialhub.kdiscord.api.request.channels

import kotlin.js.JsExport

/**
 * Request for POST /users/@me/channels (Create DM).
 * https://discord.com/developers/docs/resources/user#create-dm
 */
@JsExport
class ChannelsCreateDmRequest(
    /** The recipient to open a DM channel with (user id snowflake). */
    var recipientId: String,
)
