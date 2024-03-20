package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class PVPToggle extends AbstractGameToggle {

    public PVPToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(EntityDamageByEntityEvent.class, event -> {
            if (isEnabled()) {
                return;
            }

            Entity damager = event.getDamager();

            if (isGameEntity(damager)) {
                event.setCancelled(true);
            }
        });
    }
}
