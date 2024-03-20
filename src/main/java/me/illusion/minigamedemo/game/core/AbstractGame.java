package me.illusion.minigamedemo.game.core;

import java.util.UUID;
import me.illusion.minigamedemo.MinigameDemoPlugin;
import me.illusion.minigamedemo.game.core.template.GameTemplate;
import me.illusion.minigamedemo.game.event.GamePlayerAddEvent;
import me.illusion.minigamedemo.game.event.GamePlayerRemoveEvent;
import me.illusion.minigamedemo.game.lifecycle.LifecyclePhase;
import me.illusion.minigamedemo.game.lifecycle.LifecycleTasks;
import me.illusion.minigamedemo.game.map.instance.LoadedMap;
import me.illusion.minigamedemo.game.phase.GamePhase;
import me.illusion.minigamedemo.game.phase.chain.LinearPhaseChain;
import me.illusion.minigamedemo.game.phase.chain.PhaseChain;
import me.illusion.minigamedemo.game.task.GameTasks;
import me.illusion.minigamedemo.game.toggle.GameToggleTracker;
import me.illusion.minigamedemo.game.tracker.GamePlayerTracker;
import me.illusion.minigamedemo.utilities.event.EventBus;
import me.illusion.minigamedemo.utilities.event.MergedEventBus;
import org.bukkit.entity.Player;

public abstract class AbstractGame implements Game {

    private final UUID gameId;
    private final GameTemplate<?> template;

    private final PhaseChain phaseChain;

    private final MinigameDemoPlugin plugin;

    private final LoadedMap map;

    private final GameTasks tasks;
    private final EventBus eventBus;
    private final LifecycleTasks lifecycleTasks;

    private final GameToggleTracker gameToggleTracker;
    private final GamePlayerTracker gamePlayerTracker;

    protected AbstractGame(LoadedMap map, GameTemplate<?> template, MinigameDemoPlugin plugin) {
        this.gameId = UUID.randomUUID();

        this.template = template;
        this.plugin = plugin;
        this.map = map;

        this.phaseChain = new LinearPhaseChain(); // You might want to go crazy here

        this.tasks = new GameTasks(plugin);
        this.eventBus = new MergedEventBus(plugin);
        this.lifecycleTasks = new LifecycleTasks();
        this.gamePlayerTracker = new GamePlayerTracker();
        this.gameToggleTracker = new GameToggleTracker(this);
    }

    protected abstract void setup();
    protected abstract void teardown();

    protected void registerPhase(GamePhase phase) {
        this.phaseChain.addPhase(phase);

        phase.onLifecyclePhase(LifecyclePhase.END, this::tryAdvancePhase);
        phase.onLifecyclePhase(LifecyclePhase.CANCEL, this::tryReversePhase);
    }

    private void tryAdvancePhase() {
        if (this.phaseChain.nextPhase()) {
            return;
        }

        this.end();
    }

    private void tryReversePhase() {
        this.phaseChain.previousPhase();
    }

    @Override
    public final void start() {
        this.setup();
        // Feel free to register any "Kick all players" phases here

        this.phaseChain.start();
    }

    @Override
    public final void end() {
        this.teardown();

        for (GamePhase phase : phaseChain.getPhases()) {
            phase.getEventBus().unregister();
        }

        this.phaseChain.dispose();
        this.callLifecycleTasks(LifecyclePhase.END);
        this.eventBus.dispose();
        this.eventBus.unregister();
        this.tasks.dispose();
    }

    @Override
    public void addPlayer(Player player) {
        this.gamePlayerTracker.addPlayer(player.getUniqueId());
        GamePlayerAddEvent event = new GamePlayerAddEvent(player, this);
        event.callEvent();
    }

    @Override
    public void removePlayer(Player player) {
        this.gamePlayerTracker.removePlayer(player.getUniqueId());
        GamePlayerRemoveEvent event = new GamePlayerRemoveEvent(player, this);
        event.callEvent();
    }

    @Override
    public UUID getGameId() {
        return gameId;
    }

    @Override
    public GameTemplate<?> getTemplate() {
        return template;
    }

    public MinigameDemoPlugin getPlugin() {
        return plugin;
    }

    @Override
    public LoadedMap getMap() {
        return map;
    }

    @Override
    public GameTasks getGlobalTasks() {
        return tasks;
    }

    @Override
    public EventBus getEventBus() {
        return eventBus;
    }

    @Override
    public LifecycleTasks getLifecycleTasks() {
        return lifecycleTasks;
    }

    @Override
    public GamePlayerTracker getPlayerTracker() {
        return gamePlayerTracker;
    }

    public GameToggleTracker getGameToggleTracker() {
        return gameToggleTracker;
    }
}
