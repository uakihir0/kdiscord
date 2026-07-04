package work.socialhub.kdiscord.entity.gateway

/**
 * Discord Gateway opcodes.
 * https://discord.com/developers/docs/topics/opcodes-and-status-codes#gateway
 */
object GatewayOpcode {
    /** An event was dispatched (receive). */
    const val DISPATCH = 0

    /** Keep the connection alive (send/receive). */
    const val HEARTBEAT = 1

    /** Start a new session during the initial handshake (send). */
    const val IDENTIFY = 2

    /** Update the client's presence (send). */
    const val PRESENCE_UPDATE = 3

    /** Join/leave or move between voice channels (send). */
    const val VOICE_STATE_UPDATE = 4

    /** Resume a previous session that was disconnected (send). */
    const val RESUME = 6

    /** You should attempt to reconnect and resume immediately (receive). */
    const val RECONNECT = 7

    /** Request information about offline guild members (send). */
    const val REQUEST_GUILD_MEMBERS = 8

    /** The session has been invalidated (receive). */
    const val INVALID_SESSION = 9

    /** Sent immediately after connecting, contains the heartbeat_interval (receive). */
    const val HELLO = 10

    /** Sent in response to receiving a heartbeat to acknowledge it (receive). */
    const val HEARTBEAT_ACK = 11
}
