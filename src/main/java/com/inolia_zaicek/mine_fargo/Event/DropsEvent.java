package com.inolia_zaicek.mine_fargo.Event;

import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.inolia_zaicek.mine_fargo.Util.MyGoUtil.isBossEntity;

public class DropsEvent {
    //用于判断击杀名称
    private static final String the_community_kill_id_nbt = "the_community_kill_id";
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void drop(LivingDropsEvent event) {
        if (isBossEntity(event.getEntity().getType())) {
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
            }
        }
    }
}
