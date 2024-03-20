package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.event.player.PlayerPortalEvent;
import org.bukkit.event.world.PortalCreateEvent;

public class PortalToggle extends AbstractGameToggle{

    public PortalToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(PlayerPortalEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getPlayer())) {
                event.setCancelled(true);
            }
        });

        game.subscribe(PortalCreateEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getEntity())) {
                event.setCancelled(true);
            }
        });
    }

    @Override
    protected boolean enabledByDefault() {
        return false;
    }
}
