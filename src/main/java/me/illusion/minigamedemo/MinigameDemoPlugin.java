package me.illusion.minigamedemo;

import me.illusion.minigamedemo.game.registry.GameInstanceTracker;
import me.illusion.minigamedemo.game.registry.GameTemplateRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinigameDemoPlugin extends JavaPlugin {

    private GameInstanceTracker instanceTracker;
    private GameTemplateRegistry templateRegistry;

    @Override
    public void onEnable() {
        // Plugin startup logic
        this.templateRegistry = new GameTemplateRegistry(this);
        this.instanceTracker = new GameInstanceTracker(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public GameInstanceTracker getInstanceTracker() {
        return instanceTracker;
    }

    public GameTemplateRegistry getTemplateRegistry() {
        return templateRegistry;
    }
}
