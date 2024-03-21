@file:Suppress("unused")

package io.github.tozydev.patek.player

import io.github.tozydev.patek.server.PaperServer
import java.util.*

/**
 * Retrieves the player with the given [name], regardless of whether they are currently online.
 *
 * @see org.bukkit.Server.getOfflinePlayer
 */
fun offlinePlayer(name: String) = PaperServer.getOfflinePlayer(name)

/**
 * Gets the player with the given [id], regardless of whether they are exist or not.
 *
 * @see org.bukkit.Server.getOfflinePlayer
 */
fun offlinePlayer(id: UUID) = PaperServer.getOfflinePlayer(id)
