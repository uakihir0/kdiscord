package work.socialhub.kdiscord.unit

import work.socialhub.kdiscord.entity.gateway.event.MessageCreateEvent
import work.socialhub.kdiscord.entity.gateway.event.ReadyEvent
import work.socialhub.kdiscord.entity.gateway.event.UnknownEvent
import work.socialhub.kdiscord.internal.InternalUtility
import work.socialhub.kdiscord.stream.internal.GatewayEventSerializer
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Offline unit tests for the tolerant gateway event decoder. Runs in CI (no network).
 */
class GatewayEventSerializerTest {

    private fun element(json: String) =
        InternalUtility.json.parseToJsonElement(json)

    @Test
    fun testDecodeReady() {
        val d = element(
            """{ "v": 10, "session_id": "abc", "resume_gateway_url": "wss://x", "user": { "id": "1" } }"""
        )
        val event = GatewayEventSerializer.decode("READY", d)
        assertTrue(event is ReadyEvent)
        assertEquals("abc", event.data.sessionId)
        assertEquals("wss://x", event.data.resumeGatewayUrl)
    }

    @Test
    fun testDecodeMessageCreate() {
        val d = element("""{ "id": "99", "channel_id": "1", "content": "hi" }""")
        val event = GatewayEventSerializer.decode("MESSAGE_CREATE", d)
        assertTrue(event is MessageCreateEvent)
        assertEquals("99", event.message.id)
        assertEquals("hi", event.message.content)
    }

    @Test
    fun testUnknownEventFallsBack() {
        // A brand-new event type kdiscord doesn't model must not crash.
        val d = element("""{ "some": "future_payload" }""")
        val event = GatewayEventSerializer.decode("SOME_FUTURE_EVENT", d)
        assertTrue(event is UnknownEvent)
        assertEquals("SOME_FUTURE_EVENT", event.type)
        assertTrue(event.raw.contains("future_payload"))
    }

    @Test
    fun testMalformedDataFallsBackToUnknown() {
        // A known type with an incompatible payload should degrade to UnknownEvent,
        // not throw and kill the stream.
        val d = element("""[1, 2, 3]""")
        val event = GatewayEventSerializer.decode("MESSAGE_CREATE", d)
        assertTrue(event is UnknownEvent)
    }
}
