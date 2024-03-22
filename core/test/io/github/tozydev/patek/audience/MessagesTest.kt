package io.github.tozydev.patek.audience

import io.mockk.confirmVerified
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.justRun
import io.mockk.verify
import io.mockk.verifyAll
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.Test

@ExtendWith(MockKExtension::class)
class MessagesTest {
    @MockK
    lateinit var audience: Audience

    @Test
    fun `Audience_sendMessage() should send a text message`() {
        justRun { audience.sendMessage(any<Component>()) }

        val message = TextMessage("Hello, world!")
        audience.sendMessage(message)

        verify { audience.sendMessage(any<Component>()) }
        confirmVerified(audience)
    }

    @Test
    fun `Audience_sendMessage() should send an action bar message`() {
        justRun { audience.sendActionBar(any()) }

        val message = ActionBarMessage("Hello, world!")
        audience.sendMessage(message)

        verify { audience.sendActionBar(any()) }
        confirmVerified(audience)
    }

    @Test
    fun `Audience_sendMessage() should send a title message`() {
        justRun { audience.showTitle(any()) }

        val message = TitleMessage("Hello, world!", "Hello, world!")
        audience.sendMessage(message)

        verify { audience.showTitle(any()) }
        confirmVerified(audience)
    }

    @Test
    fun `Audience_sendMessage() should play a sound`() {
        justRun { audience.playSound(any()) }

        val message = SoundMessage("entity.player.levelup")
        audience.sendMessage(message)

        verify { audience.playSound(any()) }
        confirmVerified(audience)
    }

    @Test
    fun `Audience_sendMessage() should send a composite message`() {
        justRun { audience.sendMessage(any<Component>()) }
        justRun { audience.sendActionBar(any()) }
        justRun { audience.showTitle(any()) }
        justRun { audience.playSound(any()) }

        val message =
            CompositeMessage(
                listOf(
                    TextMessage("Hello, world!"),
                    ActionBarMessage("Hello, world!"),
                    TitleMessage("Hello, world!", "Hello, world!"),
                    SoundMessage("entity.player.levelup"),
                ),
            )
        audience.sendMessage(message)

        verifyAll {
            audience.sendMessage(any<Component>())
            audience.sendActionBar(any())
            audience.showTitle(any())
            audience.playSound(any())
        }
        confirmVerified(audience)
    }
}
