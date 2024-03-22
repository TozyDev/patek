package io.github.tozydev.patek.event

import io.github.tozydev.patek.plugin.retrieveCallingPlugin
import org.bukkit.event.Event
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.plugin.Plugin

/**
 * Represents a listener for a specific event type [E].
 *
 * @param E the event type to listen for
 */
fun interface EventListener<E : Event> : Listener {
    /**
     * Executes a callback when the specified event occurs.
     *
     * @param event the event to listen for
     */
    fun on(event: E)
}

/**
 * Registers the listener to receive the event [E].
 *
 * @param E the event type to listen for
 * @param plugin the plugin that owns the listener (defaults to the calling plugin)
 * @param priority the priority of the listener (defaults to [EventPriority.NORMAL])
 * @param ignoreCancelled specifies if cancelled events should be ignored (defaults to false)
 * @return the registered listener
 * @see Listener.register
 */
inline fun <reified E : Event> EventListener<E>.register(
    plugin: Plugin = retrieveCallingPlugin(),
    priority: EventPriority = EventPriority.NORMAL,
    ignoreCancelled: Boolean = false,
) = register<E>(plugin, priority, ignoreCancelled) { _, event -> on(event) }
