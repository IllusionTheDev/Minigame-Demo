package me.illusion.minigamedemo.game.phase;

import java.util.function.UnaryOperator;
import me.illusion.minigamedemo.MinigameDemoPlugin;
import me.illusion.minigamedemo.game.core.AbstractGame;
import me.illusion.minigamedemo.game.lifecycle.LifecyclePhase;
import me.illusion.minigamedemo.game.lifecycle.LifecycleTasks;
import me.illusion.minigamedemo.game.task.GameTasks;
import me.illusion.minigamedemo.utilities.event.EventBus;
import me.illusion.minigamedemo.utilities.event.MergedEventBus;
import org.bukkit.World;
import org.bukkit.entity.Player;

public abstract class AbstractPhase<T extends AbstractGame> implements GamePhase {

    protected final T game;
    protected final GameTasks tasks;
    private final EventBus eventBus;
    private final LifecycleTasks lifecycleTasks;

    protected AbstractPhase(T game) {
        this.game = game;
        this.eventBus = new MergedEventBus(getPlugin());
        this.tasks = new GameTasks(getPlugin());
        this.lifecycleTasks = new LifecycleTasks();
    }

    protected abstract void setup();

    public abstract void teardown();

    protected void registerListeners() {
    }

    protected void registerTasks() {
    }

    public final void start() {
        this.callLifecycleTasks(LifecyclePhase.START);
        setup();
        registerListeners();
        registerTasks();
    }

    public final void nextPhase() {
        this.callLifecycleTasks(LifecyclePhase.END);
        this.dispose();
    }

    protected void previousPhase() {
        this.callLifecycleTasks(LifecyclePhase.CANCEL);
        this.dispose();
    }

    public final void dispose() {
        this.teardown();
        this.callLifecycleTasks(LifecyclePhase.DISPOSE);
        this.eventBus.dispose();
        this.tasks.dispose();
    }


    public T getGame() {
        return game;
    }

    protected MinigameDemoPlugin getPlugin() {
        return game.getPlugin();
    }

    public World getWorld() {
        return game.getMap().getWorld();
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public LifecycleTasks getLifecycleTasks() {
        return lifecycleTasks;
    }
}
