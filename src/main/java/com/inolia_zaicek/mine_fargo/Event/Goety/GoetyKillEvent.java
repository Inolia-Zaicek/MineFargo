package com.inolia_zaicek.mine_fargo.Event.Goety;

import com.Polarice3.Goety.api.items.magic.ITotem;
import com.Polarice3.Goety.utils.TotemFinder;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.EctoplasmSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.boss.enderdragon.EnderDragon;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.Set;

import static com.Polarice3.Goety.utils.SEHelper.*;

public class GoetyKillEvent {
    @SubscribeEvent
    public static void entityKilled(LivingDeathEvent event) {
        if (ModList.get().isLoaded("goety")) {
            if (!(event.getEntity() instanceof Player)) {
                if (event.getSource().getEntity() instanceof Player player && !EntityType.getKey(event.getEntity().getType()).toString().equals("goety:obsidian_monolith")) {
                    Set<Item> curios = MyGoUtil.getCuriosItems(player);
                    if (MyGoUtil.hasGoetyItem(curios,player, EctoplasmSoulStone.get())) {
                        //判断是否有灵魂方舟
                        if (getSEActive(player)) {
                            sendSEUpdatePacket(player);
                            increaseSESouls(player, (int) (MyGoConfig.ectoplasm_soul_stone.get() * 1));
                        } else {
                            ItemStack foundStack = TotemFinder.FindTotem(player);
                            if (foundStack != null && foundStack.getTag() != null) {
                                ITotem.increaseSouls(foundStack, (int) (MyGoConfig.ectoplasm_soul_stone.get() * 1));
                            }
                        }
                    }
                }
                //如果攻击者是随从
                else if (event.getSource().getEntity() instanceof LivingEntity && !EntityType.getKey(event.getEntity().getType()).toString().equals("goety:obsidian_monolith")) {
                    if (event.getSource().getEntity() instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() instanceof Player player) {
                        Set<Item> curios = MyGoUtil.getCuriosItems(player);
                        if (MyGoUtil.hasGoetyItem(curios,player, EctoplasmSoulStone.get())) {
                            //判断是否有灵魂方舟
                            if (getSEActive(player)) {
                                sendSEUpdatePacket(player);
                                increaseSESouls(player, (int) (MyGoConfig.ectoplasm_soul_stone.get() * 1));
                            } else {
                                ItemStack foundStack = TotemFinder.FindTotem(player);
                                if (foundStack != null && foundStack.getTag() != null) {
                                    ITotem.increaseSouls(foundStack, (int) (MyGoConfig.ectoplasm_soul_stone.get() * 1));
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void drop(LivingDropsEvent event) {
        if (ModList.get().isLoaded("goety")) {
            Level level = event.getEntity().level();
            //使徒
            if (EntityType.getKey(event.getEntity().getType()).toString().equals("goety:apostle")&&!level.dimension().equals(Level.NETHER)) {
                    ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(),
                            event.getEntity().getY(), event.getEntity().getZ(),
                            MyGoItemRegister.ApostleSoulStone.get()
                                    .getDefaultInstance());
                    itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                            (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                    level.addFreshEntity(itementity);
            }else if (EntityType.getKey(event.getEntity().getType()).toString().equals("goety:apostle")&&level.dimension().equals(Level.NETHER)) {
                ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(),
                        event.getEntity().getY(), event.getEntity().getZ(),
                        MyGoItemRegister.NetherApostleSoulStone.get()
                                .getDefaultInstance());
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                        (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                level.addFreshEntity(itementity);
            }else if (EntityType.getKey(event.getEntity().getType()).toString().equals("goety:vizier")) {
                ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(),
                        event.getEntity().getY(), event.getEntity().getZ(),
                        MyGoItemRegister.VizierSoulStone.get()
                                .getDefaultInstance());
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                        (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                level.addFreshEntity(itementity);
            }else if (EntityType.getKey(event.getEntity().getType()).toString().equals("goety:hostile_redstone_monstrosity")) {
                ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(),
                        event.getEntity().getY(), event.getEntity().getZ(),
                        MyGoItemRegister.RedstoneMonstrositySoulStone.get()
                                .getDefaultInstance());
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                        (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                level.addFreshEntity(itementity);
            }else if (EntityType.getKey(event.getEntity().getType()).toString().equals("goety:ender_keeper")) {
                ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(),
                        event.getEntity().getY(), event.getEntity().getZ(),
                        MyGoItemRegister.EnderKeeperSoulStone.get()
                                .getDefaultInstance());
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                        (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                level.addFreshEntity(itementity);
            }else if (EntityType.getKey(event.getEntity().getType()).toString().equals("goety:minister")) {
                ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(),
                        event.getEntity().getY(), event.getEntity().getZ(),
                        MyGoItemRegister.MinisterSoulStone.get()
                                .getDefaultInstance());
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                        (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                level.addFreshEntity(itementity);
            }
        }
    }
}