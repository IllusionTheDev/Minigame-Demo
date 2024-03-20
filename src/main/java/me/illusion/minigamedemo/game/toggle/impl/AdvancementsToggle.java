package me.illusion.minigamedemo.game.toggle.impl;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

public class AdvancementsToggle extends AbstractGameToggle{

    public AdvancementsToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(PlayerAdvancementCriterionGrantEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getPlayer())) {
                event.setCancelled(true);
            }
        });

        game.subscribe(PlayerAdvancementDoneEvent.class, event -> {
            if (!isEnabled() && isGameEntity(event.getPlayer())) {
                event.message(null);
            }
        });
    }

    @Override
    protected boolean enabledByDefault() {
        return false;
    }
}
