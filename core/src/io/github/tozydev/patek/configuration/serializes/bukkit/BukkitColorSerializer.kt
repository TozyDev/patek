package io.github.tozydev.patek.configuration.serializes.bukkit

import io.github.tozydev.patek.utils.BukkitColor
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import java.lang.reflect.Type

internal object BukkitColorSerializer : TypeSerializer<BukkitColor> {
    private const val DEFAULT_ALPHA = 255

    internal const val ALPHA = "alpha"
    internal const val RED = "red"
    internal const val GREEN = "green"
    internal const val BLUE = "blue"

    override fun deserialize(
        type: Type,
        node: ConfigurationNode,
    ) = when {
        node.isMap -> deserializeFromMap(node)
        node.rawScalar() is Number -> BukkitColor(node.long.toString(16))
        else -> deserializeFromString(node)
    }

    private fun deserializeFromString(node: ConfigurationNode) =
        try {
            BukkitColor(node.rawScalar().toString())
        } catch (e: NumberFormatException) {
            throw SerializationException("Invalid color format")
        }

    private fun deserializeFromMap(node: ConfigurationNode): BukkitColor {
        val alpha =
            if (node.node(ALPHA).empty()) {
                DEFAULT_ALPHA
            } else {
                node.node(ALPHA).int
            }
        val red = node.node(RED).int
        val green = node.node(GREEN).int
        val blue = node.node(BLUE).int
        return BukkitColor.fromARGB(alpha, red, green, blue)
    }

    override fun serialize(
        type: Type,
        obj: BukkitColor?,
        node: ConfigurationNode,
    ) {
        if (obj == null) {
            node.set(null)
            return
        }

        node.node(ALPHA).set(obj.alpha)
        node.node(RED).set(obj.red)
        node.node(GREEN).set(obj.green)
        node.node(BLUE).set(obj.blue)
    }
}
