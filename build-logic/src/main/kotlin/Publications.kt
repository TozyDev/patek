import org.gradle.api.component.SoftwareComponent
import org.gradle.api.publish.PublicationContainer
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.assign
import org.gradle.kotlin.dsl.register

fun PublicationContainer.createPublication(
    component: SoftwareComponent,
    publicationName: String = "maven",
) = register<MavenPublication>(publicationName) {
    from(component)
    pom {
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
