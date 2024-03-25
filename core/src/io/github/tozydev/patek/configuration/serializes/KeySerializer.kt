package io.github.tozydev.patek.configuration.serializes

import net.kyori.adventure.key.InvalidKeyException
import net.kyori.adventure.key.Key
import org.bukkit.Keyed
import org.spongepowered.configurate.serialize.ScalarSerializer
import org.spongepowered.configurate.serialize.SerializationException
import java.lang.reflect.Type
import java.util.function.Predicate

internal object KeySerializer : ScalarSerializer<Key>(Key::class.java) {
    override fun deserialize(
        type: Type,
        obj: Any,
    ) = try {
        Key.key(obj.toString())
    } catch (e: InvalidKeyException) {
        throw SerializationException(e)
    }

    override fun serialize(
        item: Key,
        typeSupported: Predicate<Class<*>>,
    ): Any = serialize(item)

    internal fun serialize(item: Key) = item.asMinimalString()

    internal fun serialize(item: Keyed): String = serialize(item.key())
}
