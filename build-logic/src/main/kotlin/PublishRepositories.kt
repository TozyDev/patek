import org.gradle.api.Project
import org.gradle.api.artifacts.dsl.RepositoryHandler

const val TOZYDEV_REPO = "tozydev"
const val TOZYDEV_RELEASES_REPO = "https://maven.nguyenthanhtan.id.vn/releases"
const val TOZYDEV_SNAPSHOT_REPO = "https://maven.nguyenthanhtan.id.vn/snapshots"
private const val SNAPSHOT_SUFFIX = "-SNAPSHOT"

fun RepositoryHandler.tozydev(project: Project) = maven {
    name = TOZYDEV_REPO
    url = if (project.version.toString().endsWith(SNAPSHOT_SUFFIX)) {
        project.uri(TOZYDEV_RELEASES_REPO)
    } else {
        project.uri(TOZYDEV_SNAPSHOT_REPO)
    }
    credentials {
        username = project.environmentOrProperty("MAVEN_USERNAME", "maven.username").orNull
        password = project.environmentOrProperty("MAVEN_TOKEN", "maven.token").orNull
    }
}
