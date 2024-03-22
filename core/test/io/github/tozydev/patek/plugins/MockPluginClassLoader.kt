package io.github.tozydev.patek.plugins

import io.papermc.paper.plugin.configuration.PluginMeta
import io.papermc.paper.plugin.provider.classloader.ConfiguredPluginClassLoader
import org.bukkit.plugin.java.JavaPlugin

@Suppress("UnstableApiUsage")
class MockPluginClassLoader : ClassLoader(), ConfiguredPluginClassLoader {
    private var holder: JavaPlugin? = null

    override fun close() = Unit

    override fun getConfiguration(): PluginMeta? = holder?.pluginMeta

    override fun loadClass(
        name: String,
        resolve: Boolean,
        checkGlobal: Boolean,
        checkLibraries: Boolean,
    ): Class<*> = super.loadClass(name, resolve)

    override fun init(plugin: JavaPlugin?) {
        holder = plugin
    }

    override fun getPlugin(): JavaPlugin? = holder

    override fun getGroup() = throw NotImplementedError()
}
