package me.illusion.minigamedemo.game.controller;

import me.illusion.minigamedemo.utilities.event.EventBus;
import me.illusion.minigamedemo.utilities.event.EventConsumer;
import org.bukkit.event.Event;

public interface EventController {

    EventBus getEventBus();

    default <T extends Event> void subscribe(Class<T> eventClass, EventConsumer<T> consumer) {
        getEventBus().subscribe(eventClass, consumer);
    }
}
