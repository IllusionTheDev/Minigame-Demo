package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import me.illusion.minigamedemo.game.toggle.GameToggle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public abstract class AbstractGameToggle implements GameToggle {

    protected final AbstractGame game;
    protected boolean enabled;

    protected AbstractGameToggle(AbstractGame game) {
        this.game = game;
        this.enabled = enabledByDefault();

        setup();
    }

    protected abstract void setup();

    protected boolean enabledByDefault() {
        return true;
    }

    protected boolean isGameEntity(Entity entity) {
        if (entity == null) {
            return false;
        }

        if (entity instanceof Player player) {
            return game.getPlayerTracker().isPlayerInGame(player);
        }

        return isGameWorld(entity.getWorld());
    }

    protected boolean isGameWorld(World world) {
        return world != null && game.getMap().getWorld().getName().equals(world.getName());
    }

    @Override
    public void enable() {
        this.enabled = true;
    }

    @Override
    public void disable() {
        this.enabled = false;
    }

    @Override
    public final boolean isEnabled() {
        return enabled;
    }
}
