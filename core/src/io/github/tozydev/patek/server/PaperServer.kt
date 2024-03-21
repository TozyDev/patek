package io.github.tozydev.patek.server

import org.bukkit.Bukkit
import org.bukkit.Server

/**
 * The `PaperServer` class represents a singleton implementation of a Paper server.
 *
 * This class provides access to the underlying `Server` instance from the Bukkit API.
 */
object PaperServer : Server by Bukkit.getServer()
