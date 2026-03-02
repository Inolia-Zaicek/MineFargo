package com.inolia_zaicek.mine_fargo.Event.Goety;

import com.Polarice3.Goety.common.entities.ally.illager.AllyIrk;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Damage.MyGoDamageType;
import com.inolia_zaicek.mine_fargo.Item.Cataclysm.MaledictusSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Entity.ApostleSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Entity.VizierSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

import static com.inolia_zaicek.mine_fargo.Event.HurtEvent.*;
import static com.inolia_zaicek.mine_fargo.Event.HurtEvent.maledictus_soul_stone_cooldown_time;
import static com.inolia_zaicek.mine_fargo.Event.TickEvent.vizier_soul_stone_dead_cooldown;

public class GoetyDeathEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    //全局事件死亡
    public static void LivingDeathVampire(net.minecraftforge.event.entity.living.LivingDeathEvent event) {
        LivingEntity livingEntity = event.getEntity();
        if (ModList.get().isLoaded("goety")&&MyGoUtil.hasGoetyEntity(livingEntity, VizierSoulStoneItem.class)
                &&livingEntity.getPersistentData().getInt(vizier_soul_stone_dead_cooldown) == 0 ) {
            //周围是否有自己的怒鬼
            var mobList = MyGoUtil.mobList(21, livingEntity);
            Mob nearestMob = null;
            double nearestDist = Double.MAX_VALUE;
            for (Mob mobs : mobList) {
                //是怒鬼，主人是自己mobs instanceof AllyIrk allyIrk
                if (
                        //ID是怒鬼
                        (EntityType.getKey(event.getEntity().getType()).toString().equals("goety:irk_servant")
                                ||EntityType.getKey(event.getEntity().getType()).toString().equals("goety:irk"))
                                //同时，也是随从
                                && mobs instanceof OwnableEntity allyIrk && allyIrk.getOwner() == livingEntity) {
                    double dist = mobs.distanceTo(livingEntity);
                    if (dist < nearestDist) {
                        nearestDist = dist;
                        nearestMob = mobs;
                    }
                }
            }
            if (nearestMob != null&&nearestMob.getHealth()>0&&!nearestMob.isDeadOrDying()) {
                nearestMob.die(MyGoDamageType.hasSource(livingEntity.level(), MyGoDamageType.TRUEDAMAGE, livingEntity) );
                nearestMob.discard();

                livingEntity.getPersistentData().putInt(vizier_soul_stone_dead_cooldown, (int) (MyGoConfig.vizier_soul_stone_hp_cooldown.get() * 20 * 2));
                livingEntity.setHealth((float) (nearestMob.getHealth() * MyGoConfig.vizier_soul_stone_hp.get()));
                livingEntity.deathTime = -2;
                livingEntity.isAlive();
                livingEntity.invulnerableTime = 1;
                event.setCanceled(true);
            }
        }
    }
}
