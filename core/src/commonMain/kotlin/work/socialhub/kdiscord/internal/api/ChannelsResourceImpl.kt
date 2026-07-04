package work.socialhub.kdiscord.internal.api

import kotlinx.serialization.Serializable
import work.socialhub.khttpclient.HttpRequest
import work.socialhub.kdiscord.api.ChannelsResource
import work.socialhub.kdiscord.api.request.channels.ChannelsCreateDmRequest
import work.socialhub.kdiscord.entity.Channel
import work.socialhub.kdiscord.entity.share.Response
import work.socialhub.kdiscord.internal.InternalUtility.toJson
import work.socialhub.kdiscord.util.MediaType
import work.socialhub.kdiscord.util.toBlocking

class ChannelsResourceImpl(
    uri: String,
    token: String,
) : AbstractResourceImpl(uri, token), ChannelsResource {

    override suspend fun getChannel(channelId: String): Response<Channel> {
        return proceed {
            HttpRequest()
                .url("$uri/channels/$channelId")
                .auth()
                .accept(MediaType.JSON)
                .get()
        }
    }

    override fun getChannelBlocking(channelId: String): Response<Channel> =
        toBlocking { getChannel(channelId) }

    override suspend fun listDmChannels(): Response<Array<Channel>> {
        return proceed {
            HttpRequest()
                .url("$uri/users/@me/channels")
                .auth()
                .accept(MediaType.JSON)
                .get()
        }
    }

    override fun listDmChannelsBlocking(): Response<Array<Channel>> =
        toBlocking { listDmChannels() }

    override suspend fun createDm(request: ChannelsCreateDmRequest): Response<Channel> {
        return proceed {
            HttpRequest()
                .url("$uri/users/@me/channels")
                .auth()
                .accept(MediaType.JSON)
                .json(toJson(CreateDmBody(recipientId = request.recipientId)))
                .post()
        }
    }

    override fun createDmBlocking(request: ChannelsCreateDmRequest): Response<Channel> =
        toBlocking { createDm(request) }

    @Serializable
    private class CreateDmBody(
        val recipientId: String,
    )
}
