package work.socialhub.kdiscord.internal.api

import work.socialhub.khttpclient.HttpRequest
import work.socialhub.kdiscord.api.UsersResource
import work.socialhub.kdiscord.entity.User
import work.socialhub.kdiscord.entity.share.Response
import work.socialhub.kdiscord.util.MediaType
import work.socialhub.kdiscord.util.toBlocking

class UsersResourceImpl(
    uri: String,
    token: String,
) : AbstractResourceImpl(uri, token), UsersResource {

    override suspend fun getMe(): Response<User> {
        return proceed {
            HttpRequest()
                .url("$uri/users/@me")
                .auth()
                .accept(MediaType.JSON)
                .get()
        }
    }

    override fun getMeBlocking(): Response<User> =
        toBlocking { getMe() }

    override suspend fun getUser(userId: String): Response<User> {
        return proceed {
            HttpRequest()
                .url("$uri/users/$userId")
                .auth()
                .accept(MediaType.JSON)
                .get()
        }
    }

    override fun getUserBlocking(userId: String): Response<User> =
        toBlocking { getUser(userId) }
}
