package io.github.tozydev.patek.plugin

import com.github.shynixn.mccoroutine.bukkit.SuspendingPlugin
import java.nio.file.Path

/**
 * Represents a Paper plugin with kotlinx-coroutines support.
 *
 * @see [SuspendingPlugin]
 */
interface PatekPlugin : SuspendingPlugin {

    /**
     * The directory that the plugin data's files are located in. The directory may not exist.
     */
    val dataDirectory: Path
}
