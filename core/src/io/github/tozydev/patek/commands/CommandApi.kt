package io.github.tozydev.patek.commands

import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import dev.jorel.commandapi.CommandAPILogger
import io.github.tozydev.patek.nbt.NBTContainer
import io.github.tozydev.patek.plugin.PatekKotlinPlugin

/**
 * Provides wrapper functions for loading and enabling the CommandAPI.
 */
internal object CommandApi {
    private var loaded = false
    private var enabled = false

    /**
     * Loads the CommandAPI if it is not already loaded.
     *
     * @param plugin The PatekKotlinPlugin instance.
     */
    fun load(plugin: PatekKotlinPlugin) {
        if (loaded) {
            return
        }
        loaded = true
        CommandAPI.setLogger(CommandAPILogger.fromSlf4jLogger(plugin.slF4JLogger))
        val config = CommandAPIBukkitConfig(plugin)
            .shouldHookPaperReload(true)
            .initializeNBTAPI(NBTContainer::class.java, ::NBTContainer)
        CommandAPI.onLoad(config)
    }

    fun enable() {
        if (enabled) {
            return
        }
        CommandAPI.onEnable()
        enabled = true
    }

    fun disable() {
        enabled = false
        loaded = false
        CommandAPI.onDisable()
    }
}
