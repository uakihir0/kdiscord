package work.socialhub.kdiscord.entity

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonEncoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.decodeFromJsonElement
import kotlinx.serialization.json.encodeToJsonElement
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.put
import kotlin.js.JsExport

/**
 * A Discord message or modal component.
 * https://discord.com/developers/docs/components/reference
 */
@JsExport
@Serializable(with = MessageComponentSerializer::class)
interface MessageComponent {
    var type: Int?
    var id: Int?
}

@JsExport
@Serializable
class ActionRowComponent : MessageComponent {
    override var type: Int? = 1
    override var id: Int? = null
    var components: Array<MessageComponent>? = null
}

@JsExport
@Serializable
class ButtonComponent : MessageComponent {
    override var type: Int? = 2
    override var id: Int? = null
    var style: Int? = null
    var label: String? = null
    var emoji: Emoji? = null
    var customId: String? = null
    var skuId: String? = null
    var url: String? = null
    var disabled: Boolean? = null
}

@JsExport
@Serializable
class StringSelectComponent : MessageComponent {
    override var type: Int? = 3
    override var id: Int? = null
    var customId: String? = null
    var options: Array<SelectOption>? = null
    var placeholder: String? = null
    var minValues: Int? = null
    var maxValues: Int? = null
    var required: Boolean? = null
    var disabled: Boolean? = null
    var values: Array<String>? = null
}

@JsExport
@Serializable
class TextInputComponent : MessageComponent {
    override var type: Int? = 4
    override var id: Int? = null
    var customId: String? = null
    var style: Int? = null
    var label: String? = null
    var minLength: Int? = null
    var maxLength: Int? = null
    var required: Boolean? = null
    var value: String? = null
    var placeholder: String? = null
}

@JsExport
@Serializable
class UserSelectComponent : MessageComponent {
    override var type: Int? = 5
    override var id: Int? = null
    var customId: String? = null
    var placeholder: String? = null
    var defaultValues: Array<SelectDefaultValue>? = null
    var minValues: Int? = null
    var maxValues: Int? = null
    var required: Boolean? = null
    var disabled: Boolean? = null
    var values: Array<String>? = null
}

@JsExport
@Serializable
class RoleSelectComponent : MessageComponent {
    override var type: Int? = 6
    override var id: Int? = null
    var customId: String? = null
    var placeholder: String? = null
    var defaultValues: Array<SelectDefaultValue>? = null
    var minValues: Int? = null
    var maxValues: Int? = null
    var required: Boolean? = null
    var disabled: Boolean? = null
    var values: Array<String>? = null
}

@JsExport
@Serializable
class MentionableSelectComponent : MessageComponent {
    override var type: Int? = 7
    override var id: Int? = null
    var customId: String? = null
    var placeholder: String? = null
    var defaultValues: Array<SelectDefaultValue>? = null
    var minValues: Int? = null
    var maxValues: Int? = null
    var required: Boolean? = null
    var disabled: Boolean? = null
    var values: Array<String>? = null
}

@JsExport
@Serializable
class ChannelSelectComponent : MessageComponent {
    override var type: Int? = 8
    override var id: Int? = null
    var customId: String? = null
    var channelTypes: Array<Int>? = null
    var placeholder: String? = null
    var defaultValues: Array<SelectDefaultValue>? = null
    var minValues: Int? = null
    var maxValues: Int? = null
    var required: Boolean? = null
    var disabled: Boolean? = null
    var values: Array<String>? = null
}

@JsExport
@Serializable
class SectionComponent : MessageComponent {
    override var type: Int? = 9
    override var id: Int? = null
    var components: Array<MessageComponent>? = null
    var accessory: MessageComponent? = null
}

@JsExport
@Serializable
class TextDisplayComponent : MessageComponent {
    override var type: Int? = 10
    override var id: Int? = null
    var content: String? = null
}

@JsExport
@Serializable
class ThumbnailComponent : MessageComponent {
    override var type: Int? = 11
    override var id: Int? = null
    var media: UnfurledMediaItem? = null
    var description: String? = null
    var spoiler: Boolean? = null
}

@JsExport
@Serializable
class MediaGalleryComponent : MessageComponent {
    override var type: Int? = 12
    override var id: Int? = null
    var items: Array<MediaGalleryItem>? = null
}

@JsExport
@Serializable
class FileComponent : MessageComponent {
    override var type: Int? = 13
    override var id: Int? = null
    var file: UnfurledMediaItem? = null
    var spoiler: Boolean? = null
    var name: String? = null
    var size: Int? = null
}

@JsExport
@Serializable
class SeparatorComponent : MessageComponent {
    override var type: Int? = 14
    override var id: Int? = null
    var divider: Boolean? = null
    var spacing: Int? = null
}

@JsExport
@Serializable
class ContainerComponent : MessageComponent {
    override var type: Int? = 17
    override var id: Int? = null
    var components: Array<MessageComponent>? = null
    var accentColor: Int? = null
    var spoiler: Boolean? = null
}

@JsExport
@Serializable
class LabelComponent : MessageComponent {
    override var type: Int? = 18
    override var id: Int? = null
    var label: String? = null
    var description: String? = null
    var component: MessageComponent? = null
}

@JsExport
@Serializable
class FileUploadComponent : MessageComponent {
    override var type: Int? = 19
    override var id: Int? = null
    var customId: String? = null
    var minValues: Int? = null
    var maxValues: Int? = null
    var required: Boolean? = null
    var values: Array<String>? = null
}

@JsExport
@Serializable
class RadioGroupComponent : MessageComponent {
    override var type: Int? = 21
    override var id: Int? = null
    var customId: String? = null
    var options: Array<ComponentChoice>? = null
    var required: Boolean? = null
    var value: String? = null
}

@JsExport
@Serializable
class CheckboxGroupComponent : MessageComponent {
    override var type: Int? = 22
    override var id: Int? = null
    var customId: String? = null
    var options: Array<ComponentChoice>? = null
    var minValues: Int? = null
    var maxValues: Int? = null
    var required: Boolean? = null
    var values: Array<String>? = null
}

@JsExport
@Serializable
class CheckboxComponent : MessageComponent {
    override var type: Int? = 23
    override var id: Int? = null
    var customId: String? = null
    var default: Boolean? = null
    var value: Boolean? = null
}

/**
 * A component type unknown to this library. [rawJson] retains the complete
 * object so a future Discord component does not break its containing message.
 */
@JsExport
@Serializable
class UnknownMessageComponent : MessageComponent {
    override var type: Int? = null
    override var id: Int? = null
    var rawJson: String? = null
}

@JsExport
@Serializable
class SelectOption {
    var label: String? = null
    var value: String? = null
    var description: String? = null
    var emoji: Emoji? = null
    var default: Boolean? = null
}

@JsExport
@Serializable
class SelectDefaultValue {
    var id: String? = null
    var type: String? = null
}

@JsExport
@Serializable
class ComponentChoice {
    var value: String? = null
    var label: String? = null
    var description: String? = null
    var default: Boolean? = null
}

@JsExport
@Serializable
class MediaGalleryItem {
    var media: UnfurledMediaItem? = null
    var description: String? = null
    var spoiler: Boolean? = null
}

@JsExport
@Serializable
class UnfurledMediaItem {
    var url: String? = null
    var proxyUrl: String? = null
    var height: Int? = null
    var width: Int? = null
    var placeholder: String? = null
    var placeholderVersion: Int? = null
    var contentType: String? = null
    var flags: Int? = null
    var attachmentId: String? = null
}

internal object MessageComponentSerializer : KSerializer<MessageComponent> {
    override val descriptor: SerialDescriptor =
        buildClassSerialDescriptor("work.socialhub.kdiscord.entity.MessageComponent")

    override fun deserialize(decoder: Decoder): MessageComponent {
        val jsonDecoder = decoder as? JsonDecoder
            ?: error("MessageComponent can only be decoded from JSON")
        val element = jsonDecoder.decodeJsonElement()
        val jsonObject = element.jsonObject

        return when (jsonObject["type"]?.jsonPrimitive?.intOrNull) {
            1 -> jsonDecoder.json.decodeFromJsonElement<ActionRowComponent>(element)
            2 -> jsonDecoder.json.decodeFromJsonElement<ButtonComponent>(element)
            3 -> jsonDecoder.json.decodeFromJsonElement<StringSelectComponent>(element)
            4 -> jsonDecoder.json.decodeFromJsonElement<TextInputComponent>(element)
            5 -> jsonDecoder.json.decodeFromJsonElement<UserSelectComponent>(element)
            6 -> jsonDecoder.json.decodeFromJsonElement<RoleSelectComponent>(element)
            7 -> jsonDecoder.json.decodeFromJsonElement<MentionableSelectComponent>(element)
            8 -> jsonDecoder.json.decodeFromJsonElement<ChannelSelectComponent>(element)
            9 -> jsonDecoder.json.decodeFromJsonElement<SectionComponent>(element)
            10 -> jsonDecoder.json.decodeFromJsonElement<TextDisplayComponent>(element)
            11 -> jsonDecoder.json.decodeFromJsonElement<ThumbnailComponent>(element)
            12 -> jsonDecoder.json.decodeFromJsonElement<MediaGalleryComponent>(element)
            13 -> jsonDecoder.json.decodeFromJsonElement<FileComponent>(element)
            14 -> jsonDecoder.json.decodeFromJsonElement<SeparatorComponent>(element)
            17 -> jsonDecoder.json.decodeFromJsonElement<ContainerComponent>(element)
            18 -> jsonDecoder.json.decodeFromJsonElement<LabelComponent>(element)
            19 -> jsonDecoder.json.decodeFromJsonElement<FileUploadComponent>(element)
            21 -> jsonDecoder.json.decodeFromJsonElement<RadioGroupComponent>(element)
            22 -> jsonDecoder.json.decodeFromJsonElement<CheckboxGroupComponent>(element)
            23 -> jsonDecoder.json.decodeFromJsonElement<CheckboxComponent>(element)
            else -> unknownComponent(jsonObject)
        }
    }

    override fun serialize(encoder: Encoder, value: MessageComponent) {
        val jsonEncoder = encoder as? JsonEncoder
            ?: error("MessageComponent can only be encoded to JSON")
        val element = when (value) {
            is ActionRowComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is ButtonComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is StringSelectComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is TextInputComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is UserSelectComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is RoleSelectComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is MentionableSelectComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is ChannelSelectComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is SectionComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is TextDisplayComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is ThumbnailComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is MediaGalleryComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is FileComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is SeparatorComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is ContainerComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is LabelComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is FileUploadComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is RadioGroupComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is CheckboxGroupComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is CheckboxComponent -> jsonEncoder.json.encodeToJsonElement(value)
            is UnknownMessageComponent -> unknownJson(jsonEncoder, value)
            else -> error("Unsupported MessageComponent implementation: ${value::class}")
        }
        jsonEncoder.encodeJsonElement(element)
    }

    private fun unknownComponent(jsonObject: JsonObject): UnknownMessageComponent {
        return UnknownMessageComponent().also {
            it.type = jsonObject["type"]?.jsonPrimitive?.intOrNull
            it.id = jsonObject["id"]?.jsonPrimitive?.intOrNull
            it.rawJson = jsonObject.toString()
        }
    }

    private fun unknownJson(
        jsonEncoder: JsonEncoder,
        component: UnknownMessageComponent,
    ): JsonObject {
        val raw = component.rawJson?.let {
            runCatching { jsonEncoder.json.parseToJsonElement(it).jsonObject }.getOrNull()
        }
        if (raw != null) {
            return raw
        }

        return buildJsonObject {
            component.type?.let { put("type", it) }
            component.id?.let { put("id", it) }
        }
    }
}
