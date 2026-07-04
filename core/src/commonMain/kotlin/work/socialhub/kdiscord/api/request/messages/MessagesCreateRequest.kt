package work.socialhub.kdiscord.api.request.messages

import kotlin.js.JsExport

/**
 * Request for POST /channels/{channel.id}/messages (Create Message).
 * https://discord.com/developers/docs/resources/message#create-message
 */
@JsExport
class MessagesCreateRequest(
    /** The channel id (snowflake) to post the message to. */
    var channelId: String,
) {
    /** Message contents (up to 2000 characters). */
    var content: String? = null

    /** True if this is a TTS message. */
    var tts: Boolean? = null

    /** Message id (snowflake) to reply to. */
    var replyMessageId: String? = null
}
