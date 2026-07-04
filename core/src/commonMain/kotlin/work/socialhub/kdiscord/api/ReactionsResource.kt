package work.socialhub.kdiscord.api

import work.socialhub.kdiscord.entity.User
import work.socialhub.kdiscord.entity.share.Response
import work.socialhub.kdiscord.entity.share.ResponseUnit
import kotlin.js.JsExport

/**
 * Reactions resource.
 * https://discord.com/developers/docs/resources/message#reactions
 *
 * The `emoji` parameter is either a unicode emoji (e.g. "👍") or a custom emoji
 * in the form `name:id` (e.g. "mmLol:216154654256398347"). It is URL-encoded
 * for the request path by the implementation.
 */
@JsExport
interface ReactionsResource {

    /**
     * Create Reaction
     * Add a reaction by the current user to a message.
     */
    suspend fun createReaction(channelId: String, messageId: String, emoji: String): ResponseUnit

    @JsExport.Ignore
    fun createReactionBlocking(channelId: String, messageId: String, emoji: String): ResponseUnit

    /**
     * Delete Own Reaction
     * Remove the current user's reaction from a message.
     */
    suspend fun deleteOwnReaction(channelId: String, messageId: String, emoji: String): ResponseUnit

    @JsExport.Ignore
    fun deleteOwnReactionBlocking(channelId: String, messageId: String, emoji: String): ResponseUnit

    /**
     * Get Reactions
     * Get a list of users that reacted with the given emoji.
     */
    suspend fun getReactions(channelId: String, messageId: String, emoji: String): Response<Array<User>>

    @JsExport.Ignore
    fun getReactionsBlocking(channelId: String, messageId: String, emoji: String): Response<Array<User>>

    /**
     * Delete All Reactions
     * Remove all reactions from a message.
     */
    suspend fun deleteAllReactions(channelId: String, messageId: String): ResponseUnit

    @JsExport.Ignore
    fun deleteAllReactionsBlocking(channelId: String, messageId: String): ResponseUnit
}
