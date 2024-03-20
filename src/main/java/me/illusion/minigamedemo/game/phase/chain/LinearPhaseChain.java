package me.illusion.minigamedemo.game.phase.chain;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import me.illusion.minigamedemo.game.phase.GamePhase;

public class LinearPhaseChain implements PhaseChain {

    private final List<GamePhase> phases = new LinkedList<>();
    private int currentPhase = 0;

    @Override
    public void addPhase(GamePhase phase) {
        phases.add(phase);
    }

    private void endPhase() {
        phases.get(currentPhase).dispose();
        currentPhase++;
    }

    private void startPhase() {
        GamePhase phase = phases.get(currentPhase);
        phase.start();
    }

    @Override
    public boolean nextPhase() {
        if (isFinished()) {
            return false; // HUH?
        }

        endPhase();

        if (isFinished()) {
            return false;
        }

        startPhase();
        return true;
    }

    @Override
    public void previousPhase() {
        if (currentPhase == 0 || isFinished()) {
            return;
        }

        int nextPhase = currentPhase - 1;
        endPhase();
        currentPhase = nextPhase;
        startPhase();
    }

    @Override
    public void start() {
        startPhase();
    }

    @Override
    public void dispose() {
        for (GamePhase phase : phases) {
            phase.dispose();
        }
    }

    @Override
    public Collection<GamePhase> getPhases() {
        return List.copyOf(phases);
    }

    private boolean isFinished() {
        return currentPhase >= phases.size();
    }

}
