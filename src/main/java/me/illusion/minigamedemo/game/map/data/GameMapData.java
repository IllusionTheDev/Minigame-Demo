package me.illusion.minigamedemo.game.map.data;

import java.util.Collection;
import me.illusion.minigamedemo.game.map.config.MapMetadata;
import me.illusion.minigamedemo.game.map.instance.LoadedMap;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;

public interface GameMapData<L extends LoadedMap> {

    Collection<String> getAvailableMapIds();
    MapMetadata getMetadata(String name);

    void registerMap(String id, ConfigurationSection mapConfig);
    L createMap(String name, Location anchor);
}
