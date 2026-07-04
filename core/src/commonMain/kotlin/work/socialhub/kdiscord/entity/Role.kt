package work.socialhub.kdiscord.entity

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Discord role object.
 * https://discord.com/developers/docs/topics/permissions#role-object
 */
@JsExport
@Serializable
class Role {
    /** Role id (snowflake). */
    var id: String? = null

    /** Role name. */
    var name: String? = null

    /** Integer representation of hexadecimal color code. */
    var color: Int? = null

    /** Whether this role is pinned in the user listing. */
    var hoist: Boolean? = null

    /** Role icon hash. */
    var icon: String? = null

    /** Role unicode emoji. */
    var unicodeEmoji: String? = null

    /** Position of this role. */
    var position: Int? = null

    /** Permission bit set (string of a 64-bit integer). */
    var permissions: String? = null

    /** Whether this role is managed by an integration. */
    var managed: Boolean? = null

    /** Whether this role is mentionable. */
    var mentionable: Boolean? = null
}
