package work.socialhub.kdiscord.api

import work.socialhub.kdiscord.entity.User
import work.socialhub.kdiscord.entity.share.Response
import kotlin.js.JsExport

/**
 * Users resource.
 * https://discord.com/developers/docs/resources/user
 */
@JsExport
interface UsersResource {

    /**
     * Get Current User
     * Returns the user object of the requester's account.
     */
    suspend fun getMe(): Response<User>

    @JsExport.Ignore
    fun getMeBlocking(): Response<User>

    /**
     * Get User
     * Returns a user object for a given user id.
     */
    suspend fun getUser(userId: String): Response<User>

    @JsExport.Ignore
    fun getUserBlocking(userId: String): Response<User>
}
