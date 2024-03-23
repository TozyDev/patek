@file:Suppress("unused")

package io.github.tozydev.patek.player

import io.github.tozydev.patek.server.PaperServer
import org.bukkit.entity.Player
import java.util.*

/**
 * Gets view of all currently logged in players.
 *
 * @see org.bukkit.Server.getOnlinePlayers
 */
val onlinePlayers: Collection<Player> get() = PaperServer.onlinePlayers

/**
 * Finds the associated with the given [name].
 *
 * @param name the name of the player to retrieve, case-insensitive.
 * @see org.bukkit.Server.getPlayerExact
 */
fun playerOrNull(name: String) = PaperServer.getPlayerExact(name)

/**
 * Gets the player with the given [name].
 *
 * @param name the name of the player to retrieve, case-insensitive.
 * @throws IllegalArgumentException if the player is not found.
 * @see org.bukkit.Server.getPlayerExact
 */
fun player(name: String) = requireNotNull(playerOrNull(name)) { "Player with name `$name` not found" }

/**
 * Finds the player with the given [id].
 *
 * @see org.bukkit.Server.getPlayer
 */
fun playerOrNull(id: UUID) = PaperServer.getPlayer(id)

/**
 * Gets the player with the given [id].
 *
 * @throws IllegalArgumentException if the player is not found.
 * @see org.bukkit.Server.getPlayer
 */
fun player(id: UUID) = requireNotNull(playerOrNull(id)) { "Player with ID $id not found" }
