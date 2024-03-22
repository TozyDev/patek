@file:Suppress("UnstableApiUsage")

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
    versionCatalogs {
        val libs by registering {
            from(files("../gradle/libs.versions.toml"))
        }
    }
}

rootProject.name = "patek-build-logic"
