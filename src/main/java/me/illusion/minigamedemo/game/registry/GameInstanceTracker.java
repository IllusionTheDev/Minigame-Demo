package me.illusion.minigamedemo.game.registry;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;
import me.illusion.minigamedemo.MinigameDemoPlugin;
import me.illusion.minigamedemo.game.core.Game;
import me.illusion.minigamedemo.game.core.template.GameTemplate;
import me.illusion.minigamedemo.game.lifecycle.LifecyclePhase;
import me.illusion.minigamedemo.game.map.config.MapMetadata;
import me.illusion.minigamedemo.game.map.data.GameMapData;
import me.illusion.minigamedemo.game.map.instance.LoadedMap;
import me.illusion.minigamedemo.game.tracker.GamePlayerTracker;
import org.bukkit.Location;

public class GameInstanceTracker {

    private final Map<UUID, Game> games = new ConcurrentHashMap<>();
    private final MinigameDemoPlugin plugin;

    public GameInstanceTracker(MinigameDemoPlugin plugin) {
        this.plugin = plugin;
    }

    public Game getPlayerGame(UUID playerId) {
        for (Game game : games.values()) {
            GamePlayerTracker playerTracker = game.getPlayerTracker();

            if (playerTracker.isPlayerInGame(playerId)) {
                return game;
            }
        }

        return null;
    }

    public <T extends LoadedMap> void startGame(String gameTemplateId) {
        GameTemplate<T> gameTemplate = plugin.getTemplateRegistry().getTemplate(gameTemplateId);

        if (gameTemplate == null) {
            throw new IllegalArgumentException("Template not found");
        }

        GameMapData<T> mapData = gameTemplate.getMapData();
        String randomMap = pickRandomMap(mapData);

        if (randomMap == null) {
            throw new IllegalArgumentException("No maps available");
        }

        MapMetadata metadata = mapData.getMetadata(randomMap);

        if (metadata == null) {
            throw new IllegalArgumentException("Map metadata not found");
        }

        String worldTemplate = metadata.getTemplate();

        // TODO: Load the map. You can do this with SlimeWorldManager or your own API
        Location loadedAnchor = null; // TODO: Load the map and get the anchor location
        T map = mapData.createMap(randomMap, loadedAnchor);

        Game game = gameTemplate.createGame(map);
        game.start();
        // Don't forget to add your players!

        registerGame(game);
    }

    public void registerGame(Game game) {
        games.put(game.getGameId(), game);
        game.onLifecyclePhase(LifecyclePhase.END, () -> unregisterGame(game));
    }

    public void unregisterGame(Game game) {
        games.remove(game.getGameId());
        // Make sure to unload your map!
    }

    private String pickRandomMap(GameMapData<?> mapData) { // Feel free to use your own algorithm here
        List<String> availableMaps = List.copyOf(mapData.getAvailableMapIds());

        if (availableMaps.isEmpty()) {
            return null;
        }

        if (availableMaps.size() == 1) {
            return availableMaps.iterator().next();
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(availableMaps.size());
        return availableMaps.get(randomIndex);
    }
}
