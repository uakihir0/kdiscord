package work.socialhub.kdiscord.entity.gateway

import kotlinx.serialization.Serializable

/**
 * Data of the IDENTIFY (op 2) payload for a USER account.
 * https://discord.com/developers/docs/events/gateway-events#identify
 *
 * NOTE: For user accounts the `intents` field is intentionally omitted — intents
 * are a bot-only concept. User accounts receive events without intent filtering
 * and can see full message content without a privileged intent. To support bots
 * later, add an `intents` field here and prefix the token with "Bot " where it is
 * assigned in the Gateway client.
 */
@Serializable
class IdentifyPayload(
    /** Authentication token (raw user token). */
    var token: String,

    /** Connection properties. */
    var properties: IdentifyProperties = IdentifyProperties(),

    /** Whether this connection supports compression of packets. */
    var compress: Boolean = false,

    /** Presence structure for initial presence information. */
    var presence: PresenceData = PresenceData(),
)

/**
 * Identify connection properties.
 * https://discord.com/developers/docs/events/gateway-events#identify-identify-connection-properties
 */
@Serializable
class IdentifyProperties(
    /** Operating system. */
    var os: String = "Windows",

    /** Library / browser name. */
    var browser: String = "Chrome",

    /** Device name. */
    var device: String = "",
)

/**
 * Presence update payload sent in IDENTIFY.
 * https://discord.com/developers/docs/events/gateway-events#update-presence
 */
@Serializable
class PresenceData(
    /** Unix time (in milliseconds) of when the client went idle, or null. */
    var since: Long? = null,

    /** The user's activities. */
    var activities: Array<String> = emptyArray(),

    /** The user's new status ("online", "dnd", "idle", "invisible", "offline"). */
    var status: String = "online",

    /** Whether or not the client is afk. */
    var afk: Boolean = false,
)
