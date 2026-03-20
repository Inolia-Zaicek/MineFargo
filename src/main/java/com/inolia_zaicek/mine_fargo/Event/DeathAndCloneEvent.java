package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Cataclysm.MaledictusSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Entity.ApostleSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Supernatural.UndyingSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.nbt.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static com.inolia_zaicek.mine_fargo.Event.HurtEvent.*;
import static com.inolia_zaicek.mine_fargo.Event.HurtEvent.maledictus_soul_stone_cooldown_time;
import static com.inolia_zaicek.mine_fargo.Event.TickEvent.ultra_hostility_soul_stone;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MineFargo.MODID)
public class DeathAndCloneEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    //全局事件死亡
    public static void LivingDeath(LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        //替死
        if (MyGoUtil.hasSupernatural(livingEntity, UndyingSoulStoneItem.class) && livingEntity.getPersistentData().getInt(undying_soul_stone) == 0 ) {
            livingEntity.getPersistentData().putInt(undying_soul_stone, (int) (MyGoConfig.undying_soul_stone_cooldown.get() * 20 * 2));
            livingEntity.setHealth((float) (1 * MyGoConfig.undying_soul_stone_hp.get()));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION,(int)(MyGoConfig.undying_soul_stone_heal_time.get()*20),
                    (int)(MyGoConfig.undying_soul_stone_heal_lvl.get()-1) ));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.ABSORPTION,(int)(MyGoConfig.undying_soul_stone_damage_time.get()*20),
                    (int)(MyGoConfig.undying_soul_stone_damage_lvl.get()-1) ));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,(int)(MyGoConfig.undying_soul_stone_fire_time.get()*20), 0));
            livingEntity.deathTime = -2;
            livingEntity.isAlive();
            event.setCanceled(true);
        }
        //灾变
        else if (ModList.get().isLoaded("cataclysm")&&livingEntity.getPersistentData().getInt(maledictus_soul_stone_cooldown_time) == 0
                    && MyGoUtil.hasCataclysm(livingEntity, MaledictusSoulStoneItem.class)) {
            livingEntity.getPersistentData().putInt(maledictus_soul_stone_cooldown_time, (int) (MyGoConfig.maledictus_soul_stone_cooldown_time.get() * 20 * 2));
            //设置玩家血量（不要滥用改写
            livingEntity.setHealth((float) (livingEntity.getMaxHealth() * MyGoConfig.maledictus_soul_stone_heal.get()));
            //给予玩家鬼魂缠身buff（咒魂
            livingEntity.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                    new ResourceLocation("cataclysm", "ghost_form"))),
                    (int) (MyGoConfig.maledictus_soul_stone_time.get() * 20), 0, false, false, false));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, (int) (MyGoConfig.maledictus_soul_stone_fire_time.get() * 20),
                    0, false, false, false));
            //设置玩家死了多久（>0死透了
            livingEntity.deathTime = -2;
            //设置玩家是活着的（isAlive是个布尔值
            livingEntity.isAlive();
            //设置无敌时间
            livingEntity.invulnerableTime = 10;
            //事件可以被取消
            event.setCanceled(true);
        }
        //使徒
        else if (ModList.get().isLoaded("goety")&&MyGoUtil.hasGoetyEntity(livingEntity, ApostleSoulStoneItem.class)
                &&livingEntity.getPersistentData().getInt(apostle_soul_stone_cooldown) == 0 ) {
            livingEntity.getPersistentData().putInt(apostle_soul_stone_cooldown, (int) (MyGoConfig.apostle_soul_stone_cooldown.get() * 20 * 2));
            livingEntity.setHealth((float) (livingEntity.getMaxHealth() * MyGoConfig.apostle_soul_stone_dead_heal.get()));
            livingEntity.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, (int) (MyGoConfig.apostle_soul_stone_buff_time.get() * 20),
                    (int)(MyGoConfig.apostle_soul_stone_buff_lvl.get()-1), false, false, false));
            livingEntity.deathTime = -2;
            livingEntity.isAlive();
            livingEntity.invulnerableTime = 10;
            event.setCanceled(true);
        }
        //莱特兰 不死
        else if (ModList.get().isLoaded("l2hostility") && MyGoUtil.hasL2Hostility(livingEntity, MaledictusSoulStoneItem.class)
        && !event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)
                &&livingEntity.getPersistentData().getInt(ultra_hostility_soul_stone) == 0 ) {
            livingEntity.getPersistentData().putInt(ultra_hostility_soul_stone, (int) (MyGoConfig.ultra_hostility_soul_stone_cooldown.get() * 20 * 2));
            float health = ForgeEventFactory.onLivingHeal(livingEntity, (float) (livingEntity.getMaxHealth()*MyGoConfig.ultra_hostility_soul_stone_heal.get()));
            livingEntity.setHealth(health);
            if (livingEntity.isAlive()) {
                event.setCanceled(true);
            }
        }
    }
    public static final String gluttony_sin_soul_stone = MineFargo.MODID + ":gluttony_sin_soul_stone";
    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.getEntity().level().isClientSide) return;

        Player original = event.getOriginal();
        Player newPlayer = event.getEntity();

        CompoundTag originalData = original.getPersistentData();
        CompoundTag newData = newPlayer.getPersistentData();

        String[] keys = {
                "gluttony_sin_soul_stone",
                "magnet_soul_stone",
                "mine_fargo:gluttony_sin_soul_stone",
                "mine_fargo:magnet_soul_stone",
                "magnet_soul_stone_open",
                "mine_fargo:magnet_soul_stone_open"
        };

        for (String key : keys) {
            if (originalData.contains(key)) {
                // 根据存储类型选择对应的get方法
                Tag tag = originalData.get(key);
                if (tag instanceof ByteTag) {
                    boolean value = originalData.getBoolean(key);
                    newData.putBoolean(key, value);
                } else if (tag instanceof IntTag) {
                    int value = originalData.getInt(key);
                    newData.putInt(key, value);
                } else if (tag instanceof StringTag) {
                    String value = originalData.getString(key);
                    newData.putString(key, value);
                }
                // 可以根据需要添加其他类型
            }
        }
    }
    public boolean validTarget(LivingEntity le) {
        return le instanceof EnderDragon ? false : le.canBeAffected(new MobEffectInstance(
                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2complements", "curse"))), 100));
    }
}
