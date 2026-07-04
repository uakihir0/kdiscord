package work.socialhub.kdiscord.api

import work.socialhub.kdiscord.api.response.gateway.GatewayResponse
import work.socialhub.kdiscord.entity.share.Response
import kotlin.js.JsExport

/**
 * Gateway resource.
 * https://discord.com/developers/docs/events/gateway
 */
@JsExport
interface GatewayResource {

    /**
     * Get Gateway
     * Returns the WSS URL used to connect to the Gateway.
     */
    suspend fun getGateway(): Response<GatewayResponse>

    @JsExport.Ignore
    fun getGatewayBlocking(): Response<GatewayResponse>
}
