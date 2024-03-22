package io.github.tozydev.patek.commands

import dev.jorel.commandapi.CommandAPI
import io.github.tozydev.patek.plugins.mockkPlugin
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.spyk
import io.mockk.unmockkStatic
import io.mockk.verify
import kotlin.test.Test

class CommandApiTest {
    private val commandApi = spyk(CommandApi)

    @Test
    fun `CommandAPI_onLoad() should load once when load multiple times`() {
        mockkStatic(CommandAPI::onLoad)
        justRun { CommandAPI.onLoad(any()) }

        commandApi.load(mockkPlugin())
        commandApi.load(mockkPlugin())
        commandApi.load(mockkPlugin())

        verify(exactly = 3) {
            commandApi.load(any())
        }

        verify(exactly = 1) {
            CommandAPI.onLoad(any())
        }

        confirmVerified(commandApi)
        unmockkStatic(CommandAPI::onLoad)
    }

    @Test
    fun `CommandAPI_onEnable() should enable once when enable multiple times`() {
        mockkStatic(CommandAPI::onEnable)
        justRun { CommandAPI.onEnable() }

        commandApi.enable()
        commandApi.enable()
        commandApi.enable()

        verify(exactly = 3) {
            commandApi.enable()
        }

        verify(exactly = 1) {
            CommandAPI.onEnable()
        }

        confirmVerified(commandApi)
        unmockkStatic(CommandAPI::onEnable)
    }

    private fun mockkPlugin() =
        mockkPlugin {
            every { slF4JLogger } returns mockk()
        }
}
