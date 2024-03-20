package me.illusion.minigamedemo.game.map.instance;

import me.illusion.minigamedemo.MinigameDemoPlugin;
import me.illusion.minigamedemo.game.map.config.ConfiguredMap;
import org.bukkit.Location;

public abstract class AbstractLoadedMap<T extends ConfiguredMap> implements LoadedMap {

    protected final T mapTemplate;
    protected final MinigameDemoPlugin plugin;
    protected final Location anchor;

    protected AbstractLoadedMap(MinigameDemoPlugin plugin, T mapTemplate, Location anchor) {
        this.plugin = plugin;
        this.mapTemplate = mapTemplate;
        this.anchor = anchor;
    }

    public MinigameDemoPlugin getPlugin() {
        return plugin;
    }

    @Override
    public Location getAnchorPoint() {
        return anchor.clone();
    }

    @Override
    public Location getDefaultSpawnPoint() {
        Location spawn = getLocation("spawn");

        if (spawn == null) {
            spawn = getAnchorPoint();
        }

        return spawn;
    }

    @Override
    public T getMapTemplate() {
        return mapTemplate;
    }

    protected Location getLocation(String path) {
        return getAnchorPoint().add(mapTemplate.getPosition(path));
    }
}
