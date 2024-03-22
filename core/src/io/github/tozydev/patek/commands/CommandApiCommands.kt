@file:Suppress("unused")

package io.github.tozydev.patek.commands

import dev.jorel.commandapi.CommandAPICommand
import dev.jorel.commandapi.kotlindsl.commandAPICommand

/**
 * Creates and registers a new command with the given [name] and [block] using the CommandAPI.
 *
 * @see commandAPICommand
 */
fun command(
    name: String,
    block: CommandAPICommand.() -> Unit,
) = commandAPICommand(name, block)
