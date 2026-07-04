package work.socialhub.kdiscord.internal.api

import kotlinx.serialization.Serializable
import work.socialhub.khttpclient.HttpRequest
import work.socialhub.kdiscord.api.MessagesResource
import work.socialhub.kdiscord.api.request.messages.MessagesCreateRequest
import work.socialhub.kdiscord.api.request.messages.MessagesEditRequest
import work.socialhub.kdiscord.api.request.messages.MessagesListRequest
import work.socialhub.kdiscord.entity.Message
import work.socialhub.kdiscord.entity.share.Response
import work.socialhub.kdiscord.entity.share.ResponseUnit
import work.socialhub.kdiscord.internal.InternalUtility.toJson
import work.socialhub.kdiscord.util.MediaType
import work.socialhub.kdiscord.util.toBlocking

class MessagesResourceImpl(
    uri: String,
    token: String,
) : AbstractResourceImpl(uri, token), MessagesResource {

    override suspend fun list(request: MessagesListRequest): Response<Array<Message>> {
        return proceed {
            HttpRequest()
                .url("$uri/channels/${request.channelId}/messages")
                .qwn("limit", request.limit)
                .qwn("around", request.around)
                .qwn("before", request.before)
                .qwn("after", request.after)
                .auth()
                .accept(MediaType.JSON)
                .get()
        }
    }

    override fun listBlocking(request: MessagesListRequest): Response<Array<Message>> =
        toBlocking { list(request) }

    override suspend fun get(channelId: String, messageId: String): Response<Message> {
        return proceed {
            HttpRequest()
                .url("$uri/channels/$channelId/messages/$messageId")
                .auth()
                .accept(MediaType.JSON)
                .get()
        }
    }

    override fun getBlocking(channelId: String, messageId: String): Response<Message> =
        toBlocking { get(channelId, messageId) }

    override suspend fun create(request: MessagesCreateRequest): Response<Message> {
        return proceed {
            val body = CreateMessageBody(
                content = request.content,
                tts = request.tts,
                messageReference = request.replyMessageId?.let {
                    MessageReferenceBody(messageId = it)
                },
            )
            HttpRequest()
                .url("$uri/channels/${request.channelId}/messages")
                .auth()
                .accept(MediaType.JSON)
                .json(toJson(body))
                .post()
        }
    }

    override fun createBlocking(request: MessagesCreateRequest): Response<Message> =
        toBlocking { create(request) }

    override suspend fun edit(request: MessagesEditRequest): Response<Message> {
        return proceed {
            HttpRequest()
                .url("$uri/channels/${request.channelId}/messages/${request.messageId}")
                .auth()
                .accept(MediaType.JSON)
                .json(toJson(EditMessageBody(content = request.content)))
                .patch()
        }
    }

    override fun editBlocking(request: MessagesEditRequest): Response<Message> =
        toBlocking { edit(request) }

    override suspend fun delete(channelId: String, messageId: String): ResponseUnit {
        return proceedUnit {
            HttpRequest()
                .url("$uri/channels/$channelId/messages/$messageId")
                .auth()
                .accept(MediaType.JSON)
                .delete()
        }
    }

    override fun deleteBlocking(channelId: String, messageId: String): ResponseUnit =
        toBlocking { delete(channelId, messageId) }

    @Serializable
    private class CreateMessageBody(
        val content: String?,
        val tts: Boolean?,
        val messageReference: MessageReferenceBody?,
    )

    @Serializable
    private class MessageReferenceBody(
        val messageId: String,
    )

    @Serializable
    private class EditMessageBody(
        val content: String?,
    )
}
