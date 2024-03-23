package io.github.tozydev.patek.inventory

import io.github.tozydev.patek.utils.BannerPattern
import io.github.tozydev.patek.utils.BukkitColor
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.meta.trim.ArmorTrim

/**
 * The FastItemStack class represents a fast and efficient way to create and manipulate ItemStack objects in Bukkit.
 *
 * @property material The Material of the ItemStack.
 * @property amount The number of items in the ItemStack.
 * @property displayName The display name of the ItemStack.
 * @property lore The lore of the ItemStack.
 * @property customModelData The custom model data of the ItemStack.
 * @property enchants The enchantments applied to the ItemStack.
 * @property flags The flags applied to the ItemStack.
 * @property isUnbreakable Indicates if the ItemStack is unbreakable.
 * @property isGlowing Indicates if the ItemStack is glowing.
 * @property nbt The map of nbt tag and it values.
 * @property damage The damage value of the ItemStack.
 * @property skullTexture The skull texture of the ItemStack.
 * @property leatherArmorColor The leather armor color of the ItemStack.
 * @property bannerPatterns The banner patterns of the ItemStack.
 * @property armorTrim The armor trim of the ItemStack.
 * @property potionColor The potion color of the ItemStack.
 * @property merge The ItemStack to merge with.
 *
 * @constructor Creates a new FastItemStack instance.
 */
data class FastItemStack(
    val material: Material = Material.AIR,
    val amount: Int? = null,
    val displayName: String? = null,
    val lore: String? = null,
    val customModelData: Int? = null,
    val enchants: Map<Enchantment, Int> = emptyMap(),
    val flags: Set<ItemFlag> = emptySet(),
    val isUnbreakable: Boolean? = null,
    val isGlowing: Boolean? = null,
    val nbt: Map<String, Any> = emptyMap(),
    val damage: Int? = null,
    val skullTexture: String? = null,
    val leatherArmorColor: BukkitColor? = null,
    val bannerPatterns: List<BannerPattern> = emptyList(),
    val armorTrim: ArmorTrim? = null,
    val potionColor: BukkitColor? = null,
    val merge: FastItemStack? = null,
)
