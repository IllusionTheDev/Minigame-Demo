package me.illusion.minigamedemo.impl.sumo.map;

import me.illusion.minigamedemo.MinigameDemoPlugin;
import me.illusion.minigamedemo.game.map.config.ConfiguredMap;
import me.illusion.minigamedemo.game.map.instance.AbstractLoadedMap;
import me.illusion.minigamedemo.utilities.geometry.Cuboid;
import org.bukkit.Location;

public class SumoLoadedMap extends AbstractLoadedMap<SumoConfiguredMap> {

    public SumoLoadedMap(MinigameDemoPlugin plugin, SumoConfiguredMap mapTemplate, Location anchor) {
        super(plugin, mapTemplate, anchor);
    }

    public Location getFirstSpawn() {
        return getLocation("spawn-1");
    }

    public Location getSecondSpawn() {
        return getLocation("spawn-2");
    }

    public Cuboid getBounds() {
        return mapTemplate.getBounds().add(anchor);
    }
}
