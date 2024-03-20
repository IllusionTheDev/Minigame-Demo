package me.illusion.minigamedemo.impl.sumo.task;

import me.illusion.minigamedemo.game.tracker.GamePlayerTracker;
import me.illusion.minigamedemo.impl.sumo.map.SumoLoadedMap;
import me.illusion.minigamedemo.impl.sumo.phase.SumoPlayPhase;
import org.bukkit.entity.Player;

public class SumoCheckPlayersTask implements Runnable {

    private final SumoPlayPhase phase;

    public SumoCheckPlayersTask(SumoPlayPhase phase) {
        this.phase = phase;
    }

    @Override
    public void run() {
        SumoLoadedMap map = phase.getGame().getLoadedMap();
        GamePlayerTracker tracker = phase.getGame().getPlayerTracker();

        for(Player player : tracker.getPlayers()) {
            if (!map.getBounds().contains(player.getLocation())) {
                phase.markDead(player);
            }
        }
    }
}
