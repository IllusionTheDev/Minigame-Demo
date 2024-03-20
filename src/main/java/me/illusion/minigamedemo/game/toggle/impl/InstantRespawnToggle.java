package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;

public class InstantRespawnToggle extends AbstractGameToggle {

    public InstantRespawnToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(PlayerDeathEvent.class, event -> {
            Player player = event.getEntity();

            if(isEnabled() && isGameEntity(player)) {
                game.getGlobalTasks().addDelayedTask(() -> player.spigot().respawn(), 1);
            }
        });
    }
}
