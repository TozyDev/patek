@file:Suppress("unused")

package io.github.tozydev.patek.inventory

import com.destroystokyo.paper.profile.ProfileProperty
import io.github.tozydev.patek.text.text
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.TextDecoration
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver
import org.bukkit.Bukkit
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ArmorMeta
import org.bukkit.inventory.meta.BannerMeta
import org.bukkit.inventory.meta.Damageable
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.PotionMeta
import org.bukkit.inventory.meta.SkullMeta
import java.util.*

/**
 * Converts a FastItemStack to a Bukkit ItemStack.
 *
 * @constructor Create a FastItemStackConverter with the provided FastItemStack.
 * @param fastStack The FastItemStack to convert.
 */
class FastItemStackConverter(private val fastStack: FastItemStack) {
    /**
     * Converts the FastItemStack to a Bukkit ItemStack.
     *
     * @param tagResolvers The tag resolvers used to resolve placeholders in the name and lore.
     * @return The converted Bukkit ItemStack.
     */
    fun asBukkit(tagResolvers: Array<out TagResolver>): ItemStack {
        val itemStack = ItemStack(prepareMaterial(), prepareAmount())
        itemStack.modifyMeta {
            displayName(prepareDisplayNameComponent(tagResolvers))
            lore(prepareLoreComponents(tagResolvers))
            determineProp(FastItemStack::customModelData)?.let { setCustomModelData(it) }
            applyEnchants()
            setPropIfPresent(FastItemStack::flags) {
                if (it.isNotEmpty()) {
                    addItemFlags(*it.toTypedArray())
                }
            }
            setPropIfPresent(FastItemStack::isUnbreakable) { isUnbreakable = it }
            applyGlowing()
            applyIfIsInstance<Damageable> {
                setPropIfPresent(FastItemStack::damage) { damage = it }
            }
            applySkullTextureIfPresent()
            applyIfIsInstance<LeatherArmorMeta> {
                setPropIfPresent(FastItemStack::leatherArmorColor) { setColor(it) }
            }
            applyIfIsInstance<BannerMeta> {
                setPropIfPresent(FastItemStack::bannerPatterns) { patterns = it }
            }
            applyIfIsInstance<ArmorMeta> {
                setPropIfPresent(FastItemStack::armorTrim) { trim = it }
            }
            applyIfIsInstance<PotionMeta> {
                setPropIfPresent(FastItemStack::potionColor) { color = it }
            }
        }
        itemStack.applyNbt()
        return itemStack
    }

    private fun prepareMaterial() = determineProp(FastItemStack::material)!!

    private fun prepareAmount() = determineProp(FastItemStack::amount) ?: 1

    private fun prepareDisplayNameComponent(tagResolvers: Array<out TagResolver>): Component? {
        val currentTagResolver = Placeholder.parsed(CURRENT_TAG, fastStack.displayName.orEmpty())
        val component = determineProp(FastItemStack::displayName)?.text(*tagResolvers, currentTagResolver)
        return component?.decoration(TextDecoration.ITALIC, false)
    }

    private fun prepareLoreComponents(tagResolvers: Array<out TagResolver>): List<Component>? {
        val currentTagResolver = Placeholder.parsed(CURRENT_TAG, fastStack.lore.orEmpty())
        val loreComponents =
            determineProp(FastItemStack::lore)
                ?.trimEnd('\n', ' ')
                ?.split('\n')
                ?.map { it.text(*tagResolvers, currentTagResolver) }
        return loreComponents?.map { it.decoration(TextDecoration.ITALIC, false) }
    }

    private fun ItemMeta.applyEnchants() {
        setPropIfPresent(FastItemStack::enchants) { enchants ->
            enchants.forEach { (enchantment, level) ->
                addEnchant(enchantment, level, true)
            }
        }
    }

    private fun ItemMeta.applyGlowing() {
        if (determineProp(FastItemStack::isGlowing) == true) {
            addEnchant(GLOWING_ENCHANTMENT, GLOWING_ENCHANTMENT_LEVEL, true)
            addItemFlags(ItemFlag.HIDE_ENCHANTS)
        }
    }

    private fun ItemMeta.applySkullTextureIfPresent() {
        setPropIfPresent(FastItemStack::skullTexture) {
            applyIfIsInstance<SkullMeta> {
                if (it.length <= MINECRAFT_USERNAME_MAX_LENGTH) return@setPropIfPresent applyPlayerHead(it)
                playerProfile =
                    Bukkit.createProfile(UUID.randomUUID()).apply {
                        setProperty(ProfileProperty(TEXTURES_PROPERTY_KEY, it))
                    }
            }
        }
    }

    private fun SkullMeta.applyPlayerHead(playerName: String) {
        owningPlayer = Bukkit.getOfflinePlayer(playerName)
    }

    private fun ItemStack.applyNbt() {
        setPropIfPresent(FastItemStack::nbt) {
            modifyNbt {
                mergeCompound(it)
            }
        }
    }

    private fun <T> setPropIfPresent(
        getter: (FastItemStack) -> T?,
        setter: (T) -> Unit,
    ) {
        determineProp(getter)?.let(setter)
    }

    private fun <T> determineProp(getter: (FastItemStack) -> T?): T? =
        fastStack.merge?.let(getter) ?: fastStack.let(getter)

    private inline fun <reified M : ItemMeta> ItemMeta.applyIfIsInstance(block: M.() -> Unit) {
        if (this is M) {
            block()
        }
    }

    companion object {
        private const val MINECRAFT_USERNAME_MAX_LENGTH = 16
        private const val TEXTURES_PROPERTY_KEY = "textures"
        private const val CURRENT_TAG = "current"
        private const val GLOWING_ENCHANTMENT_LEVEL = 1
        private val GLOWING_ENCHANTMENT = Enchantment.DURABILITY
    }
}

/**
 * Converts a FastItemStack to a Bukkit ItemStack.
 *
 * @param tagResolvers The tag resolvers used to resolve placeholders in the name and lore.
 */
fun FastItemStack.asBukkit(vararg tagResolvers: TagResolver) = FastItemStackConverter(this).asBukkit(tagResolvers)
