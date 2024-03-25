package io.github.tozydev.patek.configuration.serializes

import org.spongepowered.configurate.serialize.SerializationException
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

class DurationSerializerTest : SerializerTestBase<Duration>(DurationSerializer) {
    @Test
    fun `deserialize should deserialize a string to a Duration`() {
        node.raw("1m30s")
        assertDeserialization(1.5.minutes)
    }

    @Test
    fun `deserialize should throw exception when deserialize invalid Duration format`() {
        node.raw("1m30")
        assertDeserializationFailsWith<SerializationException>()
    }

    @Test
    fun `serialize should serialize a Duration to a string`() {
        node.set(1.minutes)
        assertIs<Duration>(node.rawScalar())
        assertEquals("1m", node.string)
    }
}
