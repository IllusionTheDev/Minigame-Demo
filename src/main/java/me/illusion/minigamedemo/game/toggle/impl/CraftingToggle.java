package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.event.inventory.PrepareItemCraftEvent;

public class CraftingToggle extends AbstractGameToggle{

    public CraftingToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(PrepareItemCraftEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getView().getPlayer())) {
                event.getInventory().setResult(null);
            }
        });
    }
}
