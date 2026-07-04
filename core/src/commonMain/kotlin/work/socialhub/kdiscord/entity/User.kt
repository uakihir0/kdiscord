package work.socialhub.kdiscord.entity

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Discord user object.
 * https://discord.com/developers/docs/resources/user#user-object
 */
@JsExport
@Serializable
class User {
    /** The user's id (snowflake). */
    var id: String? = null

    /** The user's username, not unique across the platform. */
    var username: String? = null

    /** The user's Discord-tag (legacy 4-digit discriminator, "0" if migrated). */
    var discriminator: String? = null

    /** The user's display name, if set. */
    var globalName: String? = null

    /** The user's avatar hash. */
    var avatar: String? = null

    /** Whether the user belongs to an OAuth2 application (bot account). */
    var bot: Boolean? = null

    /** Whether the user is an Official Discord System user. */
    var system: Boolean? = null

    /** Whether the user has two factor enabled on their account. */
    var mfaEnabled: Boolean? = null

    /** The user's banner hash. */
    var banner: String? = null

    /** The user's banner color encoded as an integer. */
    var accentColor: Int? = null

    /** The user's chosen language option. */
    var locale: String? = null

    /** Whether the email on this account has been verified. */
    var verified: Boolean? = null

    /** The user's email. */
    var email: String? = null

    /** The flags on a user's account. */
    var flags: Int? = null

    /** The type of Nitro subscription on a user's account. */
    var premiumType: Int? = null

    /** The public flags on a user's account. */
    var publicFlags: Int? = null
}
