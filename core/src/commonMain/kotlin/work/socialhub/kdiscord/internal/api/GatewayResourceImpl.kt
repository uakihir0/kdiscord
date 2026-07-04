package work.socialhub.kdiscord.internal.api

import work.socialhub.khttpclient.HttpRequest
import work.socialhub.kdiscord.api.GatewayResource
import work.socialhub.kdiscord.api.response.gateway.GatewayResponse
import work.socialhub.kdiscord.entity.share.Response
import work.socialhub.kdiscord.util.MediaType
import work.socialhub.kdiscord.util.toBlocking

class GatewayResourceImpl(
    uri: String,
    token: String,
) : AbstractResourceImpl(uri, token), GatewayResource {

    override suspend fun getGateway(): Response<GatewayResponse> {
        return proceed {
            HttpRequest()
                .url("$uri/gateway")
                .auth()
                .accept(MediaType.JSON)
                .get()
        }
    }

    override fun getGatewayBlocking(): Response<GatewayResponse> =
        toBlocking { getGateway() }
}
