package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.event.entity.EntityDamageEvent;

public class DamageToggle extends AbstractGameToggle {

    public DamageToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(EntityDamageEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getEntity())) {
                event.setCancelled(true);
            }
        });
    }
}
