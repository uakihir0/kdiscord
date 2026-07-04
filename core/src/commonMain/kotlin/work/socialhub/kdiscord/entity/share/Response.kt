package work.socialhub.kdiscord.entity.share

import kotlin.js.JsExport

@JsExport
class Response<T>(
    var data: T
) {
    var limit: RateLimit? = null
    var json: String? = null
}
