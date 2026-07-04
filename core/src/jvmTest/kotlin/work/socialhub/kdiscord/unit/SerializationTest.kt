package work.socialhub.kdiscord.unit

import work.socialhub.kdiscord.entity.Message
import work.socialhub.kdiscord.entity.MessageReference
import work.socialhub.kdiscord.internal.InternalUtility
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

/**
 * Offline unit tests for JSON (de)serialization. These run in CI without network.
 */
class SerializationTest {

    @Test
    fun testMessageSnakeCaseMapping() {
        // Discord sends snake_case fields; the SnakeCase naming strategy should map them
        // onto camelCase Kotlin properties, and unknown keys must be ignored.
        val jsonStr = """
            {
              "id": "1234567890123456789",
              "channel_id": "9876543210987654321",
              "content": "hello world",
              "mention_everyone": false,
              "author": { "id": "42", "username": "tester", "global_name": "Tester" },
              "some_unknown_future_field": { "x": 1 }
            }
        """.trimIndent()

        val message = InternalUtility.fromJson<Message>(jsonStr)
        assertEquals("1234567890123456789", message.id)
        assertEquals("9876543210987654321", message.channelId)
        assertEquals("hello world", message.content)
        assertEquals(false, message.mentionEveryone)
        assertNotNull(message.author)
        assertEquals("tester", message.author?.username)
        assertEquals("Tester", message.author?.globalName)
    }

    @Test
    fun testSnowflakeIdPreservedAsString() {
        // Large snowflake ids must survive as strings without precision loss.
        val id = "1290000000000000123"
        val jsonStr = """{ "id": "$id" }"""
        val message = InternalUtility.fromJson<Message>(jsonStr)
        assertEquals(id, message.id)
    }

    @Test
    fun testEncodeOmitsNulls() {
        // explicitNulls = false: null fields should not be encoded.
        val message = Message().also { it.content = "hi" }
        val encoded = InternalUtility.toJson(message)
        assertTrue(encoded.contains("\"content\":\"hi\""))
        assertTrue(!encoded.contains("\"id\""))
    }

    @Test
    fun testCamelCaseEncodesToSnakeCase() {
        // Outgoing payloads must encode camelCase Kotlin names to snake_case for Discord.
        val ref = MessageReference().also {
            it.messageId = "123"
            it.channelId = "456"
        }
        val encoded = InternalUtility.toJson(ref)
        assertTrue(encoded.contains("\"message_id\":\"123\""), "expected snake_case message_id: $encoded")
        assertTrue(encoded.contains("\"channel_id\":\"456\""), "expected snake_case channel_id: $encoded")
    }
}
