package com.inolia_zaicek.mine_fargo.Event.Botania;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Botania.ManasteelSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Botania.TerrasteelSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import vazkii.botania.api.mana.ManaItemHandler;

import java.util.Set;


public class ManaRepair {
    @SubscribeEvent
    public static void tick(TickEvent.PlayerTickEvent event) {
        if (ModList.get().isLoaded("botania")) {
            Player player = event.player;
            Set<Item> curios = MyGoUtil.getCuriosItems(player);
            if (MyGoUtil.hasBotania(curios,player, ManasteelSoulStone.get()) &&
                    player.level().getGameTime() % (20 * MyGoConfig.manasteel_soul_stone_time.get()) == 0) {
                ItemStack mainHandItem = player.getMainHandItem();
                ItemStack offHandItem = player.getOffhandItem();
                ItemStack itemStack = player.getInventory().getSelected();

                // 定义一个工具方法，处理装备的减魔逻辑
                handleModularItem(player, mainHandItem);
                handleModularItem(player, offHandItem);

                // 处理盔甲部位
                handleHeadgear(player);
                handleChestplate(player);
                handleLeggings(player);
                handleBoots(player);
            }
            if (MyGoUtil.hasBotania(curios,player, TerrasteelSoulStone.get()) &&
                    player.level().getGameTime() % 20 == 0) {
                ItemStack itemStack = player.getInventory().getSelected();
                ManaItemHandler.instance().dispatchManaExact(itemStack, player, (int) (MyGoConfig.terrasteel_soul_stone_mana.get() * 1), true);

            }
        }
    }

    // 处理单个工具或装备的魔力修复
    private static void handleModularItem(Player player, ItemStack stack) {
        int currentDamage = stack.getDamageValue();
        if (ManaItemHandler.INSTANCE.requestManaExactForTool(stack, player, (int) (MyGoConfig.manasteel_soul_stone_mana.get() * 1), true) && currentDamage > 0) {
            //修复1耐久
            int newDamage = (int) (currentDamage - (int)(MyGoConfig.manasteel_soul_stone_number.get()*1));
            stack.setDamageValue(Math.max(0, newDamage));
            ManaItemHandler.INSTANCE.dispatchManaExact(stack, player, (int) (MyGoConfig.manasteel_soul_stone_mana.get() * 1), false);
        }
    }

    // 处理头盔
    private static void handleHeadgear(Player player) {
        ItemStack headStack = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.HEAD);
        processArmorPart(player, headStack);
    }
    // 处理胸甲
    private static void handleChestplate(Player player) {
        ItemStack chestStack = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.CHEST);
        processArmorPart(player, chestStack);
    }
    // 处理护腿
    private static void handleLeggings(Player player) {
        ItemStack legStack = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.LEGS);
        processArmorPart(player, legStack);
    }
    // 处理靴子
    private static void handleBoots(Player player) {
        ItemStack feetStack = player.getItemBySlot(net.minecraft.world.entity.EquipmentSlot.FEET);
        processArmorPart(player, feetStack);
    }

    // 统一处理装备盔甲部分的逻辑
    private static void processArmorPart(Player player, ItemStack armorStack) {
        int currentDamage = armorStack.getDamageValue();
        if (ManaItemHandler.INSTANCE.requestManaExactForTool(armorStack, player, (int) (MyGoConfig.manasteel_soul_stone_mana.get() * 1), true) && currentDamage > 0) {
            int newDamage = (int) (currentDamage - (int)(MyGoConfig.manasteel_soul_stone_number.get()*1));
            armorStack.setDamageValue(Math.max(0, newDamage));
            ManaItemHandler.INSTANCE.dispatchManaExact(armorStack, player, (int) (MyGoConfig.manasteel_soul_stone_mana.get() * 1), false);
        }
    }
}
