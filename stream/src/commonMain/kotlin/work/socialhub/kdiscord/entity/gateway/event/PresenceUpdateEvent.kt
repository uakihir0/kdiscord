package work.socialhub.kdiscord.entity.gateway.event

import kotlinx.serialization.Serializable
import work.socialhub.kdiscord.entity.User
import kotlin.js.JsExport

/**
 * PRESENCE_UPDATE dispatch event data.
 * https://discord.com/developers/docs/events/gateway-events#presence-update
 */
@JsExport
@Serializable
class PresenceUpdateEvent {
    /** The user presence is being updated for. */
    var user: User? = null

    /** Id of the guild (snowflake). */
    var guildId: String? = null

    /** Either "idle", "dnd", "online", or "offline". */
    var status: String? = null
}
