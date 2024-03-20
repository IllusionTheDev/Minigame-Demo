package me.illusion.minigamedemo.game.event;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.entity.Player;

public class GamePlayerAddEvent extends GameEvent {

    private final Player player;

    public GamePlayerAddEvent(Player player, AbstractGame game) {
        super(game);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}
