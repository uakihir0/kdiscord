package work.socialhub.kdiscord.entity.gateway

import kotlinx.serialization.Serializable

/**
 * Data of the HELLO (op 10) payload.
 * https://discord.com/developers/docs/events/gateway-events#hello
 */
@Serializable
class HelloData {
    /** Interval (in milliseconds) an app should heartbeat with. */
    var heartbeatInterval: Long = 0
}
