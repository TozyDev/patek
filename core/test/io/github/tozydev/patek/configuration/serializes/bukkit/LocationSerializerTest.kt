package io.github.tozydev.patek.configuration.serializes.bukkit

import io.github.tozydev.patek.configuration.serializes.SerializerTestBase
import io.github.tozydev.patek.configuration.serializes.bukkit.LocationSerializer.PITCH
import io.github.tozydev.patek.configuration.serializes.bukkit.LocationSerializer.WORLD
import io.github.tozydev.patek.configuration.serializes.bukkit.LocationSerializer.X
import io.github.tozydev.patek.configuration.serializes.bukkit.LocationSerializer.Y
import io.github.tozydev.patek.configuration.serializes.bukkit.LocationSerializer.YAW
import io.github.tozydev.patek.configuration.serializes.bukkit.LocationSerializer.Z
import io.github.tozydev.patek.server.PaperServer
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkObject
import org.bukkit.Location
import org.bukkit.World
import org.spongepowered.configurate.serialize.SerializationException
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class LocationSerializerTest : SerializerTestBase<Location>(Location::class, LocationSerializer) {
    @BeforeTest
    override fun setup() {
        mockkObject(PaperServer)
        super.setup()
    }

    @AfterTest
    fun tearDown() {
        unmockkObject(PaperServer)
    }

    @Test
    fun `should deserialize location from map`() {
        val world: World = mockk()
        every { PaperServer.getWorld("world") } returns world
        node.node(WORLD).set("world")
        node.node(X).set(1.0)
        node.node(Y).set(2.0)
        node.node(Z).set(3.0)
        node.node(YAW).set(4.0)
        node.node(PITCH).set(5.0)

        assertDeserialization(Location(world, 1.0, 2.0, 3.0, 4.0f, 5.0f))
    }

    @Test
    fun `should deserialize location from map with null world`() {
        node.node(X).set(1.0)
        node.node(Y).set(2.0)
        node.node(Z).set(3.0)
        node.node(YAW).set(4.0)
        node.node(PITCH).set(5.0)

        assertDeserialization(Location(null, 1.0, 2.0, 3.0, 4.0f, 5.0f))
    }

    @Test
    fun `should deserialize location from map without yaw and pitch`() {
        node.node(X).set(1.0)
        node.node(Y).set(2.0)
        node.node(Z).set(3.0)

        assertDeserialization(Location(null, 1.0, 2.0, 3.0))
    }

    @Test
    fun `should deserialize location from string`() {
        val world: World = mockk()
        every { PaperServer.getWorld("world") } returns world
        node.set("world, 1.0; 2.0, 3.0 4.0, 5.0")

        assertDeserialization(Location(world, 1.0, 2.0, 3.0, 4.0f, 5.0f))
    }

    @Test
    fun `should deserialize location from string with null world`() {
        node.set("1.0, 2.0, 3.0, 4.0, 5.0")

        assertDeserialization(Location(null, 1.0, 2.0, 3.0, 4.0f, 5.0f))
    }

    @Test
    fun `should deserialize location from string without yaw and pitch`() {
        node.set("1.0, 2.0, 3.0")

        assertDeserialization(Location(null, 1.0, 2.0, 3.0))
    }

    @Test
    fun `should throw exception when deserializing invalid location`() {
        node.set("1.0, 2.0")

        assertDeserializationFailsWith<SerializationException>("Invalid location format")
    }

    @Test
    fun `should serialize location to map`() {
        val world: World = mockk()
        every { world.name } returns "world"
        every { PaperServer.getWorld("world") } returns world
        val location = Location(world, 1.0, 2.0, 3.0, 4.0f, 5.0f)
        node.set(location)

        assertEquals("world", node.node(WORLD).string)
        assertEquals(1.0, node.node(X).double)
        assertEquals(2.0, node.node(Y).double)
        assertEquals(3.0, node.node(Z).double)
        assertEquals(4.0, node.node(YAW).double)
        assertEquals(5.0, node.node(PITCH).double)
    }

    @Test
    fun `should serialize location to map with null world`() {
        val location = Location(null, 1.0, 2.0, 3.0, 4.0f, 5.0f)
        node.set(location)

        assertTrue(node.node(WORLD).empty())
        assertEquals(1.0, node.node(X).double)
        assertEquals(2.0, node.node(Y).double)
        assertEquals(3.0, node.node(Z).double)
        assertEquals(4.0, node.node(YAW).double)
        assertEquals(5.0, node.node(PITCH).double)
    }
}
