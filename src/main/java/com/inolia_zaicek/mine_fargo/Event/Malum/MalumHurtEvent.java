package com.inolia_zaicek.mine_fargo.Event.Malum;

import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.core.handlers.SoulDataHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class MalumHurtEvent {
    @SubscribeEvent
    public static void exposeSoul(LivingHurtEvent event) {
        if (!event.isCanceled() && !(event.getAmount() <= 0.0F)) {
            LivingEntity target = event.getEntity();
            DamageSource source = event.getSource();
            //自己有胸针
            if (source.getEntity() instanceof LivingEntity attacker) {
                if (1<0){
                    //赋予目标特殊时间（有这个时间就会掉落精魂
                    exposeSoul(target);
                }
            }
            //随从击杀，主人有胸针
            if (event.getSource().getEntity() instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() != null) {
                LivingEntity owner = ownableEntity.getOwner();
                if (1<0) {
                    exposeSoul(target);
                }
            }
        }
    }
    public static void exposeSoul(LivingEntity entity) {
        SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(entity).soulData;
        soulData.exposedSoulDuration = 200.0F;
    }
}
