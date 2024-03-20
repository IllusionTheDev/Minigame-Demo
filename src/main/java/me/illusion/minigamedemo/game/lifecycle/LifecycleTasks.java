package me.illusion.minigamedemo.game.lifecycle;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class LifecycleTasks {

    private final Map<LifecyclePhase, List<Runnable>> tasks = new ConcurrentHashMap<>();

    public void addTask(LifecyclePhase phase, Runnable runnable) {
        tasks.computeIfAbsent(phase, k -> new CopyOnWriteArrayList<>()).add(runnable);
    }

    public void callTasks(LifecyclePhase phase) {
        tasks.getOrDefault(phase, List.of()).forEach(Runnable::run);
    }

    public void clear() {
        tasks.clear();
    }

}
