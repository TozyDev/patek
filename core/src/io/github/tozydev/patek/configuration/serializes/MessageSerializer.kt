package io.github.tozydev.patek.configuration.serializes

import io.github.tozydev.patek.audience.ActionBarMessage
import io.github.tozydev.patek.audience.CompositeMessage
import io.github.tozydev.patek.audience.Message
import io.github.tozydev.patek.audience.SoundMessage
import io.github.tozydev.patek.audience.TextMessage
import io.github.tozydev.patek.audience.TitleMessage
import io.github.tozydev.patek.configuration.mapping.resolvers.Multiline.Factory.multilineString
import net.kyori.adventure.sound.Sound
import org.spongepowered.configurate.ConfigurationNode
import org.spongepowered.configurate.kotlin.extensions.get
import org.spongepowered.configurate.serialize.SerializationException
import org.spongepowered.configurate.serialize.TypeSerializer
import java.lang.reflect.Type

@Suppress("FunctionName", "RedundantSuppression")
internal object MessageSerializer : TypeSerializer<Message> {
    internal const val TEXT = "text"
    internal const val ACTION_BAR = "action-bar"
    internal const val TITLE = "title"
    internal const val SUBTITLE = "subtitle"
    internal const val TIMES = "times"
    internal const val FADE_IN = "fade-in"
    internal const val STAY = "stay"
    internal const val FADE_OUT = "fade-out"
    internal const val SOUND = "sound"
    internal const val SOURCE = "source"
    internal const val VOLUME = "volume"
    internal const val PITCH = "pitch"

    override fun deserialize(
        type: Type,
        node: ConfigurationNode,
    ) = deserializeNode(node)

    private fun deserializeNode(node: ConfigurationNode): Message {
        if (node.isList) {
            return node.childrenList().map { deserializeNode(it) }.let(::CompositeMessage)
        }

        if (!node.isMap) {
            return TextMessage(node)
        }

        node.node(TEXT).takeUnless { it.empty() }?.let { return TextMessage(it) }
        node.node(ACTION_BAR).takeUnless { it.empty() }?.let { return ActionBarMessage(it) }

        val isTitleNode = !(node.node(TITLE).empty() && node.node(SUBTITLE).empty() && node.node(TIMES).empty())
        if (isTitleNode) {
            return TitleMessage(node)
        }

        if (node.node(SOUND).empty().not()) {
            return SoundMessage(node)
        }

        throw SerializationException("Unknown message type")
    }

    private fun TextMessage(node: ConfigurationNode) = TextMessage(node.multilineString.orEmpty())

    private fun ActionBarMessage(node: ConfigurationNode) = ActionBarMessage(node.string.orEmpty())

    private fun TitleMessage(node: ConfigurationNode): TitleMessage {
        val title = node.node(TITLE).string
        val subtitle = node.node(SUBTITLE).string
        return TitleMessage(title, subtitle, node.node(TIMES).takeUnless { it.empty() }?.let(::Times))
    }

    private fun Times(node: ConfigurationNode): TitleMessage.Times {
        val fadeIn = node.node(FADE_IN).duration ?: throw SerializationException("Missing $FADE_IN duration")
        val stay = node.node(STAY).duration ?: throw SerializationException("Missing $STAY duration")
        val fadeOut = node.node(FADE_OUT).duration ?: throw SerializationException("Missing $FADE_OUT duration")
        return TitleMessage.Times(fadeIn, stay, fadeOut)
    }

    private fun SoundMessage(node: ConfigurationNode): SoundMessage {
        val sound = node.node(SOUND).string ?: throw SerializationException("Missing $SOUND")
        val source = node.node(SOURCE).get<Sound.Source>()
        val volume = node.node(VOLUME).takeUnless { it.empty() }?.float
        val pitch = node.node(PITCH).takeUnless { it.empty() }?.float
        return SoundMessage(sound, source, volume, pitch)
    }

    override fun serialize(
        type: Type,
        obj: Message?,
        node: ConfigurationNode,
    ) {
        if (obj == null) {
            node.raw(null)
            return
        }

        when (obj) {
            is ActionBarMessage -> node.node(ACTION_BAR).set(obj.actionBar)
            is CompositeMessage -> node.setList(Message::class.java, obj.messages)
            is SoundMessage -> {
                node.node(SOUND).set(obj.sound)
                node.node(SOURCE).set(obj.source)
                node.node(VOLUME).set(obj.volume)
                node.node(PITCH).set(obj.pitch)
            }

            is TextMessage -> node.raw(obj.text)
            is TitleMessage -> {
                node.node(TITLE).set(obj.title)
                node.node(SUBTITLE).set(obj.subtitle)
                if (obj.times != null) {
                    node.node(TIMES).apply {
                        node(FADE_IN).set(obj.times.fadeIn)
                        node(STAY).set(obj.times.stay)
                        node(FADE_OUT).set(obj.times.fadeOut)
                    }
                }
            }
        }
    }
}
