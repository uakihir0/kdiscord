package work.socialhub.kdiscord.entity

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Discord channel object.
 * https://discord.com/developers/docs/resources/channel#channel-object
 */
@JsExport
@Serializable
class Channel {
    /** The id of this channel (snowflake). */
    var id: String? = null

    /** The type of channel. */
    var type: Int? = null

    /** The id of the guild (snowflake). */
    var guildId: String? = null

    /** Sorting position of the channel. */
    var position: Int? = null

    /** The name of the channel (1-100 characters). */
    var name: String? = null

    /** The channel topic (0-4096 characters). */
    var topic: String? = null

    /** Whether the channel is nsfw. */
    var nsfw: Boolean? = null

    /** The id of the last message sent in this channel (snowflake). */
    var lastMessageId: String? = null

    /** The bitrate (in bits) of the voice channel. */
    var bitrate: Int? = null

    /** The user limit of the voice channel. */
    var userLimit: Int? = null

    /** Amount of seconds a user has to wait before sending another message. */
    var rateLimitPerUser: Int? = null

    /** The recipients of the DM. */
    var recipients: Array<User>? = null

    /** Icon hash of the group DM. */
    var icon: String? = null

    /** Id of the creator of the group DM or thread (snowflake). */
    var ownerId: String? = null

    /** Id of the parent category / text channel (snowflake). */
    var parentId: String? = null

    /** When the last pinned message was pinned (ISO8601 timestamp). */
    var lastPinTimestamp: String? = null
}
