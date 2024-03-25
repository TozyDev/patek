package io.github.tozydev.patek.configuration.serializes.bukkit

import io.github.tozydev.patek.configuration.serializes.SerializerTestBase
import io.github.tozydev.patek.configuration.serializes.bukkit.BukkitColorSerializer.BLUE
import io.github.tozydev.patek.configuration.serializes.bukkit.BukkitColorSerializer.GREEN
import io.github.tozydev.patek.configuration.serializes.bukkit.BukkitColorSerializer.RED
import io.github.tozydev.patek.utils.BukkitColor
import org.spongepowered.configurate.serialize.SerializationException
import kotlin.test.Test
import kotlin.test.assertEquals

class BukkitColorSerializerTest : SerializerTestBase<BukkitColor>(BukkitColor::class, BukkitColorSerializer) {
    @Test
    fun `should deserialize a rgb hex string`() {
        node.set(YELLOW_RBG_HEX.toString(16))
        assertDeserialization(YELLOW_RGB)
    }

    @Test
    fun `should deserialize a argb hex string`() {
        node.set(YELLOW_ARGB_HEX.toString(16))
        assertDeserialization(YELLOW_ARGB)
    }

    @Test
    fun `should deserialize a rgb map`() {
        node.node(RED).set(YELLOW_RGB.red)
        node.node(GREEN).set(YELLOW_RGB.green)
        node.node(BLUE).set(YELLOW_RGB.blue)
        assertDeserialization(YELLOW_RGB)
    }

    @Test
    fun `should deserialize a argb map`() {
        node.node(BukkitColorSerializer.ALPHA).set(TEST_ALPHA)
        node.node(RED).set(YELLOW_RGB.red)
        node.node(GREEN).set(YELLOW_RGB.green)
        node.node(BLUE).set(YELLOW_RGB.blue)
        assertDeserialization(YELLOW_ARGB)
    }

    @Test
    fun `should deserialize a rbg int`() {
        node.set(YELLOW_RBG_HEX)
        assertDeserialization(YELLOW_RGB)
    }

    @Test
    fun `should deserialize a argb int`() {
        node.set(YELLOW_ARGB_HEX)
        assertDeserialization(YELLOW_ARGB)
    }

    @Test
    fun `should throw an exception when deserializing an invalid color`() {
        node.set("invalid")
        assertDeserializationFailsWith<SerializationException>()
    }

    @Test
    fun `should serialize a rgb color`() {
        node.set(YELLOW_RGB)
        assertEquals(YELLOW_RGB.red, node.node(RED).int)
        assertEquals(YELLOW_RGB.green, node.node(GREEN).int)
        assertEquals(YELLOW_RGB.blue, node.node(BLUE).int)
    }

    companion object {
        private const val TEST_ALPHA = 0xaa
        private val YELLOW_RGB = BukkitColor.YELLOW
        private val YELLOW_ARGB = YELLOW_RGB.setAlpha(TEST_ALPHA)

        private val YELLOW_RBG_HEX = YELLOW_RGB.asRGB()
        private const val YELLOW_ARGB_HEX = 0xaaffff00
    }
}
