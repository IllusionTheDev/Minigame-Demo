package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.event.GamePlayerAddEvent;
import me.illusion.minigamedemo.game.event.GamePlayerRemoveEvent;
import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

public class MovementToggle extends AbstractGameToggle {

    private static final float DEFAULT_WALK_SPEED = 0.2f;
    private static final float DEFAULT_FLY_SPEED = 0.1f;

    public MovementToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(GamePlayerAddEvent.class, event -> {
            updatePlayer(event.getPlayer());
        });

        game.subscribe(GamePlayerRemoveEvent.class, event -> {
            updatePlayer(event.getPlayer());
        });

        game.subscribe(PlayerMoveEvent.class, event -> {
            if(!enabled && isGameEntity(event.getPlayer())) {
                Vector direction = event.getTo().toVector().subtract(event.getFrom().toVector());

                if (direction.getY() > 0 || direction.getX() != 0 || direction.getZ() != 0) {
                    event.setCancelled(true);
                }
            }
        });
    }

    @Override
    public void disable() {
        super.disable();

        for (Player player : game.getPlayerTracker().getPlayers()) {
            updatePlayer(player);
        }
    }

    @Override
    public void enable() {
        super.enable();

        for (Player player : game.getPlayerTracker().getPlayers()) {
            updatePlayer(player);
        }
    }

    private void updatePlayer(Player player) {
        if (isEnabled()) {
            player.setWalkSpeed(DEFAULT_WALK_SPEED);
            player.setFlySpeed(DEFAULT_FLY_SPEED);
        } else {
            player.setWalkSpeed(0);
            player.setFlySpeed(0);
        }
    }

}
