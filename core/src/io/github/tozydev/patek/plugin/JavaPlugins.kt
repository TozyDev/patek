@file:Suppress("unused")

package io.github.tozydev.patek.plugin

import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass

@PublishedApi
internal val pluginInstances by lazy { mutableMapOf<KClass<*>, JavaPlugin>() }

/**
 * Retrieves the instance of a specified plugin class.
 *
 * @param T The plugin class extending [JavaPlugin].
 * @see JavaPlugin.getPlugin
 */
inline fun <reified T : JavaPlugin> getPlugin() = pluginInstances.getOrPut(T::class) {
    JavaPlugin.getPlugin(T::class.java)
}

/**
 * Retrieves the plugin with the specified [name], if it is of the specified type.
 *
 * @param T The plugin class extending [JavaPlugin].
 */
inline fun <reified T : JavaPlugin> getPluginOrNull(name: String) = Bukkit.getPluginManager().getPlugin(name) as? T

/**
 * Retrieves the plugin with the specified [name], if it is of the specified type.
 *
 * @param T The plugin class extending [JavaPlugin].
 * @throws IllegalArgumentException if the plugin with the given name is not found.
 * @throws ClassCastException if the plugin is not of the specified type.
 */
inline fun <reified T : JavaPlugin> getPlugin(name: String) =
    requireNotNull(Bukkit.getPluginManager().getPlugin(name)) { "Plugin $name not found" } as T

private val stackWalker by lazy { StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE) }

/**
 * Retrieves the currently working plugin that calls this method.
 *
 * This method use [StackWalker] to retrieve the first caller class that it's class loader is a [ConfiguredPluginClassLoader].
 */
@Suppress("UnstableApiUsage")
@PublishedApi
internal fun retrieveCallingPlugin() = stackWalker
    .walk { stream ->
        stream
            .filter { it.declaringClass.classLoader is ConfiguredPluginClassLoader }
            .findFirst()
            .get()
    }.let { JavaPlugin.getProvidingPlugin(it.declaringClass) }
