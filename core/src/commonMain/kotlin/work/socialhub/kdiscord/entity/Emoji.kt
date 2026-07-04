package work.socialhub.kdiscord.entity

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Discord emoji object.
 * https://discord.com/developers/docs/resources/emoji#emoji-object
 */
@JsExport
@Serializable
class Emoji {
    /** Emoji id (snowflake); null for standard unicode emoji. */
    var id: String? = null

    /** Emoji name; can be null only in reaction emoji objects. */
    var name: String? = null

    /** Whether this emoji is animated. */
    var animated: Boolean? = null

    /** Whether this emoji must be wrapped in colons. */
    var requireColons: Boolean? = null

    /** Whether this emoji is managed. */
    var managed: Boolean? = null

    /** Whether this emoji is available. */
    var available: Boolean? = null
}
