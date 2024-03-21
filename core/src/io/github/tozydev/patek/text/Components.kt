package io.github.tozydev.patek.text

import io.github.tozydev.patek.text.minimessage.miniMessage
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

/**
 * Deserializes the given string using _MiniMessage_ format with optional [tagResolvers].
 *
 * @receiver The string in _MiniMessage_ format to be deserialized.
 * @param tagResolvers The array of tag resolvers to be used during deserialization.
 * @return The deserialized component.
 * @see net.kyori.adventure.text.minimessage.MiniMessage.deserialize
 */
fun String.text(vararg tagResolvers: TagResolver) = miniMessage.deserialize(this, *tagResolvers)

/**
 * Returns this Component if it is not null, otherwise returns an empty Component.
 *
 * @receiver the Component being checked for null
 * @return the original Component if it is not null, a new empty Component otherwise
 */
fun Component?.orEmpty() = this ?: Component.empty()
