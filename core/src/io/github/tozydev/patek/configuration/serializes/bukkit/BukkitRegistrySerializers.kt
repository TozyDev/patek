package io.github.tozydev.patek.configuration.serializes.bukkit

import org.bukkit.Keyed
import org.bukkit.Registry
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.serialize.SerializationException

internal object BukkitRegistrySerializers {
    val Collections by lazy {
        @Suppress("UnstableApiUsage")
        mapOf(
            registryItemSerializer(Registry.ADVANCEMENT),
            registryItemSerializer(Registry.ART),
            registryItemSerializer(Registry.ATTRIBUTE),
            registryItemSerializer(Registry.BANNER_PATTERN),
            registryItemSerializer(Registry.BIOME),
            registryItemSerializer(Registry.CAT_VARIANT),
            registryItemSerializer(Registry.ENCHANTMENT),
            registryItemSerializer(Registry.ENTITY_TYPE),
            registryItemSerializer(Registry.INSTRUMENT),
            registryItemSerializer(Registry.LOOT_TABLES),
            registryItemSerializer(Registry.MATERIAL),
            registryItemSerializer(Registry.EFFECT),
            registryItemSerializer(Registry.PARTICLE_TYPE),
            registryItemSerializer(Registry.POTION),
            registryItemSerializer(Registry.STATISTIC),
            registryItemSerializer(Registry.STRUCTURE),
            registryItemSerializer(Registry.STRUCTURE_TYPE),
            registryItemSerializer(Registry.SOUNDS),
            registryItemSerializer(Registry.TRIM_MATERIAL),
            registryItemSerializer(Registry.TRIM_PATTERN),
            registryItemSerializer(Registry.DAMAGE_TYPE),
            registryItemSerializer(Registry.VILLAGER_PROFESSION),
            registryItemSerializer(Registry.VILLAGER_TYPE),
            registryItemSerializer(Registry.FLUID),
            registryItemSerializer(Registry.FROG_VARIANT),
            registryItemSerializer(Registry.GAME_EVENT),
            registryItemSerializer(Registry.POTION_EFFECT_TYPE),
        )
    }

    private inline fun <reified T : Keyed> registryItemSerializer(registry: Registry<T>) = T::class to RegistryItemSerializer(registry)

    inline fun <reified T : Keyed> deserialize(node: ConfigurationNode) =
        node.rawScalar()
            ?.let { Collections[T::class]?.deserialize(T::class.java, it) as T }
            ?: throw SerializationException("${T::class.simpleName} is null")
}
