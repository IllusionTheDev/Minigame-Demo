package me.illusion.minigamedemo.game.controller;

import me.illusion.minigamedemo.game.lifecycle.LifecyclePhase;
import me.illusion.minigamedemo.game.lifecycle.LifecycleTasks;

public interface LifecycleController {

    LifecycleTasks getLifecycleTasks();

    default void onLifecyclePhase(LifecyclePhase phase, Runnable runnable) {
        getLifecycleTasks().addTask(phase, runnable);
    }

    default void callLifecycleTasks(LifecyclePhase phase) {
        getLifecycleTasks().callTasks(phase);
    }
}
