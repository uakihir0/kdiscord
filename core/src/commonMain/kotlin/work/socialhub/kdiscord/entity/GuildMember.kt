package work.socialhub.kdiscord.entity

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Discord guild member object.
 * https://discord.com/developers/docs/resources/guild#guild-member-object
 */
@JsExport
@Serializable
class GuildMember {
    /** The user this guild member represents. */
    var user: User? = null

    /** This user's guild nickname. */
    var nick: String? = null

    /** The member's guild avatar hash. */
    var avatar: String? = null

    /** Array of role object ids (snowflakes). */
    var roles: Array<String>? = null

    /** When the user joined the guild (ISO8601 timestamp). */
    var joinedAt: String? = null

    /** When the user started boosting the guild (ISO8601 timestamp). */
    var premiumSince: String? = null

    /** Whether the user is deafened in voice channels. */
    var deaf: Boolean? = null

    /** Whether the user is muted in voice channels. */
    var mute: Boolean? = null

    /** Guild member flags represented as a bit set. */
    var flags: Int? = null

    /** Whether the user has not yet passed the guild's Membership Screening. */
    var pending: Boolean? = null

    /** When the user's timeout will expire (ISO8601 timestamp). */
    var communicationDisabledUntil: String? = null
}
