package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Cataclysm.MaledictusSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static com.inolia_zaicek.mine_fargo.Event.HurtEvent.maledictus_soul_stone_cooldown_time;

public class LivingDeathEvent {
    @SubscribeEvent(priority = EventPriority.HIGH)
    //全局事件死亡
    public static void LivingDeathVampire(net.minecraftforge.event.entity.living.LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        //灾变
        if (ModList.get().isLoaded("cataclysm")) {
            //检测到玩家寄了&&玩家没有鬼魅缠身buff
            if (livingEntity.getPersistentData().getInt(maledictus_soul_stone_cooldown_time) ==0
                    && MyGoUtil.hasCataclysm(livingEntity, MaledictusSoulStoneItem.class)) {
                livingEntity.getPersistentData().putInt(maledictus_soul_stone_cooldown_time, (int) (MyGoConfig.ender_guardian_soul_stone_cooldown.get()*20*2));
                //设置玩家血量（不要滥用改写
                livingEntity.setHealth((float) (livingEntity.getMaxHealth()*MyGoConfig.maledictus_soul_stone_heal.get()));
                //给予玩家鬼魂缠身buff（咒魂
                livingEntity.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                        new ResourceLocation("cataclysm", "ghost_form"))),
                        (int) (MyGoConfig.maledictus_soul_stone_time.get() * 1), 0, false, false,false));
                livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,(int) (MyGoConfig.maledictus_soul_stone_fire_time.get() * 1),
                        0,false,false,false));
                //设置玩家死了多久（>0死透了
                livingEntity.deathTime = -2;
                //设置玩家是活着的（isAlive是个布尔值
                livingEntity.isAlive();
                //设置无敌时间
                livingEntity.invulnerableTime = 10;
                //事件可以被取消
                event.setCanceled(true);
            }
        }
    }
}
