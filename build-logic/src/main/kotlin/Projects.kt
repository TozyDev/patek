import org.gradle.api.Project
import org.gradle.api.provider.Provider

fun Project.environmentOrProperty(env: String, prop: String): Provider<String> =
    providers.environmentVariable(env).orElse(providers.gradleProperty(prop))
