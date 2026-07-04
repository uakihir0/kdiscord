package work.socialhub.kdiscord.api

import work.socialhub.kdiscord.api.request.messages.MessagesCreateRequest
import work.socialhub.kdiscord.api.request.messages.MessagesEditRequest
import work.socialhub.kdiscord.api.request.messages.MessagesListRequest
import work.socialhub.kdiscord.entity.Message
import work.socialhub.kdiscord.entity.share.Response
import work.socialhub.kdiscord.entity.share.ResponseUnit
import kotlin.js.JsExport

/**
 * Messages resource.
 * https://discord.com/developers/docs/resources/message
 */
@JsExport
interface MessagesResource {

    /**
     * Get Channel Messages
     * Returns the messages for a channel.
     */
    suspend fun list(request: MessagesListRequest): Response<Array<Message>>

    @JsExport.Ignore
    fun listBlocking(request: MessagesListRequest): Response<Array<Message>>

    /**
     * Get Channel Message
     * Returns a specific message in the channel.
     */
    suspend fun get(channelId: String, messageId: String): Response<Message>

    @JsExport.Ignore
    fun getBlocking(channelId: String, messageId: String): Response<Message>

    /**
     * Create Message
     * Post a message to a guild text or DM channel.
     */
    suspend fun create(request: MessagesCreateRequest): Response<Message>

    @JsExport.Ignore
    fun createBlocking(request: MessagesCreateRequest): Response<Message>

    /**
     * Edit Message
     * Edit a previously sent message.
     */
    suspend fun edit(request: MessagesEditRequest): Response<Message>

    @JsExport.Ignore
    fun editBlocking(request: MessagesEditRequest): Response<Message>

    /**
     * Delete Message
     * Delete a message.
     */
    suspend fun delete(channelId: String, messageId: String): ResponseUnit

    @JsExport.Ignore
    fun deleteBlocking(channelId: String, messageId: String): ResponseUnit
}
