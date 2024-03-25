package io.github.tozydev.patek.configuration.serializes

import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.serialize.ScalarSerializer
import org.spongepowered.configurate.serialize.SerializationException
import java.lang.reflect.Type
import java.util.function.Predicate
import kotlin.time.Duration

/**
 * Converts a value to and from a [Duration].
 *
 * The following formats are accepted:
 *
 * - ISO-8601 Duration format, e.g. `P1DT2H3M4.058S`, see [Duration.toIsoString] and [Duration.parseIsoString].
 * - The format of string returned by the default [Duration.toString] and `toString` in a specific unit,
 *   e.g. `10s`, `1h 30m` or `-(1h 30m)`.
 */
internal object DurationSerializer : ScalarSerializer<Duration>(Duration::class.java) {
    override fun deserialize(
        type: Type,
        obj: Any,
    ) = try {
        Duration.parse(obj.toString())
    } catch (e: IllegalArgumentException) {
        throw SerializationException(e)
    }

    override fun serialize(
        item: Duration,
        typeSupported: Predicate<Class<*>>,
    ): Any = item.toString()
}

/**
 * Attempts to deserialize a [Duration] from the given [ConfigurationNode].
 */
val ConfigurationNode.duration: Duration? get() = DurationSerializer.tryDeserialize(rawScalar())
