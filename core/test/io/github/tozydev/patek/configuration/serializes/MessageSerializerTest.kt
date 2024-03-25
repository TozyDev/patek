package io.github.tozydev.patek.configuration.serializes

import io.github.tozydev.patek.audience.ActionBarMessage
import io.github.tozydev.patek.audience.CompositeMessage
import io.github.tozydev.patek.audience.Message
import io.github.tozydev.patek.audience.SoundMessage
import io.github.tozydev.patek.audience.TextMessage
import io.github.tozydev.patek.audience.TitleMessage
import io.github.tozydev.patek.configuration.serializes.MessageSerializer.ACTION_BAR
import io.github.tozydev.patek.configuration.serializes.MessageSerializer.FADE_IN
import io.github.tozydev.patek.configuration.serializes.MessageSerializer.FADE_OUT
import io.github.tozydev.patek.configuration.serializes.MessageSerializer.PITCH
import io.github.tozydev.patek.configuration.serializes.MessageSerializer.SOUND
import io.github.tozydev.patek.configuration.serializes.MessageSerializer.SOURCE
import io.github.tozydev.patek.configuration.serializes.MessageSerializer.STAY
import io.github.tozydev.patek.configuration.serializes.MessageSerializer.SUBTITLE
import io.github.tozydev.patek.configuration.serializes.MessageSerializer.TEXT
import io.github.tozydev.patek.configuration.serializes.MessageSerializer.TIMES
import io.github.tozydev.patek.configuration.serializes.MessageSerializer.TITLE
import io.github.tozydev.patek.configuration.serializes.MessageSerializer.VOLUME
import net.kyori.adventure.sound.Sound
import org.spongepowered.configurate.serialize.SerializationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.seconds

class MessageSerializerTest : SerializerTestBase<Message>(Message::class, MessageSerializer) {
    @Test
    fun `should deserialize a text message`() {
        node.node(TEXT).raw("Hello, world!")
        assertDeserialization(TextMessage("Hello, world!"))
    }

    @Test
    fun `should deserialize a text message with a list content`() {
        node.node(TEXT).raw(listOf("Hello", "world!"))
        assertDeserialization(TextMessage("Hello\nworld!"))
    }

    @Test
    fun `should deserialize a text message without 'text' node`() {
        node.raw("Hello, world!")
        assertDeserialization(TextMessage("Hello, world!"))
    }

    @Test
    fun `should deserialize an action bar message`() {
        node.node(ACTION_BAR).raw("Hello, world!")
        assertDeserialization(ActionBarMessage("Hello, world!"))
    }

    @Test
    fun `should deserialize a title message`() {
        node.node(TITLE).raw("Title")
        node.node(SUBTITLE).raw("Subtitle")
        node.node(TIMES).run {
            node(FADE_IN).raw("10s")
            node(STAY).raw("20s")
            node(FADE_OUT).raw("30s")
        }
        assertDeserialization(TitleMessage("Title", "Subtitle", TitleMessage.Times(10.seconds, 20.seconds, 30.seconds)))
    }

    @Test
    fun `should deserialize a title message without 'times' node`() {
        node.node(TITLE).raw("Title")
        node.node(SUBTITLE).raw("Subtitle")
        assertDeserialization(TitleMessage("Title", "Subtitle"))
    }

    @Test
    fun `should deserialize a title message without 'title' node`() {
        node.node(SUBTITLE).raw("Subtitle")
        node.node(TIMES).run {
            node(FADE_IN).raw("10s")
            node(STAY).raw("20s")
            node(FADE_OUT).raw("30s")
        }
        assertDeserialization(
            TitleMessage(
                subtitle = "Subtitle",
                times = TitleMessage.Times(10.seconds, 20.seconds, 30.seconds),
            ),
        )
    }

    @Test
    fun `should deserialize a title message without 'subtitle' node`() {
        node.node(TITLE).raw("Title")
        node.node(TIMES).run {
            node(FADE_IN).raw("10s")
            node(STAY).raw("20s")
            node(FADE_OUT).raw("30s")
        }
        assertDeserialization(TitleMessage("Title", times = TitleMessage.Times(10.seconds, 20.seconds, 30.seconds)))
    }

    @Test
    fun `should deserialize a title message without 'title' and 'subtitle' nodes`() {
        node.node(TIMES).run {
            node(FADE_IN).raw("10s")
            node(STAY).raw("20s")
            node(FADE_OUT).raw("30s")
        }
        assertDeserialization(TitleMessage(times = TitleMessage.Times(10.seconds, 20.seconds, 30.seconds)))
    }

    @Test
    fun `should throw an exception when deserializing a title message without 'fade-in' node`() {
        node.node(TIMES).run {
            node(STAY).raw("20s")
            node(FADE_OUT).raw("30s")
        }
        assertDeserializationFailsWith<SerializationException>("Missing $FADE_IN duration")
    }

    @Test
    fun `should throw an exception when deserializing a title message without 'stay' node`() {
        node.node(TIMES).run {
            node(FADE_IN).raw("10s")
            node(FADE_OUT).raw("30s")
        }
        assertDeserializationFailsWith<SerializationException>("Missing $STAY duration")
    }

    @Test
    fun `should throw an exception when deserializing a title message without 'fade-out' node`() {
        node.node(TIMES).run {
            node(FADE_IN).raw("10s")
            node(STAY).raw("20s")
        }
        assertDeserializationFailsWith<SerializationException>("Missing $FADE_OUT duration")
    }

    @Test
    fun `should deserialize a sound message`() {
        node.node(SOUND).raw("sound")
        node.node(SOURCE).raw("master")
        node.node(VOLUME).raw(1.0)
        node.node(PITCH).raw(1.0)
        assertDeserialization(SoundMessage("sound", Sound.Source.MASTER, 1f, 1f))
    }

    @Test
    fun `should deserialize a sound message without optional nodes`() {
        node.node(SOUND).raw("sound")
        assertDeserialization(SoundMessage("sound"))
    }

    @Test
    fun `should throw an exception when deserializing a sound message without 'sound' node`() {
        node.node(SOURCE).raw("master")
        node.node(VOLUME).raw(1.0)
        node.node(PITCH).raw(1.0)
        assertDeserializationFailsWith<SerializationException>("Missing $SOUND")
    }

    @Test
    fun `should throw an exception when deserializing an unknown message type`() {
        node.set(listOf(mapOf("" to "")))
        assertDeserializationFailsWith<SerializationException>("Unknown message type")
    }

    @Test
    fun `should deserialize a composite message`() {
        node.raw(
            listOf(
                mapOf(TEXT to "Hello, world!"),
                mapOf(ACTION_BAR to "Hello, world!"),
                mapOf(
                    TITLE to "Title",
                    SUBTITLE to "Subtitle",
                    TIMES to mapOf(FADE_IN to "10s", STAY to "20s", FADE_OUT to "30s"),
                ),
                mapOf(SOUND to "sound", SOURCE to "master", VOLUME to 1.0, PITCH to 1.0),
            ),
        )
        assertDeserialization(
            CompositeMessage(
                listOf(
                    TextMessage("Hello, world!"),
                    ActionBarMessage("Hello, world!"),
                    TitleMessage("Title", "Subtitle", TitleMessage.Times(10.seconds, 20.seconds, 30.seconds)),
                    SoundMessage("sound", Sound.Source.MASTER, 1f, 1f),
                ),
            ),
        )
    }

    @Test
    fun `should deserialize a list of string as composite message`() {
        node.raw(listOf("Hello", "world!"))
        assertDeserialization(CompositeMessage(listOf(TextMessage("Hello"), TextMessage("world!"))))
    }

    @Test
    fun `should serialize a text message without 'text' nodes`() {
        node.set(TextMessage("Hello, world!"))
        assertTrue(node.node(TEXT).empty())
        assertEquals("Hello, world!", node.string)
    }

    @Test
    fun `should serialize an action bar message`() {
        node.set(ActionBarMessage("Hello, world!"))
        assertEquals("Hello, world!", node.node(ACTION_BAR).string)
    }

    @Test
    fun `should serialize a title message`() {
        node.set(TitleMessage("Title", "Subtitle", TitleMessage.Times(10.seconds, 20.seconds, 30.seconds)))
        assertEquals("Title", node.node(TITLE).string)
        assertEquals("Subtitle", node.node(SUBTITLE).string)
        node.node(TIMES).run {
            assertEquals("10s", node(FADE_IN).string)
            assertEquals("20s", node(STAY).string)
            assertEquals("30s", node(FADE_OUT).string)
        }
    }

    @Test
    fun `should serialize a title message without 'times' node`() {
        node.set(TitleMessage("Title", "Subtitle"))
        assertEquals("Title", node.node(TITLE).string)
        assertEquals("Subtitle", node.node(SUBTITLE).string)
        assertTrue(node.node(TIMES).empty())
    }

    @Test
    fun `should serialize a title message without 'title' node`() {
        node.set(TitleMessage(subtitle = "Subtitle", times = TitleMessage.Times(10.seconds, 20.seconds, 30.seconds)))
        assertTrue(node.node(TITLE).empty())
        assertEquals("Subtitle", node.node(SUBTITLE).string)
        node.node(TIMES).run {
            assertEquals("10s", node(FADE_IN).string)
            assertEquals("20s", node(STAY).string)
            assertEquals("30s", node(FADE_OUT).string)
        }
    }

    @Test
    fun `should serialize a title message without 'subtitle' node`() {
        node.set(TitleMessage("Title", times = TitleMessage.Times(10.seconds, 20.seconds, 30.seconds)))
        assertEquals("Title", node.node(TITLE).string)
        assertTrue(node.node(SUBTITLE).empty())
        node.node(TIMES).run {
            assertEquals("10s", node(FADE_IN).string)
            assertEquals("20s", node(STAY).string)
            assertEquals("30s", node(FADE_OUT).string)
        }
    }

    @Test
    fun `should serialize a title message without 'title' and 'subtitle' nodes`() {
        node.set(TitleMessage(times = TitleMessage.Times(10.seconds, 20.seconds, 30.seconds)))
        assertTrue(node.node(TITLE).empty())
        assertTrue(node.node(SUBTITLE).empty())
        node.node(TIMES).run {
            assertEquals("10s", node(FADE_IN).string)
            assertEquals("20s", node(STAY).string)
            assertEquals("30s", node(FADE_OUT).string)
        }
    }

    @Test
    fun `should serialize a sound message`() {
        node.set(SoundMessage("sound", Sound.Source.MASTER, 1f, 1f))
        assertEquals("sound", node.node(SOUND).string)
        assertEquals("MASTER", node.node(SOURCE).string)
        assertEquals(1.0, node.node(VOLUME).double)
        assertEquals(1.0, node.node(PITCH).double)
    }

    @Test
    fun `should serialize a sound message without optional nodes`() {
        node.set(SoundMessage("sound"))
        assertEquals("sound", node.node(SOUND).string)
        assertTrue(node.node(SOURCE).empty())
        assertTrue(node.node(VOLUME).empty())
        assertTrue(node.node(PITCH).empty())
    }

    @Test
    fun `should serialize a composite message`() {
        node.set(
            CompositeMessage(
                listOf(
                    TextMessage("Hello, world!"),
                    ActionBarMessage("Hello, world!"),
                    TitleMessage("Title", "Subtitle", TitleMessage.Times(10.seconds, 20.seconds, 30.seconds)),
                    SoundMessage("sound", Sound.Source.MASTER, 1f, 1f),
                ),
            ),
        )
        val messageNodes = node.childrenList()
        assertEquals(4, messageNodes.size)
        assertEquals("Hello, world!", messageNodes[0].string)
        assertEquals("Hello, world!", messageNodes[1].node(ACTION_BAR).string)
        assertEquals("Title", messageNodes[2].node(TITLE).string)
        assertEquals("Subtitle", messageNodes[2].node(SUBTITLE).string)
        messageNodes[2].node(TIMES).run {
            assertEquals("10s", node(FADE_IN).string)
            assertEquals("20s", node(STAY).string)
            assertEquals("30s", node(FADE_OUT).string)
        }
        assertEquals("sound", messageNodes[3].node(SOUND).string)
        assertEquals("MASTER", messageNodes[3].node(SOURCE).string)
        assertEquals(1.0, messageNodes[3].node(VOLUME).double)
        assertEquals(1.0, messageNodes[3].node(PITCH).double)
    }
}
