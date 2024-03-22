import org.gradle.api.component.SoftwareComponent
import org.gradle.api.publish.PublicationContainer
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.create

private const val GITHUB_REPO = "https://github/TozyDev/patek"

fun MavenPublication.withDefaults() {
    pom {
        url = GITHUB_REPO
        inceptionYear = "2024"
        licenses {
            license {
                name = "MIT"
                url = "$GITHUB_REPO/blob/main/LICENSE"
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
            url = GITHUB_REPO
        }
    }
}

fun PublicationContainer.createPublication(
    component: SoftwareComponent,
    publicationName: String = "maven",
) = create<MavenPublication>(publicationName) {
    from(component)
}
