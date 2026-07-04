package work.socialhub.kdiscord.entity.share

import kotlin.js.JsExport

/**
 * A file to upload as a message attachment.
 *
 * The [filename] extension is used by khttpclient to infer the content type.
 */
@JsExport
class FileContent(
    /** The file name (including extension, e.g. "image.png"). */
    var filename: String,
    /** The raw file bytes. */
    var bytes: ByteArray,
) {
    /** Optional description (alt text) for the attachment. */
    var description: String? = null
}
