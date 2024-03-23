@file:Suppress("unused")

package io.github.tozydev.patek.world

import io.github.tozydev.patek.key.asBukkit
import io.github.tozydev.patek.server.PaperServer
import net.kyori.adventure.key.Key
import org.bukkit.World
import java.util.*

/**
 * Returns the list of available worlds on this server.
 */
val worlds: List<World> get() = PaperServer.worlds

/**
 * Finds the world with the given [id].
 */
fun worldOrNull(id: UUID) = PaperServer.getWorld(id)

/**
 * Finds the world with the given [name].
 */
fun worldOrNull(name: String) = PaperServer.getWorld(name)

/**
 * Finds the world with the given [key]
 */
fun worldOrNull(key: Key) = PaperServer.getWorld(key.asBukkit())

/**
 * Gets the world with the given [id].
 *
 * @throws IllegalArgumentException if the world is not found.
 */
fun world(id: UUID) = requireNotNull(worldOrNull(id)) { "World with ID $id not found" }

/**
 * Gets the world with the given [name].
 *
 * @throws IllegalArgumentException if the world is not found.
 */
fun world(name: String) = requireNotNull(worldOrNull(name)) { "World with name `$name` not found" }

/**
 * Gets the world with the given [key].
 *
 * @throws IllegalArgumentException if the world is not found.
 */
fun world(key: Key) = requireNotNull(worldOrNull(key)) { "World with key $key not found" }
