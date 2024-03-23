package io.github.tozydev.patek.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

class BukkitColorKtTest {
    @Test
    fun `should parse RGB color with default alpha`() {
        val color = BukkitColor("#ff0000")
        assertEquals(0xff, color.alpha, "Alpha")
        assertEquals(0xff, color.red, "Red")
        assertEquals(0, color.green, "Green")
        assertEquals(0, color.blue, "Blue")
    }

    @Test
    fun `should parse ARGB color`() {
        val color = BukkitColor("#f00000ff")
        assertEquals(0xf0, color.alpha, "Alpha")
        assertEquals(0, color.red, "Red")
        assertEquals(0, color.green, "Green")
        assertEquals(0xff, color.blue, "Blue")
    }

    @Test
    fun `should keep the zero alpha when parsing RGB color`() {
        val color = BukkitColor("#0000ff00")
        assertEquals(0, color.alpha, "Alpha")
        assertEquals(0, color.red, "Red")
        assertEquals(0xff, color.green, "Green")
        assertEquals(0, color.blue, "Blue")
    }

    @Test
    fun `should throws an exception when parsing an invalid color`() {
        assertFailsWith<IllegalArgumentException> {
            BukkitColor("#ghijklmn")
            BukkitColor("1029033334")
        }
    }

    @Test
    fun `should parse color from string without hashtag`() {
        val color = BukkitColor("ff0000")
        assertEquals(0xff, color.alpha, "Alpha")
        assertEquals(0xff, color.red, "Red")
        assertEquals(0, color.green, "Green")
        assertEquals(0, color.blue, "Blue")
    }
}
