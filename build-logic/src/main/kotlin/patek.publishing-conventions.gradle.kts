plugins {
    `maven-publish`
    signing
}

signing {
    configureInMemoryGpgKeys(project)
    sign(publishing.publications)
}

publishing {
    repositories {
        tozydev(project)
    }
}
