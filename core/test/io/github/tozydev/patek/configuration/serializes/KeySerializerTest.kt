package io.github.tozydev.patek.configuration.serializes

import net.kyori.adventure.key.Key
import net.kyori.adventure.key.Key.key
import org.junit.jupiter.api.Disabled
import org.spongepowered.configurate.serialize.SerializationException
import kotlin.test.Test
import kotlin.test.assertEquals

class KeySerializerTest : SerializerTestBase<Key>(KeySerializer) {
    @Test
    fun `should deserialize key`() {
        node.set("minecraft:stone")
        assertDeserialization(key("minecraft:stone"))
    }

    @Test
    fun `should deserialize key without namespace`() {
        node.set("stone")
        assertDeserialization(key("stone"))
    }

    @Test
    fun `should fail to deserialize invalid key`() {
        node.set("minecraft:stone:invalid")
        assertDeserializationFailsWith<SerializationException>()
    }

    @Test
    fun `should serialize key`() {
        node.set(key("namespace:custom_stone"))
        assertEquals("namespace:custom_stone", node.string)
    }

    @Test
    @Disabled("Needs solution for key without namespace")
    fun `should serialize key without minecraft namespace`() {
        node.set(key("minecraft:stone"))
        assertEquals("stone", node.string)
    }
}
