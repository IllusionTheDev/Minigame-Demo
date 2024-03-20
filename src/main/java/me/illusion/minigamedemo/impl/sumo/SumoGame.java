package me.illusion.minigamedemo.impl.sumo;

import me.illusion.minigamedemo.MinigameDemoPlugin;
import me.illusion.minigamedemo.game.core.AbstractGame;
import me.illusion.minigamedemo.game.core.template.GameTemplate;
import me.illusion.minigamedemo.game.map.instance.LoadedMap;
import me.illusion.minigamedemo.game.phase.impl.KickEverybodyPhase;
import me.illusion.minigamedemo.game.phase.impl.WaitForPlayersPhase;
import me.illusion.minigamedemo.game.phase.impl.WaitPhase;
import me.illusion.minigamedemo.impl.sumo.map.SumoLoadedMap;
import me.illusion.minigamedemo.impl.sumo.phase.SumoDisableTogglesPhase;
import me.illusion.minigamedemo.impl.sumo.phase.SumoEnableTogglesPhase;
import me.illusion.minigamedemo.impl.sumo.phase.SumoPlayPhase;

public class SumoGame extends AbstractGame {

    private static final int FIFTEEN_SECONDS = 15 * 20;
    private static final int FIVE_SECONDS = 5 * 20;

    private final SumoLoadedMap loadedMap;

    public SumoGame(SumoLoadedMap map, GameTemplate<?> template, MinigameDemoPlugin plugin) {
        super(map, template, plugin);

        this.loadedMap = map;
    }

    @Override
    protected void setup() {
        registerPhase(new SumoDisableTogglesPhase(this));
        registerPhase(new WaitForPlayersPhase(this, 2));
        registerPhase(new WaitPhase(this, FIFTEEN_SECONDS));

        registerPhase(new SumoEnableTogglesPhase(this));
        registerPhase(new SumoPlayPhase(this));

        registerPhase(new SumoDisableTogglesPhase(this)); // Disable all interaction, maybe make a custom phase that lets people fly?
        registerPhase(new WaitPhase(this, FIVE_SECONDS));

        registerPhase(new KickEverybodyPhase(this));
    }

    @Override
    protected void teardown() {

    }

    public SumoLoadedMap getLoadedMap() {
        return loadedMap;
    }
}
