package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.World;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherToggle extends AbstractGameToggle{

    public WeatherToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(WeatherChangeEvent.class, event -> {
            World world = event.getWorld();

            if (!isEnabled() && isGameWorld(world)) {
                event.setCancelled(true);
                world.setThundering(false);
                world.setStorm(false);
            }
        });
    }

    @Override
    protected boolean enabledByDefault() {
        return false;
    }
}
