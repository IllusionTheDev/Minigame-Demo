package me.illusion.minigamedemo.game.task.scheduled;

import java.util.function.Consumer;
import me.illusion.minigamedemo.game.task.AbstractGameTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

public class ScheduledDelayedTask extends AbstractGameTask {

    protected final JavaPlugin plugin;
    protected final Consumer<BukkitTask> action;
    protected final long delay;

    protected int taskId = -1;

    public ScheduledDelayedTask(JavaPlugin plugin, Runnable runnable, long delay) {
        this.plugin = plugin;
        this.action = task -> runnable.run();
        this.delay = delay;
    }

    public ScheduledDelayedTask(JavaPlugin plugin, Consumer<BukkitTask> action, long delay) {
        this.plugin = plugin;
        this.action = action;
        this.delay = delay;
    }

    @Override
    public void start() {
        if (taskId != -1) {
            throw new IllegalStateException("Task already started");
        }

        Bukkit.getScheduler().runTaskLater(plugin, (task) -> {
            taskId = task.getTaskId();
            action.accept(task);
        }, delay);
    }

    @Override
    public void end() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }
    }
}
