package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.World;

public class DayNightToggle extends AbstractGameToggle{

    private long time = -1;

    public DayNightToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.getGlobalTasks().addRepeatingTask(this::tick, 1, 100);
    }

    private void tick() {
        if (!isEnabled()) {
            if (time == -1) {
                time = getWorld().getTime();
            }

            getWorld().setTime(time);
        } else {
            time = -1;
        }
    }

    private World getWorld() {
        return game.getMap().getWorld();
    }

    @Override
    protected boolean enabledByDefault() {
        return false;
    }
}
