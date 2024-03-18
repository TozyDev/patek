package io.github.tozydev.patek.plugin

import com.github.shynixn.mccoroutine.bukkit.SuspendingJavaPlugin
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path

/**
 * Base class for Patek Kotlin plugins.
 * This class provides the functionality for a Paper plugin with kotlinx-coroutines support.
 */
abstract class PatekKotlinPlugin : SuspendingJavaPlugin(), PatekPlugin {

    override val dataDirectory: Path = dataFolder.toPath()

    override suspend fun onDisableAsync() = Unit

    override suspend fun onLoadAsync() = Unit

    override suspend fun onEnableAsync() = Unit

    final override fun onDisable() = super.onEnable()

    final override fun onLoad() = super.onLoad()

    final override fun onEnable() = super.onEnable()

    companion object {

        /**
         * Retrieves the instance of a specified plugin class.
         *
         * @param T The plugin class extending [JavaPlugin].
         * @see JavaPlugin.getPlugin
         */
        @Suppress("unused")
        inline fun <reified T : JavaPlugin> getPlugin() = getPlugin(T::class.java)
    }
}
