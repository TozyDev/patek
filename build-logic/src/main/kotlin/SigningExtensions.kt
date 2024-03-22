import org.gradle.api.Project
import org.gradle.plugins.signing.SigningExtension

fun SigningExtension.configureInMemoryGpgKeys(project: Project) {
    useInMemoryPgpKeys(
        project.environmentOrProperty("GPG_KEY_ID", "gpg.keyId").orNull,
        project.environmentOrProperty("GPG_KEY", "gpg.key").orNull,
        project.environmentOrProperty("GPG_PASSPHRASE", "gpg.passphrase").orNull,
    )
}
