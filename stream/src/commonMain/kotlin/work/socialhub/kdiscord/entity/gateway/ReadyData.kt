package work.socialhub.kdiscord.entity.gateway

import kotlinx.serialization.Serializable
import work.socialhub.kdiscord.entity.User
import kotlin.js.JsExport

/**
 * Data of the READY dispatch event.
 * https://discord.com/developers/docs/events/gateway-events#ready
 */
@JsExport
@Serializable
class ReadyData {
    /** API version. */
    var v: Int? = null

    /** Information about the user including email. */
    var user: User? = null

    /** Used for resuming connections. */
    var sessionId: String? = null

    /** Gateway URL for resuming connections. */
    var resumeGatewayUrl: String? = null
}
