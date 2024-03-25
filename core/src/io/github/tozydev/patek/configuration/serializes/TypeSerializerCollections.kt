@file:Suppress("unused")

package io.github.tozydev.patek.configuration.serializes

import io.github.tozydev.patek.configuration.serializes.bukkit.BukkitTypeSerializers
import org.spongepowered.configurate.serialize.TypeSerializer
import org.spongepowered.configurate.serialize.TypeSerializerCollection

/**
 * Collection of type serializers that are used for various types in the Patek library.
 *
 * Registered TypeSerializers:
 * - DurationSerializer: Serializer for serializing and deserializing Duration objects.
 * - KeySerializer: Serializer for serializing and deserializing Key objects.
 * - MessageSerializer: Serializer for serializing and deserializing Message objects.
 * - BukkitTypeSerializers: A collection of TypeSerializers for Bukkit related types.
 */
val PatekTypeSerializers: TypeSerializerCollection by lazy {
    TypeSerializerCollection.builder()
        .registerExact(DurationSerializer)
        .register(KeySerializer)
        .register(MessageSerializer)
        .registerAll(BukkitTypeSerializers)
        .build()
}

/**
 * Registers a [TypeSerializer] for a specific type [T] in the [TypeSerializerCollection.Builder].
 *
 * @param T The target type to register the serializer for.
 * @param serializer The [TypeSerializer] to register.
 */
inline fun <reified T> TypeSerializerCollection.Builder.register(serializer: TypeSerializer<T>) =
    register(T::class.java, serializer)
