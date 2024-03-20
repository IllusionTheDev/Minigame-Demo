package me.illusion.minigamedemo.utilities.event;

import org.bukkit.event.Event;

public interface EventBus {

    /**
     * Registers an event handler for the specified event class
     *
     * @param eventClass The event class
     * @param handler    The event handler
     * @param <T>        The event type
     */
    <T extends Event> SubscribedListener subscribe(Class<T> eventClass, EventConsumer<T> handler);

    /**
     * Unregisters all associated handlers
     */
    void dispose();

    /**
     * Unregisters the eventbus
     */
    void unregister();

}