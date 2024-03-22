package io.github.tozydev.patek.plugin

import com.github.shynixn.mccoroutine.bukkit.MCCoroutine
import io.github.tozydev.patek.commands.CommandApi
import io.github.tozydev.patek.nbt.NbtApi
import kotlinx.coroutines.runBlocking
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path

/**
 * Base class for Patek Kotlin plugins.
 * This class provides the functionality for a Paper plugin with kotlinx-coroutines support.
 */
abstract class PatekKotlinPlugin : JavaPlugin(), PatekPlugin {
    override val dataDirectory: Path = dataFolder.toPath()

    override suspend fun onLoadAsync() = Unit

    override suspend fun onEnableAsync() = Unit

    override suspend fun onDisableAsync() = Unit

    final override fun onLoad() {
        runBlocking {
            NbtApi.init(logger)
            CommandApi.load(this@PatekKotlinPlugin)
            onLoadAsync()
        }
    }

    final override fun onEnable() {
        mcCoroutine.getCoroutineSession(this).isManipulatedServerHeartBeatEnabled = true
        runBlocking {
            CommandApi.enable()
            onEnableAsync()
        }
        mcCoroutine.getCoroutineSession(this).isManipulatedServerHeartBeatEnabled = false
    }

    final override fun onDisable() {
        runBlocking {
            CommandApi.disable()
            onDisableAsync()
        }
    }

    companion object {
        internal val mcCoroutine: MCCoroutine by lazy {
            try {
                Class.forName(MCCoroutine.Driver).getDeclaredConstructor().newInstance() as MCCoroutine
            } catch (e: Exception) {
                throw RuntimeException("Failed to load MCCoroutine implementation. Shade patek-core into your plugin.")
            }
        }
    }
}
