package io.github.tozydev.patek.audience

import io.github.tozydev.patek.text.orEmpty
import io.github.tozydev.patek.text.text
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.sound.Sound.Source
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import net.kyori.adventure.title.Title
import org.bukkit.NamespacedKey
import kotlin.time.Duration
import kotlin.time.toJavaDuration

/**
 * Represents a message that can be sent to a [Audience].
 *
 * @see TextMessage
 * @see ActionBarMessage
 * @see TitleMessage
 * @see SoundMessage
 * @see CompositeMessage
 */
sealed interface Message

/**
 * Represents a text message that can be sent to an audience.
 *
 * @property text The text content of the message.
 *
 * @see Audience.sendMessage
 */
data class TextMessage(val text: String) : Message

/**
 * Represents an action bar message that can be sent to an audience.
 *
 * @property actionBar The action bar content of the message.
 *
 * @see Audience.sendActionBar
 */
data class ActionBarMessage(val actionBar: String) : Message

/**
 * Represents a title message that can be displayed on audience screen.
 *
 * @property title The title of the message.
 * @property subtitle The subtitle of the message.
 * @property times The times for the message to fadeIn, stay, and fadeOut.
 *
 * @see Audience.showTitle
 */
data class TitleMessage(
    val title: String? = null,
    val subtitle: String? = null,
    val times: Times? = null,
) : Message {
    /**
     * Represents the duration for the fadeIn, stay, and fadeOut effects of a title message.
     *
     * @property fadeIn The duration of the fadeIn effect in milliseconds.
     * @property stay The duration the message should stay on screen in milliseconds.
     * @property fadeOut The duration of the fadeOut effect in milliseconds.
     */
    data class Times(
        val fadeIn: Duration,
        val stay: Duration,
        val fadeOut: Duration,
    )
}

/**
 * Represents a sound that can be played to an audience.
 *
 * @property sound The sound to play.
 * @property source The source of the sound.
 * @property volume The volume of the sound. Must be a positive number.
 * @property pitch The pitch of the sound. Must be between -1 and 1.
 *
 * @see org.bukkit.Sound
 * @see Audience.playSound
 */
data class SoundMessage(
    val sound: String,
    val source: Source? = null,
    val volume: Float? = null,
    val pitch: Float? = null,
) : Message

/**
 * Represents a composite message that can be sent to an audience.
 *
 * @property messages The list of messages in the composite message.
 */
data class CompositeMessage(val messages: List<Message> = emptyList()) : Message

/**
 * Sends a message to the audience.
 *
 * This function use [net.kyori.adventure.text.minimessage.MiniMessage] to deserialize the message string to a
 * component.
 *
 * @receiver The audience to send the message to.
 * @param message The message to be sent.
 * @param tagResolvers The array of tag resolvers to be used during deserialization.
 */
fun Audience.sendMessage(
    message: Message,
    vararg tagResolvers: TagResolver,
) {
    when (message) {
        is TextMessage -> sendMessage(message.text.text(*tagResolvers))
        is ActionBarMessage -> sendActionBar(message.actionBar.text(*tagResolvers))
        is TitleMessage -> showTitle(message.toAdventureTitle(*tagResolvers))
        is SoundMessage -> playSound(message.toAdventureSound())
        is CompositeMessage -> message.messages.forEach { sendMessage(it) }
    }
}

private fun TitleMessage.toAdventureTitle(vararg tagResolvers: TagResolver) =
    Title.title(
        title?.text(*tagResolvers).orEmpty(),
        subtitle?.text(*tagResolvers).orEmpty(),
        times?.toAdventureTimes(),
    )

private fun TitleMessage.Times.toAdventureTimes() =
    Title.Times.times(
        fadeIn.toJavaDuration(),
        stay.toJavaDuration(),
        fadeOut.toJavaDuration(),
    )

private fun SoundMessage.toAdventureSound() =
    Sound.sound().run {
        val type =
            try {
                org.bukkit.Sound.valueOf(sound.uppercase()).key
            } catch (e: IllegalArgumentException) {
                val key = NamespacedKey.minecraft(sound)
                org.bukkit.Sound.entries.firstOrNull { it.key == key }?.key ?: key
            }
        type(type)
        source?.let { source(it) }
        volume?.let { volume(volume) }
        pitch?.let { pitch(pitch) }
        build()
    }
