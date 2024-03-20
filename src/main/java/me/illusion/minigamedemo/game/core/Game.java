package me.illusion.minigamedemo.game.core;

import java.util.UUID;
import me.illusion.minigamedemo.game.controller.EventController;
import me.illusion.minigamedemo.game.controller.LifecycleController;
import me.illusion.minigamedemo.game.core.template.GameTemplate;
import me.illusion.minigamedemo.game.map.instance.LoadedMap;
import me.illusion.minigamedemo.game.task.GameTasks;
import me.illusion.minigamedemo.game.tracker.GamePlayerTracker;
import org.bukkit.entity.Player;

public interface Game extends LifecycleController, EventController {

    UUID getGameId(); // Might be used for infrastructure or something
    GameTemplate<?> getTemplate();

    LoadedMap getMap();

    GamePlayerTracker getPlayerTracker();
    GameTasks getGlobalTasks();

    void addPlayer(Player player);
    void removePlayer(Player player);

    void start();
    void end(); // In case you want to force-end the game
}
