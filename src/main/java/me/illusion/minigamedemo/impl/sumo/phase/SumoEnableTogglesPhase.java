package me.illusion.minigamedemo.impl.sumo.phase;

import me.illusion.minigamedemo.game.core.AbstractGame;
import me.illusion.minigamedemo.game.phase.AbstractPhase;
import me.illusion.minigamedemo.game.toggle.GameToggles;

public class SumoEnableTogglesPhase extends AbstractPhase<AbstractGame> {

    public SumoEnableTogglesPhase(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.getGameToggleTracker().enable(
            GameToggles.PVP,
            GameToggles.DAMAGE,
            GameToggles.ENTITY_INTERACT
        );

        nextPhase();
    }

    @Override
    public void teardown() {

    }
}
