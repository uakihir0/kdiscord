package work.socialhub.kdiscord

class DiscordException : Exception {
    var status: Int? = null
    var body: String? = null

    /** Seconds to wait before retrying (parsed from a 429 response), if present. */
    var retryAfter: Double? = null

    constructor(m: String) : super(m)
    constructor(e: Exception) : super(e)

    constructor(
        status: Int,
        body: String
    ) : super("status code: $status, body: $body") {
        this.status = status
        this.body = body
    }
}
