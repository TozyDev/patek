package io.github.tozydev.patek.configuration.serializes

import net.kyori.adventure.key.InvalidKeyException
import net.kyori.adventure.key.Key
import org.spongepowered.configurate.serialize.ScalarSerializer
import org.spongepowered.configurate.serialize.SerializationException
import java.lang.reflect.Type
import java.util.function.Predicate

object KeySerializer : ScalarSerializer<Key>(Key::class.java) {
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
    ): Any = item.asMinimalString()
}
