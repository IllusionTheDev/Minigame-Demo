package me.illusion.minigamedemo.game.phase.impl;


import me.illusion.minigamedemo.game.core.AbstractGame;
import me.illusion.minigamedemo.game.phase.AbstractPhase;

public class WaitPhase extends AbstractPhase<AbstractGame> {

    private final long ticks;

    public WaitPhase(AbstractGame game, long ticks) {
        super(game);
        this.ticks = ticks;
    }

    @Override
    public void setup() {
        tasks.addDelayedTask(this::nextPhase, ticks);
    }

    @Override
    public void teardown() {

    }
}
