package io.github.tozydev.patek.configuration.serializes.bukkit

import io.github.tozydev.patek.configuration.serializes.KeySerializer
import io.github.tozydev.patek.key.asBukkit
import org.bukkit.Registry
import org.bukkit.enchantments.Enchantment
import org.spongepowered.configurate.serialize.ScalarSerializer
import org.spongepowered.configurate.serialize.SerializationException
import java.lang.reflect.Type
import java.util.function.Predicate

internal object EnchantmentSerializer : ScalarSerializer<Enchantment>(Enchantment::class.java) {
    override fun deserialize(
        type: Type,
        obj: Any,
    ): Enchantment =
        KeySerializer.tryDeserialize(obj)?.asBukkit()?.let { Registry.ENCHANTMENT.get(it) }
            ?: throw SerializationException("Unknown enchantment: $obj")

    override fun serialize(
        item: Enchantment,
        typeSupported: Predicate<Class<*>>,
    ): Any = item.key.asMinimalString()
}
