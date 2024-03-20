package me.illusion.minigamedemo.game.phase.chain;

import java.util.Collection;
import me.illusion.minigamedemo.game.phase.GamePhase;

public interface PhaseChain {

    void addPhase(GamePhase phase);

    boolean nextPhase();
    void previousPhase();

    void start();
    void dispose();

    Collection<GamePhase> getPhases();
}
