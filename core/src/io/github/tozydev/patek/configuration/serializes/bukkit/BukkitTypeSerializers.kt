@file:Suppress("unused")

package io.github.tozydev.patek.configuration.serializes.bukkit

import io.github.tozydev.patek.configuration.serializes.register
import org.spongepowered.configurate.serialize.TypeSerializerCollection

/**
 * Returns the collection of type serializers for Bukkit types.
 *
 * Supported types:
 * - [io.github.tozydev.patek.utils.BukkitColor] ([org.bukkit.Color])
 * - [org.bukkit.Location]
 * - [org.bukkit.inventory.meta.trim.ArmorTrim]
 * - [io.github.tozydev.patek.utils.BannerPattern]
 * - [org.bukkit.potion.PotionEffect]
 * - Almost all Bukkit [org.bukkit.Registry] types.
 */
val BukkitTypeSerializers: TypeSerializerCollection by lazy {
    TypeSerializerCollection.builder().apply {
        register(BukkitColorSerializer)
        register(LocationSerializer)
        register(ArmorTrimSerializer)
        register(BannerPatternSerializer)
        register(PotionEffectSerializer)

        BukkitRegistrySerializers.Collections.forEach { registerExact(it.value) }
    }.build()
}
