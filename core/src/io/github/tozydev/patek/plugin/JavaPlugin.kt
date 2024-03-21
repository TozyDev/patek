package io.github.tozydev.patek.plugin

import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader
import org.bukkit.plugin.java.JavaPlugin
import kotlin.reflect.KClass

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
