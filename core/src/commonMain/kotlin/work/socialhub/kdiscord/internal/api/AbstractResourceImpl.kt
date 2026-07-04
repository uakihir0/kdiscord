package work.socialhub.kdiscord.internal.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import work.socialhub.khttpclient.HttpRequest
import work.socialhub.khttpclient.HttpResponse
import work.socialhub.kdiscord.DiscordException
import work.socialhub.kdiscord.entity.share.RateLimit
import work.socialhub.kdiscord.entity.share.Response
import work.socialhub.kdiscord.entity.share.ResponseUnit
import work.socialhub.kdiscord.internal.InternalUtility
import work.socialhub.kdiscord.internal.InternalUtility.fromJson
import work.socialhub.kdiscord.util.Headers

abstract class AbstractResourceImpl(
    val uri: String,
    val token: String,
) {

    companion object {
        /**
         * Discord requires a descriptive User-Agent header for all requests.
         * https://discord.com/developers/docs/reference#user-agent
         */
        const val USER_AGENT = "kdiscord (https://github.com/uakihir0/kdiscord, 0.0.1)"
    }

    /**
     * Inject authentication and required headers.
     *
     * NOTE: This is the single place where the token is attached to a request.
     * kdiscord uses a raw user token (no prefix). To support bot tokens later,
     * change the value here to "Bot $token".
     */
    fun HttpRequest.auth(): HttpRequest {
        return this
            .header(Headers.AUTHORIZATION, token)
            .header(Headers.USER_AGENT, USER_AGENT)
    }

    protected suspend inline fun <reified T> proceed(
        crossinline function: suspend () -> HttpResponse
    ): Response<T> {
        try {
            val response = withContext(Dispatchers.Default) { function() }
            if (response.status in 200..299) {
                return Response(fromJson<T>(response.stringBody))
                    .also {
                        it.limit = RateLimit.of(response)
                        it.json = response.stringBody
                    }
            }
            throw handleError(response)
        } catch (e: Exception) {
            throw e as? DiscordException
                ?: DiscordException(e)
        }
    }

    protected suspend inline fun proceedUnit(
        crossinline function: suspend () -> HttpResponse
    ): ResponseUnit {
        try {
            val response = withContext(Dispatchers.Default) { function() }
            if (response.status in 200..299) {
                return ResponseUnit().also {
                    it.limit = RateLimit.of(response)
                }
            }
            throw handleError(response)
        } catch (e: Exception) {
            throw e as? DiscordException
                ?: DiscordException(e)
        }
    }

    /** Build a [DiscordException] from a failed response, parsing 429 retry_after. */
    fun handleError(response: HttpResponse): DiscordException {
        val exception = DiscordException(response.status, response.stringBody)
        if (response.status == 429) {
            try {
                val body = InternalUtility.json
                    .parseToJsonElement(response.stringBody).jsonObject
                exception.retryAfter = body["retry_after"]?.jsonPrimitive?.doubleOrNull
            } catch (e: Exception) {
                // ignore parse failures; retryAfter stays null
            }
        }
        return exception
    }

    // conditional query parameter (skips null)
    fun HttpRequest.qwn(
        key: String,
        value: Any?,
    ): HttpRequest {
        if (value != null) query(key, value)
        return this
    }

    // conditional body form parameter (skips null)
    fun HttpRequest.pwn(
        key: String,
        value: Any?,
    ): HttpRequest {
        if (value != null) param(key, value)
        return this
    }
}
