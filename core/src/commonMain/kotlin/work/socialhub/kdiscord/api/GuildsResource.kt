package work.socialhub.kdiscord.api

import work.socialhub.kdiscord.api.request.guilds.GuildsListRequest
import work.socialhub.kdiscord.entity.Channel
import work.socialhub.kdiscord.entity.Guild
import work.socialhub.kdiscord.entity.share.Response
import kotlin.js.JsExport

/**
 * Guilds resource.
 * https://discord.com/developers/docs/resources/guild
 */
@JsExport
interface GuildsResource {

    /**
     * Get Current User Guilds
     * Returns a list of partial guild objects the current user is a member of.
     */
    suspend fun getCurrentUserGuilds(request: GuildsListRequest): Response<Array<Guild>>

    @JsExport.Ignore
    fun getCurrentUserGuildsBlocking(request: GuildsListRequest): Response<Array<Guild>>

    /**
     * Get Guild
     * Returns the guild object for the given id.
     */
    suspend fun getGuild(guildId: String): Response<Guild>

    @JsExport.Ignore
    fun getGuildBlocking(guildId: String): Response<Guild>

    /**
     * Get Guild Channels
     * Returns a list of guild channel objects.
     */
    suspend fun listGuildChannels(guildId: String): Response<Array<Channel>>

    @JsExport.Ignore
    fun listGuildChannelsBlocking(guildId: String): Response<Array<Channel>>
}
