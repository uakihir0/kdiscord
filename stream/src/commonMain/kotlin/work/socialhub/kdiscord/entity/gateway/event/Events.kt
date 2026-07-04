package work.socialhub.kdiscord.entity.gateway.event

import work.socialhub.kdiscord.entity.Guild
import work.socialhub.kdiscord.entity.Message
import work.socialhub.kdiscord.entity.gateway.ReadyData

/** READY dispatch event. */
class ReadyEvent(val data: ReadyData) : GatewayEvent {
    override val type = "READY"
}

/** MESSAGE_CREATE dispatch event. */
class MessageCreateEvent(val message: Message) : GatewayEvent {
    override val type = "MESSAGE_CREATE"
}

/** MESSAGE_UPDATE dispatch event. */
class MessageUpdateEvent(val message: Message) : GatewayEvent {
    override val type = "MESSAGE_UPDATE"
}

/** MESSAGE_DELETE dispatch event. */
class MessageDeleteGatewayEvent(val data: MessageDeleteEvent) : GatewayEvent {
    override val type = "MESSAGE_DELETE"
}

/** GUILD_CREATE dispatch event. */
class GuildCreateEvent(val guild: Guild) : GatewayEvent {
    override val type = "GUILD_CREATE"
}

/** PRESENCE_UPDATE dispatch event. */
class PresenceUpdateGatewayEvent(val data: PresenceUpdateEvent) : GatewayEvent {
    override val type = "PRESENCE_UPDATE"
}

/**
 * Any event kdiscord does not (yet) model, carrying the raw JSON.
 * This is the fallback that keeps the stream alive for unknown event types.
 */
class UnknownEvent(
    override val type: String,
    /** The raw JSON string of the event data (`d`). */
    val raw: String,
) : GatewayEvent
