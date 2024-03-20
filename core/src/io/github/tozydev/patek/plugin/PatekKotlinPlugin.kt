package io.github.tozydev.patek.plugin

import com.github.shynixn.mccoroutine.bukkit.MCCoroutine
import dev.jorel.commandapi.CommandAPI
import dev.jorel.commandapi.CommandAPIBukkitConfig
import dev.jorel.commandapi.CommandAPILogger
import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader
import kotlinx.coroutines.runBlocking
import org.bukkit.plugin.java.JavaPlugin
import java.nio.file.Path
import kotlin.reflect.KClass

/**
 * Base class for Patek Kotlin plugins.
 * This class provides the functionality for a Paper plugin with kotlinx-coroutines support.
 */
abstract class PatekKotlinPlugin : JavaPlugin(), PatekPlugin {

    override val dataDirectory: Path = dataFolder.toPath()

    override suspend fun onLoadAsync() = Unit

    override suspend fun onEnableAsync() = Unit

    override suspend fun onDisableAsync() = Unit

    private fun initCommandApi() {
        CommandAPI.setLogger(CommandAPILogger.fromSlf4jLogger(slF4JLogger))
        val config = CommandAPIBukkitConfig(this@PatekKotlinPlugin).shouldHookPaperReload(true)
        CommandAPI.onLoad(config)
    }

    final override fun onLoad() {
        runBlocking {
            initCommandApi()
            onLoadAsync()
        }
    }

    final override fun onEnable() {
        mcCoroutine.getCoroutineSession(this).isManipulatedServerHeartBeatEnabled = true
        runBlocking {
            CommandAPI.onEnable()
            onEnableAsync()
        }
        mcCoroutine.getCoroutineSession(this).isManipulatedServerHeartBeatEnabled = false
    }

    final override fun onDisable() {
        runBlocking {
            onDisableAsync()

            CommandAPI.onDisable()
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

@PublishedApi
internal val pluginInstances by lazy { mutableMapOf<KClass<*>, PatekKotlinPlugin>() }

/**
 * Retrieves the instance of a specified plugin class.
 *
 * @param T The plugin class extending [PatekKotlinPlugin].
 * @see JavaPlugin.getPlugin
 */
@Suppress("unused")
inline fun <reified T : PatekKotlinPlugin> getPlugin() = pluginInstances.getOrPut(T::class) {
    JavaPlugin.getPlugin(T::class.java)
}

private val stackWalker by lazy { StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE) }

/**
 * Retrieves the currently working plugin that calls this method.
 *
 * This method use [StackWalker] to retrieve the first caller class that it's class loader is a [ConfiguredPluginClassLoader].
 */
@Suppress("UnstableApiUsage")
internal fun retrieveCallingPlugin() = stackWalker
    .walk { stream ->
        stream
            .filter { it.declaringClass.classLoader is ConfiguredPluginClassLoader }
            .findFirst()
            .get()
    }.let { JavaPlugin.getProvidingPlugin(it.declaringClass) }
