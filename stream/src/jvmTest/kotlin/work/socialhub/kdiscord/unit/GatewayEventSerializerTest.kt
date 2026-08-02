package work.socialhub.kdiscord.unit

import work.socialhub.kdiscord.entity.gateway.event.MessageCreateEvent
import work.socialhub.kdiscord.entity.gateway.event.MessageUpdateEvent
import work.socialhub.kdiscord.entity.gateway.event.ReadyEvent
import work.socialhub.kdiscord.entity.gateway.event.UnknownEvent
import work.socialhub.kdiscord.entity.UnknownMessageComponent
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
    fun testDecodeRichMessageCreateWithUnknownFields() {
        val event = GatewayEventSerializer.decode("MESSAGE_CREATE", richMessage())

        assertTrue(event is MessageCreateEvent)
        assertEquals("Example gateway embed", event.message.embeds?.single()?.title)
        assertEquals("Gateway rich embed body.", event.message.embeds?.single()?.description)
        assertEquals("Environment", event.message.embeds?.single()?.fields?.single()?.name)
        assertEquals("AQIDBA==", event.message.embeds?.single()?.image?.placeholder)
        assertTrue(event.message.components?.single() is UnknownMessageComponent)
    }

    @Test
    fun testDecodeRichMessageUpdateWithUnknownFields() {
        val event = GatewayEventSerializer.decode("MESSAGE_UPDATE", richMessage())

        assertTrue(event is MessageUpdateEvent)
        assertEquals("300000000000000001", event.message.id)
        assertEquals("https://cdn.example.com/gateway.png", event.message.embeds?.single()?.image?.url)
        assertEquals(1, event.message.reactions?.single()?.countDetails?.burst)
        assertTrue(event.message.components?.single() is UnknownMessageComponent)
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

    private fun richMessage() = element(
        """
        {
          "id": "300000000000000001",
          "channel_id": "300000000000000002",
          "content": "",
          "embeds": [
            {
              "type": "rich",
              "title": "Example gateway embed",
              "description": "Gateway rich embed body.",
              "fields": [
                { "name": "Environment", "value": "Test", "inline": true }
              ],
              "image": {
                "url": "https://cdn.example.com/gateway.png",
                "width": 800,
                "height": 450,
                "content_type": "image/png",
                "placeholder": "AQIDBA==",
                "placeholder_version": 1
              },
              "future_embed_field": "ignored"
            }
          ],
          "reactions": [
            {
              "count": 2,
              "count_details": { "burst": 1, "normal": 1 },
              "me": false,
              "me_burst": false,
              "emoji": { "name": "sparkles" },
              "burst_colors": ["#5865F2"]
            }
          ],
          "components": [
            {
              "type": 999,
              "id": 1,
              "future_component_data": { "label": "Preserved" }
            }
          ],
          "future_message_field": { "version": 2 }
        }
        """.trimIndent()
    )
}
