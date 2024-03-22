plugins {
    id("patek.utils")
}

repositories {
    tozydev(project)
}

dependencies {
    paperLib(project(":patek-core"))
}
