package me.illusion.minigamedemo.utilities.event;

import java.util.function.Consumer;
import org.bukkit.event.Event;

public class SubscribedListener {

    private final MergedEventBus bus;
    private final Consumer<Event> action;
    private final Class<? extends Event> eventClass;

    public SubscribedListener(MergedEventBus bus, Consumer<Event> action, Class<? extends Event> eventClass) {
        this.bus = bus;
        this.action = action;
        this.eventClass = eventClass;
    }

    void callEvent(Event event) {
        action.accept(event);
    }

    public Class<? extends Event> getEventClass() {
        return eventClass;
    }

    public void dispose() {
        bus.unsubscribe(this);
    }

}
