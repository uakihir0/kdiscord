package work.socialhub.kdiscord.internal.api

import io.ktor.http.encodeURLPathPart
import work.socialhub.khttpclient.HttpRequest
import work.socialhub.kdiscord.api.ReactionsResource
import work.socialhub.kdiscord.entity.User
import work.socialhub.kdiscord.entity.share.Response
import work.socialhub.kdiscord.entity.share.ResponseUnit
import work.socialhub.kdiscord.util.MediaType
import work.socialhub.kdiscord.util.toBlocking

class ReactionsResourceImpl(
    uri: String,
    token: String,
) : AbstractResourceImpl(uri, token), ReactionsResource {

    override suspend fun createReaction(
        channelId: String,
        messageId: String,
        emoji: String,
    ): ResponseUnit {
        return proceedUnit {
            HttpRequest()
                .url("$uri/channels/$channelId/messages/$messageId/reactions/${emoji.encodeURLPathPart()}/@me")
                .auth()
                .accept(MediaType.JSON)
                .put()
        }
    }

    override fun createReactionBlocking(
        channelId: String,
        messageId: String,
        emoji: String,
    ): ResponseUnit = toBlocking { createReaction(channelId, messageId, emoji) }

    override suspend fun deleteOwnReaction(
        channelId: String,
        messageId: String,
        emoji: String,
    ): ResponseUnit {
        return proceedUnit {
            HttpRequest()
                .url("$uri/channels/$channelId/messages/$messageId/reactions/${emoji.encodeURLPathPart()}/@me")
                .auth()
                .accept(MediaType.JSON)
                .delete()
        }
    }

    override fun deleteOwnReactionBlocking(
        channelId: String,
        messageId: String,
        emoji: String,
    ): ResponseUnit = toBlocking { deleteOwnReaction(channelId, messageId, emoji) }

    override suspend fun getReactions(
        channelId: String,
        messageId: String,
        emoji: String,
    ): Response<Array<User>> {
        return proceed {
            HttpRequest()
                .url("$uri/channels/$channelId/messages/$messageId/reactions/${emoji.encodeURLPathPart()}")
                .auth()
                .accept(MediaType.JSON)
                .get()
        }
    }

    override fun getReactionsBlocking(
        channelId: String,
        messageId: String,
        emoji: String,
    ): Response<Array<User>> = toBlocking { getReactions(channelId, messageId, emoji) }

    override suspend fun deleteAllReactions(
        channelId: String,
        messageId: String,
    ): ResponseUnit {
        return proceedUnit {
            HttpRequest()
                .url("$uri/channels/$channelId/messages/$messageId/reactions")
                .auth()
                .accept(MediaType.JSON)
                .delete()
        }
    }

    override fun deleteAllReactionsBlocking(
        channelId: String,
        messageId: String,
    ): ResponseUnit = toBlocking { deleteAllReactions(channelId, messageId) }
}
