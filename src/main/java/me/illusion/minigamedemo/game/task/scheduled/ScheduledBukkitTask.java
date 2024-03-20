package me.illusion.minigamedemo.game.task.scheduled;

import me.illusion.minigamedemo.game.task.AbstractGameTask;
import me.illusion.minigamedemo.game.task.GameTask;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class ScheduledBukkitTask extends AbstractGameTask implements GameTask {

    private int taskId;

    public ScheduledBukkitTask(BukkitTask task) {
        this.taskId = task.getTaskId();
    }

    @Override
    public void start() {
        // Do nothing
    }

    @Override
    public void end() {
        if (taskId != -1) {
            Bukkit.getScheduler().cancelTask(taskId);
            taskId = -1;
        }
    }
}
