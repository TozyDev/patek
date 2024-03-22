@file:Suppress("unused")

package io.github.tozydev.patek.inventory

import io.github.tozydev.patek.nbt.NBT
import io.github.tozydev.patek.nbt.iface.ReadWriteItemNBT
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta

/**
 * Gets the copy of the desired item meta of this item stack, or null if the item meta is not of the specified type.
 *
 * @param M The desired item meta type.
 */
inline fun <reified M : ItemMeta> ItemStack.itemMetaOrNull() = itemMeta as? M

/**
 * Gets the desired item meta of this item stack.
 *
 * @param M The desired item meta type.
 * @throws ClassCastException if the item meta is not of the specified type.
 */
inline fun <reified M : ItemMeta> ItemStack.itemMeta() = itemMeta as M

/**
 * Modifies the metadata of the given [ItemStack] using the specified [modify] function.
 *
 * @return `true` if the metadata was successfully modified, `false` otherwise.
 */
inline fun ItemStack.modifyMeta(modify: ItemMeta.() -> Unit) = modifyMeta<ItemMeta>(modify)

/**
 * Modifies the metadata of the given `ItemStack` using the specified [modify] function.
 *
 * @return `true` if the metadata was successfully modified, `false` otherwise.
 */
inline fun <reified M : ItemMeta> ItemStack.modifyMeta(modify: ItemMeta.() -> Unit): Boolean {
    val meta = itemMetaOrNull<M>() ?: return false
    itemMeta = meta.apply(modify)
    return true
}

/**
 * Modifies the NBT (Named Binary Tag) data of the ItemStack using the [modify] function.
 */
fun ItemStack.modifyNbt(modify: ReadWriteItemNBT.() -> Unit) = apply { NBT.modify(this, modify) }
