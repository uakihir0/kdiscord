package work.socialhub.kdiscord.api.response.gateway

import kotlinx.serialization.Serializable
import kotlin.js.JsExport

/**
 * Response of GET /gateway.
 * https://discord.com/developers/docs/events/gateway#get-gateway
 */
@JsExport
@Serializable
class GatewayResponse {
    /** The WSS URL that can be used for connecting to the Gateway. */
    var url: String? = null
}
