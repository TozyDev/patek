package io.github.tozydev.patek.plugins

import io.github.tozydev.patek.plugin.PatekKotlinPlugin
import io.mockk.mockkClass
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
fun mockkPlugin(
    name: String? = null,
    relaxed: Boolean = false,
    vararg moreInterfaces: KClass<*>,
    relaxUnitFun: Boolean = false,
    block: PatekKotlinPlugin.() -> Unit = {},
): PatekKotlinPlugin {
    val classLoader = MockPluginClassLoader()
    val pluginClass = classLoader.loadClass(PatekKotlinPlugin::class.qualifiedName) as Class<PatekKotlinPlugin>
    return mockkClass(pluginClass.kotlin, name, relaxed, *moreInterfaces, relaxUnitFun = relaxUnitFun, block = block)
}
