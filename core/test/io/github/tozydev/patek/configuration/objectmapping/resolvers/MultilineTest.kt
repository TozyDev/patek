package io.github.tozydev.patek.configuration.objectmapping.resolvers

import io.github.tozydev.patek.configuration.mapping.resolvers.Multiline
import org.spongepowered.configurate.kotlin.dataClassFieldDiscoverer
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.kotlin.node
import org.spongepowered.configurate.objectmapping.ConfigSerializable
import org.spongepowered.configurate.objectmapping.ObjectMapper
import kotlin.test.Test
import kotlin.test.assertEquals

class MultilineTest {
    private val objectMapper =
        ObjectMapper.factoryBuilder()
            .addDiscoverer(dataClassFieldDiscoverer())
            .addNodeResolver(Multiline.Factory)
            .build()
            .get<TestClass>()

    @Test
    fun `should join list of strings with new line`() {
        val mapped =
            objectMapper.load(
                node {
                    node("multiline").set(listOf("lorem", "ipsum", "dolor"))
                },
            )
        assertEquals("lorem\nipsum\ndolor", mapped.multiline)
    }

    @Test
    fun `should load a single string`() {
        val mapped =
            objectMapper.load(
                node {
                    node("multiline").set("lorem ipsum dolor")
                },
            )
        assertEquals("lorem ipsum dolor", mapped.multiline)
    }

    @Test
    fun `should load list of other type to string`() {
        val mapped =
            objectMapper.load(
                node {
                    node("multiline").set(listOf(1, 2, 3))
                },
            )
        assertEquals("1\n2\n3", mapped.multiline)
    }

    @ConfigSerializable
    data class TestClass(
        @Multiline val multiline: String,
    )
}
