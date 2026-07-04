package work.socialhub.kdiscord.stream

import work.socialhub.kdiscord.entity.Guild
import work.socialhub.kdiscord.entity.Message
import work.socialhub.kdiscord.entity.gateway.ReadyData
import work.socialhub.kdiscord.entity.gateway.event.MessageDeleteEvent
import work.socialhub.kdiscord.entity.gateway.event.PresenceUpdateEvent
import kotlin.js.JsExport

/**
 * Listener for Discord Gateway events.
 *
 * All methods have empty default bodies; implement only the ones you care about.
 */
@JsExport
interface DiscordStreamListener {

    /** Fired once the session is ready (READY dispatch). */
    fun onReady(data: ReadyData) {}

    /** Fired when a message is created (MESSAGE_CREATE). */
    fun onMessageCreate(message: Message) {}

    /** Fired when a message is edited (MESSAGE_UPDATE). */
    fun onMessageUpdate(message: Message) {}

    /** Fired when a message is deleted (MESSAGE_DELETE). */
    fun onMessageDelete(event: MessageDeleteEvent) {}

    /** Fired when a guild becomes available (GUILD_CREATE). */
    fun onGuildCreate(guild: Guild) {}

    /** Fired when a user's presence is updated (PRESENCE_UPDATE). */
    fun onPresenceUpdate(event: PresenceUpdateEvent) {}

    /** Fired for any event kdiscord does not model; `raw` is the event's JSON. */
    fun onUnknownEvent(type: String, raw: String) {}

    /** Fired when the WebSocket connection is established. */
    fun onOpen() {}

    /** Fired when the WebSocket connection is closed. */
    fun onClose() {}

    /** Fired when an error occurs. */
    fun onError(error: Exception) {}
}
