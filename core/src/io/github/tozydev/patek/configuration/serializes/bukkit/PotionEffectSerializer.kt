package io.github.tozydev.patek.configuration.serializes.bukkit

import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import java.lang.reflect.Type

internal object PotionEffectSerializer : TypeSerializer<PotionEffect> {
    private const val EFFECT = "effect"
    private const val TICKS_DURATION = "ticks-duration"
    private const val AMPLIFIER = "amplifier"
    private const val AMBIENT = "ambient"
    private const val HAS_PARTICLES = "has-particles"
    private const val HAS_ICON = "has-icon"

    override fun deserialize(
        type: Type,
        node: ConfigurationNode,
    ): PotionEffect {
        if (!node.isMap) {
            throw SerializationException("Invalid potion effect format")
        }
        val effect = BukkitRegistrySerializers.deserialize<PotionEffectType>(node.node(EFFECT))
        val duration = node.node(TICKS_DURATION).getInt(PotionEffect.INFINITE_DURATION)
        val amplifier = node.node(AMPLIFIER).getInt(1)
        val ambient = node.node(AMBIENT).getBoolean(true)
        val particles = node.node(HAS_PARTICLES).getBoolean(true)
        val icon = node.node(HAS_ICON).boolean
        return PotionEffect(effect, duration, amplifier, ambient, particles, icon)
    }

    override fun serialize(
        type: Type,
        obj: PotionEffect?,
        node: ConfigurationNode,
    ) {
        if (obj == null) {
            node.raw(null)
            return
        }

        node.node(EFFECT).set(obj.type.key().asMinimalString())
        node.node(TICKS_DURATION).set(obj.duration)
        node.node(AMPLIFIER).set(obj.amplifier)
        node.node(AMBIENT).set(obj.isAmbient)
        node.node(HAS_PARTICLES).set(obj.hasParticles())
        node.node(HAS_ICON).set(obj.hasIcon())
    }
}
