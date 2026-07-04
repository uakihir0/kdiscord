package work.socialhub.kdiscord.entity

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Discord guild object.
 * https://discord.com/developers/docs/resources/guild#guild-object
 */
@JsExport
@Serializable
class Guild {
    /** Guild id (snowflake). */
    var id: String? = null

    /** Guild name (2-100 characters). */
    var name: String? = null

    /** Icon hash. */
    var icon: String? = null

    /** Splash hash. */
    var splash: String? = null

    /** Discovery splash hash. */
    var discoverySplash: String? = null

    /** True if the current user is the owner of the guild. */
    var owner: Boolean? = null

    /** Id of owner (snowflake). */
    var ownerId: String? = null

    /** Total permissions for the current user in the guild (bit set string). */
    var permissions: String? = null

    /** Id of afk channel (snowflake). */
    var afkChannelId: String? = null

    /** Afk timeout in seconds. */
    var afkTimeout: Int? = null

    /** Verification level required for the guild. */
    var verificationLevel: Int? = null

    /** Roles in the guild. */
    var roles: Array<Role>? = null

    /** Custom guild emojis. */
    var emojis: Array<Emoji>? = null

    /** Enabled guild features. */
    var features: Array<String>? = null

    /** The description of a guild. */
    var description: String? = null

    /** Banner hash. */
    var banner: String? = null

    /** Premium tier (Server Boost level). */
    var premiumTier: Int? = null

    /** The number of boosts this guild currently has. */
    var premiumSubscriptionCount: Int? = null

    /** The preferred locale of a Community guild. */
    var preferredLocale: String? = null

    /** Approximate number of members in this guild. */
    var approximateMemberCount: Int? = null

    /** Approximate number of non-offline members in this guild. */
    var approximatePresenceCount: Int? = null
}
