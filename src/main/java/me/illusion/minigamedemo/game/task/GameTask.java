package me.illusion.minigamedemo.game.task;

public interface GameTask {

    void start();

    void dispose();

    void onDispose(Runnable runnable);
}
