package me.illusion.minigamedemo.impl.sumo.phase;

import me.illusion.minigamedemo.game.phase.AbstractPhase;
import me.illusion.minigamedemo.impl.sumo.SumoGame;
import me.illusion.minigamedemo.impl.sumo.task.SumoCheckPlayersTask;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class SumoPlayPhase extends AbstractPhase<SumoGame> {

    public SumoPlayPhase(SumoGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        // No-OP
    }

    @Override
    public void teardown() {
        // No-OP
    }

    @Override
    protected void registerTasks() {
        tasks.addRepeatingTask(new SumoCheckPlayersTask(this), 1, 1);
    }

    public void markDead(Player player) {
        player.sendMessage("You died! Noob."); // Feel free to make a nice messaging system. This is just a demo.
        nextPhase();
    }
}
