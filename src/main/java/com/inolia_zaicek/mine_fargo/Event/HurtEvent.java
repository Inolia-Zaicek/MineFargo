package com.inolia_zaicek.mine_fargo.Event;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.confluence.mod.misc.ModAttributes;

import java.util.Objects;
import java.util.Random;

import static net.minecraft.tags.DamageTypeTags.*;

public class HurtEvent {

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        if (attacked != null) {
            float baseDamage = event.getAmount();
            float number = 1;
            float overNumber = 1;
            float fixedNumber = 0;
            float damage = Math.max(0, (baseDamage * number + fixedNumber) * overNumber);
            event.setAmount(damage);
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != null) {
            float number = 1;
            float overNumber = 1;
            float fixedNumber = 0;
            float damage = (event.getAmount() * number + fixedNumber) * overNumber;
            event.setAmount(damage);
        }
    }
}