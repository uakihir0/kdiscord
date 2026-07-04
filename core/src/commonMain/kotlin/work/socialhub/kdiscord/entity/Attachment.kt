package work.socialhub.kdiscord.entity

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Discord attachment object.
 * https://discord.com/developers/docs/resources/message#attachment-object
 */
@JsExport
@Serializable
class Attachment {
    /** Attachment id (snowflake). */
    var id: String? = null

    /** Name of file attached. */
    var filename: String? = null

    /** Description for the file. */
    var description: String? = null

    /** The attachment's media type. */
    var contentType: String? = null

    /** Size of file in bytes. */
    var size: Int? = null

    /** Source url of file. */
    var url: String? = null

    /** A proxied url of file. */
    var proxyUrl: String? = null

    /** Height of file (if image). */
    var height: Int? = null

    /** Width of file (if image). */
    var width: Int? = null

    /** Whether this attachment is ephemeral. */
    var ephemeral: Boolean? = null

    /** The duration of the audio file (for voice messages). */
    var durationSecs: Double? = null

    /** Base64 encoded bytearray representing a sampled waveform (for voice messages). */
    var waveform: String? = null
}
