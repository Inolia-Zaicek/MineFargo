package com.inolia_zaicek.mine_fargo.Event.Iron;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Iron.*;
import com.inolia_zaicek.mine_fargo.Register.MyGoEffectsRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
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
                    if (MyGoUtil.hasIron(owner, EvocationSectSoulStoneItem.class)){
                        overNumber*=1-MyGoConfig.evocation_sect_soul_stone_armor.get();
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
                    if (MyGoUtil.hasIron(owner, EvocationSectSoulStoneItem.class)){
                        number+=MyGoConfig.evocation_sect_soul_stone_damage.get();
                    }
                }
                if (event.getSource().is(ISSDamageTypes.NATURE_MAGIC)&& MyGoUtil.hasIron(attacker, NatureSectSoulStoneItem.class)){
                    Random random = new Random();
                    int addLevel = 0;
                    if (random.nextInt(100) <= MyGoConfig.nature_sect_soul_stone_chance.get() * 100){
                        addLevel+=1;
                    }
                    if(attacked.hasEffect(MobEffects.POISON)){
                        int level = Objects.requireNonNull(attacker.getEffect(MobEffects.POISON)).getAmplifier();
                        int time = Objects.requireNonNull(attacker.getEffect(MobEffects.POISON)).getDuration();
                        //最终的等级（不会超过上限
                        int finalLevel = (int) Math.min(MyGoConfig.nature_sect_soul_stone_level.get(),level+addLevel);
                        attacked.addEffect(new MobEffectInstance(MobEffects.POISON, (int) ((MyGoConfig.nature_sect_soul_stone_time.get()+1)*time), finalLevel));
                        if (!attacked.hasEffect(MobEffects.POISON)) {
                            map.put(MobEffects.POISON, new MobEffectInstance(MobEffects.POISON,
                                    (int) ((MyGoConfig.nature_sect_soul_stone_time.get()+1)*time), finalLevel));
                        }
                    }
                    if(attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))))){
                        int level = Objects.requireNonNull(attacker.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))))).getAmplifier();
                        int time = Objects.requireNonNull(attacker.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))))).getDuration();
                        //最终的等级（不会超过上限
                        int finalLevel = (int) Math.min(MyGoConfig.nature_sect_soul_stone_level.get(),level+addLevel);
                        attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))), (int) ((MyGoConfig.nature_sect_soul_stone_time.get()+1)*time), finalLevel));
                        if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))))) {
                            map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))), new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "rend"))),
                                    (int) ((MyGoConfig.nature_sect_soul_stone_time.get()+1)*time), finalLevel));
                        }
                    }
                    if(attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))))){
                        int level = Objects.requireNonNull(attacker.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))))).getAmplifier();
                        int time = Objects.requireNonNull(attacker.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))))).getDuration();
                        //最终的等级（不会超过上限
                        int finalLevel = (int) Math.min(MyGoConfig.nature_sect_soul_stone_level.get(),level+addLevel);
                        attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))), (int) ((MyGoConfig.nature_sect_soul_stone_time.get()+1)*time), finalLevel));
                        if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))))) {
                            map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))), new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "blight"))),
                                    (int) ((MyGoConfig.nature_sect_soul_stone_time.get()+1)*time), finalLevel));
                        }
                    }
                }
                if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC)&& MyGoUtil.hasIron(attacker, FireSectSoulStoneItem.class)){
                    //最大增伤距离最小增伤的数额
                    double fire = MyGoConfig.fire_sect_soul_stone_fire_max_damage.get()-MyGoConfig.fire_sect_soul_stone_fire_min_damage.get();
                    double fireTime = Math.min(1,attacked.getRemainingFireTicks()/100);
                    //最小增伤+[增伤差额]*距离5s的百分比
                    number += MyGoConfig.fire_sect_soul_stone_fire_min_damage.get()+fire*fireTime;
                }
                if (event.getSource().is(ISSDamageTypes.ICE_MAGIC)&& MyGoUtil.hasIron(attacker, IceSectSoulStoneItem.class)){
                    attacked.addEffect(new MobEffectInstance(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "chilled")))
                            , (int) (MyGoConfig.ice_sect_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "chilled"))))) {
                        map.put(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "chilled"))),
                                new MobEffectInstance(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "chilled"))),
                                        (int) (MyGoConfig.ice_sect_soul_stone_time.get()*20), 0));
                    }
                    number += MyGoConfig.ice_sect_soul_stone_ice_damage.get();
                }
                if (event.getSource().is(ISSDamageTypes.HOLY_MAGIC)&& MyGoUtil.hasIron(attacker, HolySectSoulStoneItem.class)){
                    attacked.addEffect(new MobEffectInstance(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "guided")))
                            , (int) (MyGoConfig.holy_sect_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "guided"))))) {
                        map.put(
                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "guided"))),
                                new MobEffectInstance(
                                        Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "guided"))),
                                        (int) (MyGoConfig.holy_sect_soul_stone_time.get()*20), 0));
                    }
                    number += MyGoConfig.holy_sect_soul_stone_damage.get();
                }
                if (event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC)&& MyGoUtil.hasIron(attacker, EldritchSectSoulStoneItem.class)){
                    number += MyGoConfig.eldritch_sect_soul_stone_damage.get();
                    attacked.addEffect(new MobEffectInstance(MobEffects.DARKNESS, (int) (MyGoConfig.eldritch_sect_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MobEffects.DARKNESS)) {
                        map.put(MobEffects.DARKNESS, new MobEffectInstance(MobEffects.DARKNESS,(int) (MyGoConfig.eldritch_sect_soul_stone_time.get()*20), 0));
                    }
                    attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (MyGoConfig.eldritch_sect_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        map.put(MobEffects.MOVEMENT_SLOWDOWN, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,(int) (MyGoConfig.eldritch_sect_soul_stone_time.get()*20), 0));
                    }
                    attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (MyGoConfig.eldritch_sect_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        map.put(MobEffects.MOVEMENT_SLOWDOWN, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,(int) (MyGoConfig.eldritch_sect_soul_stone_time.get()*20), 0));
                    }
                    attacked.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, (int) (MyGoConfig.eldritch_sect_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MobEffects.WEAKNESS)) {
                        map.put(MobEffects.WEAKNESS, new MobEffectInstance(MobEffects.WEAKNESS,(int) (MyGoConfig.eldritch_sect_soul_stone_time.get()*20), 0));
                    }
                    attacked.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, (int) (MyGoConfig.eldritch_sect_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MobEffects.BLINDNESS)) {
                        map.put(MobEffects.BLINDNESS, new MobEffectInstance(MobEffects.BLINDNESS,(int) (MyGoConfig.eldritch_sect_soul_stone_time.get()*20), 0));
                    }
                }
                if (event.getSource().is(ISSDamageTypes.ENDER_MAGIC)&& MyGoUtil.hasIron(attacker, EnderSectSoulStoneItem.class)){
                    attacked.addEffect(new MobEffectInstance(MyGoEffectsRegister.Enderference.get(), (int) (MyGoConfig.ender_sect_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MyGoEffectsRegister.Enderference.get())) {
                        map.put(MyGoEffectsRegister.Enderference.get(), new MobEffectInstance(MyGoEffectsRegister.Enderference.get(),
                                (int) (MyGoConfig.ender_sect_soul_stone_time.get()*20), 0));
                    }
                    number += MyGoConfig.ender_sect_soul_stone_damage.get();
                }
                double damage = (event.getAmount() * number + fixedNumber) * overNumber;
                if (event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC)&& MyGoUtil.hasIron(attacker, LightningSectSoulStoneItem.class)){
                    int time = (int) Math.min(MyGoConfig.lightning_sect_soul_stone_time.get()*20,damage*MyGoConfig.lightning_sect_soul_stone_damage.get()*20 );
                    //记录
                    //attacker.sendSystemMessage(Component.translatable(String.valueOf(time)).withStyle(ChatFormatting.BLUE));
                    attacker.addEffect(new MobEffectInstance(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("irons_spellbooks", "charged"))),
                            time,0));
                }
                if (event.getSource().is(ISSDamageTypes.BLOOD_MAGIC)&& MyGoUtil.hasIron(attacker, BloodSectSoulStoneItem.class)){
                    attacker.heal((float) (damage*MyGoConfig.blood_sect_soul_stone_heal.get()));
                    var mobList = MyGoUtil.mobList(22, attacker);
                    for (Mob mobs : mobList) {
                        if (mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() == attacker) {
                            attacker.heal((float) (damage*MyGoConfig.blood_sect_soul_stone_heal.get()*MyGoConfig.blood_sect_soul_stone_owner.get()));
                        }
                    }
                }
                event.setAmount((float) damage);
            }
        }
    }
}