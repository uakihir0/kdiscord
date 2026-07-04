package work.socialhub.kdiscord.api

import work.socialhub.kdiscord.api.request.channels.ChannelsCreateDmRequest
import work.socialhub.kdiscord.entity.Channel
import work.socialhub.kdiscord.entity.share.Response
import kotlin.js.JsExport

/**
 * Channels resource.
 * https://discord.com/developers/docs/resources/channel
 */
@JsExport
interface ChannelsResource {

    /**
     * Get Channel
     * Returns a channel object for a given channel id.
     */
    suspend fun getChannel(channelId: String): Response<Channel>

    @JsExport.Ignore
    fun getChannelBlocking(channelId: String): Response<Channel>

    /**
     * Get User DMs
     * Returns a list of DM channel objects for the current user.
     */
    suspend fun listDmChannels(): Response<Array<Channel>>

    @JsExport.Ignore
    fun listDmChannelsBlocking(): Response<Array<Channel>>

    /**
     * Create DM
     * Create a new DM channel with a user.
     */
    suspend fun createDm(request: ChannelsCreateDmRequest): Response<Channel>

    @JsExport.Ignore
    fun createDmBlocking(request: ChannelsCreateDmRequest): Response<Channel>
}
