package io.github.tozydev.patek.configuration.serializes.bukkit

import io.github.tozydev.patek.configuration.serializes.KeySerializer
import io.github.tozydev.patek.key.asBukkit
import org.bukkit.Keyed
import org.bukkit.Registry
import org.spongepowered.configurate.serialize.ScalarSerializer
import org.spongepowered.configurate.serialize.SerializationException
import java.lang.reflect.Type
import java.util.function.Predicate
import kotlin.reflect.KClass

/**
 * Converts a string to a [registry] item and vice versa.
 *
 * @param T the type of the registry item
 * @param registry the registry to get the item from
 */
class RegistryItemSerializer<T : Keyed>
    @PublishedApi
    internal constructor(
        private val kClass: KClass<T>,
        private val registry: Registry<T>,
    ) : ScalarSerializer<T>(kClass.java) {
        override fun deserialize(
            type: Type,
            obj: Any,
        ): T =
            KeySerializer.tryDeserialize(obj)?.asBukkit()?.let { registry.get(it) }
                ?: throw SerializationException("Unknown ${kClass.simpleName}: $obj")

        override fun serialize(
            item: T,
            typeSupported: Predicate<Class<*>>?,
        ): Any = KeySerializer.serialize(item)
    }

/**
 * Creates a scalar serializer for the given [registry] item.
 *
 * @param T the type of the registry item
 * @param registry the registry to get the item from
 */
@Suppress("FunctionName", "RedundantSuppression")
inline fun <reified T : Keyed> RegistryItemSerializer(registry: Registry<T>) = RegistryItemSerializer(T::class, registry)
