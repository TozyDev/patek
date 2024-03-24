package io.github.tozydev.patek.configuration.serializes.bukkit

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
        val material = BukkitRegistrySerializers.deserialize<TrimMaterial>(node.node(MATERIAL))
        val pattern = BukkitRegistrySerializers.deserialize<TrimPattern>(node.node(PATTERN))
        return ArmorTrim(material, pattern)
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
