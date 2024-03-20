package me.illusion.minigamedemo.game.phase.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import me.illusion.minigamedemo.game.phase.AbstractPhase;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class KickEverybodyPhase extends AbstractPhase<AbstractGame> {

    public KickEverybodyPhase(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        for (Player player : game.getPlayerTracker().getPlayers()) {
            player.kick(Component.text("The game is over!"));
        }

        nextPhase();
    }

    @Override
    public void teardown() {

    }
}
