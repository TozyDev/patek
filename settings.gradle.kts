import io.github.tozydev.fastmodule.dsl.fastModule
import io.github.tozydev.fastmodule.dsl.rootModule
import io.github.tozydev.fastmodule.modules.ModuleType

pluginManagement {
    repositories {
        gradlePluginPortal()
        maven("https://maven.nguyenthanhtan.id.vn/releases")
    }
}

plugins {
    id("io.github.tozydev.fast-module") version "1.0.0-Final"
}

rootProject.name = "patek"

fastModule {
    rootModule(ModuleType.PAPER_LIB)
}
