@file:Suppress("UnstableApiUsage")

plugins {
    id("patek.publishing-conventions")
}

dependencies {
    api(embeddedKotlin("stdlib"))
    api(embeddedKotlin("reflect"))
    api(platform(libs.kotlinx.coroutines.bom))
    api(libs.kotlinx.coroutines.core)
    api(libs.mccoroutine.bukkit.api) {
        exclude(module = "kotlin-stdlib-jdk8")
    }
    api(libs.bundles.commandapi.bukkit)

    implementation(libs.mccoroutine.bukkit.core) {
        exclude(module = "kotlin-stdlib-jdk8")
    }
}

testing {
    val test by suites.getting(JvmTestSuite::class) {
        useKotlinTest(embeddedKotlinVersion)

        dependencies {
            implementation(libs.mockk)
            implementation(libs.paper.api)
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}

publishing {
    publications.createPublication(components["java"]).configure {
        pom {
            name = "Patek Core"
        }
    }
}
