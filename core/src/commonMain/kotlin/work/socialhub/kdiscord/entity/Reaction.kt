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

    /** Normal and burst reaction counts. */
    var countDetails: ReactionCountDetails? = null

    /** Whether the current user reacted using this emoji. */
    var me: Boolean? = null

    /** Colors used for burst reactions. */
    var burstColors: Array<String>? = null

    /** Number of burst reactions, when supplied by the client API. */
    var burstCount: Int? = null

    /** Whether the current user added a burst reaction, using the legacy client field. */
    var burstMe: Boolean? = null

    /** Whether the current user added a burst reaction. */
    var meBurst: Boolean? = null

    /** Emoji information. */
    var emoji: Emoji? = null
}

@JsExport
@Serializable
class ReactionCountDetails {
    /** Number of burst reactions. */
    var burst: Int? = null

    /** Number of normal reactions. */
    var normal: Int? = null
}
