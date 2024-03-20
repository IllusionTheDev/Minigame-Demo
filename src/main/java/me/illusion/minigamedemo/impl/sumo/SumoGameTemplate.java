package me.illusion.minigamedemo.impl.sumo;

import me.illusion.minigamedemo.MinigameDemoPlugin;
import me.illusion.minigamedemo.game.core.Game;
import me.illusion.minigamedemo.game.core.template.GameTemplate;
import me.illusion.minigamedemo.game.map.data.GameMapData;
import me.illusion.minigamedemo.game.map.data.GameMapDataImpl;
import me.illusion.minigamedemo.impl.sumo.map.SumoConfiguredMap;
import me.illusion.minigamedemo.impl.sumo.map.SumoLoadedMap;

public class SumoGameTemplate implements GameTemplate<SumoLoadedMap> {

    private final MinigameDemoPlugin plugin;

    public SumoGameTemplate(MinigameDemoPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getName() {
        return "sumo";
    }

    @Override
    public Game createGame(SumoLoadedMap map) {
        return new SumoGame(map, this, plugin);
    }

    @Override
    public GameMapData<SumoLoadedMap> getMapData() {
        return new GameMapDataImpl<>(plugin, SumoConfiguredMap::new, SumoLoadedMap::new);
    }
}
