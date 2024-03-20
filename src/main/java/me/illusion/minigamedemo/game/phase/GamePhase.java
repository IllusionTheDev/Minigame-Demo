package me.illusion.minigamedemo.game.phase;

import me.illusion.minigamedemo.game.controller.EventController;
import me.illusion.minigamedemo.game.controller.LifecycleController;

public interface GamePhase extends LifecycleController, EventController {

    void start();
    void dispose();
}
