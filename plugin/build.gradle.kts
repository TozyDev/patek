repositories {
    maven {
        name = "tozydev"
        url = if (version.toString().endsWith("-SNAPSHOT")) {
            uri("https://maven.nguyenthanhtan.id.vn/snapshots")
        } else {
            uri("https://maven.nguyenthanhtan.id.vn/releases")
        }
    }
    maven("https://repo.codemc.org/repository/maven-public/")
}

dependencies {
    paperLib(project(":patek-core"))
}
