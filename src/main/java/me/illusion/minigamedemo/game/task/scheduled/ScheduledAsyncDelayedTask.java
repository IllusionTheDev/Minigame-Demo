package me.illusion.minigamedemo.game.task.scheduled;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class ScheduledAsyncDelayedTask extends ScheduledDelayedTask {

    public ScheduledAsyncDelayedTask(JavaPlugin plugin, Runnable runnable, long delay) {
        super(plugin, runnable, delay);
    }

    @Override
    public void start() {
        if (taskId != -1) {
            throw new IllegalStateException("Task already started");
        }

        Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, (task) -> {
            taskId = task.getTaskId();
            action.accept(task);
        }, delay);
    }


}
