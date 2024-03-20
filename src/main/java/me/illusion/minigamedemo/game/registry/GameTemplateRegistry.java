package me.illusion.minigamedemo.game.registry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import me.illusion.minigamedemo.MinigameDemoPlugin;
import me.illusion.minigamedemo.game.core.template.GameTemplate;
import me.illusion.minigamedemo.game.map.instance.LoadedMap;
import me.illusion.minigamedemo.impl.sumo.SumoGameTemplate;

public class GameTemplateRegistry {

    private final Map<String, GameTemplate<?>> templates = new ConcurrentHashMap<>();
    private final MinigameDemoPlugin plugin;

    public GameTemplateRegistry(MinigameDemoPlugin plugin) {
        this.plugin = plugin;
        registerDefaults();
    }

    public void registerTemplate(GameTemplate<?> template) {
        templates.put(template.getName(), template);
    }

    public <T extends LoadedMap> GameTemplate<T> getTemplate(String name) {
        return (GameTemplate<T>) templates.get(name);
    }

    public Map<String, GameTemplate<?>> getTemplates() {
        return Map.copyOf(templates);
    }

    private void registerDefaults() {
        registerTemplate(new SumoGameTemplate(plugin));
    }

}
