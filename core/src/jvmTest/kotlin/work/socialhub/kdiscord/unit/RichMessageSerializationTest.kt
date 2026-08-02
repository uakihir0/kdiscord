package work.socialhub.kdiscord.unit

import work.socialhub.kdiscord.entity.ActionRowComponent
import work.socialhub.kdiscord.entity.ButtonComponent
import work.socialhub.kdiscord.entity.ContainerComponent
import work.socialhub.kdiscord.entity.MediaGalleryComponent
import work.socialhub.kdiscord.entity.Message
import work.socialhub.kdiscord.entity.StringSelectComponent
import work.socialhub.kdiscord.entity.TextDisplayComponent
import work.socialhub.kdiscord.entity.UnknownMessageComponent
import work.socialhub.kdiscord.internal.InternalUtility
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class RichMessageSerializationTest {

    @Test
    fun testRichWebhookMessageFixture() {
        val message = decodeFixture()

        assertEquals("", message.content)
        assertNotNull(message.components)
        assertTrue(message.components!!.isEmpty())
        assertEquals(0, message.flags)

        assertEquals("100000000000000004", message.webhookId)
        assertEquals("Example Webhook", message.author?.username)
        assertEquals("EXMP", message.author?.clan?.tag)
        assertEquals("100000000000000003", message.author?.primaryGuild?.identityGuildId)

        val embed = message.embeds?.single()
        assertNotNull(embed)
        assertEquals("Example release", embed.title)
        assertEquals("An anonymized rich embed body.", embed.description)
        assertEquals(1, embed.contentScanVersion)
        assertEquals("Status", embed.fields?.get(0)?.name)
        assertEquals("Available", embed.fields?.get(0)?.value)
        assertEquals("Notes", embed.fields?.get(1)?.name)

        val image = embed.image
        assertNotNull(image)
        assertEquals("https://cdn.example.com/images/release.png", image.url)
        assertEquals("https://media.example.com/images/release.png", image.proxyUrl)
        assertEquals(1200, image.width)
        assertEquals(630, image.height)
        assertEquals("image/png", image.contentType)
        assertEquals("AAECAwQFBgcICQ==", image.placeholder)
        assertEquals(1, image.placeholderVersion)

        val reaction = message.reactions?.single()
        assertNotNull(reaction)
        assertEquals(3, reaction.count)
        assertEquals(1, reaction.countDetails?.burst)
        assertEquals(2, reaction.countDetails?.normal)
        assertContentEquals(arrayOf("#5865F2", "#EB459E"), reaction.burstColors)
        assertEquals(1, reaction.burstCount)
        assertEquals(true, reaction.burstMe)
        assertEquals(true, reaction.meBurst)
    }

    @Test
    fun testRichWebhookMessageRoundTrip() {
        val original = decodeFixture()
        val encoded = InternalUtility.toJson(original)
        val decoded = InternalUtility.fromJson<Message>(encoded)

        assertEquals("", decoded.content)
        assertTrue(decoded.components?.isEmpty() == true)
        assertEquals(original.webhookId, decoded.webhookId)
        assertEquals(original.embeds?.single()?.description, decoded.embeds?.single()?.description)
        assertEquals(original.embeds?.single()?.image?.placeholder, decoded.embeds?.single()?.image?.placeholder)
        assertEquals(original.reactions?.single()?.countDetails?.burst, decoded.reactions?.single()?.countDetails?.burst)
        assertContentEquals(original.reactions?.single()?.burstColors, decoded.reactions?.single()?.burstColors)
        assertEquals(original.author?.clan?.badge, decoded.author?.clan?.badge)
    }

    @Test
    fun testKnownAndUnknownComponentsRoundTrip() {
        val json = """
            {
              "id": "200000000000000001",
              "flags": 32768,
              "components": [
                {
                  "type": 17,
                  "id": 1,
                  "accent_color": 703487,
                  "components": [
                    { "type": 10, "id": 2, "content": "# Component content" },
                    {
                      "type": 12,
                      "id": 3,
                      "items": [
                        {
                          "media": {
                            "url": "https://cdn.example.com/gallery/image.png",
                            "content_type": "image/png",
                            "placeholder": "AQIDBA==",
                            "placeholder_version": 1
                          },
                          "description": "Example image",
                          "spoiler": false
                        }
                      ]
                    },
                    {
                      "type": 1,
                      "id": 4,
                      "components": [
                        {
                          "type": 2,
                          "id": 5,
                          "style": 5,
                          "label": "Details",
                          "url": "https://example.com"
                        },
                        {
                          "type": 3,
                          "id": 6,
                          "custom_id": "release",
                          "options": [
                            { "label": "Stable", "value": "stable", "default": true }
                          ]
                        }
                      ]
                    }
                  ]
                },
                {
                  "type": 999,
                  "id": 7,
                  "future_data": {
                    "nested": [1, 2, 3]
                  }
                }
              ]
            }
        """.trimIndent()

        val original = InternalUtility.fromJson<Message>(json)
        val container = assertIs<ContainerComponent>(original.components?.get(0))
        assertEquals(703487, container.accentColor)
        assertIs<TextDisplayComponent>(container.components?.get(0))
        assertIs<MediaGalleryComponent>(container.components?.get(1))
        val row = assertIs<ActionRowComponent>(container.components?.get(2))
        assertIs<ButtonComponent>(row.components?.get(0))
        assertIs<StringSelectComponent>(row.components?.get(1))

        val unknown = assertIs<UnknownMessageComponent>(original.components?.get(1))
        assertTrue(unknown.rawJson?.contains("\"future_data\"") == true)

        val decoded = InternalUtility.fromJson<Message>(InternalUtility.toJson(original))
        val decodedUnknown = assertIs<UnknownMessageComponent>(decoded.components?.get(1))
        assertEquals(999, decodedUnknown.type)
        assertTrue(decodedUnknown.rawJson?.contains("\"nested\":[1,2,3]") == true)
    }

    private fun decodeFixture(): Message {
        val resource = checkNotNull(javaClass.getResource("/fixtures/rich-webhook-message.json"))
        return InternalUtility.fromJson(resource.readText())
    }
}
