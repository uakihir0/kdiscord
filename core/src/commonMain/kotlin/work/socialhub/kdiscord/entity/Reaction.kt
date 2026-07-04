package work.socialhub.kdiscord.entity

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Discord reaction object.
 * https://discord.com/developers/docs/resources/message#reaction-object
 */
@JsExport
@Serializable
class Reaction {
    /** Total number of times this emoji has been used to react. */
    var count: Int? = null

    /** Whether the current user reacted using this emoji. */
    var me: Boolean? = null

    /** Emoji information. */
    var emoji: Emoji? = null
}
