package me.illusion.minigamedemo.game.map.config;

import org.bukkit.configuration.ConfigurationSection;

public class MapMetadata {

    private final String name;
    private final String template;
    // Add whatever data you'd like

    private MapMetadata(String name, String template) {
        this.name = name;
        this.template = template;
    }

    public static MapMetadata create(String name, String template) {
        return new MapMetadata(name, template);
    }

    public static MapMetadata create(ConfigurationSection section) {
        if (section == null) {
            throw new IllegalArgumentException("Section cannot be null");
        }

        String name = section.getString("name", "Unnamed Map");
        String template = section.getString("template");

        if (template == null) {
            throw new IllegalArgumentException("Template cannot be null");
        }

        return new MapMetadata(name, template);
    }

    public String getTemplate() {
        return template;
    }

    public String getName() {
        return name;
    }
}
