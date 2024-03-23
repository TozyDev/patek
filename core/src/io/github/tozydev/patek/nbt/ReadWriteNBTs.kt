package io.github.tozydev.patek.nbt

import io.github.tozydev.patek.nbt.iface.ReadWriteNBT
import org.bukkit.inventory.ItemStack
import java.util.*

/**
 * Sets the [value] associated with a given [key] in the NBT compound.
 *
 * @throws IllegalArgumentException if the value is of unsupported NBT type
 * @see ReadWriteNBT
 */
operator fun ReadWriteNBT.set(
    key: String,
    value: Any?,
) = when (value) {
    is String -> setString(key, value)
    is Int -> setInteger(key, value)
    is Double -> setDouble(key, value)
    is Byte -> setByte(key, value)
    is Short -> setShort(key, value)
    is Long -> setLong(key, value)
    is Float -> setFloat(key, value)
    is ByteArray -> setByteArray(key, value)
    is IntArray -> setIntArray(key, value)
    is LongArray -> setLongArray(key, value)
    is Boolean -> setBoolean(key, value)
    is ItemStack -> setItemStack(key, value)
    is UUID -> setUUID(key, value)
    else -> throw IllegalArgumentException("Unsupported NBT type: ${value?.javaClass?.simpleName}")
}

/**
 * Removes a [key] from the NBT compound.
 *
 * @see ReadWriteNBT.removeKey
 */
operator fun ReadWriteNBT.minusAssign(key: String) = removeKey(key)
