package me.illusion.minigamedemo.game.task.scheduled;

import java.util.function.Consumer;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class ScheduledAsyncRepeatingTask extends ScheduledRepeatingTask {

    public ScheduledAsyncRepeatingTask(JavaPlugin plugin, Runnable runnable, int delay, int period) {
        super(plugin, runnable, delay, period);
    }

    public ScheduledAsyncRepeatingTask(JavaPlugin plugin, Runnable runnable, int period) {
        super(plugin, runnable, period);
    }

    public ScheduledAsyncRepeatingTask(JavaPlugin plugin, Consumer<BukkitTask> task, int delay, int period) {
        super(plugin, task, delay, period);
    }

    public ScheduledAsyncRepeatingTask(JavaPlugin plugin, Consumer<BukkitTask> task, int period) {
        super(plugin, task, period);
    }

    @Override
    public void start() {
        if (taskId != -1) {
            throw new IllegalStateException("Task already started");
        }

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimerAsynchronously(plugin, task, delay, period);
    }
}
