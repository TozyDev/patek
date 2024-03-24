package io.github.tozydev.patek.configuration.serializes

import io.leangen.geantyref.TypeToken
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.ConfigurationOptions
import org.spongepowered.configurate.kotlin.node
import org.spongepowered.configurate.serialize.ScalarSerializer
import org.spongepowered.configurate.serialize.TypeSerializer
import kotlin.reflect.KClass
import kotlin.test.BeforeTest
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertNull

abstract class SerializerTestBase<T : Any>(val type: TypeToken<T>, private val serializer: TypeSerializer<T>) {
    constructor(serializer: ScalarSerializer<T>) : this(serializer.type(), serializer)
    constructor(kClass: KClass<T>, serializer: TypeSerializer<T>) : this(TypeToken.get(kClass.java), serializer)

    protected lateinit var node: ConfigurationNode

    @BeforeTest
    open fun setup() {
        node = node(ConfigurationOptions.defaults().serializers { it.register(type, serializer) }) {}
    }

    protected fun assertDeserialization(expected: T) {
        assertEquals(expected, node.get(type))
    }

    protected inline fun <reified T : Throwable> assertDeserializationFailsWith(message: String? = null) {
        assertFailsWith<T>(message) {
            node.get(type)
        }
    }

    protected fun assertNullDeserialization() {
        assertNull(node.get(type))
    }
}
