package work.socialhub.kdiscord.entity.share

import work.socialhub.khttpclient.HttpResponse
import kotlin.js.JsExport
import kotlin.time.Instant

/**
 * Discord rate-limit information parsed from response headers.
 * See https://discord.com/developers/docs/topics/rate-limits
 */
@JsExport
class RateLimit {
    /** Number of requests that can be made (X-RateLimit-Limit). */
    var limit: Int = 0

    /** Number of remaining requests that can be made (X-RateLimit-Remaining). */
    var remaining: Int = 0

    /** Epoch time at which the rate limit resets (X-RateLimit-Reset). */
    @Suppress("NON_EXPORTABLE_TYPE")
    var reset: Instant? = null

    /** Bucket identifier for the encountered rate limit (X-RateLimit-Bucket). */
    var bucket: String? = null

    companion object {
        private const val X_RATELIMIT_LIMIT = "X-RateLimit-Limit"
        private const val X_RATELIMIT_REMAINING = "X-RateLimit-Remaining"
        private const val X_RATELIMIT_RESET = "X-RateLimit-Reset"
        private const val X_RATELIMIT_BUCKET = "X-RateLimit-Bucket"

        @Suppress("NON_EXPORTABLE_TYPE")
        fun of(response: HttpResponse): RateLimit? {

            try {
                val limit = response.headers[X_RATELIMIT_LIMIT]
                val remaining = response.headers[X_RATELIMIT_REMAINING]
                val reset = response.headers[X_RATELIMIT_RESET]
                val bucket = response.headers[X_RATELIMIT_BUCKET]

                return RateLimit().also {
                    if (limit?.isNotEmpty() == true) {
                        it.limit = limit[0].toInt()
                    }
                    if (remaining?.isNotEmpty() == true) {
                        it.remaining = remaining[0].toInt()
                    }
                    if (reset?.isNotEmpty() == true) {
                        // Discord sends the reset time as a floating point unix timestamp.
                        it.reset = Instant.fromEpochSeconds(reset[0].toDouble().toLong())
                    }
                    if (bucket?.isNotEmpty() == true) {
                        it.bucket = bucket[0]
                    }
                }
            } catch (e: Exception) {
                return null
            }
        }
    }
}
