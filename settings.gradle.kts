import io.github.tozydev.fastmodule.dsl.fastModule
import io.github.tozydev.fastmodule.dsl.module
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
    module(ModuleType.PAPER_LIB, "core") {
        moduleName = "${rootProject.name}-core"
    }
    module(ModuleType.PAPER_PLUGIN, "plugin") {
        moduleName = "${rootProject.name}-plugin"
    }
}
