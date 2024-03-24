package io.github.tozydev.patek.configuration.serializes.bukkit

import io.github.tozydev.patek.world.worldOrNull
import net.kyori.adventure.key.Key
import org.bukkit.Location
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import java.lang.reflect.Type

internal object LocationSerializer : TypeSerializer<Location> {
    private val partDelimiters = charArrayOf(' ', ',', ';')

    internal const val WORLD = "world"
    internal const val X = "x"
    internal const val Y = "y"
    internal const val Z = "z"
    internal const val YAW = "yaw"
    internal const val PITCH = "pitch"

    override fun deserialize(
        type: Type,
        node: ConfigurationNode,
    ): Location {
        if (node.isMap) {
            return deserializeFromMap(node)
        }
        return parseString(node.string ?: invalidLocationFormat())
    }

    private fun deserializeFromMap(node: ConfigurationNode): Location {
        val world = node.node(WORLD).string?.let { worldOrNull(it) }
        val x = node.node(X).double
        val y = node.node(Y).double
        val z = node.node(Z).double
        val yaw = node.node(YAW).float
        val pitch = node.node(PITCH).float
        return Location(world, x, y, z, yaw, pitch)
    }

    private fun findWord(input: String) = worldOrNull(input) ?: worldOrNull(Key.key(input))

    private fun parseString(input: String): Location {
        fun xyz(
            parts: List<String>,
            offset: Int,
        ) = Triple(
            parts[offset].toDouble(),
            parts[offset + 1].toDouble(),
            parts[offset + 2].toDouble(),
        )

        fun yawPitch(
            parts: List<String>,
            offset: Int,
        ) = Pair(
            parts[offset].toFloat(),
            parts[offset + 1].toFloat(),
        )

        val parts = input.split(delimiters = partDelimiters).filter { it.isNotBlank() }.map { it.trim() }
        return when (parts.size) {
            3 -> {
                val (x, y, z) = xyz(parts, 0)
                Location(null, x, y, z)
            }

            4 -> {
                val world = findWord(parts[0])
                val (x, y, z) = xyz(parts, 1)
                Location(world, x, y, z)
            }

            5 -> {
                val (x, y, z) = xyz(parts, 0)
                val (yaw, pitch) = yawPitch(parts, 3)
                Location(null, x, y, z, yaw, pitch)
            }

            6 -> {
                val world = findWord(parts[0])
                val (x, y, z) = xyz(parts, 1)
                val (yaw, pitch) = yawPitch(parts, 4)
                Location(world, x, y, z, yaw, pitch)
            }

            else -> invalidLocationFormat()
        }
    }

    private fun invalidLocationFormat(): Nothing = throw SerializationException("Invalid location format")

    override fun serialize(
        type: Type,
        obj: Location?,
        node: ConfigurationNode,
    ) {
        if (obj == null) {
            node.raw(null)
            return
        }

        if (obj.world != null) {
            node.node(WORLD).set(obj.world!!.name)
        }
        node.node(X).set(obj.x)
        node.node(Y).set(obj.y)
        node.node(Z).set(obj.z)
        node.node(YAW).set(obj.yaw)
        node.node(PITCH).set(obj.pitch)
    }
}
