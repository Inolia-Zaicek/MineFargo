package com.inolia_zaicek.mine_fargo.Event.Iron;

import com.gametechbc.traveloptics.util.TravelopticsDamageTypes;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Iron.*;
import com.inolia_zaicek.mine_fargo.Register.MyGoEffectsRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;

public class TOHurtEvent {

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (ModList.get().isLoaded("traveloptics")) {
            LivingEntity attacked = event.getEntity();
            if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != null) {
                double number = 1;
                double overNumber = 1;
                double fixedNumber = 0;
                var map = attacked.getActiveEffectsMap();
                if (MyGoUtil.hasSpecificItem(attacker, AquaSectSoulStoneItem.class)) {
                    if (event.getSource().is(TravelopticsDamageTypes.AQUA_MAGIC)) {
                        if (!attacked.hasEffect(
                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))))) {
                            attacked.addEffect(new MobEffectInstance(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet")))
                                    , (int) (MyGoConfig.aqua_sect_soul_stone_time.get() * 20), 0));
                            if (!attacked.hasEffect(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))))) {
                                map.put(
                                        Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))),
                                        new MobEffectInstance(
                                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))),
                                                (int) (MyGoConfig.aqua_sect_soul_stone_time.get() * 20), 0));
                            }
                        }else{
                            int level = Objects.requireNonNull(attacked.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))))).getAmplifier();
                            attacked.addEffect(new MobEffectInstance(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet")))
                                    , (int) (MyGoConfig.aqua_sect_soul_stone_time.get() * 20), level));
                            if (!attacked.hasEffect(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))))) {
                                map.put(
                                        Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))),
                                        new MobEffectInstance(
                                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))),
                                                (int) (MyGoConfig.aqua_sect_soul_stone_time.get() * 20), level));
                            }
                        }
                    }
                    if (event.getSource().is(TravelopticsDamageTypes.AQUA_MAGIC) || event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC) || event.getSource().is(ISSDamageTypes.ICE_MAGIC)) {
                        Random random = new Random();
                        if (random.nextInt(100) <= MyGoConfig.aqua_sect_soul_stone_chance.get() * 100) {
                            if (attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))))) {
                                int level = Objects.requireNonNull(attacked.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))))).getAmplifier();
                                int time = Objects.requireNonNull(attacked.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))))).getDuration();
                                //最终的等级（不会超过上限
                                int finalLevel = (int) Math.min(MyGoConfig.aqua_sect_soul_stone_level.get(), level + 1);
                                attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))), time, finalLevel));
                                if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))))) {
                                    map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))), new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("traveloptics", "wet"))),
                                            time, finalLevel));
                                }
                            }
                        }
                    }
                    double damage = (event.getAmount() * number + fixedNumber) * overNumber;
                    event.setAmount((float) damage);
                }
            }
        }
    }
}