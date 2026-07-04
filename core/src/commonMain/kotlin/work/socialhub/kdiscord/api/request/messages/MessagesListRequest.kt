package work.socialhub.kdiscord.api.request.messages

import kotlin.js.JsExport

/**
 * Request for GET /channels/{channel.id}/messages (Get Channel Messages).
 * https://discord.com/developers/docs/resources/message#get-channel-messages
 *
 * The before/after/around parameters are mutually exclusive.
 */
@JsExport
class MessagesListRequest(
    /** The channel id (snowflake) to fetch messages from. */
    var channelId: String,
) {
    /** Max number of messages to return (1-100, default 50). */
    var limit: Int? = null

    /** Get messages around this message id (snowflake). */
    var around: String? = null

    /** Get messages before this message id (snowflake). */
    var before: String? = null

    /** Get messages after this message id (snowflake). */
    var after: String? = null
}
