package work.socialhub.kdiscord.api.request.messages

import kotlin.js.JsExport

/**
 * Request for PATCH /channels/{channel.id}/messages/{message.id} (Edit Message).
 * https://discord.com/developers/docs/resources/message#edit-message
 */
@JsExport
class MessagesEditRequest(
    /** The channel id (snowflake) containing the message. */
    var channelId: String,
    /** The message id (snowflake) to edit. */
    var messageId: String,
) {
    /** New message contents (up to 2000 characters). */
    var content: String? = null
}
