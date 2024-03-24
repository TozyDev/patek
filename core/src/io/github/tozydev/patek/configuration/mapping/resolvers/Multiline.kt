package io.github.tozydev.patek.configuration.mapping.resolvers

import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.objectmapping.meta.NodeResolver
import java.lang.reflect.AnnotatedElement

/**
 * Marks a property as a multiline string.
 *
 * Fields annotated with this will be serialized from a list of strings to a single string with line breaks.
 */
@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class Multiline {
    companion object Factory : NodeResolver.Factory {
        private const val NEW_LINE = "\n"

        internal val ConfigurationNode.multilineString: String?
            get() = if (isList) childrenList().joinToString(NEW_LINE) { it.rawScalar().toString() } else string

        override fun make(
            name: String,
            element: AnnotatedElement,
        ) = if (element.isAnnotationPresent(Multiline::class.java)) {
            NodeResolver { parent ->
                parent.node(name).apply {
                    if (isList) {
                        set(multilineString)
                    }
                }
            }
        } else {
            null
        }
    }
}
