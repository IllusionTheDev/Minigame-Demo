package me.illusion.minigamedemo.game.event;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public abstract class GameEvent extends Event {

    private static final HandlerList handlerList = new HandlerList();
    private final AbstractGame game;

    protected GameEvent(AbstractGame game) {
        super(!Bukkit.isPrimaryThread());
        this.game = game;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public AbstractGame getGame() {
        return game;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

}
