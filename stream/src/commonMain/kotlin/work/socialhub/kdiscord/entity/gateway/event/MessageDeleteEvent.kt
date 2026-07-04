package work.socialhub.kdiscord.entity.gateway.event

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * MESSAGE_DELETE dispatch event data.
 * https://discord.com/developers/docs/events/gateway-events#message-delete
 */
@JsExport
@Serializable
class MessageDeleteEvent {
    /** Id of the deleted message (snowflake). */
    var id: String? = null

    /** Id of the channel (snowflake). */
    var channelId: String? = null

    /** Id of the guild (snowflake). */
    var guildId: String? = null
}
