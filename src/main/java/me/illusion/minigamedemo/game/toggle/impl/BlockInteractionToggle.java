package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockInteractionToggle extends AbstractGameToggle{

    public BlockInteractionToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(PlayerInteractEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getPlayer())) {
                event.setCancelled(true);
            }
        });
    }
}
