package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;

public class ItemDropToggle extends AbstractGameToggle{

    public ItemDropToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(PlayerDropItemEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getPlayer())) {
                event.setCancelled(true);
            }
        });

        game.subscribe(InventoryClickEvent.class, event -> {
            if (isEnabled()) {
                return;
            }

            if (!isGameEntity(event.getWhoClicked())) {
                return;
            }

            if (isDropAction(event.getAction())) {
                event.setCancelled(true);
            }
        });
    }

    private boolean isDropAction(InventoryAction action) {
        return action == InventoryAction.DROP_ALL_CURSOR || action == InventoryAction.DROP_ONE_CURSOR || action == InventoryAction.DROP_ALL_SLOT || action == InventoryAction.DROP_ONE_SLOT;
    }
}
