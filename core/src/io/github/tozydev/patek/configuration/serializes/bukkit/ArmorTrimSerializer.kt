package io.github.tozydev.patek.configuration.serializes.bukkit

import io.github.tozydev.patek.configuration.serializes.KeySerializer
import io.github.tozydev.patek.key.asBukkit
import org.bukkit.Registry
import org.bukkit.inventory.meta.trim.ArmorTrim
import org.bukkit.inventory.meta.trim.TrimMaterial
import org.bukkit.inventory.meta.trim.TrimPattern
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import java.lang.reflect.Type

internal object ArmorTrimSerializer : TypeSerializer<ArmorTrim> {
    private const val MATERIAL = "material"
    private const val PATTERN = "pattern"

    override fun deserialize(
        type: Type,
        node: ConfigurationNode,
    ): ArmorTrim {
        if (!node.isMap) {
            throw SerializationException("Invalid armor trim format")
        }
        val material = deserializeTrimMaterial(node.node(MATERIAL).rawScalar())
        val pattern = deserializeTrimPattern(node.node(PATTERN).rawScalar())
        return ArmorTrim(material, pattern)
    }

    private fun deserializeTrimMaterial(input: Any?): TrimMaterial {
        val key = KeySerializer.deserialize(input)
        return Registry.TRIM_MATERIAL[KeySerializer.deserialize(input).asBukkit()]
            ?: throw SerializationException("Unknown trim material: $key")
    }

    private fun deserializeTrimPattern(input: Any?): TrimPattern {
        val key = KeySerializer.deserialize(input)
        return Registry.TRIM_PATTERN[KeySerializer.deserialize(input).asBukkit()]
            ?: throw SerializationException("Unknown trim pattern: $key")
    }

    override fun serialize(
        type: Type,
        obj: ArmorTrim?,
        node: ConfigurationNode,
    ) {
        if (obj == null) {
            node.raw(null)
            return
        }

        node.node(MATERIAL).raw(obj.material.key().asMinimalString())
        node.node(PATTERN).raw(obj.pattern.key().asMinimalString())
    }
}
