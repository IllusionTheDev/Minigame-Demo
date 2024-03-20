package me.illusion.minigamedemo.utilities.config;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.Vector;

public class ConfiguredObject {

    protected final ConfigurationSection config;

    protected ConfiguredObject(ConfigurationSection config) {
        this.config = config;
    }

    protected Vector getVector(String identifier) {
        return parseVector(config.getString(identifier));
    }

    protected Location getLocation(String identifier) {
        return parseLocation(config.getString(identifier));
    }

    protected Vector parseVector(String text) {
        if (text == null) {
            return null;
        }

        String[] split = text.split(" ");
        return new Vector(Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]));
    }

    protected Location parseLocation(String text) {
        if (text == null) {
            return null;
        }

        String[] split = text.split(" ");
        float yaw = split.length > 3 ? Float.parseFloat(split[3]) : 0;
        float pitch = split.length > 4 ? Float.parseFloat(split[4]) : 0;

        return new Location(null, Double.parseDouble(split[0]), Double.parseDouble(split[1]), Double.parseDouble(split[2]), yaw, pitch);
    }

    protected Color getColor(String identifier) {
        int red = config.getInt(identifier + ".red");
        int green = config.getInt(identifier + ".green");
        int blue = config.getInt(identifier + ".blue");

        return Color.fromRGB(red, green, blue);
    }

    protected String getString(String identifier) {
        return config.getString(identifier);
    }

    protected int getInt(String identifier) {
        return config.getInt(identifier);
    }

    protected boolean getBoolean(String identifier) {
        return config.getBoolean(identifier);
    }

    protected double getDouble(String identifier) {
        return config.getDouble(identifier);
    }

}