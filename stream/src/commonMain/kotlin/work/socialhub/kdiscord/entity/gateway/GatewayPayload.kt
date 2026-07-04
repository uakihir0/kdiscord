package work.socialhub.kdiscord.entity.gateway

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Gateway payload envelope.
 * https://discord.com/developers/docs/events/gateway-events#payload-structure
 *
 * Field names are fixed by the protocol (op/d/s/t), so they are annotated
 * explicitly rather than relying on the snake_case naming strategy.
 */
@Serializable
class GatewayPayload {
    /** Gateway opcode, which indicates the payload type. */
    @SerialName("op")
    var op: Int = 0

    /** Event data. */
    @SerialName("d")
    var d: JsonElement? = null

    /** Sequence number of event used for resuming sessions and heartbeating. */
    @SerialName("s")
    var s: Int? = null

    /** Event name for this payload (only for DISPATCH). */
    @SerialName("t")
    var t: String? = null
}
