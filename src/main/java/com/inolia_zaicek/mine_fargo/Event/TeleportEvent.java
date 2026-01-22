package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Register.MyGoEffectsRegister;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TeleportEvent {
    @SubscribeEvent
    public static void event(EntityTeleportEvent event) {
        //有抑影，阻止传送
        if (event.getEntity() instanceof LivingEntity livingEntity&& livingEntity.hasEffect(MyGoEffectsRegister.Enderference.get())) {
            event.setCanceled(true);
        }
    }
}
