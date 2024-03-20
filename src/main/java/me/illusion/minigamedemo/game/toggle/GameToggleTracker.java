package me.illusion.minigamedemo.game.toggle;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import me.illusion.minigamedemo.game.core.AbstractGame;
import me.illusion.minigamedemo.game.toggle.impl.AdvancementsToggle;
import me.illusion.minigamedemo.game.toggle.impl.BlockBreakToggle;
import me.illusion.minigamedemo.game.toggle.impl.BlockInteractionToggle;
import me.illusion.minigamedemo.game.toggle.impl.BlockPlaceToggle;
import me.illusion.minigamedemo.game.toggle.impl.CraftingToggle;
import me.illusion.minigamedemo.game.toggle.impl.DamageToggle;
import me.illusion.minigamedemo.game.toggle.impl.DayNightToggle;
import me.illusion.minigamedemo.game.toggle.impl.EntityInteractionToggle;
import me.illusion.minigamedemo.game.toggle.impl.FallDamageToggle;
import me.illusion.minigamedemo.game.toggle.impl.FoodLevelToggle;
import me.illusion.minigamedemo.game.toggle.impl.InstantRespawnToggle;
import me.illusion.minigamedemo.game.toggle.impl.InstantVoidDeathToggle;
import me.illusion.minigamedemo.game.toggle.impl.InventoryToggle;
import me.illusion.minigamedemo.game.toggle.impl.ItemDropToggle;
import me.illusion.minigamedemo.game.toggle.impl.ItemPickupToggle;
import me.illusion.minigamedemo.game.toggle.impl.MovementToggle;
import me.illusion.minigamedemo.game.toggle.impl.NaturalMobsToggle;
import me.illusion.minigamedemo.game.toggle.impl.PVPToggle;
import me.illusion.minigamedemo.game.toggle.impl.PortalToggle;
import me.illusion.minigamedemo.game.toggle.impl.WeatherToggle;

public class GameToggleTracker {

    private final Map<String, GameToggle> toggles = new ConcurrentHashMap<>();
    private final AbstractGame game;

    public GameToggleTracker(AbstractGame game) {
        this.game = game;

        registerDefaults();
    }

    public void register(String name, GameToggle toggle) {
        this.toggles.put(name, toggle);
    }

    public GameToggle getToggle(String name) {
        return toggles.get(name);
    }

    private void operate(Consumer<GameToggle> consumer, String... names) {
        for (String name : names) {
            GameToggle toggle = getToggle(name);

            if (toggle != null) {
                consumer.accept(toggle);
            }
        }
    }

    public void enable(String... names) {
        operate(GameToggle::enable, names);
    }

    public void disable(String... names) {
        operate(GameToggle::disable, names);
    }

    public boolean isEnabled(String name) {
        GameToggle toggle = getToggle(name);

        return toggle != null && toggle.isEnabled();
    }

    public void setEnabled(String name, boolean enabled) {
        GameToggle toggle = getToggle(name);

        if (toggle != null) {
            if (enabled) {
                toggle.enable();
            } else {
                toggle.disable();
            }
        }
    }

    private void registerDefaults() {
        // Register default toggles
        register(GameToggles.FOOD_LEVEL, new FoodLevelToggle(game));
        register(GameToggles.PVP, new PVPToggle(game));
        register(GameToggles.DAMAGE, new DamageToggle(game));
        register(GameToggles.BLOCK_BREAK, new BlockBreakToggle(game));
        register(GameToggles.BLOCK_PLACE, new BlockPlaceToggle(game));
        register(GameToggles.ITEM_PICKUP, new ItemPickupToggle(game));
        register(GameToggles.ITEM_DROP, new ItemDropToggle(game));
        register(GameToggles.BLOCK_INTERACT, new BlockInteractionToggle(game));
        register(GameToggles.ENTITY_INTERACT, new EntityInteractionToggle(game));
        register(GameToggles.PORTAL, new PortalToggle(game));
        register(GameToggles.CRAFTING, new CraftingToggle(game));
        register(GameToggles.ADVANCEMENTS, new AdvancementsToggle(game));
        register(GameToggles.INVENTORY, new InventoryToggle(game));
        register(GameToggles.NATURAL_MOBS, new NaturalMobsToggle(game));
        register(GameToggles.DAY_NIGHT, new DayNightToggle(game));
        register(GameToggles.WEATHER, new WeatherToggle(game));
        register(GameToggles.INSTANT_RESPAWN, new InstantRespawnToggle(game));
        register(GameToggles.INSTANT_VOID_DEATH, new InstantVoidDeathToggle(game));
        register(GameToggles.MOVEMENT, new MovementToggle(game));
        register(GameToggles.FALL_DAMAGE, new FallDamageToggle(game));
    }


}
