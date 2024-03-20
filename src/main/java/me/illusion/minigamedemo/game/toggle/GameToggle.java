package me.illusion.minigamedemo.game.toggle;

public interface GameToggle {

    void enable();

    void disable();

    boolean isEnabled();

    default void toggle() {
        if (isEnabled()) {
            disable();
        } else {
            enable();
        }
    }
}
