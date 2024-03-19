package io.github.tozydev.patek.plugin

import com.github.shynixn.mccoroutine.bukkit.SuspendingPlugin
import org.bukkit.plugin.Plugin
import java.nio.file.Path

/**
 * Represents a Paper plugin with kotlinx-coroutines support.
 *
 * @see [SuspendingPlugin]
 */
interface PatekPlugin : Plugin {

    /**
     * The directory that the plugin data's files are located in. The directory may not exist.
     */
    val dataDirectory: Path

    /**
     * Loads the plugin asynchronously.
     *
     * This method is called when the plugin is loaded, before it is enabled.
     */
    suspend fun onLoadAsync()

    /**
     * Enables the plugin asynchronously.
     *
     * This method is called when the plugin is enabled.
     */
    suspend fun onEnableAsync()

    /**
     * Disables the plugin asynchronously.
     *
     * This method is called when the plugin is disabled.
     */
    suspend fun onDisableAsync()
}
