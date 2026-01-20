package com.inolia_zaicek.mine_fargo.Event;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class HealEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void heal(LivingHealEvent event) {
        LivingEntity livingEntity = event.getEntity();
        //基础数值
        float number = 1;
        if(number!=1){
            event.setAmount(event.getAmount()*number);
        }
    }
}
