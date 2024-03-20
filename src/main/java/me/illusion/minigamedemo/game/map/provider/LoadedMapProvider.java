package me.illusion.minigamedemo.game.map.provider;

import me.illusion.minigamedemo.MinigameDemoPlugin;
import me.illusion.minigamedemo.game.map.config.ConfiguredMap;
import me.illusion.minigamedemo.game.map.instance.LoadedMap;
import org.bukkit.Location;

public interface LoadedMapProvider<C extends ConfiguredMap, L extends LoadedMap> {

    L createMap(MinigameDemoPlugin plugin, C configuredMap, Location anchor);
}
