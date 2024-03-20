package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.event.entity.EntityPickupItemEvent;

public class ItemPickupToggle extends AbstractGameToggle{

    public ItemPickupToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(EntityPickupItemEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getEntity())) {
                event.setCancelled(true);
            }
        });
    }
}
