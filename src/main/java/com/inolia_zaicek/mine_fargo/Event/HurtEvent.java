package com.inolia_zaicek.mine_fargo.Event;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Entity.*;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.EnderSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.LavaSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.NetherSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.SnowSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

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
            if (MyGoUtil.hasNature(attacked, EnderSoulStoneItem.class)
                    &&event.getSource().getEntity()!=null&&event.getSource().getEntity() instanceof EnderMan) {
                number -= MyGoConfig.ender_soul_stone_armor.get() ;
            }
            if (MyGoUtil.hasNature(attacked, SnowSoulStoneItem.class)&&event.getSource().is(IS_FREEZING)) {
                event.setCanceled(true);
            }else if (MyGoUtil.hasNature(attacked, LavaSoulStoneItem.class)&&event.getSource().is(IS_FIRE)) {
                event.setCanceled(true);
            }else if (MyGoUtil.hasEntity(attacked, WingSoulStoneItem.class)&&event.getSource().is(IS_FALL)) {
                event.setCanceled(true);
            }else {
                float damage = Math.max(0, (baseDamage * number + fixedNumber) * overNumber);
                event.setAmount(damage);
            }
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != null) {
            //普通乘区内，150%是+0.5，即+0.5
            float number = 1;
            float overNumber = 1;
            float fixedNumber = 0;
            var map = attacked.getActiveEffectsMap();
            int freeze = 0;
            if (MyGoUtil.hasNature(attacker, SnowSoulStoneItem.class)) {
                freeze+=1;
            }
            if (MyGoUtil.hasNature(attacker, NetherSoulStoneItem.class)&&attacker.level().dimension().equals(Level.NETHER)) {
                number += MyGoConfig.nether_soul_stone.get() ;
            }
            if (MyGoUtil.hasNature(attacker, EnderSoulStoneItem.class)&&attacked instanceof EnderMan) {
                number += MyGoConfig.ender_soul_stone_attack.get() ;
            }
            if (MyGoUtil.hasEntity(attacker, AquaticSoulStoneItem.class)&&
                    (attacker.isUnderWater()||attacker.isInWater()||attacker.isInWaterOrRain()||attacker.isInWaterRainOrBubble())
            ) {
                number += MyGoConfig.aquatic_soul_stone.get() ;
            }
            if (MyGoUtil.hasEntity(attacker, BlazeSoulStoneItem.class)) {
                attacked.setRemainingFireTicks(attacked.getRemainingFireTicks()+100);
                if(attacked.getRemainingFireTicks()>0) {
                    number += MyGoConfig.blaze_soul_stone.get();
                }
            }
            if (MyGoUtil.hasEntity(attacker, DeathSoulStoneItem.class)&&attacked.getMobType() == MobType.UNDEAD) {
                number += MyGoConfig.death_soul_stone.get() ;
            }
            if (MyGoUtil.hasEntity(attacker, ArthropodSoulStoneItem.class)&&attacked.getMobType() == MobType.ARTHROPOD) {
                number += MyGoConfig.arthropod_soul_stone.get() ;
            }
            if(freeze>0){
                attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100*freeze, freeze-1));
                if (!attacked.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                    map.put(MobEffects.MOVEMENT_SLOWDOWN, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100*freeze, freeze-1));
                }
            }
            float damage = (event.getAmount() * number + fixedNumber) * overNumber;
            event.setAmount(damage);
        }
    }
}