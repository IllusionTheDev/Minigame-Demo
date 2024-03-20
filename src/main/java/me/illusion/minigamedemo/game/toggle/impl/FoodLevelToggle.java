package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class FoodLevelToggle extends AbstractGameToggle {

    public FoodLevelToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(FoodLevelChangeEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getEntity())) {
                event.setCancelled(true);
            }
        });
    }
}
