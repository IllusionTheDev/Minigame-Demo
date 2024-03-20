package me.illusion.minigamedemo.game.map.config;

import me.illusion.minigamedemo.utilities.config.ConfiguredObject;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

public abstract class ConfiguredMap extends ConfiguredObject {

    // Required for inheritance
    // protected final ConfigurationSection config; <-- Get this from the configuredobject protected field

    // Applicable for every map
    protected final MapMetadata metadata;

    protected ConfiguredMap(ConfigurationSection config) {
        super(config);

        this.metadata = MapMetadata.create(getMetadataSection());
        this.load(config);
    }

    protected abstract void load(ConfigurationSection config);

    private ConfigurationSection getMetadataSection() {
        return config.getConfigurationSection("metadata");
    }

    public String getName() {
        return metadata.getName();
    }

    public ConfigurationSection getConfig() {
        return config; // In case any other class needs the config
    }

    public MapMetadata getMetadata() {
        return metadata;
    }

    public Location getPosition(String identifier) {
        return getLocation(identifier);
    }
}
