package me.illusion.minigamedemo.game.phase.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import me.illusion.minigamedemo.game.event.GamePlayerAddEvent;
import me.illusion.minigamedemo.game.phase.AbstractPhase;

public class WaitForPlayersPhase extends AbstractPhase<AbstractGame> {

    private final int requiredPlayers;

    public WaitForPlayersPhase(AbstractGame game, int requiredPlayers) {
        super(game);
        this.requiredPlayers = requiredPlayers;
    }

    @Override
    public void setup() {
        handleAdd();
        subscribe(GamePlayerAddEvent.class, ignored -> handleAdd());
    }

    @Override
    public void teardown() {

    }

    private void handleAdd() {
        int playerCount = game.getPlayerTracker().getPlayerCount();

        if (playerCount >= requiredPlayers) {
            nextPhase();
        }
    }

}
