package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

public class NaturalMobsToggle extends AbstractGameToggle{

    public NaturalMobsToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(CreatureSpawnEvent.class, event -> {
            if (!isEnabled() && isGameWorld(event.getEntity().getWorld()) && event.getSpawnReason() != SpawnReason.CUSTOM) {
                event.setCancelled(true);
            }
        });
    }

    @Override
    protected boolean enabledByDefault() {
        return false;
    }
}
