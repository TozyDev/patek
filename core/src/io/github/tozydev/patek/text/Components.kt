package io.github.tozydev.patek.text

import io.github.tozydev.patek.text.minimessage.miniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

/**
 * Deserializes the given string using _MiniMessage_ format with optional [tagResolvers].
 *
 * @param tagResolvers The array of tag resolvers to be used during deserialization.
 * @return The deserialized component.
 * @see net.kyori.adventure.text.minimessage.MiniMessage.deserialize
 */
fun String.text(vararg tagResolvers: TagResolver) = miniMessage.deserialize(this, *tagResolvers)
