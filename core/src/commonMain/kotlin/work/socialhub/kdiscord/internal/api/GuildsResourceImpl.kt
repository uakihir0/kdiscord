package work.socialhub.kdiscord.internal.api

import work.socialhub.khttpclient.HttpRequest
import work.socialhub.kdiscord.api.GuildsResource
import work.socialhub.kdiscord.api.request.guilds.GuildsListRequest
import work.socialhub.kdiscord.entity.Channel
import work.socialhub.kdiscord.entity.Guild
import work.socialhub.kdiscord.entity.share.Response
import work.socialhub.kdiscord.util.MediaType
import work.socialhub.kdiscord.util.toBlocking

class GuildsResourceImpl(
    uri: String,
    token: String,
) : AbstractResourceImpl(uri, token), GuildsResource {

    override suspend fun getCurrentUserGuilds(request: GuildsListRequest): Response<Array<Guild>> {
        return proceed {
            HttpRequest()
                .url("$uri/users/@me/guilds")
                .qwn("before", request.before)
                .qwn("after", request.after)
                .qwn("limit", request.limit)
                .qwn("with_counts", request.withCounts)
                .auth()
                .accept(MediaType.JSON)
                .get()
        }
    }

    override fun getCurrentUserGuildsBlocking(request: GuildsListRequest): Response<Array<Guild>> =
        toBlocking { getCurrentUserGuilds(request) }

    override suspend fun getGuild(guildId: String): Response<Guild> {
        return proceed {
            HttpRequest()
                .url("$uri/guilds/$guildId")
                .auth()
                .accept(MediaType.JSON)
                .get()
        }
    }

    override fun getGuildBlocking(guildId: String): Response<Guild> =
        toBlocking { getGuild(guildId) }

    override suspend fun listGuildChannels(guildId: String): Response<Array<Channel>> {
        return proceed {
            HttpRequest()
                .url("$uri/guilds/$guildId/channels")
                .auth()
                .accept(MediaType.JSON)
                .get()
        }
    }

    override fun listGuildChannelsBlocking(guildId: String): Response<Array<Channel>> =
        toBlocking { listGuildChannels(guildId) }
}
