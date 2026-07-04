package work.socialhub.kdiscord.stream.internal

import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.decodeFromJsonElement
import work.socialhub.kdiscord.entity.Guild
import work.socialhub.kdiscord.entity.Message
import work.socialhub.kdiscord.entity.gateway.ReadyData
import work.socialhub.kdiscord.entity.gateway.event.GatewayEvent
import work.socialhub.kdiscord.entity.gateway.event.GuildCreateEvent
import work.socialhub.kdiscord.entity.gateway.event.MessageCreateEvent
import work.socialhub.kdiscord.entity.gateway.event.MessageDeleteEvent
import work.socialhub.kdiscord.entity.gateway.event.MessageDeleteGatewayEvent
import work.socialhub.kdiscord.entity.gateway.event.MessageUpdateEvent
import work.socialhub.kdiscord.entity.gateway.event.PresenceUpdateEvent
import work.socialhub.kdiscord.entity.gateway.event.PresenceUpdateGatewayEvent
import work.socialhub.kdiscord.entity.gateway.event.ReadyEvent
import work.socialhub.kdiscord.entity.gateway.event.UnknownEvent
import work.socialhub.kdiscord.internal.InternalUtility

/**
 * Decodes the `d` payload of a DISPATCH event into a concrete [GatewayEvent]
 * based on the event name `t`.
 *
 * Unknown event types (or decode failures) fall back to [UnknownEvent] holding
 * the raw JSON, so new/unrecognized Discord event types never break the stream.
 */
object GatewayEventSerializer {

    fun decode(type: String, data: JsonElement?): GatewayEvent {
        if (data == null) {
            return UnknownEvent(type, "null")
        }

        return try {
            when (type) {
                "READY" ->
                    ReadyEvent(decodeAs<ReadyData>(data))

                "MESSAGE_CREATE" ->
                    MessageCreateEvent(decodeAs<Message>(data))

                "MESSAGE_UPDATE" ->
                    MessageUpdateEvent(decodeAs<Message>(data))

                "MESSAGE_DELETE" ->
                    MessageDeleteGatewayEvent(decodeAs<MessageDeleteEvent>(data))

                "GUILD_CREATE" ->
                    GuildCreateEvent(decodeAs<Guild>(data))

                "PRESENCE_UPDATE" ->
                    PresenceUpdateGatewayEvent(decodeAs<PresenceUpdateEvent>(data))

                else ->
                    UnknownEvent(type, data.toString())
            }
        } catch (e: Exception) {
            // Tolerate schema drift: never let a decode failure kill the stream.
            UnknownEvent(type, data.toString())
        }
    }

    private inline fun <reified T> decodeAs(data: JsonElement): T {
        return InternalUtility.json.decodeFromJsonElement(data)
    }
}
