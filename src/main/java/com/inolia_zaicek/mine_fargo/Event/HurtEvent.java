package com.inolia_zaicek.mine_fargo.Event;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Damage.MyGoDamageType;
import com.inolia_zaicek.mine_fargo.Item.Cataclysm.*;
import com.inolia_zaicek.mine_fargo.Item.Create.BrassSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Create.SturdySheetSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Entity.*;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.EscortSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.GoetyDarkSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.OrderAboutSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.IceAndFire.Dragon.*;
import com.inolia_zaicek.mine_fargo.Item.IceAndFire.Entity.HippocampusSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.IceAndFire.Entity.TrollSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.L2.*;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Entity.LavaEaterSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Entity.*;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Monsters.*;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Entity.*;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.EnderSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.LavaSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.NetherSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.*;
import com.inolia_zaicek.mine_fargo.Item.SonsOfSins.*;
import com.inolia_zaicek.mine_fargo.Item.Twilight.*;
import com.inolia_zaicek.mine_fargo.Item.Twilight.TwilightForest.FieryIronSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Twilight.TwilightForest.FluffyCloudSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Twilight.TwilightForest.QuestRamSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;

import static com.inolia_zaicek.mine_fargo.Event.DeathAndCloneEvent.*;
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
    public static final String apostle_soul_stone_cooldown = MineFargo.MODID + ":apostle_soul_stone";
    public static final String iaf_dragon_steel_time = MineFargo.MODID + ":iaf_dragon_steel_time";
    public static final String zone_hostility_soul_stone = MineFargo.MODID + ":zone_hostility_soul_stone";
    // 数据结构，保存每个实体的适应信息（伤害类型及次数，顺序保存）
    private static final Map<LivingEntity, HurtEvent.Data> ENTITY_ADAPT_DATA = Collections.synchronizedMap(new WeakHashMap<>());

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        Random random = new Random();
        if (attacked != null) {
            boolean canceled = false;
            double baseDamage = event.getAmount();
            //减伤采用乘算，保证稀释
            double number = 1;
            double overNumber = 1;
            float fixedNumber = 0;
            double invulnerableTime = 1;
            //限伤【最大生命值*该数额】
            double xianShang = 1;
            if (ModList.get().isLoaded("legendary_monsters")) {
                if (MyGoUtil.hasLegendaryMonsters(attacked, CloudGolemSoulStoneItem.class)) {
                    invulnerableTime += MyGoConfig.cloud_golem_soul_stone_time.get();
                }
            }
            if (MyGoUtil.hasNature(attacked, EnderSoulStoneItem.class)
                    && event.getSource().getEntity() != null && event.getSource().getEntity() instanceof EnderMan) {
                number *= 1 - MyGoConfig.ender_soul_stone_armor.get();
            }
            if (ModList.get().isLoaded("cataclysm")) {
                //水中
                if (MyGoUtil.hasCataclysm(attacked, TheLeviathanSoulStoneItem.class) &&
                        !(attacked.isUnderWater() || attacked.isInWater() || attacked.isInWaterOrRain() || attacked.isInWaterRainOrBubble())) {
                    number *= 1 - MyGoConfig.the_leviathan_soul_stone_armor.get();
                }
            }
            //莱特兰
            if (ModList.get().isLoaded("l2hostility")) {
                //禁域自身减伤
                if (MyGoUtil.hasL2Hostility(attacked, AquaHostilitySoulStoneItem.class) && event.getSource().getEntity() instanceof LivingEntity attacker) {
                    boolean aqua = false;
                    //实际范围A=（数值+1）/2——————范围5x5实际上是输入3=（5+1）/2
                    var mobList = MyGoUtil.mobList((int) ((MyGoConfig.aqua_hostility_soul_stone_range.get() + 1) / 2), attacked);
                    for (Mob mobs : mobList) {
                        if (mobs != null) {
                            //如果这些范围里有人和伤害源一样
                            if (mobs == attacker) {
                                aqua = true;
                            }
                        }
                    }
                    if (aqua) {
                        number *= 1 - MyGoConfig.aqua_hostility_soul_stone_my_armor.get();
                    }
                }
                //禁域随从减伤
                if (attacked instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() != null) {
                    if (MyGoUtil.hasL2Hostility(ownableEntity.getOwner(), AquaHostilitySoulStoneItem.class)) {
                        number *= 1 - MyGoConfig.aqua_hostility_soul_stone_own_armor.get();
                    }
                }
                //四相
                if (MyGoUtil.hasL2Hostility(attacked, ResistanceHostilitySoulStoneItem.class)) {
                    //火
                    if (event.getSource().is(IS_FIRE)) {
                        number *= 1 - MyGoConfig.resistance_hostility_soul_stone_fire.get();
                    }
                    //物理
                    else if (!event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !event.getSource().is(DamageTypeTags.BYPASSES_EFFECTS)
                            && !event.getSource().is(WITCH_RESISTANT_TO)) {
                        number *= 1 - MyGoConfig.resistance_hostility_soul_stone_atk.get();
                    }
                    //弹射物
                    else if (event.getSource().is(IS_PROJECTILE) || (ModList.get().isLoaded("tacz")
                            && event.getSource().type().msgId().equals(new ResourceLocation("tacz", "bullet"))
                    ) || (ModList.get().isLoaded("tacz")
                            && event.getSource().type().msgId().equals(new ResourceLocation("tacz", "bullet_ignore_armor"))
                    ) || (ModList.get().isLoaded("tacz")
                            && event.getSource().type().msgId().equals(new ResourceLocation("tacz", "bullet_void"))
                    ) || (ModList.get().isLoaded("tacz")
                            && event.getSource().type().msgId().equals(new ResourceLocation("tacz", "bullet_void_ignore_armor"))
                    ) || (ModList.get().isLoaded("tacz")
                            && event.getSource().type().msgId().equals(new ResourceLocation("tacz", "bullets"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "gunfire"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "gunfire_absolute"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "gunfire_headshot"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "gunfire_headshot_absolute"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "laser"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "laser_headshot"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "projectile_hit"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "projectile_explosion"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "custom_explosion"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "drone_hit"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "laser_static"))
                    ) || (ModList.get().isLoaded("superbwarfare")
                            && event.getSource().type().msgId().equals(new ResourceLocation("superbwarfare", "grapeshot_hit"))
                    )
                    ) {
                        number *= 1 - MyGoConfig.resistance_hostility_soul_stone_pre.get();
                    }
                    //魔法
                    else if (event.getSource().is(WITCH_RESISTANT_TO)) {
                        number *= 1 - MyGoConfig.resistance_hostility_soul_stone_magic.get();
                    } else if ((ModList.get().isLoaded("alshanex_familiars")
                            && event.getSource().type().msgId().equals(new ResourceLocation("alshanex_familiars", "sound_magic"))
                    ) || (ModList.get().isLoaded("traveloptics")
                            && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                    ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                            && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                    ) || (ModList.get().isLoaded("fantasy_ending")
                            && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                    ) || (ModList.get().isLoaded("fantasy_ending")
                            && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                    )) {
                        number *= 1 - MyGoConfig.resistance_hostility_soul_stone_magic.get();
                    } else if ((ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "blood_cauldron"))
                    ) || (ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "blood_magic"))
                    ) || (ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "dragon_breath_pool"))
                    ) || (ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "eldritch_magic"))
                    ) || (ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "ender_magic"))
                    ) || (ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "evocation_magic"))
                    ) || (ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "fire_field"))
                    ) || (ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "fire_magic"))
                    ) || (ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "heartstop"))
                    ) || (ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "holy_magic"))
                    ) || (ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "ice_magic"))
                    ) || (ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "lightning_magic"))
                    ) || (ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "nature_magic"))
                    ) || (ModList.get().isLoaded("irons_spellbooks")
                            && event.getSource().type().msgId().equals(new ResourceLocation("irons_spellbooks", "poison_cloud"))
                    )) {
                        number *= 1 - MyGoConfig.resistance_hostility_soul_stone_magic.get();
                    }
                }
                //适应
                if (MyGoUtil.hasL2Hostility(attacked, BodyHostilitySoulStoneItem.class)
                        // 跳过穿透无敌或效果的伤害，跟原代码逻辑保持一致
                        && !event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY) && !event.getSource().is(DamageTypeTags.BYPASSES_EFFECTS)) {
                    // 获得伤害来源的唯一标识（类似MsgId）
                    String id = event.getSource().getMsgId();
                    // 获取或创建该实体的适应数据
                    HurtEvent.Data data = ENTITY_ADAPT_DATA.computeIfAbsent(attacked, k -> new HurtEvent.Data());
                    synchronized (data) {
                        if (data.memory.contains(id)) {
                            // 如果之前打过这个伤害类型，放到最前面
                            data.memory.remove(id);
                            data.memory.add(0, id);
                            // 适应次数加1
                            int count = data.adaption.compute(id, (k, old) -> old == null ? 1 : old + 1);
                            // 计算减伤系数【计算 MyGoConfig.body_hostility_soul_stone_adaptive_re.get() 的 (count - 1) 次方】
                            double factor = Math.pow(MyGoConfig.body_hostility_soul_stone_adaptive_re.get(), count - 1);
                            // 调整事件伤害，实现伤害减免，注意要乘以原伤害【factor就《1】
                            //float newDamage = (float) (event.getAmount() * factor);
                            overNumber *= Math.max(1 - MyGoConfig.body_hostility_soul_stone_adaptive_max_re.get(), factor);
                        } else {
                            // 第一次收到这个伤害类型，加入记忆
                            data.memory.add(0, id);
                            data.adaption.put(id, 1);
                            // 超出最大记忆数时移除最旧的记忆及对应记录
                            if (data.memory.size() > (int) (MyGoConfig.body_hostility_soul_stone_adaptive_number.get() * 1)) {
                                String old = data.memory.remove(data.memory.size() - 1);
                                data.adaption.remove(old);
                            }
                        }
                    }
                }
                //适应结束
            }
            //法伤减免
            if (ModList.get().isLoaded("goety")) {
                if (MyGoUtil.hasGoetyEntity(attacked, ApostleSoulStoneItem.class)) {
                    //限伤设置：无法超过
                    xianShang = Math.min(1, MyGoConfig.apostle_soul_stone_damage_restriction.get());
                    if (event.getSource().is(WITCH_RESISTANT_TO)) {
                        number *= 1 - MyGoConfig.apostle_soul_stone_damage_down.get();
                    } else if ((ModList.get().isLoaded("alshanex_familiars")
                            && event.getSource().type().msgId().equals(new ResourceLocation("alshanex_familiars", "sound_magic"))
                    ) || (ModList.get().isLoaded("traveloptics")
                            && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                    ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                            && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                    ) || (ModList.get().isLoaded("fantasy_ending")
                            && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                    ) || (ModList.get().isLoaded("fantasy_ending")
                            && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                    )) {
                        number *= 1 - MyGoConfig.apostle_soul_stone_damage_down.get();
                    } else if ((ModList.get().isLoaded("ars_nouveau")
                            && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "spell"))
                    ) || (ModList.get().isLoaded("ars_nouveau")
                            && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "windshear"))
                    ) || (ModList.get().isLoaded("ars_nouveau")
                            && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "frost"))
                    ) || (ModList.get().isLoaded("ars_nouveau")
                            && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "flare"))
                    ) || (ModList.get().isLoaded("ars_nouveau")
                            && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "crush"))
                    )) {
                        number *= 1 - MyGoConfig.apostle_soul_stone_damage_down.get();
                    }
                }
                if (MyGoUtil.hasGoetyEntity(attacked, NetherApostleSoulStoneItem.class)) {
                    if (attacked.level().dimension().equals(Level.NETHER)) {
                        number *= 1 - MyGoConfig.nether_apostle_soul_stone_damage_down.get();
                    }
                    if (event.getSource().is(IS_FALL)) {
                        canceled = true;
                    }
                }
            }
            //机械动力
            if (ModList.get().isLoaded("create")) {
                if (MyGoUtil.hasCreate(attacked, SturdySheetSoulStoneItem.class)) {
                    fixedNumber -= MyGoConfig.sturdy_sheet_soul_stone_damage.get();
                }
                //机械动力伤害
                if (event.getSource().type().msgId().equals(new ResourceLocation("create", "crush"))
                        || event.getSource().type().msgId().equals(new ResourceLocation("create", "cuckoo_surprise"))
                        || event.getSource().type().msgId().equals(new ResourceLocation("create", "fan_fire"))
                        || event.getSource().type().msgId().equals(new ResourceLocation("create", "fan_lava"))
                        || event.getSource().type().msgId().equals(new ResourceLocation("create", "mechanical_drill"))
                        || event.getSource().type().msgId().equals(new ResourceLocation("create", "mechanical_roller"))
                        || event.getSource().type().msgId().equals(new ResourceLocation("create", "mechanical_saw"))
                        || event.getSource().type().msgId().equals(new ResourceLocation("create", "potato_cannon"))
                        || event.getSource().type().msgId().equals(new ResourceLocation("create", "run_over"))
                ) {
                    if (MyGoUtil.hasCreate(attacked, BrassSoulStoneItem.class)) {
                        number *= 1 - MyGoConfig.brass_soul_stone_damage.get();
                    }
                }
            }
            if (ModList.get().isLoaded("twilightforest")) {
                if (MyGoUtil.hasTwilightForest(attacked, QuestRamSoulStoneItem.class)) {
                    if (event.getSource().is(WITCH_RESISTANT_TO)) {
                        number *= 1 - MyGoConfig.quest_ram_soul_stone.get();
                    } else if ((ModList.get().isLoaded("alshanex_familiars")
                            && event.getSource().type().msgId().equals(new ResourceLocation("alshanex_familiars", "sound_magic"))
                    ) || (ModList.get().isLoaded("traveloptics")
                            && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                    ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                            && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                    ) || (ModList.get().isLoaded("fantasy_ending")
                            && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                    ) || (ModList.get().isLoaded("fantasy_ending")
                            && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                    )) {
                        number *= 1 - MyGoConfig.quest_ram_soul_stone.get();
                    } else if ((ModList.get().isLoaded("irons_spellbooks")
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
                    )) {
                        number *= 1 - MyGoConfig.quest_ram_soul_stone.get();
                    } else if ((ModList.get().isLoaded("ars_nouveau")
                            && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "flare"))
                    ) || (ModList.get().isLoaded("ars_nouveau")
                            && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "frost"))
                    ) || (ModList.get().isLoaded("ars_nouveau")
                            && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "spell"))
                    ) || (ModList.get().isLoaded("ars_nouveau")
                            && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "windshear"))
                    ) || (ModList.get().isLoaded("ars_nouveau")
                            && event.getSource().type().msgId().equals(new ResourceLocation("ars_nouveau", "crush"))
                    )) {
                        number *= 1 - MyGoConfig.quest_ram_soul_stone.get();
                    }
                }
                //九头蛇减伤
                if (MyGoUtil.hasTwilight(attacked, TwilightHydraSoulStoneItem.class) && event.getSource().getEntity() instanceof LivingEntity attacker) {
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
                if (MyGoUtil.hasTwilight(attacked, UrGhastSoulStoneItem.class)) {
                    if (event.getAmount() >= attacked.getMaxHealth() * MyGoConfig.ur_ghast_soul_stone_number.get()) {
                        attacked.getPersistentData().putInt(ur_ghast_soul_stone_time, (int) (MyGoConfig.ur_ghast_soul_stone_time.get() * 20 * 2));
                    }
                    if (attacked.getPersistentData().getInt(ur_ghast_soul_stone_time) > 0) {
                        number *= 1 - MyGoConfig.ur_ghast_soul_stone_armor.get();
                    }
                }
                if (MyGoUtil.hasTwilightForest(attacked, FluffyCloudSoulStoneItem.class) && event.getSource().getEntity() instanceof LivingEntity attacker && !attacker.onGround()) {
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
                    if (event.getSource().is(IS_FIRE) || event.getSource().is(IS_EXPLOSION)) {
                        number *= 1 - MyGoConfig.goety_dark_soul_stone_fire.get();
                    } else if (event.getSource().is(WITCH_RESISTANT_TO)) {
                        number *= 1 - MyGoConfig.goety_dark_soul_stone_magic.get();
                    }
                }
                //末影守望距离减伤
                if (MyGoUtil.hasGoetyEntity(attacked, EnderKeeperSoulStoneItem.class) && event.getSource().getEntity() instanceof LivingEntity attacker) {
                    invulnerableTime += MyGoConfig.ender_keeper_soul_stone_time_up.get();
                    boolean hydra = false;
                    //实际范围A=（数值+1）/2——————范围5x5实际上是输入3=（5+1）/2
                    var mobList = MyGoUtil.mobList((int) ((MyGoConfig.ender_keeper_soul_stone_damage_down.get() + 1) / 2), attacked);
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
            }
            if (invulnerableTime != 1) {
                double finishTime = Math.max(0, invulnerableTime);
                attacked.invulnerableTime = (int) (attacked.invulnerableTime * finishTime);
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
            //冰火
            if (ModList.get().isLoaded("iceandfire")) {
                if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                    if (EntityType.getKey(attacker.getType()).toString().equals("iceandfire:fire_dragon")
                            || EntityType.getKey(attacker.getType()).toString().equals("iceandfire:ice_dragon")
                            || EntityType.getKey(attacker.getType()).toString().equals("iceandfire:lightning_dragon_blood")) {
                        if (MyGoUtil.hasIAFDragon(attacked, DragonBoneSoulStoneItem.class)) {
                            fixedNumber -= MyGoConfig.dragon_bone_soul_stone_fixed.get();
                        }
                        double dragonDamageDown = 0;
                        //龙钢减伤
                        if (MyGoUtil.hasIAFDragon(attacked, FireDragonSteelSoulStoneItem.class)) {
                            dragonDamageDown += MyGoConfig.fire_dragon_steel_soul_stone_dragon.get();
                        }
                        if (MyGoUtil.hasIAFDragon(attacked, IceDragonSteelSoulStoneItem.class)) {
                            dragonDamageDown += MyGoConfig.ice_dragon_steel_soul_stone_dragon.get();
                        }
                        if (MyGoUtil.hasIAFDragon(attacked, LightningDragonSteelSoulStoneItem.class)) {
                            dragonDamageDown += MyGoConfig.lightning_dragon_steel_soul_stone_dragon.get();
                        }
                        if (dragonDamageDown > 0) {
                            overNumber *= 1 - dragonDamageDown;
                        }
                    }
                }
            }

            if (ModList.get().isLoaded("sons_of_sins")) {
                if (MyGoUtil.hasSonsOfSins(attacked, GreedSinsSoulStoneItem.class) && event.getSource().is(DamageTypes.IN_WALL)) {
                    number *= 1 - MyGoConfig.greed_sin_soul_stone_down.get();
                }
                if (MyGoUtil.hasSonsOfSins(attacked, LustSinsSoulStoneItem.class) && event.getSource().is(IS_PROJECTILE)) {
                    number *= 1 - MyGoConfig.lust_sin_soul_stone_down.get();
                }
            }
            if (ModList.get().isLoaded("iceandfire")) {
                if (MyGoUtil.hasIAFEntity(attacked, TrollSoulStoneItem.class) && event.getSource().is(IS_PROJECTILE)) {
                    number *= 1 - MyGoConfig.troll_soul_stone.get();
                }
            }
            if (MyGoUtil.hasNature(attacked, SnowSoulStoneItem.class) && event.getSource().is(IS_FREEZING)) {
                canceled = true;
            }
            if (MyGoUtil.hasNature(attacked, LavaSoulStoneItem.class) && event.getSource().is(IS_FIRE)) {
                number *= 1 - MyGoConfig.lava_soul_stone.get();
            }
            if (MyGoUtil.hasEntity(attacked, WingSoulStoneItem.class) && event.getSource().is(IS_FALL)) {
                canceled = true;
            }
            if (baseDamage + fixedNumber <= 0 || number <= 0 || overNumber <= 0 || ((baseDamage * number + fixedNumber) * overNumber) <= 0) {
                canceled = true;
            }
            if (canceled) {
                event.setCanceled(true);
            } else {
                float damage = (float) Math.max(0, (baseDamage * number + fixedNumber) * overNumber);
                //限伤不为1且伤害大于限伤数额
                if (xianShang < 1 && damage > (attacked.getMaxHealth() * xianShang)) {
                    damage = (float) (attacked.getMaxHealth() * xianShang);
                }
                if (damage <= 0) {
                    event.setCanceled(true);
                } else {
                    //反伤
                    if (event.getSource().getEntity() instanceof LivingEntity attacker) {
                        //莱特兰
                        if (ModList.get().isLoaded("l2hostility")) {
                            //疆域反伤
                            if (MyGoUtil.hasL2Hostility(attacked, ZoneHostilitySoulStoneItem.class) && attacker.getPersistentData().getInt(zone_hostility_soul_stone) == 0) {
                                attacker.getPersistentData().putInt(zone_hostility_soul_stone, (int) (0.05 * 20 * 2));
                                attacker.invulnerableTime = 0;
                                attacker.hurt(MyGoDamageType.hasSource(attacked.level(), MyGoDamageType.TickMagicDamage, attacked),
                                        (float) (damage * MyGoConfig.zone_hostility_soul_stone_damage.get()));
                            }
                        }
                        if (ModList.get().isLoaded("legendary_monsters")) {
                            if (MyGoUtil.hasLegendaryMonsters(attacked, AncientGuardianSoulStoneItem.class) && attacked.getPersistentData().getInt(ancient_guardian_soul_stone_time) == 0 && attacker instanceof LivingEntity) {
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

            //莱特兰
            if (ModList.get().isLoaded("l2hostility")) {
                if (MyGoUtil.hasL2Hostility(attacker, UltraHostilitySoulStoneItem.class)) {
                    //复印
                    for (EquipmentSlot slot : EquipmentSlot.values()) {
                        ItemStack src = attacker.getItemBySlot(slot);
                        Map<Enchantment, Integer> targetEnch = src.getAllEnchantments();
                        long total = 0L;
                        int maxLv = 0;
                        for (Map.Entry<Enchantment, Integer> e : targetEnch.entrySet()) {
                            //是当前检查的附魔等级
                            int lv = e.getValue();
                            //检查过的所有附魔中的最大附魔等级
                            maxLv = Math.max(maxLv, lv);
                            //total附魔等级加权的总和
                            if (total >= 0L) {
                                total += 1L << (lv - 1);
                            }
                        }
                        if (total >= 1) {
                            number += total * MyGoConfig.ultra_hostility_soul_stone_damage.get();
                        }
                    }
                }
                if (MyGoUtil.hasL2Hostility(attacker, DestroyHostilitySoulStoneItem.class) && attacked != null) {
                    int neutralAndHarmfulCount = 0;
                    for (MobEffectInstance effect : attacked.getActiveEffects()) {
                        // 判断是否为NEUTRAL或Harmful【非正面——负面
                        boolean isNEUTRAL = effect.getEffect().getCategory() == MobEffectCategory.NEUTRAL;
                        boolean isHarmful = effect.getEffect().getCategory() == MobEffectCategory.HARMFUL;
                        // 统计非NEUTRAL且非Harmful的效果
                        if (isNEUTRAL || isHarmful) {
                            neutralAndHarmfulCount++;
                        }
                    }
                    number += neutralAndHarmfulCount * MyGoConfig.destroy_hostility_soul_stone_buff.get();
                    int emptyArmorSlots = 0; // 用于计数目标生物身上空着的护甲槽位数量
                    // 遍历目标生物的四种主要护甲槽位：头盔、胸甲、护腿、鞋子
                    for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
                        ItemStack armorStack = attacked.getItemBySlot(slot); // 获取该槽位上的护甲物品
                        if (!armorStack.isEmpty()) { // 如果该护甲槽位不是空的
                            emptyArmorSlots++; // 增加护甲槽位的计数
                        }
                    }
                    number += emptyArmorSlots * MyGoConfig.destroy_hostility_soul_stone_armor.get();
                }
                //禁域自身增伤
                if (MyGoUtil.hasL2Hostility(attacker, AquaHostilitySoulStoneItem.class) && attacked != null) {
                    boolean aqua = false;
                    //实际范围A=（数值+1）/2——————范围5x5实际上是输入3=（5+1）/2
                    var mobList = MyGoUtil.mobList((int) ((MyGoConfig.aqua_hostility_soul_stone_range.get() + 1) / 2), attacker);
                    for (Mob mobs : mobList) {
                        if (mobs != null) {
                            //如果这些范围里有人和伤害源一样
                            if (mobs == attacked) {
                                aqua = true;
                            }
                        }
                    }
                    if (aqua) {
                        number += MyGoConfig.aqua_hostility_soul_stone_my_damage.get();
                    }
                }
                //禁域随从减伤
                if (attacked instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() != null) {
                    if (MyGoUtil.hasL2Hostility(ownableEntity.getOwner(), AquaHostilitySoulStoneItem.class)) {
                        number += MyGoConfig.aqua_hostility_soul_stone_own_damage.get();
                    }
                }
                if (MyGoUtil.hasL2Hostility(attacker, ResistanceHostilitySoulStoneItem.class)) {
                    overArmorPenetration += MyGoConfig.resistance_hostility_soul_stone_armor_p.get();
                    fireTime += MyGoConfig.resistance_hostility_soul_stone_time.get() * 20;
                }
                //腐蚀
                if (MyGoUtil.hasL2Hostility(attacker, CorrosionHostilitySoulStoneItem.class)&&attacked!=null) {
                    int corrosionTime = (int) (20 * MyGoConfig.corrosion_hostility_soul_stone_time.get());
                    //虚弱
                    attacked.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, corrosionTime,
                            (int) (MyGoConfig.corrosion_hostility_soul_stone_weakness.get() - 1)));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(MobEffects.WEAKNESS)) {
                        map.put(MobEffects.WEAKNESS, new MobEffectInstance(MobEffects.WEAKNESS, corrosionTime,
                                (int) (MyGoConfig.corrosion_hostility_soul_stone_weakness.get() - 1)));
                    }
                    //缓慢
                    attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, corrosionTime,
                            (int) (MyGoConfig.corrosion_hostility_soul_stone_slowness.get() - 1)));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        map.put(MobEffects.MOVEMENT_SLOWDOWN, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, corrosionTime,
                                (int) (MyGoConfig.corrosion_hostility_soul_stone_slowness.get() - 1)));
                    }
                    //中毒
                    attacked.addEffect(new MobEffectInstance(MobEffects.POISON, corrosionTime,
                            (int) (MyGoConfig.corrosion_hostility_soul_stone_poison.get() - 1)));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(MobEffects.POISON)) {
                        map.put(MobEffects.POISON, new MobEffectInstance(MobEffects.POISON, corrosionTime,
                                (int) (MyGoConfig.corrosion_hostility_soul_stone_poison.get() - 1)));
                    }
                    //凋零
                    attacked.addEffect(new MobEffectInstance(MobEffects.WITHER, corrosionTime,
                            (int) (MyGoConfig.corrosion_hostility_soul_stone_wither.get() - 1)));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(MobEffects.WITHER)) {
                        map.put(MobEffects.WITHER, new MobEffectInstance(MobEffects.WITHER, corrosionTime,
                                (int) (MyGoConfig.corrosion_hostility_soul_stone_wither.get() - 1)));
                    }
                    //魂火
                    attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                            new ResourceLocation("l2complements", "flame"))),
                            corrosionTime, (int) (MyGoConfig.corrosion_hostility_soul_stone_flame.get() - 1), false, false, false));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2complements", "flame")))
                    )) {
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2complements", "flame"))),
                                new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                        new ResourceLocation("l2complements", "flame"))), corrosionTime,
                                        (int) (MyGoConfig.corrosion_hostility_soul_stone_flame.get() - 1), false, false, false));
                    }
                    //寒流
                    attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                            new ResourceLocation("l2complements", "frozen"))),
                            corrosionTime, (int) (MyGoConfig.corrosion_hostility_soul_stone_frozen.get() - 1), false, false, false));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2complements", "frozen")))
                    )) {
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2complements", "frozen"))),
                                new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                        new ResourceLocation("l2complements", "frozen"))), corrosionTime,
                                        (int) (MyGoConfig.corrosion_hostility_soul_stone_frozen.get() - 1), false, false, false));
                    }
                    //重力
                    attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                            new ResourceLocation("l2hostility", "gravity"))),
                            corrosionTime, (int) (MyGoConfig.corrosion_hostility_soul_stone_gravity.get() - 1), false, false, false));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2hostility", "gravity")))
                    )) {
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2hostility", "gravity"))),
                                new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                        new ResourceLocation("l2hostility", "gravity"))), corrosionTime,
                                        (int) (MyGoConfig.corrosion_hostility_soul_stone_gravity.get() - 1), false, false, false));
                    }
                    //诅咒
                    attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                            new ResourceLocation("l2complements", "curse"))),
                            corrosionTime, (int) (MyGoConfig.corrosion_hostility_soul_stone_curse.get() - 1), false, false, false));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2complements", "curse")))
                    )) {
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2complements", "curse"))),
                                new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                        new ResourceLocation("l2complements", "curse"))), corrosionTime,
                                        (int) (MyGoConfig.corrosion_hostility_soul_stone_curse.get() - 1), false, false, false));
                    }

                }
            }
            //七罪
            if (ModList.get().isLoaded("sons_of_sins")) {
                if (MyGoUtil.hasSonsOfSins(attacker, EnvySinsSoulStoneItem.class)) {
                    number += MyGoConfig.envy_sin_soul_stone_atk.get();
                }
                if (MyGoUtil.hasSonsOfSins(attacker, GluttonySinsSoulStoneItem.class)) {
                    int killNumber = attacker.getPersistentData().getInt(gluttony_sin_soul_stone);
                    number += Math.min(MyGoConfig.gluttony_sin_soul_stone_max.get(), killNumber * MyGoConfig.gluttony_sin_soul_stone_kill.get());
                }
                if (MyGoUtil.hasSonsOfSins(attacker, LustSinsSoulStoneItem.class)) {
                    overArmorPenetration += MyGoConfig.lust_sin_soul_stone_armor_p.get();
                }
                if (MyGoUtil.hasSonsOfSins(attacker, PrideSinsSoulStoneItem.class)) {
                    if (attacker.getHealth() / attacker.getMaxHealth() > attacked.getHealth() / attacked.getMaxHealth()) {
                        number += MyGoConfig.pride_sin_soul_stone.get();
                    }
                }
                if (MyGoUtil.hasSonsOfSins(attacker, SlothSinsSoulStoneItem.class)) {
                    attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (MyGoConfig.sloth_sin_soul_stone_time.get() * 20),
                            (int) (MyGoConfig.sloth_sin_soul_stone_lvl.get() - 1)));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        map.put(MobEffects.MOVEMENT_SLOWDOWN, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                                (int) (MyGoConfig.sloth_sin_soul_stone_time.get() * 20), (int) (MyGoConfig.sloth_sin_soul_stone_lvl.get() - 1)));
                    }
                }
                if (MyGoUtil.hasSonsOfSins(attacker, WrathSinsSoulStoneItem.class)) {
                    number += MyGoConfig.wrath_sin_soul_stone_damage.get();
                }
            }
            //冰火
            if (ModList.get().isLoaded("iceandfire")) {
                if (MyGoUtil.hasIAFEntity(attacker, HippocampusSoulStoneItem.class)) {
                    attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (MyGoConfig.hippocampus_soul_stone_time.get() * 20),
                            (int) (MyGoConfig.hippocampus_soul_stone_lvl1.get() - 1)));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        map.put(MobEffects.MOVEMENT_SLOWDOWN, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                                (int) (MyGoConfig.hippocampus_soul_stone_time.get() * 20), (int) (MyGoConfig.hippocampus_soul_stone_lvl1.get() - 1)));
                    }
                    attacked.addEffect(new MobEffectInstance(MobEffects.CONFUSION, (int) (MyGoConfig.hippocampus_soul_stone_time.get() * 20),
                            (int) (MyGoConfig.hippocampus_soul_stone_lvl1.get() - 1)));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(MobEffects.CONFUSION)) {
                        map.put(MobEffects.CONFUSION, new MobEffectInstance(MobEffects.CONFUSION,
                                (int) (MyGoConfig.hippocampus_soul_stone_time.get() * 20), (int) (MyGoConfig.hippocampus_soul_stone_lvl1.get() - 1)));
                    }
                }
                if (MyGoUtil.hasIAFDragon(attacker, DragonBoneSoulStoneItem.class)) {
                    if (EntityType.getKey(attacked.getType()).toString().equals("iceandfire:fire_dragon")
                            || EntityType.getKey(attacked.getType()).toString().equals("iceandfire:ice_dragon")
                            || EntityType.getKey(attacked.getType()).toString().equals("iceandfire:lightning_dragon_blood")) {
                        number += MyGoConfig.dragon_bone_soul_stone_damage.get();
                    }
                    overArmorPenetration += MyGoConfig.dragon_bone_soul_stone_armor_p.get();
                }
                //龙血
                if (MyGoUtil.hasIAFDragon(attacker, FireDragonBloodSoulStoneItem.class)) {
                    fireTime += MyGoConfig.fire_dragon_blood_soul_stone_time.get() * 20;
                    if (attacked.getRemainingFireTicks() > 0) {
                        number += MyGoConfig.fire_dragon_blood_soul_stone_damage.get();
                    }
                    if (EntityType.getKey(attacked.getType()).toString().equals("iceandfire:ice_dragon")) {
                        number += MyGoConfig.fire_dragon_blood_soul_stone_dragon.get();
                    }
                }
                if (MyGoUtil.hasIAFDragon(attacker, IceDragonBloodSoulStoneItem.class)) {
                    if (EntityType.getKey(attacked.getType()).toString().equals("iceandfire:fire_dragon")) {
                        number += MyGoConfig.fire_dragon_blood_soul_stone_dragon.get();
                    }
                    attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (20 * MyGoConfig.ice_dragon_blood_soul_stone_time.get()),
                            (int) (MyGoConfig.ice_dragon_blood_soul_stone_ms_lvl.get() - 1)));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        map.put(MobEffects.MOVEMENT_SLOWDOWN, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                                (int) (20 * MyGoConfig.ice_dragon_blood_soul_stone_time.get()), (int) (MyGoConfig.ice_dragon_blood_soul_stone_ms_lvl.get() - 1)));
                    }
                    attacked.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, (int) (20 * MyGoConfig.ice_dragon_blood_soul_stone_time.get()),
                            (int) (MyGoConfig.ice_dragon_blood_soul_stone_dig_lvl.get() - 1)));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                        map.put(MobEffects.DIG_SLOWDOWN, new MobEffectInstance(MobEffects.DIG_SLOWDOWN,
                                (int) (20 * MyGoConfig.ice_dragon_blood_soul_stone_time.get()), (int) (MyGoConfig.ice_dragon_blood_soul_stone_dig_lvl.get() - 1)));
                    }
                }
                if (MyGoUtil.hasIAFDragon(attacker, LightningDragonBloodSoulStoneItem.class)) {
                    if (EntityType.getKey(attacked.getType()).toString().equals("iceandfire:ice_dragon")
                            || EntityType.getKey(attacked.getType()).toString().equals("iceandfire:fire_dragon")) {
                        number += MyGoConfig.fire_dragon_blood_soul_stone_dragon.get();
                    }
                }
            }
            //诡厄
            if (ModList.get().isLoaded("goety")) {
                if (MyGoUtil.hasGoetyEntity(attacker, RedstoneMonstrositySoulStoneItem.class)
                        && attacker.getPersistentData().getInt(redstone_monstrosity_soul_stone_cooldown) == 0 && attacker instanceof LivingEntity) {
                    attacker.getPersistentData().putInt(redstone_monstrosity_soul_stone_cooldown, (int) (MyGoConfig.redstone_monstrosity_soul_stone_cooldown.get() * 20 * 2));
                    fixedNumber += Math.min(MyGoConfig.redstone_monstrosity_soul_stone_max_damage.get(),
                            attacked.getMaxHealth() * MyGoConfig.redstone_monstrosity_soul_stone_hp_damage.get());
                }
                //随从
                if (attacker instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() != null) {
                    LivingEntity owner = ownableEntity.getOwner();
                    if (MyGoUtil.hasGoetyItem(owner, OrderAboutSoulStoneItem.class)) {
                        number += MyGoConfig.order_about_soul_stone_damage.get();
                    }
                }
                //侵蚀
                if (MyGoUtil.hasBotania(attacker, NetherApostleSoulStoneItem.class)) {
                    attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                            new ResourceLocation("goety", "sapped"))),
                            (int) (MyGoConfig.nether_apostle_soul_stone_time.get() * 20), 0, false, false, false));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("goety", "sapped")))
                    )) {
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("goety", "sapped"))),
                                new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                        new ResourceLocation("goety", "sapped"))),
                                        (int) (MyGoConfig.nether_apostle_soul_stone_time.get() * 20), 0, false, false, false));
                    } else {
                        int level = Objects.requireNonNull(attacked.getEffect(
                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("goety", "sapped")))
                        )).getAmplifier();
                        if (random.nextInt(100) <= MyGoConfig.nether_apostle_soul_stone_chance.get() * 100 && level < (int) (MyGoConfig.nether_apostle_soul_stone_lvl.get() - 1)) {
                            attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                    new ResourceLocation("goety", "sapped"))),
                                    (int) (MyGoConfig.nether_apostle_soul_stone_time.get() * 20), level + 1, false, false, false));
                            if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("goety", "sapped")))
                            )) {
                                map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("goety", "sapped"))),
                                        new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                                new ResourceLocation("goety", "sapped"))),
                                                (int) (MyGoConfig.nether_apostle_soul_stone_time.get() * 20), level + 1, false, false, false));
                            }
                        }
                    }
                }
                //虚空
                if (MyGoUtil.hasGoetyEntity(attacked, EnderKeeperSoulStoneItem.class)) {
                    attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                            new ResourceLocation("goety", "void_touched"))),
                            (int) (MyGoConfig.ender_keeper_soul_stone_time.get() * 20), (int) (MyGoConfig.ender_keeper_soul_stone_level.get() - 1), false, false, false));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("goety", "void_touched")))
                    )) {
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("goety", "void_touched"))),
                                new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                        new ResourceLocation("goety", "void_touched"))),
                                        (int) (MyGoConfig.ender_keeper_soul_stone_time.get() * 20), (int) (MyGoConfig.ender_keeper_soul_stone_level.get() - 1), false, false, false));
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
                fireTime += 100;
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
                    //水中
                    if ((attacker.isUnderWater() || attacker.isInWater() || attacker.isInWaterOrRain() || attacker.isInWaterRainOrBubble())) {
                        number += MyGoConfig.the_leviathan_soul_stone_damage.get();
                    }
                    attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(
                            ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "abyssal_curse"))),
                            (int) (MyGoConfig.the_leviathan_soul_stone_time.get() * 20), (int) (MyGoConfig.the_leviathan_soul_stone_level.get() - 1)));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "abyssal_curse"))))) {
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
                        if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))))) {
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

                            int finalLevel = (int) Math.min(MyGoConfig.ignis_soul_stone_max_level.get() - 1, level + 1);
                            attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(
                                    ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))),
                                    (int) (MyGoConfig.ignis_soul_stone_time.get() * 20), finalLevel));
                            if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "blazing_brand"))))) {
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
                if (MyGoUtil.hasCataclysm(attacker, EnderGuardianSoulStoneItem.class) && attacker.getPersistentData().getInt(ender_guardian_soul_stone_cooldown_time) == 0 &&
                        random.nextInt(100) <= MyGoConfig.ender_guardian_soul_stone_chance.get() * 100) {
                    attacker.getPersistentData().putInt(ender_guardian_soul_stone_cooldown_time, (int) (MyGoConfig.ender_guardian_soul_stone_cooldown.get() * 20 * 2));
                    attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(
                            ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "stun"))),
                            (int) (MyGoConfig.ender_guardian_soul_stone_time.get() * 20), 0));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "stun"))))) {
                        map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "stun")))
                                , new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                        new ResourceLocation("cataclysm", "stun"))),
                                        (int) (MyGoConfig.ender_guardian_soul_stone_time.get() * 20), 0));
                    }
                }
                if (MyGoUtil.hasCataclysm(attacker, NetheriteMonstrositySoulStoneItem.class) && random.nextInt(100) <= MyGoConfig.netherite_monstrosity_soul_stone_chance.get() * 100) {
                    if (attacker.hasEffect(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "monstrous"))))
                    ) {
                        int level = Objects.requireNonNull(attacker.getEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                                new ResourceLocation("cataclysm", "monstrous"))))).getAmplifier();
                        int finalLevel = (int) Math.min(MyGoConfig.netherite_monstrosity_soul_stone_max_lvl.get() - 1, level + 1);
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
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "bone_fracture"))))) {
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
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("cataclysm", "wetness"))))) {
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
                if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
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
                    double dhp = (attacker.getMaxHealth() - attacker.getHealth())
                            /
                            (attacker.getMaxHealth() * (1 - MyGoConfig.twilight_hydra_soul_stone_last_hp.get()));
                    //最大增伤*距离阈值的进度
                    number += MyGoConfig.twilight_hydra_soul_stone_damage.get() * dhp;
                }
                //幻影骑士
                if (MyGoUtil.hasTwilight(attacker, KnightPhantomSoulStoneItem.class) && attacked.getAttributes().hasAttribute(Attributes.ARMOR)) {
                    if (attacked.getAttributeValue(Attributes.ARMOR) >= MyGoConfig.knight_phantom_soul_stone_armor.get()) {
                        number += MyGoConfig.knight_phantom_soul_stone_damage.get();
                    }
                }
                if (MyGoUtil.hasTwilight(attacker, MinoshroomSoulStoneItem.class)) {
                    //玩家破盾
                    if (attacked instanceof Player player) {
                        player.disableShield(true);
                    }
                    overArmorPenetration += MyGoConfig.minoshroom_soul_stone.get();
                }
            }
            if (ModList.get().isLoaded("legendary_monsters")) {
                if (MyGoUtil.hasLegendaryEntity(attacker, WitheredAbominationSoulStoneItem.class)) {
                    attacked.addEffect(new MobEffectInstance(MobEffects.WITHER, (int) (MyGoConfig.withered_abomination_soul_stone_time.get() * 20), (int) (MyGoConfig.withered_abomination_soul_stone_time.get() - 1)));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(MobEffects.WITHER)) {
                        map.put(MobEffects.WITHER, new MobEffectInstance(MobEffects.WITHER, (int) (MyGoConfig.withered_abomination_soul_stone_time.get() * 20), (int) (MyGoConfig.withered_abomination_soul_stone_time.get() - 1)));
                    }
                }
                if (MyGoUtil.hasLegendaryEntity(attacker, OvergrownColossusSoulStoneItem.class)) {
                    attacked.addEffect(new MobEffectInstance(MobEffects.POISON, (int) (MyGoConfig.overgrown_colossus_soul_stone_time.get() * 20), (int) (MyGoConfig.overgrown_colossus_soul_stone_level.get() - 1)));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(MobEffects.POISON)) {
                        map.put(MobEffects.POISON, new MobEffectInstance(MobEffects.POISON, (int) (MyGoConfig.overgrown_colossus_soul_stone_time.get() * 20), (int) (MyGoConfig.overgrown_colossus_soul_stone_level.get() - 1)));
                    }
                }
                if (MyGoUtil.hasLegendaryEntity(attacker, ShulkerMimicSoulStoneItem.class) && random.nextInt(100) <= MyGoConfig.shulker_mimic_soul_stone_chance.get() * 100
                        && attacker.getPersistentData().getInt(shulker_mimic_soul_stone_time) == 0) {
                    attacker.getPersistentData().putInt(shulker_mimic_soul_stone_time, (int) (MyGoConfig.shulker_mimic_soul_stone_cooldown.get() * 20 * 2));
                    attacked.addEffect(new MobEffectInstance(MobEffects.LEVITATION, (int) (MyGoConfig.shulker_mimic_soul_stone_time.get() * 20), (int) (MyGoConfig.shulker_mimic_soul_stone_level.get() - 1)));
                    if (!EntityType.getKey(attacked.getType()).toString().equals("eeeabsmobs:immortal") && !attacked.hasEffect(MobEffects.LEVITATION)) {
                        map.put(MobEffects.LEVITATION, new MobEffectInstance(MobEffects.LEVITATION, (int) (MyGoConfig.shulker_mimic_soul_stone_time.get() * 20), (int) (MyGoConfig.shulker_mimic_soul_stone_level.get() - 1)));
                    }
                }
                if (MyGoUtil.hasLegendaryEntity(attacked, DuneSentinelSoulStoneItem.class) &&
                        (event.getSource().is(IS_EXPLOSION) || event.getSource().is(IS_PROJECTILE))) {
                    number += MyGoConfig.dune_sentinel_soul_stone_up.get();
                }
            }
            //结算部分
            float damage = (float) ((event.getAmount() * number + fixedNumber) * overNumber);
            //吸血
            double heal = 0;
            //诡厄
            if (ModList.get().isLoaded("goety")) {
                if (MyGoUtil.hasGoetyEntity(attacker, ApostleSoulStoneItem.class)) {
                    if (attacker.getHealth() > attacker.getMaxHealth() * MyGoConfig.apostle_soul_stone_half.get()) {
                        heal += attacker.getHealth() * MyGoConfig.apostle_soul_stone_heal.get() * MyGoConfig.apostle_soul_stone_up.get();
                    } else {
                        heal += attacker.getHealth() * MyGoConfig.apostle_soul_stone_heal.get();
                    }
                }
            }
            //暮色
            if (ModList.get().isLoaded("twilightforest")) {
                if (MyGoUtil.hasTwilightLich(attacker, LifedrainSoulStoneItem.class)) {
                    heal += damage * MyGoConfig.lifedrain_soul_stone_heal.get();
                    if (attacker instanceof Player player) {
                        float saturation = player.getFoodData().getSaturationLevel();
                        player.getFoodData().setSaturation((float) Math.min(saturation + damage * MyGoConfig.lifedrain_soul_stone_sat.get(), 20));
                        int food = player.getFoodData().getFoodLevel();
                        player.getFoodData().setFoodLevel((int) Math.min(food + damage * MyGoConfig.lifedrain_soul_stone_food.get(), 20));
                    }
                }
                var DamageType = MyGoDamageType.hasSource(attacker.level(), DamageTypes.FREEZE, attacker);
                if (MyGoUtil.hasTwilight(attacker, AlphaYetiSoulStoneItem.class) && attacker.getPersistentData().getInt(alpha_yeti_soul_stone_cooldown_time) == 0) {
                    attacker.getPersistentData().putInt(alpha_yeti_soul_stone_cooldown_time, (int) (MyGoConfig.alpha_yeti_soul_stone_cooldown.get() * 20 * 2));
                    var mobList = MyGoUtil.mobList((int) ((MyGoConfig.alpha_yeti_soul_stone_range.get() + 1) / 2), attacker);
                    for (Mob mobs : mobList) {
                        //！生物是随从&&生物有主&&生物
                        if ((!(mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null) && mobs != attacker)) {
                            mobs.invulnerableTime = 0;
                            mobs.hurt(DamageType, (float) (damage * MyGoConfig.alpha_yeti_soul_stone_damage.get()));
                        }
                    }
                }
                if (MyGoUtil.hasTwilight(attacker, SnowQueenSoulStoneItem.class) && attacker.getPersistentData().getInt(snow_queen_soul_stone_cooldown_time) == 0) {
                    attacker.getPersistentData().putInt(snow_queen_soul_stone_cooldown_time, (int) (MyGoConfig.snow_queen_soul_stone_cooldown.get() * 20 * 2));
                    var mobList = MyGoUtil.mobList((int) ((MyGoConfig.snow_queen_soul_stone_range.get() + 1) / 2), attacked);
                    for (Mob mobs : mobList) {
                        if ((!(mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null) && mobs != attacker)) {
                            mobs.invulnerableTime = 0;
                            mobs.hurt(DamageType, (float) (damage * MyGoConfig.snow_queen_soul_stone_damage.get()));
                        }
                    }
                }
                if (MyGoUtil.hasTwilightForest(attacker, FieryIronSoulStoneItem.class)) {
                    fireTime += MyGoConfig.fiery_iron_soul_stone_time.get() * 20;
                }
            }
            if (ModList.get().isLoaded("legendary_monsters")) {
                if (MyGoUtil.hasLegendaryEntity(attacker, LavaEaterSoulStoneItem.class)) {
                    fireTime += MyGoConfig.lava_eater_soul_stone_time.get() * 20;
                }
                if (MyGoUtil.hasLegendaryMonsters(attacker, CloudGolemSoulStoneItem.class) && attacker.getPersistentData().getInt(cloud_golem_soul_stone_time) == 0 && attacker instanceof LivingEntity) {
                    attacker.getPersistentData().putInt(cloud_golem_soul_stone_time, (int) (MyGoConfig.cloud_golem_soul_stone_cooldown.get() * 20 * 2));
                    var DamageType = MyGoDamageType.hasSource(attacker.level(), DamageTypes.LIGHTNING_BOLT, attacker);
                    attacked.invulnerableTime = 0;
                    attacked.hurt(DamageType, (float) (damage * MyGoConfig.snow_queen_soul_stone_damage.get()));
                }
                if (MyGoUtil.hasLegendaryMonsters(attacker, FrostbittenGolemSoulStoneItem.class) && attacker.getPersistentData().getInt(frostbitten_golem_soul_stone_time) == 0) {
                    var DamageType = MyGoDamageType.hasSource(attacker.level(), DamageTypes.FREEZE, attacker);
                    attacker.getPersistentData().putInt(frostbitten_golem_soul_stone_time, (int) (MyGoConfig.frostbitten_golem_soul_stone_cooldown.get() * 20 * 2));
                    var mobList = MyGoUtil.mobList((int) ((MyGoConfig.frostbitten_golem_soul_stone_range.get() + 1) / 2), attacker);
                    for (Mob mobs : mobList) {
                        if ((!(mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null) && mobs != attacker)) {
                            mobs.invulnerableTime = 0;
                            mobs.hurt(DamageType, (float) (damage * MyGoConfig.frostbitten_golem_soul_stone_damage.get()));
                            mobs.addEffect(new MobEffectInstance(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "freeze")))
                                    , (int) (MyGoConfig.frostbitten_golem_soul_stone_time.get() * 20), (int) (MyGoConfig.frostbitten_golem_soul_stone_level.get() - 1)));
                            if (!EntityType.getKey(mobs.getType()).toString().equals("eeeabsmobs:immortal") && !mobs.hasEffect(
                                    Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "freeze")))
                            )) {
                                map.put(
                                        Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "freeze")))
                                        , new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "freeze"))), (int) (MyGoConfig.frostbitten_golem_soul_stone_time.get() * 20), (int) (MyGoConfig.frostbitten_golem_soul_stone_level.get() - 1)));
                            }
                        }
                    }
                }
            }
            //可着火且目标时间小于最大着火时间
            if (fireTime > 0 && attacked.getRemainingFireTicks() < fireTime) {
                attacked.setRemainingFireTicks((int) fireTime);
            }
            //暮色炽铁烤火结算
            if (ModList.get().isLoaded("twilightforest")) {
                if (MyGoUtil.hasTwilightForest(attacker, FieryIronSoulStoneItem.class) && attacked.getRemainingFireTicks() > 0) {
                    heal += (attacked.getRemainingFireTicks() * MyGoConfig.fiery_iron_soul_stone_heal.get()) / 20;
                }
            }
            //莱特兰结算部分
            if (ModList.get().isLoaded("l2hostility")) {
                if (MyGoUtil.hasL2Hostility(attacker, DestroyHostilitySoulStoneItem.class) && attacked != null) {
                    List<MobEffect> toRemove = new ArrayList<>();
                    for (MobEffectInstance effect : attacked.getActiveEffects()) {
                        if (effect.getEffect().getCategory().equals(MobEffectCategory.BENEFICIAL) && effect.getEffect() != Objects.requireNonNull(
                                ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("l2hostility", "antibuild")))) {
                            toRemove.add(effect.getEffect());
                        }
                    }
                    for (MobEffect effect : toRemove) {
                        attacked.removeEffect(effect);
                    }
                    if(attacker.getPersistentData().getInt(destroy_hostility_soul_stone) == 0) {
                        attacker.getPersistentData().putInt(destroy_hostility_soul_stone, (int) (0.05 * 20 * 2));
                        //连锁
                        var mobList = MyGoUtil.mobList((int) ((MyGoConfig.destroy_hostility_soul_stone_range.get() + 1) / 2), attacked);
                        for (Mob mobs : mobList) {
                            if (mobs != null && mobs != attacked && mobs != attacker) {
                                //如果这些范围里有人和伤害源一样
                                if (mobs.getType().toString() == attacked.getType().toString() && mobs != attacked && mobs != attacker) {
                                    mobs.invulnerableTime = 0;
                                    mobs.hurt(MyGoDamageType.hasSource(attacker.level(), MyGoDamageType.TRUEDAMAGE, attacker), (float) (damage * MyGoConfig.destroy_hostility_soul_stone_damage.get()));
                                }
                            }
                        }
                    }
                }
            }
            //冰火附伤
            if (ModList.get().isLoaded("iceandfire")) {
                if (attacker.getPersistentData().getInt(iaf_dragon_steel_time) == 0) {
                    attacker.getPersistentData().putInt(iaf_dragon_steel_time, (int) (0.05 * 20 * 2));
                    //雷龙血 附伤
                    if (MyGoUtil.hasIAFDragon(attacker, LightningDragonBloodSoulStoneItem.class)) {
                        attacked.invulnerableTime = 0;
                        attacked.hurt(MyGoDamageType.hasSource(attacker.level(), DamageTypes.LIGHTNING_BOLT, attacker), (float) (damage * MyGoConfig.lightning_dragon_blood_soul_stone_damage.get()));
                    }
                    //三龙钢
                    if (MyGoUtil.hasIAFDragon(attacker, FireDragonSteelSoulStoneItem.class)) {
                        attacked.invulnerableTime = 0;
                        attacked.hurt(MyGoDamageType.hasSource(attacker.level(), DamageTypes.LAVA, attacker), (float) (damage * MyGoConfig.fire_dragon_steel_soul_stone_damage.get()));
                    }
                    if (MyGoUtil.hasIAFDragon(attacker, IceDragonSteelSoulStoneItem.class)) {
                        attacked.invulnerableTime = 0;
                        attacked.hurt(MyGoDamageType.hasSource(attacker.level(), DamageTypes.FREEZE, attacker), (float) (damage * MyGoConfig.ice_dragon_steel_soul_stone_damage.get()));
                    }
                    if (MyGoUtil.hasIAFDragon(attacker, LightningDragonSteelSoulStoneItem.class)) {
                        attacked.invulnerableTime = 0;
                        attacked.hurt(MyGoDamageType.hasSource(attacker.level(), DamageTypes.LIGHTNING_BOLT, attacker), (float) (damage * MyGoConfig.lightning_dragon_steel_soul_stone_damage.get()));
                    }
                }
            }
            if (heal > 0) {
                attacker.heal((float) heal);
            }
            //穿甲
            if (fixedArmorPenetration > 0) {
                double finalFixedArmorPenetration = fixedArmorPenetration;
                Optional.of(attacked)
                        .map(LivingEntity::getAttributes)
                        .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                        .map(manager -> manager.getInstance(Attributes.ARMOR))
                        .filter(instance -> instance.getModifier(uuid1) == null)
                        .ifPresent(instance -> instance.addTransientModifier(
                                new AttributeModifier(uuid1, "mygo_fixed_armor_penetration", finalFixedArmorPenetration * -1, AttributeModifier.Operation.ADDITION)));
            }
            if (overArmorPenetration > 0) {
                double finalOverArmorPenetration = overArmorPenetration;
                Optional.of(attacked)
                        .map(LivingEntity::getAttributes)
                        .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                        .map(manager -> manager.getInstance(Attributes.ARMOR))
                        .filter(instance -> instance.getModifier(uuid2) == null)
                        .ifPresent(instance -> instance.addTransientModifier(
                                new AttributeModifier(uuid2, "mygo_over_armor_penetration", finalOverArmorPenetration * -1, AttributeModifier.Operation.MULTIPLY_TOTAL)));
            }
            if (orderOwnable) {
                List<Mob> mobList = MyGoUtil.mobList(22, attacker);
                for (Mob mobs : mobList) {
                    //周围有随从、随从的主人是玩家、可以攻击目标————集体转火
                    if (mobs instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() instanceof Player player && mobs.canAttack(attacked)) {
                        if (!mobs.level().isClientSide()) {
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

    //适应代码使用
    // 内部数据类，记录内存（顺序），和适应次数
    private static class Data {
        public final ArrayList<String> memory = new ArrayList<>();
        public final HashMap<String, Integer> adaption = new HashMap<>();
    }
}