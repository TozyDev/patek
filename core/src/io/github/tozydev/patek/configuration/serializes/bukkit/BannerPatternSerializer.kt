package io.github.tozydev.patek.configuration.serializes.bukkit

import io.github.tozydev.patek.configuration.serializes.KeySerializer
import io.github.tozydev.patek.utils.BannerPattern
import org.bukkit.DyeColor
import org.bukkit.block.banner.PatternType
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import java.lang.reflect.Type

object BannerPatternSerializer : TypeSerializer<BannerPattern> {
    private const val PATTERN = "pattern"
    private const val COLOR = "color"

    override fun deserialize(
        type: Type,
        node: ConfigurationNode,
    ): BannerPattern {
        if (!node.isMap) {
            throw SerializationException("Invalid banner pattern format")
        }
        val pattern = deserializePatternType(node.node(PATTERN).rawScalar())
        val color =
            node.node("color").run {
                this[DyeColor::class.java] ?: throw SerializationException("Invalid dye color: ${this.rawScalar()}")
            }
        return BannerPattern(color, pattern)
    }

    private fun deserializePatternType(input: Any?): PatternType {
        val key = KeySerializer.deserialize(input)
        return PatternType.entries.firstOrNull { it.key == key }
            ?: throw SerializationException("Unknown pattern type: $key")
    }

    override fun serialize(
        type: Type,
        obj: BannerPattern?,
        node: ConfigurationNode,
    ) {
        if (obj == null) {
            node.raw(null)
            return
        }

        node.node(PATTERN).raw(obj.pattern.key().asMinimalString())
        node.node(COLOR).raw(obj.color)
    }
}
