package me.illusion.minigamedemo.game.task;

import java.util.LinkedList;
import java.util.List;

public abstract class AbstractGameTask implements GameTask {

    private final List<Runnable> disposeListeners = new LinkedList<>();

    protected abstract void end();

    @Override
    public void dispose() {
        end();
        disposeListeners.forEach(Runnable::run);
    }

    @Override
    public void onDispose(Runnable runnable) {
        disposeListeners.add(runnable);
    }
}
