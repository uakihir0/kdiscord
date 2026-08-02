package work.socialhub.kdiscord.entity

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Discord embed object.
 * https://discord.com/developers/docs/resources/message#embed-object
 */
@JsExport
@Serializable
class Embed {
    /** Title of embed. */
    var title: String? = null

    /** Type of embed (always "rich" for webhook embeds). */
    var type: String? = null

    /** Description of embed. */
    var description: String? = null

    /** Url of embed. */
    var url: String? = null

    /** ISO8601 timestamp of embed content. */
    var timestamp: String? = null

    /** Color code of the embed. */
    var color: Int? = null

    /** Footer information. */
    var footer: EmbedFooter? = null

    /** Image information. */
    var image: EmbedMedia? = null

    /** Thumbnail information. */
    var thumbnail: EmbedMedia? = null

    /** Video information. */
    var video: EmbedMedia? = null

    /** Provider information. */
    var provider: EmbedProvider? = null

    /** Author information. */
    var author: EmbedAuthor? = null

    /** Fields information. */
    var fields: Array<EmbedField>? = null

    /** Version of Discord's content scan for this embed. */
    var contentScanVersion: Int? = null
}

@JsExport
@Serializable
class EmbedFooter {
    var text: String? = null
    var iconUrl: String? = null
    var proxyIconUrl: String? = null
}

@JsExport
@Serializable
class EmbedMedia {
    var url: String? = null
    var proxyUrl: String? = null
    var height: Int? = null
    var width: Int? = null
    var contentType: String? = null
    var placeholder: String? = null
    var placeholderVersion: Int? = null
}

@JsExport
@Serializable
class EmbedProvider {
    var name: String? = null
    var url: String? = null
}

@JsExport
@Serializable
class EmbedAuthor {
    var name: String? = null
    var url: String? = null
    var iconUrl: String? = null
    var proxyIconUrl: String? = null
}

@JsExport
@Serializable
class EmbedField {
    var name: String? = null
    var value: String? = null
    var inline: Boolean? = null
}
