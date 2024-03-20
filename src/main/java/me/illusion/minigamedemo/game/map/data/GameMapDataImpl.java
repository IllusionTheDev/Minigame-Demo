package me.illusion.minigamedemo.game.map.data;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import me.illusion.minigamedemo.MinigameDemoPlugin;
import me.illusion.minigamedemo.game.map.config.ConfiguredMap;
import me.illusion.minigamedemo.game.map.config.MapMetadata;
import me.illusion.minigamedemo.game.map.instance.AbstractLoadedMap;
import me.illusion.minigamedemo.game.map.provider.ConfiguredMapProvider;
import me.illusion.minigamedemo.game.map.provider.LoadedMapProvider;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public class GameMapDataImpl<C extends ConfiguredMap, L extends AbstractLoadedMap<C>> implements GameMapData<L> {

    private final MinigameDemoPlugin plugin;
    private final Map<String, C> configuredMaps = new ConcurrentHashMap<>();
    private final LoadedMapProvider<C, L> loadedMapProvider;
    private final ConfiguredMapProvider<C> configuredMapProvider;

    public GameMapDataImpl(MinigameDemoPlugin plugin, ConfiguredMapProvider<C> configuredMapProvider, LoadedMapProvider<C, L> loadedMapProvider) {
        this.plugin = plugin;
        this.configuredMapProvider = configuredMapProvider;
        this.loadedMapProvider = loadedMapProvider;

        if (plugin == null) {
            throw new IllegalArgumentException("Plugin cannot be null");
        }
    }

    @Override
    public Collection<String> getAvailableMapIds() {
        return Set.copyOf(configuredMaps.keySet());
    }

    @Override
    public MapMetadata getMetadata(String name) {
        C map = configuredMaps.get(name);

        if (map == null) {
            return null;
        }

        return map.getMetadata();
    }

    @Override
    public void registerMap(String id, ConfigurationSection mapConfig) {
        C map = configuredMapProvider.provide(mapConfig);
        this.configuredMaps.put(id, map);
    }

    @Override
    public L createMap(String name, Location anchor) {
        C config = configuredMaps.get(name);

        if (config == null) {
            throw new IllegalArgumentException("Map " + name + " does not exist");
        }

        return loadedMapProvider.createMap(plugin, config, anchor);
    }
}
