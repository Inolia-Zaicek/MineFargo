package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Ars.DrygmySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Entity.AnimalSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.SonsOfSins.EnvySinsSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.SonsOfSins.GluttonySinsSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.SonsOfSins.GreedSinsSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;

import static com.inolia_zaicek.mine_fargo.Item.EnigmaticLegacy.AbyssSoulStoneItem.abyss_soul_stone_nbt;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.inolia_zaicek.mine_fargo.Event.DeathAndCloneEvent.gluttony_sin_soul_stone;

public class DropsEvent {
    @SubscribeEvent
    public static void entityKilled(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            if (event.getSource().getEntity() instanceof Player livingEntity &&
                    (!EntityType.getKey(event.getEntity().getType()).toString().equals("goety:obsidian_monolith")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:terrible_ten")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:ttphase_2")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:the_plauge_bringer")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:pbdowned")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:faded_king")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:doomharbor_lich")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:vessel_spawn")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:vessel_of_calamity")
                    )) {
                Set<Item> curios = MyGoUtil.getCuriosItems(livingEntity);
                int number = 0;
                if (ModList.get().isLoaded("sons_of_sins")) {
                    if (MyGoUtil.hasSonsOfSins(curios,livingEntity, GluttonySinsSoulStone.get())) {
                        int killNumber = livingEntity.getPersistentData().getInt(gluttony_sin_soul_stone);
                        livingEntity.getPersistentData().putInt(gluttony_sin_soul_stone, Math.min(killNumber + 1,
                                (int) (MyGoConfig.gluttony_sin_soul_stone_max.get() / MyGoConfig.gluttony_sin_soul_stone_kill.get())));
                    }
                    if (MyGoUtil.hasSonsOfSins(curios,livingEntity, GreedSinsSoulStone.get())) {
                        number += (int) (MyGoConfig.greed_sin_soul_stone_drop.get() * 1);
                    }
                }
                if (MyGoUtil.hasEntity(curios,livingEntity, AnimalSoulStone.get())) {
                    number += (int) (MyGoConfig.animal_soul_stone.get() * 1);
                }
                if (ModList.get().isLoaded("ars_nouveau")) {
                    if (MyGoUtil.hasArs(curios,livingEntity, DrygmySoulStone.get())) {
                        number += (int) (MyGoConfig.drygmy_soul_stone_drop.get() * 1);
                    }
                }
                if (number > 0) {
                    for (int i = 0; i < number; i++) {
                        Level level = livingEntity.level();
                        if (!level.isClientSide() && level.getServer() != null) {
                            LootTable loot = ((MinecraftServer) Objects.requireNonNull(level.getServer())).getLootData().getLootTable(event.getEntity().getType().getDefaultLootTable());
                            LootParams context = (new LootParams.Builder((ServerLevel) level)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(event.getEntity().blockPosition())).withParameter(LootContextParams.THIS_ENTITY, event.getEntity()).withParameter(LootContextParams.DAMAGE_SOURCE, livingEntity.damageSources().playerAttack(livingEntity)).create(LootContextParamSets.ENTITY);
                            List<ItemStack> drops = loot.getRandomItems(context);
                            for (ItemStack drop : drops) {
                                ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), drop);
                                itementity.setDefaultPickUpDelay();
                                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                                level.addFreshEntity(itementity);
                            }
                        }
                    }
                }
            }
            //如果攻击者是随从
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity &&
                    (!EntityType.getKey(event.getEntity().getType()).toString().equals("goety:obsidian_monolith")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:terrible_ten")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:ttphase_2")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:the_plauge_bringer")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:pbdowned")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:faded_king")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:doomharbor_lich")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:vessel_spawn")
                            &&!EntityType.getKey(event.getEntity().getType()).toString().equals("faded_conquest_2:vessel_of_calamity")
                    )) {
                if (event.getSource().getEntity() instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() instanceof Player owner) {
                    int number = 0;
                    Set<Item> curios = MyGoUtil.getCuriosItems(owner);
                    if (MyGoUtil.hasEntity(curios,owner, AnimalSoulStone.get())) {
                        number += (int) (MyGoConfig.animal_soul_stone.get() * 1);
                    }
                    if (ModList.get().isLoaded("ars_nouveau")) {
                        if (MyGoUtil.hasArs(curios,owner, DrygmySoulStone.get())) {
                            number += (int) (MyGoConfig.drygmy_soul_stone_drop.get() * 1);
                        }
                    }
                    if (ModList.get().isLoaded("sons_of_sins")) {
                        if (MyGoUtil.hasSonsOfSins(curios,owner, GluttonySinsSoulStone.get())) {
                            int killNumber = owner.getPersistentData().getInt(gluttony_sin_soul_stone);
                            livingEntity.getPersistentData().putInt(gluttony_sin_soul_stone, Math.min(killNumber + 1,
                                    (int) (MyGoConfig.gluttony_sin_soul_stone_max.get() / MyGoConfig.gluttony_sin_soul_stone_kill.get())));
                        }
                        if (MyGoUtil.hasSonsOfSins(curios,owner, GreedSinsSoulStone.get())) {
                            number += (int) (MyGoConfig.greed_sin_soul_stone_drop.get() * 1);
                        }
                    }
                    if (number > 0) {
                        for (int i = 0; i < number; i++) {
                            Level level = owner.level();
                            if (!level.isClientSide() && level.getServer() != null) {
                                LootTable loot = ((MinecraftServer) Objects.requireNonNull(level.getServer())).getLootData().getLootTable(event.getEntity().getType().getDefaultLootTable());
                                LootParams context = (new LootParams.Builder((ServerLevel) level)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(event.getEntity().blockPosition())).withParameter(LootContextParams.THIS_ENTITY, event.getEntity()).withParameter(LootContextParams.DAMAGE_SOURCE, livingEntity.damageSources().playerAttack(owner)).create(LootContextParamSets.ENTITY);
                                List<ItemStack> drops = loot.getRandomItems(context);
                                for (ItemStack drop : drops) {
                                    ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), drop);
                                    itementity.setDefaultPickUpDelay();
                                    itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                                    level.addFreshEntity(itementity);
                                }
                            }
                        }
                    }
                }
            }
            //深渊魂石判断
            if (ModList.get().isLoaded("enigmaticlegacy")&&event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                Set<Item> curios = MyGoUtil.getCuriosItems(livingEntity);
                if ( MyGoUtil.hasEnigmaticLegacy(curios,livingEntity, MyGoItemRegister.AbyssSoulStone.get())) {
                    //被杀的实体的记录（是ID
                    String killedEntityId = EntityType.getKey(event.getEntity().getType()).toString();
                    // 构建用于存储击杀实体ID的NBT键
                    String nbtKeyForKilledId = "killed_entity_id_" + killedEntityId;
                    CompoundTag compoundTag = MyGoUtil.getFirstCurioCompoundTag(livingEntity, MyGoItemRegister.AbyssSoulStone.get());
                    if (compoundTag != null) {
                        // 存储这个击杀实体ID
                        compoundTag.putString(nbtKeyForKilledId, killedEntityId);
                        // 统计所有被杀实体ID的数量（即不同ID的数量）
                        int killedIdCount = 0;
                        for (String existingKey : compoundTag.getAllKeys()) {
                            if (existingKey.startsWith("killed_entity_id_")) {
                                killedIdCount++;
                            }
                        }
                        //记录
                        compoundTag.putInt(String.valueOf(abyss_soul_stone_nbt), killedIdCount);
                    }
                }
                //如果是随从击杀，自己有深渊
                if (event.getSource().getEntity() instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() != null) {
                    LivingEntity owner = ownableEntity.getOwner();
                    Set<Item> ownerCurios = MyGoUtil.getCuriosItems(owner);
                    if (MyGoUtil.hasEnigmaticLegacy(ownerCurios,owner, MyGoItemRegister.AbyssSoulStone.get())) {
                        //被杀的实体的记录（是ID
                        String killedEntityId = EntityType.getKey(event.getEntity().getType()).toString();
                        // 构建用于存储击杀实体ID的NBT键
                        String nbtKeyForKilledId = "killed_entity_id_" + killedEntityId;
                        CompoundTag compoundTag = MyGoUtil.getFirstCurioCompoundTag(owner, MyGoItemRegister.AbyssSoulStone.get());
                        if (compoundTag != null) {
                            // 存储这个击杀实体ID
                            compoundTag.putString(nbtKeyForKilledId, killedEntityId);
                            // 统计所有被杀实体ID的数量（即不同ID的数量）
                            int killedIdCount = 0;
                            for (String existingKey : compoundTag.getAllKeys()) {
                                if (existingKey.startsWith("killed_entity_id_")) {
                                    killedIdCount++;
                                }
                            }
                            //记录
                            compoundTag.putInt(String.valueOf(abyss_soul_stone_nbt), killedIdCount);
                        }
                    }
                }
            }
            //
        }
    }
}