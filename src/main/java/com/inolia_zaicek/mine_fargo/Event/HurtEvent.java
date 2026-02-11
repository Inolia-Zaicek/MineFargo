package com.inolia_zaicek.mine_fargo.Event;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Damage.MyGoDamageType;
import com.inolia_zaicek.mine_fargo.Item.Cataclysm.*;
import com.inolia_zaicek.mine_fargo.Item.Create.BrassSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Create.SturdySheetSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.EscortSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.GoetyDarkSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.OrderAboutSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Iron.EvocationSectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Entity.LavaEaterSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Entity.*;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Monsters.*;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Entity.*;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.EnderSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.LavaSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.NetherSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.SnowSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Twilight.*;
import com.inolia_zaicek.mine_fargo.Item.Twilight.TwilightForest.FieryIronSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Twilight.TwilightForest.FluffyCloudSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Twilight.TwilightForest.QuestRamSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

import static com.inolia_zaicek.mine_fargo.Event.TickEvent.*;
import static net.minecraft.tags.DamageTypeTags.*;

public class HurtEvent {

    private static final UUID uuid1 = UUID.fromString("1718C426-F3F8-50B7-7F97-4C83C4537EF9");
    private static final UUID uuid2 = UUID.fromString("FB637D10-4240-B77D-B699-C9E50DF0EBFA");
    public static final String ender_guardian_soul_stone_cooldown_time = MineFargo.MODID + ":ender_guardian_soul_stone";
    public static final String maledictus_soul_stone_cooldown_time = MineFargo.MODID + ":maledictus_soul_stone";
    public static final String ur_ghast_soul_stone_time = MineFargo.MODID + ":ur_ghast_soul_stone";
    public static final String alpha_yeti_soul_stone_cooldown_time = MineFargo.MODID + ":alpha_yeti_soul_stone";
    public static final String snow_queen_soul_stone_cooldown_time = MineFargo.MODID + ":snow_queen_soul_stone";
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void hurt(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        Random random = new Random();
        if (attacked != null) {
            double baseDamage = event.getAmount();
            //减伤采用乘算，保证稀释
            double number = 1;
            double overNumber = 1;
            float fixedNumber = 0;
            double invulnerableTime = 1;
            if (ModList.get().isLoaded("legendary_monsters")) {
                if (MyGoUtil.hasLegendaryMonsters(attacked, CloudGolemSoulStoneItem.class) ) {
                    invulnerableTime+=MyGoConfig.cloud_golem_soul_stone_time.get();
                }
            }
            if (MyGoUtil.hasNature(attacked, EnderSoulStoneItem.class)
                    && event.getSource().getEntity() != null && event.getSource().getEntity() instanceof EnderMan) {
                number *= 1 - MyGoConfig.ender_soul_stone_armor.get();
            }
            if (ModList.get().isLoaded("cataclysm")) {
                if (MyGoUtil.hasCataclysm(attacked, TheLeviathanSoulStoneItem.class) &&
                        (attacked.isUnderWater() || attacked.isInWater() || attacked.isInWaterOrRain() || attacked.isInWaterRainOrBubble())) {
                    number *= 1 - MyGoConfig.the_leviathan_soul_stone_damage.get();
                }
            }
            //机械动力
            if (ModList.get().isLoaded("create")) {
                if(MyGoUtil.hasCreate(attacked, SturdySheetSoulStoneItem.class)) {
                    fixedNumber -= MyGoConfig.sturdy_sheet_soul_stone_damage.get();
                }
                //机械动力伤害
                if (event.getSource().type().msgId().equals(new ResourceLocation("create", "crush"))
                        ||event.getSource().type().msgId().equals(new ResourceLocation("create", "cuckoo_surprise"))
                        ||event.getSource().type().msgId().equals(new ResourceLocation("create", "fan_fire"))
                        ||event.getSource().type().msgId().equals(new ResourceLocation("create", "fan_lava"))
                        ||event.getSource().type().msgId().equals(new ResourceLocation("create", "mechanical_drill"))
                        ||event.getSource().type().msgId().equals(new ResourceLocation("create", "mechanical_roller"))
                        ||event.getSource().type().msgId().equals(new ResourceLocation("create", "mechanical_saw"))
                        ||event.getSource().type().msgId().equals(new ResourceLocation("create", "potato_cannon"))
                        ||event.getSource().type().msgId().equals(new ResourceLocation("create", "run_over"))
                ) {
                    if(MyGoUtil.hasCreate(attacked, BrassSoulStoneItem.class)) {
                        number *= 1 - MyGoConfig.brass_soul_stone_damage.get();
                    }
                }
            }
            if (ModList.get().isLoaded("twilightforest")) {
                    if(MyGoUtil.hasTwilightForest(attacked, QuestRamSoulStoneItem.class)) {
                        if (event.getSource().is(WITCH_RESISTANT_TO)) {
                            number *= 1 - MyGoConfig.quest_ram_soul_stone.get();
                        }else if((ModList.get().isLoaded("alshanex_familiars")
                                && event.getSource().type().msgId().equals(new ResourceLocation("alshanex_familiars", "sound_magic"))
                        ) || (ModList.get().isLoaded("traveloptics")
                                && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                        ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                                && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                        ) || (ModList.get().isLoaded("fantasy_ending")
                                && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                        ) || (ModList.get().isLoaded("fantasy_ending")
                                && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                        )){
                            number *= 1 - MyGoConfig.quest_ram_soul_stone.get();
                        }else if((ModList.get().isLoaded("irons_spellbooks")
                                && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "blood_magic"))
                        ) || (ModList.get().isLoaded("irons_spellbooks")
                                && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "eldritch_magic"))
                        ) || (ModList.get().isLoaded("irons_spellbooks")
                                && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "ender_magic"))
                        ) || (ModList.get().isLoaded("irons_spellbooks")
                                && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "evocation_magic"))
                        ) || (ModList.get().isLoaded("irons_spellbooks")
                                && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "fire_magic"))
                        ) || (ModList.get().isLoaded("irons_spellbooks")
                                && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "fire_field"))
                        ) || (ModList.get().isLoaded("irons_spellbooks")
                                && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "holy_magic"))
                        ) || (ModList.get().isLoaded("irons_spellbooks")
                                && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "ice_magic"))
                        ) || (ModList.get().isLoaded("irons_spellbooks")
                                && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "lightning_magic"))
                        ) || (ModList.get().isLoaded("irons_spellbooks")
                                && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "nature_magic"))
                        )){
                            number *= 1 - MyGoConfig.quest_ram_soul_stone.get();
                        }else if((ModList.get().isLoaded("ars_nouveau")
                                && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "flare"))
                        ) || (ModList.get().isLoaded("ars_nouveau")
                                && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "frost"))
                        ) || (ModList.get().isLoaded("ars_nouveau")
                                && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "spell"))
                        ) || (ModList.get().isLoaded("ars_nouveau")
                                && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "windshear"))
                        ) || (ModList.get().isLoaded("ars_nouveau")
                                && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "crush"))
                        )){
                            number *= 1 - MyGoConfig.quest_ram_soul_stone.get();
                        }
                    }
                //九头蛇减伤
                if(MyGoUtil.hasTwilight(attacked, TwilightHydraSoulStoneItem.class)&&event.getSource().getEntity() instanceof LivingEntity attacker) {
                    boolean hydra = false;
                    //实际范围A=（数值+1）/2——————范围5x5实际上是输入3=（5+1）/2
                    var mobList = MyGoUtil.mobList((int) ((MyGoConfig.twilight_hydra_soul_stone_range.get() + 1) / 2), attacked);
                    for (Mob mobs : mobList) {
                        if (mobs != null) {
                            //如果这些范围里没有人和伤害源一样
                            if (mobs != attacker) {
                                hydra = true;
                            }
                        }
                    }
                    if (hydra) {
                        number *= 1 - MyGoConfig.twilight_hydra_soul_stone_armor.get();
                    }
                }
                if(MyGoUtil.hasTwilight(attacked, UrGhastSoulStoneItem.class)){
                    if(event.getAmount()>=attacked.getMaxHealth()*MyGoConfig.ur_ghast_soul_stone_number.get()){
                        attacked.getPersistentData().putInt(ur_ghast_soul_stone_time, (int) (MyGoConfig.ur_ghast_soul_stone_time.get()*20*2));
                    }
                    if(attacked.getPersistentData().getInt(ur_ghast_soul_stone_time)>0) {
                        number *= 1 - MyGoConfig.ur_ghast_soul_stone_armor.get();
                    }
                }
                if(MyGoUtil.hasTwilightForest(attacked, FluffyCloudSoulStoneItem.class)&&event.getSource().getEntity() instanceof LivingEntity attacker&&!attacker.onGround()) {
                    number *= 1 - MyGoConfig.fluffy_cloud_soul_stone.get();
                }
            }
            if (ModList.get().isLoaded("legendary_monsters")) {
                if (MyGoUtil.hasLegendaryEntity(attacked, DuneSentinelSoulStoneItem.class) && event.getSource().is(IS_EXPLOSION)) {
                    number *= 1 - MyGoConfig.dune_sentinel_soul_stone_down.get();
                }
                if (MyGoUtil.hasLegendaryEntity(attacked, EndersentSoulStoneItem.class) && event.getSource().is(IS_PROJECTILE)) {
                    number *= 1 - MyGoConfig.endersent_soul_stone.get();
                }
            }
            //诡厄
            if (ModList.get().isLoaded("goety")) {
                //随从减伤
                if (attacked instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() != null) {
                    LivingEntity owner = ownableEntity.getOwner();
                    if (MyGoUtil.hasGoetyItem(owner, EscortSoulStoneItem.class)) {
                        number *= 1 - MyGoConfig.escort_soul_stone.get();
                    }
                }
                if (MyGoUtil.hasGoetyItem(attacked, GoetyDarkSoulStoneItem.class)) {
                    if(event.getSource().is(IS_FIRE)||event.getSource().is(IS_EXPLOSION)) {
                        number *= 1 - MyGoConfig.goety_dark_soul_stone_fire.get();
                    }else if (event.getSource().is(WITCH_RESISTANT_TO) ){
                        number *= 1 - MyGoConfig.goety_dark_soul_stone_magic.get();
                    }
                }
            }
            if(invulnerableTime!=1){
                double finishTime = Math.max(0,invulnerableTime);
                attacked.invulnerableTime= (int) (attacked.invulnerableTime*finishTime);
            }
            //结算
            if (ModList.get().isLoaded("cataclysm")) {
                if (MyGoUtil.hasCataclysm(attacked, MaledictusSoulStoneItem.class)) {
                    double chance = MyGoConfig.maledictus_soul_stone_chance.get();
                    if (event.getSource().is(IS_PROJECTILE)) {
                        chance += MyGoConfig.maledictus_soul_stone_arrow_chance.get();
                    }
                    if (random.nextInt(100) <= chance * 100) {
                        number = 0;
                        overNumber = 0;
                    }
                }
            }
            if (MyGoUtil.hasNature(attacked, SnowSoulStoneItem.class) && event.getSource().is(IS_FREEZING)) {
                event.setCanceled(true);
            } else if (MyGoUtil.hasNature(attacked, LavaSoulStoneItem.class) && event.getSource().is(IS_FIRE)) {
                event.setCanceled(true);
            } else if (MyGoUtil.hasEntity(attacked, WingSoulStoneItem.class) && event.getSource().is(IS_FALL)) {
                event.setCanceled(true);
            } else {
                float damage = (float) Math.max(0, (baseDamage * number + fixedNumber) * overNumber);
                //反伤
                if(event.getSource().getEntity() instanceof LivingEntity attacker) {
                    if (ModList.get().isLoaded("legendary_monsters")) {
                        if (MyGoUtil.hasLegendaryMonsters(attacked, AncientGuardianSoulStoneItem.class) && attacked.getPersistentData().getInt(ancient_guardian_soul_stone_time) == 0) {
                            attacked.getPersistentData().putInt(ancient_guardian_soul_stone_time, (int) (MyGoConfig.ancient_guardian_soul_stone_cooldown.get() * 20 * 2));
                            var DamageType = MyGoDamageType.hasSource(attacked.level(), MyGoDamageType.TRUEDAMAGE, attacked);
                            attacker.invulnerableTime = 0;
                            attacker.hurt(DamageType, (float) (MyGoConfig.ancient_guardian_soul_stone_fixed_damage.get() +
                                    damage * MyGoConfig.ancient_guardian_soul_stone_damage.get()));
                            if (random.nextInt(100) <= MyGoConfig.ancient_guardian_soul_stone_chance.get() * 100) {
                                attacker.addEffect(new MobEffectInstance(
                                        Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "bleeding")))
                                        , (int) (MyGoConfig.ancient_guardian_soul_stone_time.get() * 20), 0));
                                if (!attacker.hasEffect(
                                        Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "bleeding")))
                                )) {
                                    var map = attacked.getActiveEffectsMap();
                                    map.put(
                                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "bleeding")))
                                            , new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "bleeding"))), (int) (MyGoConfig.ancient_guardian_soul_stone_time.get() * 20), 0));
                                }
                            }
                        }
                    }
                }
                //结束
                event.setAmount(damage);
            }
        }
        if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != null) {
            //普通乘区内，150%是+0.5，即+0.5
            double number = 1;
            double overNumber = 1;
            double fixedNumber = 0;
            float time = 1;
            //固定穿甲
            double fixedArmorPenetration = 0;
            //百分比穿甲
            double overArmorPenetration = 0;
                double fireTime = 0;
                var map = attacked.getActiveEffectsMap();
            int freeze = 0;
            if (MyGoUtil.hasNature(attacker, SnowSoulStoneItem.class)) {
                freeze += 1;
            }
            //随从锁定
            boolean orderOwnable = false;
            //诡厄
            if (ModList.get().isLoaded("goety")) {
                //随从
                if (attacked instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() != null) {
                    LivingEntity owner = ownableEntity.getOwner();
                    if (MyGoUtil.hasGoetyItem(owner, OrderAboutSoulStoneItem.class)) {
                        number += MyGoConfig.order_about_soul_stone_damage.get();
                    }
                }
            }
            if (MyGoUtil.hasNature(attacker, NetherSoulStoneItem.class) && attacker.level().dimension().equals(Level.NETHER)) {
                number += MyGoConfig.nether_soul_stone.get();
            }
            if (MyGoUtil.hasNature(attacker, EnderSoulStoneItem.class) && attacked instanceof EnderMan) {
                number += MyGoConfig.ender_soul_stone_attack.get();
            }
            if (MyGoUtil.hasEntity(attacker, AquaticSoulStoneItem.class) &&
                    //水中
                    (attacker.isUnderWater() || attacker.isInWater() || attacker.isInWaterOrRain() || attacker.isInWaterRainOrBubble())
            ) {
                number += MyGoConfig.aquatic_soul_stone.get();
            }
            if (MyGoUtil.hasEntity(attacker, BlazeSoulStoneItem.class)) {
                fireTime+=100;
                if (attacked.getRemainingFireTicks() > 0) {
                    number += MyGoConfig.blaze_soul_stone.get();
                }
            }
            if (MyGoUtil.hasEntity(attacker, DeathSoulStoneItem.class) && attacked.getMobType() == MobType.UNDEAD) {
                number += MyGoConfig.death_soul_stone.get();
            }
            if (MyGoUtil.hasEntity(attacker, ArthropodSoulStoneItem.class) && attacked.getMobType() == MobType.ARTHROPOD) {
                number += MyGoConfig.arthropod_soul_stone.get();
            }
            //灾变
            if (ModList.get().isLoaded("cataclysm")) {
                if (MyGoUtil.hasCataclysm(attacker, TheLeviathanSoulStoneItem.class)) {
                    if ((attacker.isUnderWater() || attacker.isInWater() || attacker.isInWaterOrRain() || attacker.isInWaterRainOrBubble())) {
                        number += MyGoConfig.the_leviathan_soul_stone_damage.get();
                    }
                    attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(
                            ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "abyssal_curse"))),
                            (int) (MyGoConfig.the_leviathan_soul_stone_time.get() * 20), (int) (MyGoConfig.the_leviathan_soul_stone_level.get() - 1)));
                    if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "abyssal_curse"))))) {
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "abyssal_curse")))
                                , new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                        new ResourceLocation("cataclysm", "abyssal_curse"))),
                                        (int) (MyGoConfig.the_leviathan_soul_stone_time.get() * 20), (int) (MyGoConfig.the_leviathan_soul_stone_level.get() - 1)));
                    }
                }
                if (MyGoUtil.hasCataclysm(attacker, IgnisSoulStoneItem.class)) {
                    if (random.nextInt(100) <= MyGoConfig.ignis_soul_stone_give_chance.get() * 100) {
                        attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(
                                ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))),
                                (int) (MyGoConfig.ignis_soul_stone_time.get() * 20), 0));
                        if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))))) {
                            map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand")))
                                    , new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                            new ResourceLocation("cataclysm", "blazing_brand"))),
                                            (int) (MyGoConfig.ignis_soul_stone_time.get() * 20), 0));
                        }
                        //触发升级
                        if (random.nextInt(100) <= MyGoConfig.ignis_soul_stone_up_chance.get() * 100 && attacked.hasEffect(
                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))))
                        ) {
                            int level = Objects.requireNonNull(attacked.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                    new ResourceLocation("cataclysm", "blazing_brand"))))).getAmplifier();

                            int finalLevel = (int) Math.min(MyGoConfig.ignis_soul_stone_max_level.get()-1, level + 1);
                            attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(
                                    ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))),
                                    (int) (MyGoConfig.ignis_soul_stone_time.get() * 20), finalLevel));
                            if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))))) {
                                map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand")))
                                        , new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                                new ResourceLocation("cataclysm", "blazing_brand"))),
                                                (int) (MyGoConfig.ignis_soul_stone_time.get() * 20), finalLevel));
                            }
                        }
                    }
                }
                if (MyGoUtil.hasCataclysm(attacker, TheHarbingerSoulStoneItem.class)) {
                    time -= MyGoConfig.the_harbinger_soul_stone.get();
                }
                if (MyGoUtil.hasCataclysm(attacker, EnderGuardianSoulStoneItem.class) && attacker.getPersistentData().getInt(ender_guardian_soul_stone_cooldown_time)==0&&
                        random.nextInt(100) <= MyGoConfig.ender_guardian_soul_stone_chance.get() * 100) {
                    attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(
                            ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "stun"))),
                            (int) (MyGoConfig.ender_guardian_soul_stone_time.get() * 20), 0));
                    if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "stun"))))) {
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "stun")))
                                , new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                        new ResourceLocation("cataclysm", "stun"))),
                                        (int) (MyGoConfig.ender_guardian_soul_stone_time.get() * 20), 0));
                    }
                    attacker.getPersistentData().putInt(ender_guardian_soul_stone_cooldown_time, (int) (MyGoConfig.ender_guardian_soul_stone_cooldown.get()*20*2));
                }
                if (MyGoUtil.hasCataclysm(attacker, NetheriteMonstrositySoulStoneItem.class) && random.nextInt(100) <= MyGoConfig.netherite_monstrosity_soul_stone_chance.get() * 100) {
                    if (attacker.hasEffect(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "monstrous"))))
                    ) {
                        int level = Objects.requireNonNull(attacker.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                new ResourceLocation("cataclysm", "monstrous"))))).getAmplifier();
                        int finalLevel = (int) Math.min(MyGoConfig.netherite_monstrosity_soul_stone_max_lvl.get()-1, level + 1);
                        attacker.addEffect(new MobEffectInstance(Objects.requireNonNull(
                                ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "monstrous"))),
                                (int) (MyGoConfig.netherite_monstrosity_soul_stone_time.get() * 20), finalLevel));
                    } else {
                        attacker.addEffect(new MobEffectInstance(Objects.requireNonNull(
                                ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "monstrous"))),
                                (int) (MyGoConfig.netherite_monstrosity_soul_stone_time.get() * 20), 0));
                    }
                }
                if (MyGoUtil.hasCataclysm(attacker, AncientRemnantSoulStoneItem.class)) {
                    attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(
                            ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "bone_fracture"))),
                            (int) (MyGoConfig.ancient_remnant_soul_stone_time.get() * 20), (int) (MyGoConfig.ancient_remnant_soul_stone_lvl.get() - 1)));
                    if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "bone_fracture"))))) {
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "bone_fracture")))
                                , new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                        new ResourceLocation("cataclysm", "bone_fracture"))),
                                        (int) (MyGoConfig.ancient_remnant_soul_stone_time.get() * 20), (int) (MyGoConfig.ancient_remnant_soul_stone_lvl.get() - 1)));
                    }
                }
                if (MyGoUtil.hasCataclysm(attacker, ScyllaSoulStoneItem.class)) {
                    attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(
                            ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "wetness"))),
                            (int) (MyGoConfig.scylla_soul_stone_time.get() * 20), (int) (MyGoConfig.scylla_soul_stone_level.get() - 1)));
                    if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "wetness"))))) {
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "wetness")))
                                , new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                        new ResourceLocation("cataclysm", "wetness"))),
                                        (int) (MyGoConfig.scylla_soul_stone_time.get() * 20), (int) (MyGoConfig.scylla_soul_stone_level.get() - 1)));
                    }
                    if (attacked.hasEffect(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "wetness"))))
                    ) {
                        int level = Objects.requireNonNull(attacked.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                new ResourceLocation("cataclysm", "wetness"))))).getAmplifier();
                        number += (level + 1) * MyGoConfig.scylla_soul_stone_damage.get();
                    }
                }
            }
            if (freeze > 0) {
                attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100 * freeze, freeze - 1));
                if (!attacked.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                    map.put(MobEffects.MOVEMENT_SLOWDOWN, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 100 * freeze, freeze - 1));
                }
            }
            if (time != 1) {
                float finalTime = Math.max(0, time);//保证为0及以上
                attacked.invulnerableTime = (int) (attacked.invulnerableTime * finalTime);
            }
            if (ModList.get().isLoaded("twilightforest")) {
                //九头蛇增伤
                if (MyGoUtil.hasTwilight(attacker, TwilightHydraSoulStoneItem.class)) {
                    //阈值进度——【当前扣除的生命值/(最大生命值*(1-阈值) ）=当前扣除生命值/需要扣除多少生命值】
                    double dhp = (attacker.getMaxHealth()-attacker.getHealth())
                            /
                            (attacker.getMaxHealth()*(1-MyGoConfig.twilight_hydra_soul_stone_last_hp.get()));
                    //最大增伤*距离阈值的进度
                    number+=MyGoConfig.twilight_hydra_soul_stone_damage.get()*dhp;
                }
                //幻影骑士
                if (MyGoUtil.hasTwilight(attacker, KnightPhantomSoulStoneItem.class)&&attacked.getAttributes().hasAttribute(Attributes.ARMOR)) {
                    if(attacked.getAttributeValue(Attributes.ARMOR)>=MyGoConfig.knight_phantom_soul_stone_armor.get()){
                        number+=MyGoConfig.knight_phantom_soul_stone_damage.get();
                    }
                }
                if (MyGoUtil.hasTwilight(attacker, MinoshroomSoulStoneItem.class)) {
                    //玩家破盾
                    if(attacked instanceof Player player){
                        player.disableShield(true);
                    }
                    overArmorPenetration+=MyGoConfig.minoshroom_soul_stone.get();
                }
            }
            if (ModList.get().isLoaded("legendary_monsters")) {
                if (MyGoUtil.hasLegendaryEntity(attacker, WitheredAbominationSoulStoneItem.class)) {
                    attacked.addEffect(new MobEffectInstance(MobEffects.WITHER, (int)(MyGoConfig.withered_abomination_soul_stone_time.get()*20), (int)(MyGoConfig.withered_abomination_soul_stone_time.get()-1)));
                    if (!attacked.hasEffect(MobEffects.WITHER)) {
                        map.put(MobEffects.WITHER, new MobEffectInstance(MobEffects.WITHER, (int)(MyGoConfig.withered_abomination_soul_stone_time.get()*20), (int)(MyGoConfig.withered_abomination_soul_stone_time.get()-1)));
                    }
                }
                if (MyGoUtil.hasLegendaryEntity(attacker, OvergrownColossusSoulStoneItem.class)) {
                    attacked.addEffect(new MobEffectInstance(MobEffects.POISON, (int)(MyGoConfig.overgrown_colossus_soul_stone_time.get()*20), (int)(MyGoConfig.overgrown_colossus_soul_stone_level.get()-1)));
                    if (!attacked.hasEffect(MobEffects.POISON)) {
                        map.put(MobEffects.POISON, new MobEffectInstance(MobEffects.POISON, (int)(MyGoConfig.overgrown_colossus_soul_stone_time.get()*20), (int)(MyGoConfig.overgrown_colossus_soul_stone_level.get()-1)));
                    }
                }
                if (MyGoUtil.hasLegendaryEntity(attacker, ShulkerMimicSoulStoneItem.class)&&random.nextInt(100) <= MyGoConfig.shulker_mimic_soul_stone_chance.get() * 100
                        &&attacker.getPersistentData().getInt(shulker_mimic_soul_stone_time)==0){
                    attacker.getPersistentData().putInt(shulker_mimic_soul_stone_time,(int)(MyGoConfig.shulker_mimic_soul_stone_cooldown.get()*20*2));
                    attacked.addEffect(new MobEffectInstance(MobEffects.LEVITATION, (int)(MyGoConfig.shulker_mimic_soul_stone_time.get()*20), (int)(MyGoConfig.shulker_mimic_soul_stone_level.get()-1)));
                    if (!attacked.hasEffect(MobEffects.LEVITATION)) {
                        map.put(MobEffects.LEVITATION, new MobEffectInstance(MobEffects.LEVITATION, (int)(MyGoConfig.shulker_mimic_soul_stone_time.get()*20), (int)(MyGoConfig.shulker_mimic_soul_stone_level.get()-1)));
                    }
                }
                if (MyGoUtil.hasLegendaryEntity(attacked, DuneSentinelSoulStoneItem.class) &&
                        (event.getSource().is(IS_EXPLOSION)||event.getSource().is(IS_PROJECTILE)) ) {
                    number+=MyGoConfig.dune_sentinel_soul_stone_up.get();
                }
            }
            //结算部分
            float damage = (float) ((event.getAmount() * number + fixedNumber) * overNumber);
            //吸血
            double heal = 0;
            //暮色
            if (ModList.get().isLoaded("twilightforest")) {
                if(MyGoUtil.hasTwilightLich(attacker, LifedrainSoulStoneItem.class)){
                    heal+=damage*MyGoConfig.lifedrain_soul_stone_heal.get();
                    if(attacker instanceof Player player){
                        float saturation = player.getFoodData().getSaturationLevel();
                        player.getFoodData().setSaturation((float) Math.min(saturation + damage*MyGoConfig.lifedrain_soul_stone_sat.get(), 20));
                        int food = player.getFoodData().getFoodLevel();
                        player.getFoodData().setFoodLevel((int) Math.min(food + damage*MyGoConfig.lifedrain_soul_stone_food.get(), 20));
                    }
                }
                var DamageType = MyGoDamageType.hasSource(attacker.level(), DamageTypes.FREEZE, attacker);
                if(MyGoUtil.hasTwilight(attacker, AlphaYetiSoulStoneItem.class)&&attacker.getPersistentData().getInt(alpha_yeti_soul_stone_cooldown_time)==0){
                    attacker.getPersistentData().putInt(alpha_yeti_soul_stone_cooldown_time,(int)(MyGoConfig.alpha_yeti_soul_stone_cooldown.get()*20*2));
                    var mobList = MyGoUtil.mobList((int)((MyGoConfig.alpha_yeti_soul_stone_range.get()+1)/2), attacker);
                    for (Mob mobs : mobList) {
                        if ((!(mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null && ownableEntity == attacker) && mobs != attacker)) {
                            mobs.invulnerableTime = 0;
                            mobs.hurt(DamageType, (float) (damage * MyGoConfig.alpha_yeti_soul_stone_damage.get()));
                        }
                    }
                }
                if(MyGoUtil.hasTwilight(attacker, SnowQueenSoulStoneItem.class)&&attacker.getPersistentData().getInt(snow_queen_soul_stone_cooldown_time)==0){
                    attacker.getPersistentData().putInt(snow_queen_soul_stone_cooldown_time,(int)(MyGoConfig.snow_queen_soul_stone_cooldown.get()*20*2));
                    var mobList = MyGoUtil.mobList((int)((MyGoConfig.snow_queen_soul_stone_range.get()+1)/2), attacked);
                    for (Mob mobs : mobList) {
                        if ((!(mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null && ownableEntity == attacker) && mobs != attacker)) {
                            mobs.invulnerableTime = 0;
                            mobs.hurt(DamageType, (float) (damage * MyGoConfig.snow_queen_soul_stone_damage.get()));
                        }
                    }
                }
                if(MyGoUtil.hasTwilightForest(attacker, FieryIronSoulStoneItem.class)){
                    fireTime+=MyGoConfig.fiery_iron_soul_stone_time.get()*20;
                }
            }
            if (ModList.get().isLoaded("legendary_monsters")) {
                if (MyGoUtil.hasLegendaryEntity(attacker, LavaEaterSoulStoneItem.class)) {
                    fireTime += MyGoConfig.lava_eater_soul_stone_time.get() * 20;
                }
                if (MyGoUtil.hasLegendaryMonsters(attacker, CloudGolemSoulStoneItem.class) && attacker.getPersistentData().getInt(cloud_golem_soul_stone_time) == 0) {
                    attacker.getPersistentData().putInt(cloud_golem_soul_stone_time, (int) (MyGoConfig.cloud_golem_soul_stone_cooldown.get() * 20 * 2));
                    var DamageType = MyGoDamageType.hasSource(attacker.level(), DamageTypes.LIGHTNING_BOLT, attacker);
                    attacked.invulnerableTime = 0;
                    attacked.hurt(DamageType, (float) (damage * MyGoConfig.snow_queen_soul_stone_damage.get()));
                }
                if(MyGoUtil.hasLegendaryMonsters(attacker, FrostbittenGolemSoulStoneItem.class)&&attacker.getPersistentData().getInt(frostbitten_golem_soul_stone_time)==0){
                    var DamageType = MyGoDamageType.hasSource(attacker.level(), DamageTypes.FREEZE, attacker);
                    attacker.getPersistentData().putInt(frostbitten_golem_soul_stone_time,(int)(MyGoConfig.frostbitten_golem_soul_stone_cooldown.get()*20*2));
                    var mobList = MyGoUtil.mobList((int)((MyGoConfig.frostbitten_golem_soul_stone_range.get()+1)/2), attacker);
                    for (Mob mobs : mobList) {
                        if ((!(mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null && ownableEntity == attacker) && mobs != attacker)) {
                            mobs.invulnerableTime = 0;
                            mobs.hurt(DamageType, (float) (damage * MyGoConfig.frostbitten_golem_soul_stone_damage.get()));
                            mobs.addEffect(new MobEffectInstance(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "freeze")))
                                    , (int)(MyGoConfig.frostbitten_golem_soul_stone_time.get()*20), (int)(MyGoConfig.frostbitten_golem_soul_stone_level.get()-1)));
                            if (!mobs.hasEffect(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "freeze")))
                            )) {
                                map.put(
                                        Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "freeze")))
                                        , new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "freeze"))), (int)(MyGoConfig.frostbitten_golem_soul_stone_time.get()*20), (int)(MyGoConfig.frostbitten_golem_soul_stone_level.get()-1)));
                            }
                        }
                    }
                }
            }
            //可着火且目标时间小于最大着火时间
            if(fireTime>0&&attacked.getRemainingFireTicks()>fireTime){
                attacked.setRemainingFireTicks((int) fireTime);
            }
            //暮色炽铁烤火结算
            if (ModList.get().isLoaded("twilightforest")) {
                if(MyGoUtil.hasTwilightForest(attacker, FieryIronSoulStoneItem.class)&&attacked.getRemainingFireTicks()>0){
                    heal+=(attacked.getRemainingFireTicks()*MyGoConfig.fiery_iron_soul_stone_heal.get())/20;
                }
            }
            if(heal>0){
                attacker.heal((float) heal);
            }
            //穿甲
            if(fixedArmorPenetration>0) {
                double finalFixedArmorPenetration = fixedArmorPenetration;
                Optional.of(attacked)
                        .map(LivingEntity::getAttributes)
                        .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                        .map(manager -> manager.getInstance(Attributes.ARMOR))
                        .filter(instance -> instance.getModifier(uuid1) == null)
                        .ifPresent(instance -> instance.addTransientModifier(
                                new AttributeModifier(uuid1, "mygo_fixed_armor_penetration", finalFixedArmorPenetration * -1, AttributeModifier.Operation.ADDITION)));
            }
            if(overArmorPenetration>0) {
                double finalOverArmorPenetration = overArmorPenetration;
                Optional.of(attacked)
                        .map(LivingEntity::getAttributes)
                        .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                        .map(manager -> manager.getInstance(Attributes.ARMOR))
                        .filter(instance -> instance.getModifier(uuid2) == null)
                        .ifPresent(instance -> instance.addTransientModifier(
                                new AttributeModifier(uuid2, "mygo_over_armor_penetration", finalOverArmorPenetration * -1, AttributeModifier.Operation.MULTIPLY_TOTAL)));
            }
            if(orderOwnable){
                List<Mob> mobList = MyGoUtil.mobList(22, attacker);
                for (Mob mobs : mobList) {
                    //周围有随从、随从的主人是玩家、可以攻击目标————集体转火
                    if(mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() instanceof Player player&&mobs.canAttack(attacked)){
                        if(!mobs.level().isClientSide()) {
                            mobs.setTarget(attacked);
                        }
                    }
                }
            }
            //结束
            event.setAmount(damage);
        }
    }
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        Optional.of(event.getEntity())
                .map(LivingEntity::getAttributes)
                .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                .map(manager -> manager.getInstance(Attributes.ARMOR))
                .ifPresent(instance -> instance.removeModifier(uuid1));
        Optional.of(event.getEntity())
                .map(LivingEntity::getAttributes)
                .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                .map(manager -> manager.getInstance(Attributes.ARMOR))
                .ifPresent(instance -> instance.removeModifier(uuid2));
    }
}