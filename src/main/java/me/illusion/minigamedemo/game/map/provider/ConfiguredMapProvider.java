package me.illusion.minigamedemo.game.map.provider;

import me.illusion.minigamedemo.game.map.config.ConfiguredMap;
import org.bukkit.configuration.ConfigurationSection;

public interface ConfiguredMapProvider<C extends ConfiguredMap> {

    C provide(ConfigurationSection section);
}
