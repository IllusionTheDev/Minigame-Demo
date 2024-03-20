package me.illusion.minigamedemo.game.task;

import com.google.common.collect.Sets;
import java.util.Collection;
import me.illusion.minigamedemo.MinigameDemoPlugin;
import me.illusion.minigamedemo.game.task.scheduled.ScheduledAsyncRepeatingTask;
import me.illusion.minigamedemo.game.task.scheduled.ScheduledBukkitTask;
import me.illusion.minigamedemo.game.task.scheduled.ScheduledDelayedTask;
import me.illusion.minigamedemo.game.task.scheduled.ScheduledRepeatingTask;
import org.bukkit.scheduler.BukkitTask;

public class GameTasks {

    private final MinigameDemoPlugin plugin;
    private final Collection<GameTask> tasks = Sets.newConcurrentHashSet();

    public GameTasks(MinigameDemoPlugin plugin) {
        this.plugin = plugin;
    }

    public GameTask addTask(GameTask task) {
        task.start();
        task.onDispose(() -> tasks.remove(task));
        tasks.add(task);
        return task;
    }

    public void remove(GameTask task) {
        task.dispose();
        tasks.remove(task);
    }

    public void dispose() {
        tasks.forEach(GameTask::dispose);
        tasks.clear();
    }

    public final GameTask addTask(BukkitTask task) {
        return addTask(new ScheduledBukkitTask(task));
    }

    public final GameTask addDelayedTask(Runnable runnable, long delay) {
        return addTask(new ScheduledDelayedTask(plugin, runnable, delay));
    }

    public final GameTask addRepeatingTask(Runnable runnable, int delay, int period) {
        return addTask(new ScheduledRepeatingTask(plugin, runnable, delay, period));
    }

    public final GameTask addRepeatingTask(Runnable runnable, int period) {
        return addRepeatingTask(runnable, 0, period);
    }

    public final GameTask addRepeatingTaskAsync(Runnable runnable, int period) {
        return addRepeatingTaskAsync(runnable, 0, period);
    }

    public final GameTask addRepeatingTaskAsync(Runnable runnable, int delay, int period) {
        return addTask(new ScheduledAsyncRepeatingTask(plugin, runnable, delay, period));
    }

}
