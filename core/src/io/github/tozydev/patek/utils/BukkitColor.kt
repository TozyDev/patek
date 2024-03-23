package io.github.tozydev.patek.utils

import org.bukkit.Color

typealias BukkitColor = Color

private const val HEX_COLOR_PREFIX = "#"
private const val ARGB_LENGTH = 8

/**
 * Parses a color from an [input] hex string.
 *
 * The input string can be in the format `#RRGGBB` or `#AARRGGBB`.
 * The alpha value is optional and defaults to `0xff` if not present.
 *
 * @throws IllegalArgumentException if the input string is not a valid hex color.
 */
fun BukkitColor(input: String): BukkitColor {
    val withoutHashtag = input.removePrefix(HEX_COLOR_PREFIX)
    return if (withoutHashtag.length == ARGB_LENGTH) {
        BukkitColor.fromARGB(withoutHashtag.toLong(16).toInt())
    } else {
        return BukkitColor.fromRGB(withoutHashtag.toInt(16))
    }
}
