package com.inolia_zaicek.mine_fargo.Event.InoIntegrationPack;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class InoIntegrationPackEvent {
    private static final UUID uuid1 = UUID.fromString("B28A3CC9-F4ED-1CB7-83CD-34C33D026D0F");
    private static final UUID uuid2 = UUID.fromString("5C1171EF-E71B-A771-E160-B1327399D8AC");

    @SubscribeEvent
    public static void addMode(net.minecraftforge.event.entity.EntityJoinLevelEvent event) {
        if (MyGoConfig.InoIntegrationPack.get()) {
            Entity entity = event.getEntity();
            double level = 0;
            if (entity instanceof LivingEntity livingEntity) {
                //1X
                if (EntityType.getKey(livingEntity.getType()).toString().equals("mowziesmobs:frostmaw")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("mowziesmobs:ferrous_wroughtnaut")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("mowziesmobs:sculptor")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("mowziesmobs:umvuthi")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:aero_guardian")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:amethyst_crab")) {
                    level = MyGoConfig.level_1_hp.get();
                }
                //史莱姆王比较特殊，自身血量需要x50先
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:king_slime")) {
                    Optional.of(livingEntity).map(LivingEntity::getAttributes)
                            .filter(manager -> manager.hasAttribute(Attributes.MAX_HEALTH))
                            .map(manager -> manager.getInstance(Attributes.MAX_HEALTH))
                            .filter(instance -> instance.getModifier(uuid1) == null)
                            .ifPresent(instance -> instance.addTransientModifier(
                                    new AttributeModifier(uuid2, "mygo_inolia_a", 49, AttributeModifier.Operation.MULTIPLY_BASE)));
                    level = MyGoConfig.level_1_hp.get();
                }
                //2x
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:skeletosaurus")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("iceandfire:cyclops")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:frostbitten_golem")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("iceandfire:gorgon")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:ancient_guardian")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("iceandfire:hydra")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("call_of_drowner:tide_raiser")) {
                    level = MyGoConfig.level_2_hp.get();
                }//3x
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:warped_fungussus")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goety:brood_mother")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:dune_sentinel")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:overgrown_colossus")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("bosses_of_mass_destruction:gauntlet")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goety:crone")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:the_plauge_bringer")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:the_plauge_bringerp_2")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:cthulhu_eye")
                ) {
                    level = MyGoConfig.level_3_hp.get();
                }
                //4x
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("call_of_drowner:drowner")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("call_of_drowner:deepwater_piranha")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:withered_abomination")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goety:wight")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:posessed_paladin")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:lava_eater")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:terrible_ten")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:puny_juggernaut")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_boot")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_heavyman")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_leaper")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_fool")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknightfist")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_longarm")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_paladin")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_servant")
                ) {
                    level = MyGoConfig.level_4_hp.get();
                }
                //5x——凋灵+幽灵+焦骸死灵法师、双领主、利刃、蜂王
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("goety:wither_necromancer")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goetyawaken:wraith_necromancer")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goetyawaken:parched_necromancer")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goety:skull_lord")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goety:bone_lord")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:pursuer")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:queen_bee")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:visual_neuron")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:eater_of_worlds_segment")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:brain_fake")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("arcalis_bosses:jack")
                ) {
                    level = MyGoConfig.level_5_hp.get();
                }
                //6x
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("minecraft:elder_guardian")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:wadjet")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:kobolediator")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:brain_of_cthulhu")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:eater_of_world")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:the_leviathan")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("alexscaves:hullbreaker")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("bosses_of_mass_destruction:void_blossom")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("arcalis_bosses:the_deathbringer")
                ) {
                    level = MyGoConfig.level_6_hp.get();
                }
                //7x
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:ancient_remnant")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("alexscaves:forsaken")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goety:minister")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("alexscaves:luxtructosaurus")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("minecraft:wither")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("bosses_of_mass_destruction:lich")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:cryomancer")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:pyromancer")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:archevoker")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:apothecarist")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:priest")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("traveloptics:aqua_grandmaster")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("traveloptics:aquamancer")
                ) {
                    level = MyGoConfig.level_7_hp.get();
                }
                //8x
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("ars_nouveau:wilden_boss")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:cloud_golem")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("iceandfire:fire_dragon")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("iceandfire:ice_dragon")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("iceandfire:lightning_dragon")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("minecraft:warden")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:dead_king")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:dead_king_corpse")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("arcalis_bosses:captain_deadbone")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goety:redstone_cube")
                ) {
                    level = MyGoConfig.level_8_hp.get();
                }
                //9x
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("goety:hostile_redstone_golem")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:lichspell")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:lich_phase_2")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("meetyourfight:swampjaw")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("meetyourfight:bellringer")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("meetyourfight:dame_fortuna")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("meetyourfight:rosalyne")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:skeletron")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:skeletron_hand")
                ) {
                    level = MyGoConfig.level_9_hp.get();
                }
                //10x
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("minecraft:ender_dragon")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:shulker_mimic")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goety:endersent")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goety:hostile_redstone_monstrosity")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:ender_golem")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("bosses_of_mass_destruction:obsidilith")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("traveloptics:enraged_dead_king")
                ) {
                    level = MyGoConfig.level_10_hp.get();
                }
                //11x
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:butcher")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:blud")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:curse")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:kelvin")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:prowler")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:walking_bed")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:wistiver")
                ) {
                    level = MyGoConfig.level_11_hp.get();
                }
                //12x
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:ignis")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:maledictus")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:netherite_monstrosity")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:the_harbinger")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:scylla")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:ender_guardian")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:faded_king")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:faded_kingp_2")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:shield_guard")
                ) {
                    level = MyGoConfig.level_12_hp.get();
                }
                //13x
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:patriot")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:rosmontis")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:rosmontis_living_installation")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:frost_nova")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:turret")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goety:vizier")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:vessel_of_calamity")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:vessel_shield")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:vessel_spawn")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:vessel_sword")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("binah:binah_v_2")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:fire_boss")
                ) {
                    level = MyGoConfig.level_13_hp.get();
                }
                //14x
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:gun_knight_patriot")
                        || (EntityType.getKey(livingEntity.getType()).toString().equals("goety:apostle")
                        && !livingEntity.level().dimension().equals(livingEntity.level().NETHER))
                        || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:dungeon_guardian")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goety:ender_keeper")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:radiance_guardian")
                ) {
                    level = MyGoConfig.level_14_hp.get();
                }
                //15x
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("traveloptics:the_nightwarden")) {
                    level = MyGoConfig.level_15_hp.get();
                }
                //16
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("goety:apostle")
                        && livingEntity.level().dimension().equals(livingEntity.level().NETHER)) {
                    level = MyGoConfig.level_16_hp.get();
                }
                //17
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("goetyawaken:hostile_mushroom_monstrosity")) {
                    level = MyGoConfig.level_17_hp.get();
                }
                //18
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("goetyawaken:nameless_one")) {
                    level = MyGoConfig.level_18_hp.get();
                }
                //19
                else if (EntityType.getKey(livingEntity.getType()).toString().equals("goety_revelation:summon_apollyon")
                        && !livingEntity.level().dimension().equals(livingEntity.level().NETHER)) {
                    level = MyGoConfig.level_19_hp.get();
                }
                //20
                else if ((EntityType.getKey(livingEntity.getType()).toString().equals("goety_revelation:summon_apollyon")
                        && livingEntity.level().dimension().equals(livingEntity.level().NETHER))
                        || EntityType.getKey(livingEntity.getType()).toString().equals("fantasy_ending:ultimate_order_manager")) {
                    level = MyGoConfig.level_20_hp.get();
                }

                if (level > 1) {
                    double finalLevel = level;
                    Optional.of(livingEntity).map(LivingEntity::getAttributes)
                            .filter(manager -> manager.hasAttribute(Attributes.MAX_HEALTH))
                            .map(manager -> manager.getInstance(Attributes.MAX_HEALTH))
                            .filter(instance -> instance.getModifier(uuid1) == null)
                            .ifPresent(instance -> instance.addTransientModifier(
                                    new AttributeModifier(uuid1, "mygo_inolia_b", (finalLevel - 1), AttributeModifier.Operation.MULTIPLY_BASE)));
                    Optional.of(livingEntity).map(LivingEntity::getAttributes)
                            .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                            .map(manager -> manager.getInstance(Attributes.ARMOR))
                            .filter(instance -> instance.getModifier(uuid1) == null)
                            .ifPresent(instance -> instance.addTransientModifier(
                                    new AttributeModifier(uuid1, "mygo_inolia_c", finalLevel, AttributeModifier.Operation.ADDITION)));
                    Optional.of(livingEntity).map(LivingEntity::getAttributes)
                            .filter(manager -> manager.hasAttribute(Attributes.ARMOR_TOUGHNESS))
                            .map(manager -> manager.getInstance(Attributes.ARMOR_TOUGHNESS))
                            .filter(instance -> instance.getModifier(uuid1) == null)
                            .ifPresent(instance -> instance.addTransientModifier(
                                    new AttributeModifier(uuid1, "mygo_inolia_d", finalLevel / 1.5, AttributeModifier.Operation.ADDITION)));
                    livingEntity.setHealth(livingEntity.getMaxHealth());
                    livingEntity.heal(livingEntity.getMaxHealth());
                }
            } else {
                level = 0;
            }
        }
    }

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        if (MyGoConfig.InoIntegrationPack.get()) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != null) {
                float level = 0;
                //1X
                if (EntityType.getKey(attacker.getType()).toString().equals("mowziesmobs:frostmaw")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:aero_guardian")
                        || EntityType.getKey(attacker.getType()).toString().equals("mowziesmobs:ferrous_wroughtnaut")
                        || EntityType.getKey(attacker.getType()).toString().equals("mowziesmobs:sculptor")
                        || EntityType.getKey(attacker.getType()).toString().equals("mowziesmobs:umvuthi")
                        || EntityType.getKey(attacker.getType()).toString().equals("cataclysm:amethyst_crab")
                        || EntityType.getKey(attacker.getType()).toString().equals("terra_entity:king_slime")) {
                    level = 0.15F;
                }
                //2x
                else if (EntityType.getKey(attacker.getType()).toString().equals("legendary_monsters:skeletosaurus")
                        || EntityType.getKey(attacker.getType()).toString().equals("iceandfire:cyclops")
                        || EntityType.getKey(attacker.getType()).toString().equals("legendary_monsters:frostbitten_golem")
                        || EntityType.getKey(attacker.getType()).toString().equals("iceandfire:gorgon")
                        || EntityType.getKey(attacker.getType()).toString().equals("legendary_monsters:lava_eater")
                        || EntityType.getKey(attacker.getType()).toString().equals("legendary_monsters:ancient_guardian")
                        || EntityType.getKey(attacker.getType()).toString().equals("iceandfire:hydra")
                        || EntityType.getKey(attacker.getType()).toString().equals("call_of_drowner:tide_raiser")) {
                    level = 0.2F;
                }//3x
                else if (EntityType.getKey(attacker.getType()).toString().equals("legendary_monsters:warped_fungussus")
                        || EntityType.getKey(attacker.getType()).toString().equals("goety:brood_mother")
                        || EntityType.getKey(attacker.getType()).toString().equals("legendary_monsters:dune_sentinel")
                        || EntityType.getKey(attacker.getType()).toString().equals("legendary_monsters:overgrown_colossus")
                        || EntityType.getKey(attacker.getType()).toString().equals("bosses_of_mass_destruction:gauntlet")
                        || EntityType.getKey(attacker.getType()).toString().equals("goety:crone")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:the_plauge_bringer")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:the_plauge_bringerp_2")
                        || EntityType.getKey(attacker.getType()).toString().equals("terra_entity:cthulhu_eye")
                ) {
                    level = 0.25F;
                }
                //4x
                else if (EntityType.getKey(attacker.getType()).toString().equals("call_of_drowner:drowner")
                        || EntityType.getKey(attacker.getType()).toString().equals("call_of_drowner:deepwater_piranha")
                        || EntityType.getKey(attacker.getType()).toString().equals("legendary_monsters:withered_abomination")
                        || EntityType.getKey(attacker.getType()).toString().equals("goety:wight")
                        || EntityType.getKey(attacker.getType()).toString().equals("legendary_monsters:posessed_paladin")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:terrible_ten")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:puny_juggernaut")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:blocknight_boot")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:blocknight_heavyman")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:blocknight_leaper")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:blocknight_fool")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:blocknightfist")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:blocknight_longarm")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:blocknight_paladin")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:blocknight_servant")
                ) {
                    level = 0.4F;
                }
                //5x——凋灵+幽灵+焦骸死灵法师、双领主、利刃、蜂王
                else if (EntityType.getKey(attacker.getType()).toString().equals("goety:wither_necromancer")
                        || EntityType.getKey(attacker.getType()).toString().equals("goetyawaken:wraith_necromancer")
                        || EntityType.getKey(attacker.getType()).toString().equals("goetyawaken:parched_necromancer")
                        || EntityType.getKey(attacker.getType()).toString().equals("goety:skull_lord")
                        || EntityType.getKey(attacker.getType()).toString().equals("goety:bone_lord")
                        || EntityType.getKey(attacker.getType()).toString().equals("torchesbecomesunlight:pursuer")
                        || EntityType.getKey(attacker.getType()).toString().equals("terra_entity:queen_bee")
                        || EntityType.getKey(attacker.getType()).toString().equals("terra_entity:visual_neuron")
                        || EntityType.getKey(attacker.getType()).toString().equals("terra_entity:eater_of_worlds_segment")
                        || EntityType.getKey(attacker.getType()).toString().equals("terra_entity:brain_fake")
                        || EntityType.getKey(attacker.getType()).toString().equals("arcalis_bosses:jack")
                ) {
                    level = 0.5F;
                }
                //6x
                else if (EntityType.getKey(attacker.getType()).toString().equals("minecraft:elder_guardian")
                        || EntityType.getKey(attacker.getType()).toString().equals("terra_entity:brain_of_cthulhu")
                        || EntityType.getKey(attacker.getType()).toString().equals("terra_entity:eater_of_world")
                        || EntityType.getKey(attacker.getType()).toString().equals("cataclysm:the_leviathan")
                        || EntityType.getKey(attacker.getType()).toString().equals("cataclysm:wadjet")
                        || EntityType.getKey(attacker.getType()).toString().equals("cataclysm:kobolediator")
                        || EntityType.getKey(attacker.getType()).toString().equals("alexscaves:hullbreaker")
                        || EntityType.getKey(attacker.getType()).toString().equals("bosses_of_mass_destruction:void_blossom")
                        || EntityType.getKey(attacker.getType()).toString().equals("arcalis_bosses:the_deathbringer")
                ) {
                    level = 0.6F;
                }
                //7x
                else if (EntityType.getKey(attacker.getType()).toString().equals("cataclysm:ancient_remnant")
                        || EntityType.getKey(attacker.getType()).toString().equals("alexscaves:forsaken")
                        || EntityType.getKey(attacker.getType()).toString().equals("goety:minister")
                        || EntityType.getKey(attacker.getType()).toString().equals("alexscaves:luxtructosaurus")
                        || EntityType.getKey(attacker.getType()).toString().equals("minecraft:wither")
                        || EntityType.getKey(attacker.getType()).toString().equals("bosses_of_mass_destruction:lich")
                        || EntityType.getKey(attacker.getType()).toString().equals("irons_spellbooks:cryomancer")
                        || EntityType.getKey(attacker.getType()).toString().equals("irons_spellbooks:pyromancer")
                        || EntityType.getKey(attacker.getType()).toString().equals("irons_spellbooks:archevoker")
                        || EntityType.getKey(attacker.getType()).toString().equals("irons_spellbooks:apothecarist")
                        || EntityType.getKey(attacker.getType()).toString().equals("irons_spellbooks:priest")
                        || EntityType.getKey(attacker.getType()).toString().equals("traveloptics:aqua_grandmaster")
                        || EntityType.getKey(attacker.getType()).toString().equals("traveloptics:aquamancer")
                ) {
                    level = 0.8F;
                }
                //8x
                else if (EntityType.getKey(attacker.getType()).toString().equals("ars_nouveau:wilden_boss")
                        || EntityType.getKey(attacker.getType()).toString().equals("legendary_monsters:cloud_golem")
                        || EntityType.getKey(attacker.getType()).toString().equals("iceandfire:fire_dragon")
                        || EntityType.getKey(attacker.getType()).toString().equals("iceandfire:ice_dragon")
                        || EntityType.getKey(attacker.getType()).toString().equals("iceandfire:lightning_dragon")
                        || EntityType.getKey(attacker.getType()).toString().equals("minecraft:warden")
                        || EntityType.getKey(attacker.getType()).toString().equals("irons_spellbooks:dead_king")
                        || EntityType.getKey(attacker.getType()).toString().equals("irons_spellbooks:dead_king_corpse")
                        || EntityType.getKey(attacker.getType()).toString().equals("arcalis_bosses:captain_deadbone")
                        || EntityType.getKey(attacker.getType()).toString().equals("goety:redstone_cube")
                ) {
                    level = 0.9F;
                }
                //9x
                else if (EntityType.getKey(attacker.getType()).toString().equals("goety:hostile_redstone_golem")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:lichspell")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:lich_phase_2")
                        || EntityType.getKey(attacker.getType()).toString().equals("meetyourfight:swampjaw")
                        || EntityType.getKey(attacker.getType()).toString().equals("meetyourfight:bellringer")
                        || EntityType.getKey(attacker.getType()).toString().equals("meetyourfight:dame_fortuna")
                        || EntityType.getKey(attacker.getType()).toString().equals("meetyourfight:rosalyne")
                        || EntityType.getKey(attacker.getType()).toString().equals("terra_entity:skeletron")
                        || EntityType.getKey(attacker.getType()).toString().equals("terra_entity:skeletron_hand")
                ) {
                    level = 1.0F;
                }
                //10x
                else if (EntityType.getKey(attacker.getType()).toString().equals("minecraft:ender_dragon")
                        || EntityType.getKey(attacker.getType()).toString().equals("legendary_monsters:shulker_mimic")
                        || EntityType.getKey(attacker.getType()).toString().equals("goety:endersent")
                        || EntityType.getKey(attacker.getType()).toString().equals("goety:hostile_redstone_monstrosity")
                        || EntityType.getKey(attacker.getType()).toString().equals("cataclysm:ender_golem")
                        || EntityType.getKey(attacker.getType()).toString().equals("bosses_of_mass_destruction:obsidilith")
                        || EntityType.getKey(attacker.getType()).toString().equals("traveloptics:enraged_dead_king")
                ) {
                    level = 1.3F;
                }
                //11x
                else if (EntityType.getKey(attacker.getType()).toString().equals("sons_of_sins:butcher")
                        || EntityType.getKey(attacker.getType()).toString().equals("sons_of_sins:blud")
                        || EntityType.getKey(attacker.getType()).toString().equals("sons_of_sins:curse")
                        || EntityType.getKey(attacker.getType()).toString().equals("sons_of_sins:kelvin")
                        || EntityType.getKey(attacker.getType()).toString().equals("sons_of_sins:prowler")
                        || EntityType.getKey(attacker.getType()).toString().equals("sons_of_sins:walking_bed")
                        || EntityType.getKey(attacker.getType()).toString().equals("sons_of_sins:wistiver")
                ) {
                    level = 1.4F;
                }
                //12x
                else if (EntityType.getKey(attacker.getType()).toString().equals("cataclysm:ignis")
                        || EntityType.getKey(attacker.getType()).toString().equals("cataclysm:maledictus")
                        || EntityType.getKey(attacker.getType()).toString().equals("cataclysm:netherite_monstrosity")
                        || EntityType.getKey(attacker.getType()).toString().equals("cataclysm:the_harbinger")
                        || EntityType.getKey(attacker.getType()).toString().equals("cataclysm:scylla")
                        || EntityType.getKey(attacker.getType()).toString().equals("cataclysm:ender_guardian")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:faded_king")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:faded_kingp_2")
                        || EntityType.getKey(attacker.getType()).toString().equals("torchesbecomesunlight:shield_guard")
                ) {
                    level = 1.5F;
                }
                //13x
                else if (EntityType.getKey(attacker.getType()).toString().equals("torchesbecomesunlight:patriot")
                        || EntityType.getKey(attacker.getType()).toString().equals("torchesbecomesunlight:rosmontis")
                        || EntityType.getKey(attacker.getType()).toString().equals("torchesbecomesunlight:rosmontis_living_installation")
                        || EntityType.getKey(attacker.getType()).toString().equals("torchesbecomesunlight:frost_nova")
                        || EntityType.getKey(attacker.getType()).toString().equals("torchesbecomesunlight:turret")
                        || EntityType.getKey(attacker.getType()).toString().equals("goety:vizier")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:vessel_of_calamity")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:vessel_shield")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:vessel_spawn")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:vessel_sword")
                        || EntityType.getKey(attacker.getType()).toString().equals("binah:binah_v_2")
                        || EntityType.getKey(attacker.getType()).toString().equals("irons_spellbooks:fire_boss")
                ) {
                    level = 1.9F;
                }
                //14x
                else if (EntityType.getKey(attacker.getType()).toString().equals("torchesbecomesunlight:gun_knight_patriot")
                        || (EntityType.getKey(attacker.getType()).toString().equals("goety:apostle")
                        && !attacked.level().dimension().equals(attacked.level().NETHER))
                        || EntityType.getKey(attacker.getType()).toString().equals("terra_entity:dungeon_guardian")
                        || EntityType.getKey(attacker.getType()).toString().equals("goety:ender_keeper")
                        || EntityType.getKey(attacker.getType()).toString().equals("faded_conquest_2:radiance_guardian")
                ) {
                    level = 2.0F;
                }
                //15x
                else if (EntityType.getKey(attacker.getType()).toString().equals("traveloptics:the_nightwarden")) {
                    level = 2.1F;
                }
                //16
                else if (EntityType.getKey(attacker.getType()).toString().equals("goety:apostle")
                        && attacked.level().dimension().equals(attacked.level().NETHER)) {
                    level = 2.5F;
                }
                //17
                else if (EntityType.getKey(attacker.getType()).toString().equals("goetyawaken:hostile_mushroom_monstrosity")) {
                    level = 2.75F;
                }
                //18
                else if (EntityType.getKey(attacker.getType()).toString().equals("goetyawaken:nameless_one")) {
                    level = 3F;
                }
                //19
                else if (EntityType.getKey(attacker.getType()).toString().equals("goety_revelation:summon_apollyon")
                        && !attacked.level().dimension().equals(attacked.level().NETHER)) {
                    level = 3.25F;
                }
                //20
                else if ((EntityType.getKey(attacker.getType()).toString().equals("goety_revelation:summon_apollyon")
                        && attacked.level().dimension().equals(attacked.level().NETHER))
                        || EntityType.getKey(attacker.getType()).toString().equals("fantasy_ending:ultimate_order_manager")) {
                    level = 3.5F;
                }

                if (level > 1) {
                    event.setAmount(event.getAmount() * (1 + level));
                }
                //飞行状态的玩家发起攻击
                if (attacker instanceof Player player && player.getAbilities().flying) {
                    event.setAmount((float) (event.getAmount() * MyGoConfig.flying_damage.get()));
                }
            }
        }
    }
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        if (!event.getEntity().isAlive())
            return;
        LivingEntity livingEntity = event.getEntity();

        if (MyGoConfig.InoIntegrationPack.get() && livingEntity.level().getGameTime() % 20L == 0) {
            if (EntityType.getKey(livingEntity.getType()).toString().equals("mowziesmobs:frostmaw")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("mowziesmobs:ferrous_wroughtnaut")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("mowziesmobs:sculptor")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("mowziesmobs:umvuthi")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:aero_guardian")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:amethyst_crab")
                    ||EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:king_slime")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:skeletosaurus")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("iceandfire:cyclops")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:frostbitten_golem")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("iceandfire:gorgon")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:ancient_guardian")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("call_of_drowner:tide_raiser")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:warped_fungussus")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goety:brood_mother")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:dune_sentinel")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:overgrown_colossus")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("bosses_of_mass_destruction:gauntlet")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("goety:crone")
                        || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:the_plauge_bringer")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:the_plauge_bringerp_2")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:cthulhu_eye")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("call_of_drowner:drowner")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("call_of_drowner:deepwater_piranha")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:withered_abomination")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("goety:wight")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:posessed_paladin")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:lava_eater")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:terrible_ten")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:puny_juggernaut")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_boot")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_heavyman")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_leaper")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_fool")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknightfist")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_longarm")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_paladin")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:blocknight_servant")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("goety:wither_necromancer")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("goetyawaken:wraith_necromancer")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("goetyawaken:parched_necromancer")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("goety:skull_lord")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("goety:bone_lord")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:pursuer")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:queen_bee")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:visual_neuron")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:eater_of_worlds_segment")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:brain_fake")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("arcalis_bosses:jack")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("minecraft:elder_guardian")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:brain_of_cthulhu")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:eater_of_world")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:the_leviathan")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:wadjet")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:kobolediator")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("alexscaves:hullbreaker")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("bosses_of_mass_destruction:void_blossom")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("arcalis_bosses:the_deathbringer")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:ancient_remnant")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("alexscaves:forsaken")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("goety:minister")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("alexscaves:luxtructosaurus")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("minecraft:wither")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("bosses_of_mass_destruction:lich")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:cryomancer")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:pyromancer")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:archevoker")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:apothecarist")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:priest")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("traveloptics:aqua_grandmaster")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("traveloptics:aquamancer")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("ars_nouveau:wilden_boss")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:cloud_golem")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("iceandfire:fire_dragon")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("iceandfire:ice_dragon")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("iceandfire:lightning_dragon")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("minecraft:warden")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:dead_king")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:dead_king_corpse")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("arcalis_bosses:captain_deadbone")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("goety:redstone_cube")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:lichspell")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:lich_phase_2")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("goety:hostile_redstone_golem")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:faded_king")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:faded_kingp_2")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:shield_guard")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("meetyourfight:swampjaw")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("meetyourfight:bellringer")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("meetyourfight:dame_fortuna")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("meetyourfight:rosalyne")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:skeletron")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:skeletron_hand")

                    || EntityType.getKey(livingEntity.getType()).toString().equals("legendary_monsters:shulker_mimic")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("goety:endersent")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("goety:hostile_redstone_monstrosity")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:ender_golem")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("bosses_of_mass_destruction:obsidilith")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("traveloptics:enraged_dead_king")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:butcher")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:blud")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:curse")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:kelvin")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:prowler")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:walking_bed")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:wistiver")

                    || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:maledictus")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:netherite_monstrosity")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:the_harbinger")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:scylla")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:ender_guardian")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:patriot")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:rosmontis")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:rosmontis_living_installation")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:frost_nova")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:turret")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("goety:vizier")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:vessel_of_calamity")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:vessel_shield")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:vessel_spawn")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:vessel_sword")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("binah:binah_v_2")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:fire_boss")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("torchesbecomesunlight:gun_knight_patriot")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:dungeon_guardian")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("goety:ender_keeper")
                    || EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:radiance_guardian")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("traveloptics:the_nightwarden")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("goetyawaken:hostile_mushroom_monstrosity")
            ||EntityType.getKey(livingEntity.getType()).toString().equals("goetyawaken:nameless_one")) {
                livingEntity.heal((float) (livingEntity.getHealth()*MyGoConfig.boss_time_heal.get()));
            }
            if( EntityType.getKey(livingEntity.getType()).toString().equals("minecraft:ender_dragon") ){
                livingEntity.heal((float) (livingEntity.getHealth()*MyGoConfig.boss_time_heal.get()*0.5));
            }
            if( EntityType.getKey(livingEntity.getType()).toString().equals("cataclysm:ignis") ){
                livingEntity.heal((float) (livingEntity.getHealth()*MyGoConfig.boss_time_heal.get()*0.25));
            }
        }
    }
    @SubscribeEvent
    public static void drop(LivingDropsEvent event) {
        if (MyGoConfig.InoIntegrationPack.get()) {
            if (!(event.getEntity() instanceof Player)) {
                Random random = new Random();
                Level level = event.getEntity().level();
                ///货币部分
                //所有生物0.25概率掉落1-3铜币
                if (ModList.get().isLoaded("lightmanscurrency") && random.nextInt(100) <= MyGoConfig.coin_copper_drop_chance.get() * 100) {
                    for (int i = 0; i < random.nextInt((int) (MyGoConfig.coin_copper_drop_number.get() * 1)) + 1; i++) {
                        ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                                Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("lightmanscurrency", "coin_copper")))
                                        .getDefaultInstance());
                        itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                                (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                        level.addFreshEntity(itementity);
                    }
                }
                if (ModList.get().isLoaded("lightmanscurrency") && MyGoUtil.isBossEntity(event.getEntity().getType())) {
                    for (int i = 0; i < random.nextInt((int) (MyGoConfig.coin_iron_drop_number.get() * 1)) + 1; i++) {
                        ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                                Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("lightmanscurrency", "coin_iron")))
                                        .getDefaultInstance());
                        itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                                (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                        level.addFreshEntity(itementity);
                    }
                }
                //刻蚀板
                if (ModList.get().isLoaded("celestial_artifacts")) {
                    //史莱姆王——欲望
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("terra_entity:king_slime")) {
                        if (random.nextInt(100) <= MyGoConfig.desire_etching.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                                    Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("celestial_artifacts", "desire_etching")))
                                            .getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                                    (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        } else {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                                    Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("celestial_artifacts", "nebula_cube")))
                                            .getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                                    (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //蜂王——混沌
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("terra_entity:queen_bee")) {
                        if (random.nextInt(100) <= MyGoConfig.honey_comb.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                                    Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("confluence", "honey_comb")))
                                            .getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                                    (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                        if (random.nextInt(100) <= MyGoConfig.chaotic_etching.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                                    Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("celestial_artifacts", "chaotic_etching")))
                                            .getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                                    (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        } else {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                                    Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("celestial_artifacts", "nebula_cube")))
                                            .getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                                    (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //骷髅王——混沌
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("terra_entity:skeletron")) {
                        if (random.nextInt(100) <= MyGoConfig.end_etching.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                                    Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("celestial_artifacts", "end_etching")))
                                            .getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                                    (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        } else {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(),
                                    Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("celestial_artifacts", "nebula_cube")))
                                            .getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                                    (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                }
                //泰拉饰品额外掉落
                if (ModList.get().isLoaded("confluence")) {
                    //通臂大师1——狂爪手套【':'】泰坦手套【''】
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("mowziesmobs:sculptor")) {
                        if (random.nextInt(100) <= MyGoConfig.feral_claws.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "confluence", "feral_claws"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                        if (random.nextInt(100) <= MyGoConfig.titan_glove.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "confluence", "titan_glove"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //骷髅蜥龙2——战士【'confluence:warrior_emblem'】legendary_monsters:skeletosaurus
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:skeletosaurus")) {
                        if (random.nextInt(100) <= MyGoConfig.warrior_emblem.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "confluence", "warrior_emblem"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //霜冻傀儡2——巫师【'confluence:sorcerer_emblem'】
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:frostbitten_golem")) {
                        if (random.nextInt(100) <= MyGoConfig.sorcerer_emblem.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "confluence", "sorcerer_emblem"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //归一心元石
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("cataclysm:the_leviathan")) {
                        if (random.nextInt(100) <= MyGoConfig.the_community.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "wizard_terra_cuiros", "the_community"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //唤潮者2——射手【'confluence:'】
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("call_of_drowner:tide_raiser")) {
                        if (random.nextInt(100) <= MyGoConfig.ranger_emblem.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "confluence", "ranger_emblem"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //荒古守卫者2——召唤师【'wizard_terra_cuiros:summoner_emblem'】
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:ancient_guardian")) {
                        if (random.nextInt(100) <= MyGoConfig.summoner_emblem.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "confluence", "summoner_emblem"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //菌怪——宝藏磁石
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:warped_fungussus")) {
                        if (random.nextInt(100) <= MyGoConfig.treasure_magnet.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "confluence", "treasure_magnet"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //暗夜巫妖
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("bosses_of_mass_destruction:lich")) {
                        if (random.nextInt(100) <= MyGoConfig.soaring_wings.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "celestial_core", "soaring_wings"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //冰(幽灵)亡灵法师
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("goetyawaken:wraith_necromancer")) {
                        if (random.nextInt(100) <= MyGoConfig.hard_ice.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "l2complements", "hard_ice"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //凋灵
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("minecraft:wither")) {
                        if (random.nextInt(100) <= MyGoConfig.force_field.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "l2complements", "force_field"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //蔓生巨像
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:overgrown_colossus")) {
                        if (random.nextInt(100) <= MyGoConfig.hard_ice.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "l2complements", "life_essence"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //凋灵恶煞
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:withered_abomination")) {
                        if (random.nextInt(100) <= MyGoConfig.blackstone_core.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "l2complements", "blackstone_core"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //沙丘哨兵3——箭袋【'confluence:magic_quiver'】
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:dune_sentinel")) {
                        if (random.nextInt(100) <= MyGoConfig.magic_quiver.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "confluence", "magic_quiver"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                        if (random.nextInt(100) <= MyGoConfig.explosion_shard.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "l2complements", "explosion_shard"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //瞑煌龙7——石巨人之眼【'confluence:eye_of_the_golem'】
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("alexscaves:luxtructosaurus")) {
                        if (random.nextInt(100) <= MyGoConfig.eye_of_the_golem.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "confluence", "eye_of_the_golem"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //云魔8——腐香囊【'confluence:putrid_scent'】
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:cloud_golem")) {
                        if (random.nextInt(100) <= MyGoConfig.putrid_scent.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "confluence", "putrid_scent"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                }
                //神化材料
                if (ModList.get().isLoaded("apotheosis")) {
                    //白
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("mowziesmobs:frostmaw")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:aero_guardian")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("mowziesmobs:ferrous_wroughtnaut")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("mowziesmobs:sculptor")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("mowziesmobs:umvuthi")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("cataclysm:amethyst_crab")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("terra_entity:king_slime")
                            ||EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:skeletosaurus")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("iceandfire:cyclops")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:frostbitten_golem")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("iceandfire:gorgon")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:ancient_guardian")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("iceandfire:hydra")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("call_of_drowner:tide_raiser") ) {
                        for (int i = 0; i < random.nextInt((int) (MyGoConfig.common_material.get() * 1)) + 1; i++) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "apotheosis", "common_material"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //绿
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:warped_fungussus")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goety:brood_mother")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:dune_sentinel")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:overgrown_colossus")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("bosses_of_mass_destruction:gauntlet")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goety:crone")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:the_plauge_bringerp_2")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("terra_entity:cthulhu_eye")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("call_of_drowner:drowner")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("call_of_drowner:deepwater_piranha")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:withered_abomination")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goety:wight")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:posessed_paladin")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:lava_eater")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:puny_juggernaut")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goety:wither_necromancer")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goetyawaken:wraith_necromancer")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goetyawaken:parched_necromancer")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goety:skull_lord")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goety:bone_lord")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("torchesbecomesunlight:pursuer")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("terra_entity:queen_bee")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("arcalis_bosses:jack")
                    ) {
                        for (int i = 0; i < random.nextInt((int) (MyGoConfig.uncommon_material.get() * 1)) + 1; i++) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "apotheosis", "uncommon_material"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //蓝
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("minecraft:elder_guardian")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("terra_entity:brain_of_cthulhu")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("terra_entity:eater_of_world")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("cataclysm:the_leviathan")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("cataclysm:wadjet")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("cataclysm:kobolediator")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("alexscaves:hullbreaker")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("bosses_of_mass_destruction:void_blossom")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("arcalis_bosses:the_deathbringer")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("cataclysm:ancient_remnant")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("alexscaves:forsaken")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goety:minister")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("alexscaves:luxtructosaurus")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("minecraft:wither")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("bosses_of_mass_destruction:lich")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("irons_spellbooks:cryomancer")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("irons_spellbooks:pyromancer")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("irons_spellbooks:archevoker")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("irons_spellbooks:apothecarist")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("irons_spellbooks:priest")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("traveloptics:aqua_grandmaster")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("traveloptics:aquamancer")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("ars_nouveau:wilden_boss")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:cloud_golem")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("iceandfire:fire_dragon")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("iceandfire:ice_dragon")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("iceandfire:lightning_dragon")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("minecraft:warden")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("irons_spellbooks:dead_king")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("irons_spellbooks:dead_king_corpse")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("arcalis_bosses:captain_deadbone")
                    ) {
                        for (int i = 0; i < random.nextInt((int) (MyGoConfig.rare_material.get() * 1)) + 1; i++) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "apotheosis", "rare_material"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //紫
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("minecraft:ender_dragon")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("legendary_monsters:shulker_mimic")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goety:endersent")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goety:hostile_redstone_monstrosity")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("cataclysm:ender_golem")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("bosses_of_mass_destruction:obsidilith")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("traveloptics:enraged_dead_king")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goety:hostile_redstone_golem")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("meetyourfight:swampjaw")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("meetyourfight:bellringer")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("meetyourfight:dame_fortuna")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("meetyourfight:rosalyne")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:lich_phase_2")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("terra_entity:skeletron")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:butcher")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:blud")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:curse")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:kelvin")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:prowler")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:walking_bed")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:wistiver")
                    ) {
                        for (int i = 0; i < random.nextInt((int) (MyGoConfig.epic_material.get() * 1)) + 1; i++) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "apotheosis", "epic_material"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //橙
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("cataclysm:ignis")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("cataclysm:maledictus")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("cataclysm:netherite_monstrosity")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("cataclysm:the_harbinger")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("cataclysm:scylla")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("cataclysm:ender_guardian")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:faded_kingp_2")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("torchesbecomesunlight:patriot")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("torchesbecomesunlight:rosmontis")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("torchesbecomesunlight:frost_nova")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("torchesbecomesunlight:turret")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goety:vizier")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:vessel_sword")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("binah:binah_v_2")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("irons_spellbooks:fire_boss")
                            ||EntityType.getKey(event.getEntity().getType()).toString().equals("torchesbecomesunlight:gun_knight_patriot")
                            || (EntityType.getKey(event.getEntity().getType()).toString().equals("goety:apostle")
                            && !event.getEntity().level().dimension().equals(event.getEntity().level().NETHER))
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("terra_entity:dungeon_guardian")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goety:ender_keeper")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:radiance_guardian")
                    ) {
                        for (int i = 0; i < random.nextInt((int) (MyGoConfig.mythic_material.get() * 1)) + 1; i++) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "apotheosis", "mythic_material"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    //金
                    if (EntityType.getKey(event.getEntity().getType()).toString().equals("traveloptics:the_nightwarden")
                            || (EntityType.getKey(event.getEntity().getType()).toString().equals("goety:apostle")
                            && event.getEntity().level().dimension().equals(event.getEntity().level().NETHER))
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goetyawaken:hostile_mushroom_monstrosity")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goetyawaken:nameless_one")
                            || EntityType.getKey(event.getEntity().getType()).toString().equals("goety_revelation:summon_apollyon")
                    ) {
                        for (int i = 0; i < random.nextInt((int) (MyGoConfig.ancient_material.get() * 1)) + 1; i++) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "apotheosis", "ancient_material"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                }
                LivingEntity livingEntity = event.getEntity();
                //万法皆通
                if (ModList.get().isLoaded("touhou_little_maid_spell")) {
                    if (
                            EntityType.getKey(livingEntity.getType()).toString().equals("iceandfire:cyclops")
                    ) {
                        if (random.nextInt(100) <= MyGoConfig.fragrant_ingenuity.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "touhou_little_maid_spell", "fragrant_ingenuity"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    if (
                            EntityType.getKey(livingEntity.getType()).toString().equals("iceandfire:gorgon")
                    ) {
                        if (random.nextInt(100) <= MyGoConfig.spring_ring.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "touhou_little_maid_spell", "spring_ring"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    if (
                            EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:brain_of_cthulhu")
                    ) {
                        if (random.nextInt(100) <= MyGoConfig.brain_of_confusion.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "confluence", "brain_of_confusion"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    if (
                            EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:eater_of_world")
                    ) {
                        if (random.nextInt(100) <= MyGoConfig.worm_scarf.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "confluence", "worm_scarf"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    if (
                            EntityType.getKey(livingEntity.getType()).toString().equals("terra_entity:cthulhu_eye")
                    ) {
                        if (random.nextInt(100) <= MyGoConfig.flow_core.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "touhou_little_maid_spell", "flow_core"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                        if (random.nextInt(100) <= MyGoConfig.shield_of_cthulhu.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "confluence", "shield_of_cthulhu"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    if (EntityType.getKey(livingEntity.getType()).toString().equals("irons_spellbooks:dead_king")) {
                        if (random.nextInt(100) <= MyGoConfig.soul_book.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "touhou_little_maid_spell", "soul_book"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    if (EntityType.getKey(livingEntity.getType()).toString().equals("traveloptics:enraged_dead_king")) {
                        if (random.nextInt(100) <= MyGoConfig.spell_overlimit_core.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "touhou_little_maid_spell", "spell_overlimit_core"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                        if (random.nextInt(100) <= MyGoConfig.quick_chant_ring.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "touhou_little_maid_spell", "quick_chant_ring"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    if (EntityType.getKey(livingEntity.getType()).toString().equals("sons_of_sins:prowler")) {
                        if (random.nextInt(100) <= MyGoConfig.spell_enhancement_core.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "touhou_little_maid_spell", "spell_enhancement_core"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    if (EntityType.getKey(livingEntity.getType()).toString().equals("goetyawaken:nameless_one")) {
                        if (random.nextInt(100) <= MyGoConfig.infinity_totem.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "avaritia", "infinity_totem"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    if ((EntityType.getKey(livingEntity.getType()).toString().equals("goety_revelation:summon_apollyon")
                            && !livingEntity.level().dimension().equals(livingEntity.level().NETHER))) {
                        if (random.nextInt(100) <= MyGoConfig.hairpin.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "avaritia", "hairpin"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                    if ((EntityType.getKey(livingEntity.getType()).toString().equals("goety_revelation:summon_apollyon")
                            && livingEntity.level().dimension().equals(livingEntity.level().NETHER))) {
                        if (random.nextInt(100) <= MyGoConfig.wound_rime_blade.get() * 100) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                    "touhou_little_maid_spell", "wound_rime_blade"))).getDefaultInstance());
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                }
                //莱特兰守卫者之眼
                if ((EntityType.getKey(livingEntity.getType()).toString().equals("minecraft:elder_guardian"))) {
                    if (random.nextInt(100) <= MyGoConfig.guardian_eye.get() * 100) {
                        ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                "l2complements", "guardian_eye"))).getDefaultInstance());
                        itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                        level.addFreshEntity(itementity);
                    }
                }
                //太阳鸟
                if ((EntityType.getKey(livingEntity.getType()).toString().equals("mowziesmobs:umvuthi"))) {
                    if (random.nextInt(100) <= MyGoConfig.pygmy_necklace.get() * 100) {
                        ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                "wizard_terra_cuiros", "pygmy_necklace"))).getDefaultInstance());
                        itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                        level.addFreshEntity(itementity);
                    }
                    if (random.nextInt(100) <= MyGoConfig.sun_membrane.get() * 100) {
                        ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                "l2complements", "sun_membrane"))).getDefaultInstance());
                        itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                        level.addFreshEntity(itementity);
                    }
                }
                //虚空之花
                if ((EntityType.getKey(livingEntity.getType()).toString().equals("bosses_of_mass_destruction:void_blossom"))) {
                    if (random.nextInt(100) <= MyGoConfig.spirit_arrow_bag.get() * 100) {
                        ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                "celestial_artifacts", "spirit_arrow_bag"))).getDefaultInstance());
                        itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                        level.addFreshEntity(itementity);
                    }
                }
                //虚空之花
                if ((EntityType.getKey(livingEntity.getType()).toString().equals("bosses_of_mass_destruction:void_blossom"))) {
                    if (random.nextInt(100) <= MyGoConfig.spirit_arrow_bag.get() * 100) {
                        ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                "celestial_artifacts", "spirit_arrow_bag"))).getDefaultInstance());
                        itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                        level.addFreshEntity(itementity);
                    }
                }
                //沼泽
                if ((EntityType.getKey(livingEntity.getType()).toString().equals("meetyourfight:swampjaw"))) {
                    if (random.nextInt(100) <= MyGoConfig.spirit_necklace.get() * 100) {
                        ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                "celestial_artifacts", "spirit_necklace"))).getDefaultInstance());
                        itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                        level.addFreshEntity(itementity);
                    }
                }
                //风暴守卫
                if ((EntityType.getKey(livingEntity.getType()).toString().equals("faded_conquest_2:aero_guardian"))) {
                    if (random.nextInt(100) <= MyGoConfig.storm_core.get() * 100) {
                        ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                "l2complements", "storm_core"))).getDefaultInstance());
                        itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                        level.addFreshEntity(itementity);
                    }
                }
                //育母蜘蛛
                if ((EntityType.getKey(livingEntity.getType()).toString().equals("goety:brood_mother"))) {
                    if (random.nextInt(100) <= MyGoConfig.hercules_beetle.get() * 100) {
                        ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                "wizard_terra_cuiros", "hercules_beetle"))).getDefaultInstance());
                        itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                        level.addFreshEntity(itementity);
                    }
                }
                //焦骸死灵法师
                if ((EntityType.getKey(livingEntity.getType()).toString().equals("goetyawaken:parched_necromancer"))) {
                    if (random.nextInt(100) <= MyGoConfig.necromantic_scroll.get() * 100) {
                        ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation(
                                "wizard_terra_cuiros", "necromantic_scroll"))).getDefaultInstance());
                        itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                        level.addFreshEntity(itementity);
                    }
                }
            }
        }
    }
}