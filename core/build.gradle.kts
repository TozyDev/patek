plugins {
    `maven-publish`
    signing
}


repositories {
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    api(embeddedKotlin("stdlib"))
    api(embeddedKotlin("reflect"))
    api(libs.kotlinx.coroutines.core)
    api(libs.mccoroutine.bukkit.api) {
        exclude(module = "kotlin-stdlib-jdk8")
    }
    api(libs.bundles.commandapi.bukkit)

    implementation(libs.mccoroutine.bukkit.core) {
        exclude(module = "kotlin-stdlib-jdk8")
    }
}

signing {
    val signingKeyId = envOrProp("GPG_KEY_ID", "gpg.keyId").orNull
    val signingKey = envOrProp("GPG_KEY", "gpg.key").orNull
    val signingPassword = envOrProp("GPG_PASSPHRASE", "gpg.passphrase").orNull
    useInMemoryPgpKeys(signingKeyId, signingKey, signingPassword)
    sign(publishing.publications)
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            pom {
                name = "Patek"
                url = "https://github/TozyDev/patek"
                inceptionYear = "2024"
                licenses {
                    license {
                        name = "MIT"
                        url = "https://github.com/TozyDev/patek/blob/main/LICENSE"
                        distribution = "repo"
                    }
                }
                developers {
                    developer {
                        id = "TozyDev"
                        name = "Nguyen Thanh Tan"
                        url = "https://nguyenthanhtan.id.vn"
                        email = "contact@nguyenthanhtan.id.vn"
                    }
                }
                scm {
                    connection = "scm:git:git://github.com/TozyDev/patek.git"
                    developerConnection = "scm:git:ssh://github.com/TozyDev/patek.git"
                    url = "https://github.com/TozyDev/patek"
                }
            }
        }
    }
    repositories {
        maven {
            name = "tozydev"
            url = if (version.toString().endsWith("-SNAPSHOT")) {
                uri("https://maven.nguyenthanhtan.id.vn/snapshots")
            } else {
                uri("https://maven.nguyenthanhtan.id.vn/releases")
            }
            credentials {
                username = envOrProp("MAVEN_USERNAME", "maven.username").orNull
                password = envOrProp("MAVEN_TOKEN", "maven.token").orNull
            }
        }
    }
}

fun Project.envOrProp(env: String, prop: String): Provider<String> =
    providers.environmentVariable(env).orElse(providers.gradleProperty(prop))
