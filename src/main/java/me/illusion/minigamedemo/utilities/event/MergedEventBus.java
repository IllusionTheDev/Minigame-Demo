package me.illusion.minigamedemo.utilities.event;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class MergedEventBus implements EventBus, Listener {

    private final Map<Class<? extends Event>, List<SubscribedListener>> handlers = new ConcurrentHashMap<>();
    private final Set<Class<? extends Event>> subscribed = ConcurrentHashMap.newKeySet();

    private final JavaPlugin plugin;

    public MergedEventBus(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    @SuppressWarnings("unchecked") // We know that the handler is for the correct event type. This is enforced by the EventBus interface
    public <T extends Event> SubscribedListener subscribe(Class<T> eventClass, EventConsumer<T> handler) {
        SubscribedListener listener = new SubscribedListener(this, (EventConsumer<Event>) handler, eventClass);
        handlers.computeIfAbsent(eventClass, irrelevant -> new LinkedList<>()).add(listener);

        ensureSubscribed(eventClass);
        return listener;
    }

    private void ensureSubscribed(Class<? extends Event> eventClass) {
        if (subscribed.contains(eventClass)) {
            return;
        }

        subscribed.add(eventClass);
        Bukkit.getPluginManager().registerEvent(eventClass, this, EventPriority.NORMAL, (listener, event) -> {
            List<SubscribedListener> listeners = handlers.get(eventClass);
            for (SubscribedListener subscribedListener : listeners) {
                if (!subscribedListener.getEventClass().isInstance(event)) {
                    continue;
                }

                try {
                    subscribedListener.callEvent(event);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, plugin, true);
    }

    void unsubscribe(SubscribedListener listener) {
        Class<? extends Event> eventClass = listener.getEventClass();
        handlers.get(eventClass).remove(listener);
    }

    @Override
    public void dispose() {
        handlers.clear();
        subscribed.clear();
        HandlerList.unregisterAll(this);
    }

    @Override
    public void unregister() {
        dispose();
    }
}
