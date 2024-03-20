package me.illusion.minigamedemo.game.map.instance;

import me.illusion.minigamedemo.game.map.config.ConfiguredMap;
import org.bukkit.Location;
import org.bukkit.World;

public interface LoadedMap {

    Location getAnchorPoint();
    ConfiguredMap getMapTemplate();
    Location getDefaultSpawnPoint();

    default World getWorld() {
        return getAnchorPoint().getWorld();
    }
}
