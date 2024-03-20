package me.illusion.minigamedemo.impl.sumo.phase;

import me.illusion.minigamedemo.game.core.AbstractGame;
import me.illusion.minigamedemo.game.phase.AbstractPhase;
import me.illusion.minigamedemo.game.toggle.GameToggles;

public class SumoDisableTogglesPhase extends AbstractPhase<AbstractGame> {

    public SumoDisableTogglesPhase(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.getGameToggleTracker().disable(
            GameToggles.FOOD_LEVEL,
            GameToggles.PVP,
            GameToggles.DAMAGE,
            GameToggles.BLOCK_BREAK,
            GameToggles.BLOCK_PLACE,
            GameToggles.ITEM_PICKUP,
            GameToggles.ITEM_DROP,
            GameToggles.BLOCK_INTERACT,
            GameToggles.ENTITY_INTERACT,
            GameToggles.PORTAL,
            GameToggles.CRAFTING,
            GameToggles.INVENTORY,
            GameToggles.ADVANCEMENTS,
            GameToggles.NATURAL_MOBS,
            GameToggles.WEATHER,
            GameToggles.DAY_NIGHT,
            GameToggles.FALL_DAMAGE
        );

        nextPhase();
    }

    @Override
    public void teardown() {

    }
}
