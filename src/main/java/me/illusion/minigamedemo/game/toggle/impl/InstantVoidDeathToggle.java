package me.illusion.minigamedemo.game.toggle.impl;

import me.illusion.minigamedemo.game.core.AbstractGame;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityDamageEvent;

public class InstantVoidDeathToggle extends AbstractGameToggle{

    public InstantVoidDeathToggle(AbstractGame game) {
        super(game);
    }

    @Override
    protected void setup() {
        game.subscribe(EntityDamageEvent.class, (event) -> {
            if (!isEnabled()) {
                return;
            }

            Entity entity = event.getEntity();

            if (!isGameEntity(entity)) {
                return;
            }

            if(!(entity instanceof LivingEntity living)) {
                return;
            }

            if (event.getCause() == EntityDamageEvent.DamageCause.VOID || entity.getLocation().getY() < 0) {
                event.setDamage(living.getHealth());
            }
        });
    }
}
