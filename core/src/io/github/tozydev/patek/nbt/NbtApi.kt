package io.github.tozydev.patek.nbt

import io.github.tozydev.patek.nbt.utils.MinecraftVersion
import java.util.logging.Logger

/**
 * Utility class for initializing the NBT API.
 */
internal object NbtApi {
    private var initialized = false

    /**
     * Initializes the NBT API.
     *
     * @param javaLogger The Java logger to be used for logging.
     */
    fun init(javaLogger: Logger) {
        if (initialized) {
            return
        }
        initialized = true
        MinecraftVersion.disableBStats()
        MinecraftVersion.disablePackageWarning()
        MinecraftVersion.disableUpdateCheck()
        MinecraftVersion.replaceLogger(javaLogger)
    }
}
