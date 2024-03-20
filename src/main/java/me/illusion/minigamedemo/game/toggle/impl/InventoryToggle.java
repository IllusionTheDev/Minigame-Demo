package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryToggle extends AbstractGameToggle{

    public InventoryToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(InventoryClickEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getWhoClicked())) {
                event.setCancelled(true);
            }
        });
    }
}
