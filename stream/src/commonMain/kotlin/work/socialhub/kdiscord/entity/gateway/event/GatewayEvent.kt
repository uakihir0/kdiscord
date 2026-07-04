package work.socialhub.kdiscord.entity.gateway.event

/**
 * Marker interface for all decoded Gateway dispatch events.
 *
 * Concrete events wrap the relevant entity/data. Any event type kdiscord does
 * not (yet) model is surfaced as an [UnknownEvent] carrying the raw JSON, so
 * unrecognized event types never break the stream.
 */
interface GatewayEvent {
    /** The Discord event name (the `t` field), e.g. "MESSAGE_CREATE". */
    val type: String
}
