package com.inolia_zaicek.mine_fargo.Event.Iron;

import com.gametechbc.gtbcs_geomancy_plus.util.GGDamageTypes;
import com.gametechbc.traveloptics.util.TravelopticsDamageTypes;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Iron.EarthSectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Iron.SoundSectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class GTBCHurtEvent {

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (ModList.get().isLoaded("gtbcs_geomancy_plus")) {
            LivingEntity attacked = event.getEntity();
            if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != null) {
                double number = 1;
                double overNumber = 1;
                double fixedNumber = 0;
                boolean earth = false;
                var map = attacked.getActiveEffectsMap();
                Set<Item> curios = MyGoUtil.getCuriosItems(attacker);
                if (curios.contains( EarthSectSoulStone.get())) {
                    double chance = MyGoConfig.earth_sect_soul_stone_chance.get() * 100;
                    int time = (int) (MyGoConfig.earth_sect_soul_stone_time.get() * 20);
                    if (event.getSource().is(GGDamageTypes.GEO_MAGIC)) {
                        earth = true;
                    } else if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC) || event.getSource().is(ISSDamageTypes.ICE_MAGIC)
                            || event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC) || event.getSource().is(ISSDamageTypes.EVOCATION_MAGIC)
                            || event.getSource().is(ISSDamageTypes.BLOOD_MAGIC) || event.getSource().is(ISSDamageTypes.HOLY_MAGIC)
                            || event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC) || event.getSource().is(ISSDamageTypes.ENDER_MAGIC)
                            || event.getSource().is(ISSDamageTypes.NATURE_MAGIC)
                            || (ModList.get().isLoaded("familiarslib")
                            && event.getSource().type().msgId().equals(new ResourceLocation("familiarslib", "sound_magic"))
                    ) || (ModList.get().isLoaded("traveloptics")
                            && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                    ) || (ModList.get().isLoaded("fantasy_ending")
                            && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                    ) || (ModList.get().isLoaded("fantasy_ending")
                            && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                    )
                    ) {
                        earth = true;
                        chance *= MyGoConfig.earth_sect_soul_stone_other.get();
                        time *= MyGoConfig.earth_sect_soul_stone_other.get();
                    }
                    if (earth) {
                        if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(
                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))))) {
                            attacked.addEffect(new MobEffectInstance(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode")))
                                    , time, 0));
                            if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))))) {
                                map.put(
                                        Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))),
                                        new MobEffectInstance(
                                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))),
                                                time, 0));
                            }
                        } else {
                            int level = Objects.requireNonNull(attacked.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))))).getAmplifier();
                            attacked.addEffect(new MobEffectInstance(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode")))
                                    , time, level));
                            if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))))) {
                                map.put(
                                        Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))),
                                        new MobEffectInstance(
                                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))),
                                                time, level));
                            }
                        }
                        //升级部分
                        Random random = new Random();
                        if (random.nextInt(100) <= chance) {
                            if (attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))))) {
                                int level = Objects.requireNonNull(attacked.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))))).getAmplifier();
                                int buffTime = Objects.requireNonNull(attacked.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))))).getDuration();
                                //最终的等级（不会超过上限
                                int finalLevel = (int) Math.min(MyGoConfig.earth_sect_soul_stone_level.get(), level + 1);
                                attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))), buffTime, finalLevel));
                                if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))))) {
                                    map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))), new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("gtbcs_geomancy_plus", "erode"))),
                                            buffTime, finalLevel));
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
}