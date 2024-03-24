package io.github.tozydev.patek.configuration.serializes

import org.spongepowered.configurate.serialize.TypeSerializer
import org.spongepowered.configurate.serialize.TypeSerializerCollection

/**
 * Registers a [TypeSerializer] for a specific type [T] in the [TypeSerializerCollection.Builder].
 *
 * @param T The target type to register the serializer for.
 * @param serializer The [TypeSerializer] to register.
 */
inline fun <reified T> TypeSerializerCollection.Builder.register(serializer: TypeSerializer<T>) {
    register(T::class.java, serializer)
}
