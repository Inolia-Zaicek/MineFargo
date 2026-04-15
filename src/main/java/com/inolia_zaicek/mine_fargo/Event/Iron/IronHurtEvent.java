package com.inolia_zaicek.mine_fargo.Event.Iron;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.EscortSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.GoetyDarkSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Iron.*;
import com.inolia_zaicek.mine_fargo.Register.MyGoEffectsRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.misc.ModAttributes;

import java.util.Objects;
import java.util.Random;

import static net.minecraft.tags.DamageTypeTags.*;

public class IronHurtEvent {

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (ModList.get().isLoaded("irons_spellbooks")) {
            LivingEntity attacked = event.getEntity();
            if (attacked != null) {
                double number = 1;
                double overNumber = 1;
                double fixedNumber = 0;
                //是随从
                if (attacked instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() != null ) {
                    LivingEntity owner = ownableEntity.getOwner();
                    if (MyGoUtil.hasIron(owner, EvocationSectSoulStone.get())){
                        overNumber*=1-MyGoConfig.evocation_sect_soul_stone_armor.get();
                    }
                }
                if(attacked instanceof MagicSummon magicSummonMob&&magicSummonMob.getSummoner()!=null){
                    LivingEntity owner = magicSummonMob.getSummoner();
                    if (MyGoUtil.hasIron(owner, EvocationSectSoulStone.get())){
                        overNumber*=1-MyGoConfig.evocation_sect_soul_stone_armor.get();
                    }
                }
                //诡厄
                if (ModList.get().isLoaded("goety")&&(event.getSource().is(ISSDamageTypes.FIRE_MAGIC) || event.getSource().is(ISSDamageTypes.ICE_MAGIC)
                        || event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC) || event.getSource().is(ISSDamageTypes.EVOCATION_MAGIC)
                        || event.getSource().is(ISSDamageTypes.BLOOD_MAGIC) || event.getSource().is(ISSDamageTypes.HOLY_MAGIC)
                        || event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC) || event.getSource().is(ISSDamageTypes.ENDER_MAGIC)
                        || event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC) || event.getSource().is(ISSDamageTypes.NATURE_MAGIC)
                )) {
                    if (MyGoUtil.hasGoetyItem(attacked, GoetyDarkSoulStone.get())) {
                        number *= 1 - MyGoConfig.goety_dark_soul_stone_magic.get();
                    }
                }
                double damage = (event.getAmount() * number + fixedNumber) * overNumber;
                event.setAmount((float) damage);
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker&&attacked!=null) {
                double number = 1;
                double overNumber = 1;
                double fixedNumber = 0;
                var map = attacked.getActiveEffectsMap();
                //是随从
                if (attacker instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() != null ) {
                    LivingEntity owner = ownableEntity.getOwner();
                    if (MyGoUtil.hasIron(owner, EvocationSectSoulStone.get())){
                        number+=MyGoConfig.evocation_sect_soul_stone_damage.get();
                    }
                }
                if(attacker instanceof MagicSummon magicSummonMob&&magicSummonMob.getSummoner()!=null){
                    LivingEntity owner = magicSummonMob.getSummoner();
                    if (MyGoUtil.hasIron(owner, EvocationSectSoulStone.get())){
                        number+=MyGoConfig.evocation_sect_soul_stone_damage.get();
                    }
                }
                if (MyGoUtil.hasIron(attacker, NatureSectSoulStone.get())) {
                    //用boolan值判断是否执行操作
                    boolean nature = false;
                    Random random = new Random();
                    int addLevel = 0;
                    if (random.nextInt(100) <= MyGoConfig.nature_sect_soul_stone_chance.get() * 100) {
                        addLevel += 1;
                    }
                    double upTime = (MyGoConfig.nature_sect_soul_stone_time.get() + 1);
                    if (event.getSource().is(ISSDamageTypes.NATURE_MAGIC)) {
                        nature = true;
                    } else if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC) || event.getSource().is(ISSDamageTypes.ICE_MAGIC)
                            || event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC) || event.getSource().is(ISSDamageTypes.EVOCATION_MAGIC)
                            || event.getSource().is(ISSDamageTypes.BLOOD_MAGIC) || event.getSource().is(ISSDamageTypes.HOLY_MAGIC)
                            || event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC) || event.getSource().is(ISSDamageTypes.ENDER_MAGIC)
                    ) {
                        nature = true;
                        upTime *= MyGoConfig.nature_sect_soul_stone_other.get();
                    } else if (
                            (ModList.get().isLoaded("familiarslib")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("familiarslib", "sound_magic"))
                            ) || (ModList.get().isLoaded("traveloptics")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                            ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                            )
                    ) {
                        nature = true;
                        upTime *= MyGoConfig.nature_sect_soul_stone_other.get();
                    }
                    if (nature) {
                        if (attacked.hasEffect(MobEffects.POISON)) {
                            int level = Objects.requireNonNull(attacked.getEffect(MobEffects.POISON)).getAmplifier();
                            int time = Objects.requireNonNull(attacked.getEffect(MobEffects.POISON)).getDuration();
                            //最终的等级（不会超过上限
                            int finalLevel = (int) Math.min(MyGoConfig.nature_sect_soul_stone_level.get() - 1, level + addLevel);
                            attacked.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (upTime * time), finalLevel));
                            if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(MobEffects.POISON)) {
                                map.put(MobEffects.POISON, new MobEffectInstance(MobEffects.POISON,
                                        (int) (upTime * time), finalLevel));
                            }
                        }
                        if (attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))))) {
                            int level = Objects.requireNonNull(attacked.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))))).getAmplifier();
                            int time = Objects.requireNonNull(attacked.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))))).getDuration();
                            //最终的等级（不会超过上限
                            int finalLevel = (int) Math.min(MyGoConfig.nature_sect_soul_stone_level.get() - 1, level + addLevel);
                            attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))), (int) (upTime * time), finalLevel));
                            if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))))) {
                                map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))), new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))),
                                        (int) (upTime * time), finalLevel));
                            }
                        }
                        if (attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))))) {
                            int level = Objects.requireNonNull(attacked.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))))).getAmplifier();
                            int time = Objects.requireNonNull(attacked.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))))).getDuration();
                            //最终的等级（不会超过上限
                            int finalLevel = (int) Math.min(MyGoConfig.nature_sect_soul_stone_level.get() - 1, level + addLevel);
                            attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))), (int) (upTime * time), finalLevel));
                            if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))))) {
                                map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))), new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))),
                                        (int) (upTime * time), finalLevel));
                            }
                        }
                    }
                }
                //火
                if(MyGoUtil.hasIron(attacker, FireSectSoulStone.get())) {
                    //最大增伤距离最小增伤的数额
                    double fire = MyGoConfig.fire_sect_soul_stone_fire_max_damage.get() - MyGoConfig.fire_sect_soul_stone_fire_min_damage.get();
                    double fireTime = Math.min(1, attacked.getRemainingFireTicks() / 100);
                    //代码直接写进去了，不需要在外面判断伤害类型
                    if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC)) {
                        //最小增伤+[增伤差额]*距离5s的百分比
                        number += MyGoConfig.fire_sect_soul_stone_fire_min_damage.get() + fire * fireTime;
                    } else if (event.getSource().is(ISSDamageTypes.ICE_MAGIC)
                            || event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC) || event.getSource().is(ISSDamageTypes.EVOCATION_MAGIC)
                            || event.getSource().is(ISSDamageTypes.BLOOD_MAGIC) || event.getSource().is(ISSDamageTypes.HOLY_MAGIC)
                            || event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC) || event.getSource().is(ISSDamageTypes.ENDER_MAGIC)
                            || event.getSource().is(ISSDamageTypes.NATURE_MAGIC)) {
                        //最小增伤+[增伤差额]*距离5s的百分比
                        number += MyGoConfig.fire_sect_soul_stone_fire_min_damage.get() + fire * fireTime * MyGoConfig.fire_sect_soul_stone_other.get();
                        //源流
                    } else if (
                            (ModList.get().isLoaded("familiarslib")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("familiarslib", "sound_magic"))
                            ) || (ModList.get().isLoaded("traveloptics")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                            ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                            )
                    ) {
                        number += MyGoConfig.fire_sect_soul_stone_fire_min_damage.get() + fire * fireTime * MyGoConfig.fire_sect_soul_stone_other.get();
                    }
                }
                //冰
                if(MyGoUtil.hasIron(attacker, IceSectSoulStone.get())) {
                    boolean ice = false;
                    int time = (int) (MyGoConfig.ice_sect_soul_stone_time.get() * 20);
                    if (event.getSource().is(ISSDamageTypes.ICE_MAGIC)) {
                        ice = true;
                        number += MyGoConfig.ice_sect_soul_stone_ice_damage.get();
                    } else if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC) || event.getSource().is(ISSDamageTypes.HOLY_MAGIC)
                            || event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC) || event.getSource().is(ISSDamageTypes.EVOCATION_MAGIC)
                            || event.getSource().is(ISSDamageTypes.BLOOD_MAGIC) || event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC)
                            || event.getSource().is(ISSDamageTypes.ENDER_MAGIC)
                            || event.getSource().is(ISSDamageTypes.NATURE_MAGIC)
                    ) {
                        ice = true;
                        time *= MyGoConfig.ice_sect_soul_stone_other.get();
                        number += MyGoConfig.ice_sect_soul_stone_ice_damage.get() * MyGoConfig.ice_sect_soul_stone_other.get();
                    } else if (
                            (ModList.get().isLoaded("familiarslib")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("familiarslib", "sound_magic"))
                            ) || (ModList.get().isLoaded("traveloptics")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                            ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                            )
                    ) {
                        ice = true;
                        time *= MyGoConfig.ice_sect_soul_stone_other.get();
                        number += MyGoConfig.ice_sect_soul_stone_ice_damage.get() * MyGoConfig.ice_sect_soul_stone_other.get();
                    }
                    if(ice) {
                        attacked.addEffect(new MobEffectInstance(
                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "chilled")))
                                , time, 0));
                        if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(
                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "chilled"))))) {
                            map.put(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "chilled"))),
                                    new MobEffectInstance(
                                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "chilled"))),
                                            time, 0));
                        }
                    }
                }
                //神圣
                if(MyGoUtil.hasIron(attacker, HolySectSoulStone.get())) {
                    boolean holy = false;
                    int time = (int) (MyGoConfig.holy_sect_soul_stone_time.get() * 20);
                    if (event.getSource().is(ISSDamageTypes.HOLY_MAGIC)) {
                        holy = true;
                        number += MyGoConfig.holy_sect_soul_stone_damage.get();
                    }else if(event.getSource().is(ISSDamageTypes.FIRE_MAGIC)||event.getSource().is(ISSDamageTypes.ICE_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC)||event.getSource().is(ISSDamageTypes.EVOCATION_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.BLOOD_MAGIC)||event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.ENDER_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.NATURE_MAGIC)
                    ){
                        holy = true;
                        time *= MyGoConfig.holy_sect_soul_stone_other.get();
                        number += MyGoConfig.holy_sect_soul_stone_damage.get()*MyGoConfig.holy_sect_soul_stone_other.get();
                    } else if (
                            (ModList.get().isLoaded("familiarslib")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("familiarslib", "sound_magic"))
                            ) || (ModList.get().isLoaded("traveloptics")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                            ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                            )
                    ) {
                        holy = true;
                        time *= MyGoConfig.holy_sect_soul_stone_other.get();
                        number += MyGoConfig.holy_sect_soul_stone_damage.get()*MyGoConfig.holy_sect_soul_stone_other.get();
                    }
                    if(holy) {
                        attacked.addEffect(new MobEffectInstance(
                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "guided")))
                                , time, 0));
                        if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(
                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "guided"))))) {
                            map.put(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "guided"))),
                                    new MobEffectInstance(
                                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "guided"))),
                                            time, 0));
                        }
                    }
                }
                //邪术
                if(MyGoUtil.hasIron(attacker, EldritchSectSoulStone.get())) {
                    boolean eldritch = false;
                    int time = (int) (MyGoConfig.eldritch_sect_soul_stone_time.get() * 20);
                    if (event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC)) {
                        eldritch = true;
                        number += MyGoConfig.eldritch_sect_soul_stone_damage.get();
                    }else if(event.getSource().is(ISSDamageTypes.FIRE_MAGIC)||event.getSource().is(ISSDamageTypes.ICE_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC)||event.getSource().is(ISSDamageTypes.EVOCATION_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.BLOOD_MAGIC)||event.getSource().is(ISSDamageTypes.HOLY_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.ENDER_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.NATURE_MAGIC)
                    ){
                        eldritch = true;
                        time*=MyGoConfig.eldritch_sect_soul_stone_other.get();
                        number += MyGoConfig.eldritch_sect_soul_stone_damage.get()*MyGoConfig.eldritch_sect_soul_stone_other.get();
                    } else if (
                            (ModList.get().isLoaded("familiarslib")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("familiarslib", "sound_magic"))
                            ) || (ModList.get().isLoaded("traveloptics")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                            ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                            )
                    ) {
                        eldritch = true;
                        time*=MyGoConfig.eldritch_sect_soul_stone_other.get();
                        number += MyGoConfig.eldritch_sect_soul_stone_damage.get()*MyGoConfig.eldritch_sect_soul_stone_other.get();
                    }
                    if(eldritch) {
                        attacked.addEffect(new MobEffectInstance(MobEffects.DARKNESS, time, 0));
                        if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(MobEffects.DARKNESS)) {
                            map.put(MobEffects.DARKNESS, new MobEffectInstance(MobEffects.DARKNESS, time, 0));
                        }
                        attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, time, 0));
                        if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                            map.put(MobEffects.MOVEMENT_SLOWDOWN, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, time, 0));
                        }
                        attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, time, 0));
                        if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                            map.put(MobEffects.MOVEMENT_SLOWDOWN, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, time, 0));
                        }
                        attacked.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, time, 0));
                        if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(MobEffects.WEAKNESS)) {
                            map.put(MobEffects.WEAKNESS, new MobEffectInstance(MobEffects.WEAKNESS, time, 0));
                        }
                        attacked.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, time, 0));
                        if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(MobEffects.BLINDNESS)) {
                            map.put(MobEffects.BLINDNESS, new MobEffectInstance(MobEffects.BLINDNESS, time, 0));
                        }
                    }
                }
                //末影
                if(MyGoUtil.hasIron(attacker, EnderSectSoulStone.get())) {
                    boolean ender = false;
                    int time = (int) (MyGoConfig.ender_sect_soul_stone_time.get() * 20);
                    if (event.getSource().is(ISSDamageTypes.ENDER_MAGIC)) {
                        ender = true;
                        number += MyGoConfig.ender_sect_soul_stone_damage.get();
                    } else if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC) || event.getSource().is(ISSDamageTypes.ICE_MAGIC)
                            || event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC) || event.getSource().is(ISSDamageTypes.EVOCATION_MAGIC)
                            || event.getSource().is(ISSDamageTypes.BLOOD_MAGIC) || event.getSource().is(ISSDamageTypes.HOLY_MAGIC)
                            || event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC)
                            || event.getSource().is(ISSDamageTypes.NATURE_MAGIC)
                    ) {
                        ender = true;
                        time *= MyGoConfig.ender_sect_soul_stone_other.get();
                        number += MyGoConfig.ender_sect_soul_stone_damage.get() * MyGoConfig.ender_sect_soul_stone_other.get();
                    } else if (
                            (ModList.get().isLoaded("familiarslib")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("familiarslib", "sound_magic"))
                            ) || (ModList.get().isLoaded("traveloptics")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                            ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                            )
                    ) {
                        ender = true;
                        time *= MyGoConfig.ender_sect_soul_stone_other.get();
                        number += MyGoConfig.ender_sect_soul_stone_damage.get() * MyGoConfig.ender_sect_soul_stone_other.get();
                    }
                    if(ender) {
                        attacked.addEffect(new MobEffectInstance(MyGoEffectsRegister.Enderference.get(), time, 0));
                        if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal")&&!attacked.hasEffect(MyGoEffectsRegister.Enderference.get())) {
                            map.put(MyGoEffectsRegister.Enderference.get(), new MobEffectInstance(MyGoEffectsRegister.Enderference.get(),
                                    time, 0));
                        }
                    }
                }
                //唤魔
                if (MyGoUtil.hasIron(attacker, EvocationSectSoulStone.get())) {
                    //写在内部，不用管
                    if (event.getSource().is(ISSDamageTypes.EVOCATION_MAGIC)) {
                        number += MyGoConfig.evocation_sect_soul_stone_owner_damage.get();
                    } else if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC) || event.getSource().is(ISSDamageTypes.ICE_MAGIC)
                            || event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC) || event.getSource().is(ISSDamageTypes.ENDER_MAGIC)
                            || event.getSource().is(ISSDamageTypes.BLOOD_MAGIC) || event.getSource().is(ISSDamageTypes.HOLY_MAGIC)
                            || event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC)
                            || event.getSource().is(ISSDamageTypes.NATURE_MAGIC)
                    ) {
                        number += MyGoConfig.evocation_sect_soul_stone_owner_damage.get() * MyGoConfig.evocation_sect_soul_stone_other.get();
                    } else if (
                            (ModList.get().isLoaded("familiarslib")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("familiarslib", "sound_magic"))
                            ) || (ModList.get().isLoaded("traveloptics")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                            ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                            )
                    ) {
                        number += MyGoConfig.evocation_sect_soul_stone_owner_damage.get() * MyGoConfig.evocation_sect_soul_stone_other.get();
                    }
                }
                double damage = (event.getAmount() * number + fixedNumber) * overNumber;
                //雷霆&&
                if (MyGoUtil.hasIron(attacker, LightningSectSoulStone.get())){
                    int time = (int) Math.min(MyGoConfig.lightning_sect_soul_stone_time.get()*20,damage*MyGoConfig.lightning_sect_soul_stone_damage.get()*20 );
                    //记录
                    //attacker.sendSystemMessage(Component.translatable(String.valueOf(time)).withStyle(ChatFormatting.BLUE));
                    //写在内部，不用管
                    if(event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC)){
                        attacker.addEffect(new MobEffectInstance(
                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "charged"))),
                                time,(int)(MyGoConfig.lightning_sect_soul_stone_lvl.get()-1) ));
                    }else if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC)||event.getSource().is(ISSDamageTypes.ICE_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.EVOCATION_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.BLOOD_MAGIC)||event.getSource().is(ISSDamageTypes.HOLY_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC)||event.getSource().is(ISSDamageTypes.ENDER_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.NATURE_MAGIC)
                    ) {
                        time *= MyGoConfig.lightning_sect_soul_stone_other.get();
                        attacker.addEffect(new MobEffectInstance(
                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "charged"))),
                                time,(int)(MyGoConfig.lightning_sect_soul_stone_lvl.get()-1) ));
                    } else if (
                            (ModList.get().isLoaded("familiarslib")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("familiarslib", "sound_magic"))
                            ) || (ModList.get().isLoaded("traveloptics")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                            ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                            )
                    ) {
                        time *= MyGoConfig.lightning_sect_soul_stone_other.get();
                        attacker.addEffect(new MobEffectInstance(
                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "charged"))),
                                time,(int)(MyGoConfig.lightning_sect_soul_stone_lvl.get()-1) ));
                    }
                }
                //血
                if(MyGoUtil.hasIron(attacker, BloodSectSoulStone.get())) {
                    //写在内部，不用管
                    if (event.getSource().is(ISSDamageTypes.BLOOD_MAGIC) ) {
                        attacker.heal((float) (damage * MyGoConfig.blood_sect_soul_stone_heal.get()));
                        var mobList = MyGoUtil.mobList(22, attacker);
                        for (Mob mobs : mobList) {
                            if (mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() == attacker) {
                                attacker.heal((float) (damage * MyGoConfig.blood_sect_soul_stone_heal.get() * MyGoConfig.blood_sect_soul_stone_owner.get()));
                            }
                        }
                    }else if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC)||event.getSource().is(ISSDamageTypes.ICE_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.EVOCATION_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC)||event.getSource().is(ISSDamageTypes.HOLY_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC)||event.getSource().is(ISSDamageTypes.ENDER_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.NATURE_MAGIC)
                    ) {
                        attacker.heal((float) (damage * MyGoConfig.blood_sect_soul_stone_heal.get()*MyGoConfig.blood_sect_soul_stone_other.get() ));
                        var mobList = MyGoUtil.mobList(22, attacker);
                        for (Mob mobs : mobList) {
                            if (mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() == attacker) {
                                attacker.heal((float) (damage * MyGoConfig.blood_sect_soul_stone_heal.get() * MyGoConfig.blood_sect_soul_stone_owner.get()
                                        *MyGoConfig.blood_sect_soul_stone_other.get() ));
                            }
                        }
                    } else if (
                            (ModList.get().isLoaded("familiarslib")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("familiarslib", "sound_magic"))
                            ) || (ModList.get().isLoaded("traveloptics")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                            ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                            ) || (ModList.get().isLoaded("fantasy_ending")
                                    && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                            )
                    ) {
                        attacker.heal((float) (damage * MyGoConfig.blood_sect_soul_stone_heal.get()*MyGoConfig.blood_sect_soul_stone_other.get() ));
                        var mobList = MyGoUtil.mobList(22, attacker);
                        for (Mob mobs : mobList) {
                            if (mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() == attacker) {
                                attacker.heal((float) (damage * MyGoConfig.blood_sect_soul_stone_heal.get() * MyGoConfig.blood_sect_soul_stone_owner.get()
                                        *MyGoConfig.blood_sect_soul_stone_other.get() ));
                            }
                        }
                    }
                }
                event.setAmount((float) damage);
            }
        }
    }
}