package work.socialhub.kdiscord.entity.gateway

import kotlinx.serialization.Serializable

/**
 * Data of the RESUME (op 6) payload.
 * https://discord.com/developers/docs/events/gateway-events#resume
 */
@Serializable
class ResumePayload(
    /** Session token (raw user token). */
    var token: String,

    /** Session id to resume. */
    var sessionId: String,

    /** Last sequence number received. */
    var seq: Int,
)
