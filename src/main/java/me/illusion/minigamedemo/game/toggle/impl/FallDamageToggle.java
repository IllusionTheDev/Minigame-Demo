package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.entity.Entity;
import org.bukkit.event.entity.EntityDamageEvent;

public class FallDamageToggle extends AbstractGameToggle {

    public FallDamageToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(EntityDamageEvent.class, (event) -> {
            Entity entity = event.getEntity();

            if (!isGameEntity(entity)) {
                return;
            }

            if (!isEnabled() && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                event.setCancelled(true);
            }
        });
    }

}
