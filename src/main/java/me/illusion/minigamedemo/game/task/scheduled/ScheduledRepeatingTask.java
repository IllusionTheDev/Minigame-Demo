package me.illusion.minigamedemo.game.task.scheduled;

import java.util.function.Consumer;
import me.illusion.minigamedemo.game.task.AbstractGameTask;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class ScheduledRepeatingTask extends AbstractGameTask {

    protected final JavaPlugin plugin;
    protected final Consumer<BukkitTask> task;
    protected final int delay;
    protected final int period;

    protected int taskId = -1;

    public ScheduledRepeatingTask(JavaPlugin plugin, Runnable runnable, int delay, int period) {
        this.plugin = plugin;
        this.delay = delay;
        this.period = period;

        this.task = (task) -> runnable.run();
    }

    public ScheduledRepeatingTask(JavaPlugin plugin, Runnable runnable, int period) {
        this(plugin, runnable, 0, period);
    }

    public ScheduledRepeatingTask(JavaPlugin plugin, Consumer<BukkitTask> task, int delay, int period) {
        this.plugin = plugin;
        this.task = task;
        this.delay = delay;
        this.period = period;
    }

    public ScheduledRepeatingTask(JavaPlugin plugin, Consumer<BukkitTask> task, int period) {
        this(plugin, task, 0, period);
    }

    @Override
    public void start() {
        if (taskId != -1) {
            throw new IllegalStateException("Task already started");
        }

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(plugin, (bukkitTask -> {
            taskId = bukkitTask.getTaskId();
            task.accept(bukkitTask);
        }), delay, period);
    }

    @Override
    public void end() {
        if (taskId != -1) {
            plugin.getServer().getScheduler().cancelTask(taskId);
            taskId = -1; // Just in case
        }
    }
}
