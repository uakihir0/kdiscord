package work.socialhub.kdiscord.internal

import work.socialhub.kdiscord.Discord

class DiscordImpl(
    override val apiHost: String,
    override val token: String,
) : Discord
