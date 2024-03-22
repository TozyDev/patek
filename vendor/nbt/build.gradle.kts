plugins {
    alias(libs.plugins.shadow)
    id("patek.publishing-conventions")
}

val itemNbtApi: Configuration by configurations.creating {
    isCanBeResolved = true
    isCanBeConsumed = false
}

repositories {
    codeMC()
}

dependencies {
    itemNbtApi(libs.item.nbt.api)
}

tasks {
    processResources {
        duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    }

    shadowJar {
        configurations = listOf(itemNbtApi)
        relocate("de.tr7zw.changeme.nbtapi", "io.github.tozydev.patek.nbt")
    }

    build {
        dependsOn(shadowJar)
    }
}

publishing {
    publications.create<MavenPublication>("maven") {
        withDefaults()
        shadow.component(this)
        pom {
            name = "Patek NBT"
            description = "A library for reading and writing NBT data. (Shaded from NBTAPI by tr7zw)"
            licenses {
                license {
                    name = "MIT"
                    url = "https://github.com/tr7zw/Item-NBT-API/blob/master/LICENSE"
                }
            }
        }
    }
}
