package me.illusion.minigamedemo.impl.sumo.map;

import me.illusion.minigamedemo.game.map.config.ConfiguredMap;
import me.illusion.minigamedemo.utilities.geometry.Cuboid;
import org.bukkit.configuration.ConfigurationSection;

public class SumoConfiguredMap extends ConfiguredMap {

    private Cuboid bounds;

    public SumoConfiguredMap(ConfigurationSection config) {
        super(config);
    }

    @Override
    protected void load(ConfigurationSection config) {
        this.bounds = new Cuboid(
            getPosition("bounds.min"),
            getPosition("bounds.max")
        );
    }

    public Cuboid getBounds() {
        return bounds;
    }
}
