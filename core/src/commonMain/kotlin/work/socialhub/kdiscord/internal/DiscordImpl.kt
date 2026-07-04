package work.socialhub.kdiscord.internal

import work.socialhub.kdiscord.Discord
import work.socialhub.kdiscord.api.ChannelsResource
import work.socialhub.kdiscord.api.GatewayResource
import work.socialhub.kdiscord.api.GuildsResource
import work.socialhub.kdiscord.api.MessagesResource
import work.socialhub.kdiscord.api.ReactionsResource
import work.socialhub.kdiscord.api.UsersResource
import work.socialhub.kdiscord.internal.api.ChannelsResourceImpl
import work.socialhub.kdiscord.internal.api.GatewayResourceImpl
import work.socialhub.kdiscord.internal.api.GuildsResourceImpl
import work.socialhub.kdiscord.internal.api.MessagesResourceImpl
import work.socialhub.kdiscord.internal.api.ReactionsResourceImpl
import work.socialhub.kdiscord.internal.api.UsersResourceImpl

class DiscordImpl(
    override val apiHost: String,
    override val token: String,
) : Discord {

    private val users: UsersResource = UsersResourceImpl(apiHost, token)
    private val channels: ChannelsResource = ChannelsResourceImpl(apiHost, token)
    private val messages: MessagesResource = MessagesResourceImpl(apiHost, token)
    private val reactions: ReactionsResource = ReactionsResourceImpl(apiHost, token)
    private val guilds: GuildsResource = GuildsResourceImpl(apiHost, token)
    private val gateway: GatewayResource = GatewayResourceImpl(apiHost, token)

    override fun users() = users
    override fun channels() = channels
    override fun messages() = messages
    override fun reactions() = reactions
    override fun guilds() = guilds
    override fun gateway() = gateway
}
