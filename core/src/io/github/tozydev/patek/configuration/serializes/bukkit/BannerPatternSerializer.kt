package io.github.tozydev.patek.configuration.serializes.bukkit

import io.github.tozydev.patek.configuration.serializes.KeySerializer
import io.github.tozydev.patek.utils.BannerPattern
import org.bukkit.DyeColor
import org.bukkit.block.banner.PatternType
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import java.lang.reflect.Type

internal object BannerPatternSerializer : TypeSerializer<BannerPattern> {
    private const val PATTERN = "pattern"
    private const val COLOR = "color"

    override fun deserialize(
        type: Type,
        node: ConfigurationNode,
    ): BannerPattern {
        if (!node.isMap) {
            throw SerializationException("Invalid banner pattern format")
        }
        val pattern = BukkitRegistrySerializers.deserialize<PatternType>(node.node(PATTERN))
        val color = node.node(COLOR).get<DyeColor>() ?: throw SerializationException("Missing color")
        return BannerPattern(color, pattern)
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

        node.node(PATTERN).raw(KeySerializer.serialize(obj.pattern))
        node.node(COLOR).raw(obj.color)
    }
}
