package com.inolia_zaicek.mine_fargo.Event.Twilight;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Botania.ManasteelSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Botania.TerrasteelSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Twilight.TwilightForest.SteeleafSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Set;


public class TwilightRepairEvent {
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        if (!event.getEntity().isAlive())
            return;
        LivingEntity livingEntity = event.getEntity();
        Set<Item> curios = MyGoUtil.getCuriosItems(livingEntity);
        if (livingEntity instanceof Player player&&player.level().getGameTime() % (20 * MyGoConfig.steeleaf_soul_stone_time.get()) == 0) {
            if (MyGoUtil.hasTwilightForest(curios,player, SteeleafSoulStone.get())) {
                ItemStack mainHandItem = player.getMainHandItem();
                ItemStack offHandItem = player.getOffhandItem();
                handleModularItem(player, mainHandItem);
                handleModularItem(player, offHandItem);

                // 处理盔甲部位
                handleHeadgear(player);
                handleChestplate(player);
                handleLeggings(player);
                handleBoots(player);
            }
        }
        //九头蛇回血
        if (livingEntity.level().getGameTime() % 20L == 0&& MyGoUtil.hasTwilight(curios,livingEntity, TwilightHydraSoulStone.get())) {
            double dhp = (livingEntity.getMaxHealth() - livingEntity.getHealth()) / (livingEntity.getMaxHealth() * (1 - MyGoConfig.twilight_hydra_soul_stone_last_hp.get()));
            livingEntity.heal((float) (MyGoConfig.twilight_hydra_soul_stone_heal.get() * dhp));
        }
    }
    // 处理单个工具或装备的魔力修
    private static void handleModularItem(Player player, ItemStack stack) {
        int currentDamage = stack.getDamageValue();
        if (currentDamage > 0) {
            //修复1耐久
            int newDamage = (int) (currentDamage - (int)(MyGoConfig.steeleaf_soul_stone_number.get()*1));
            stack.setDamageValue(Math.max(0, newDamage));
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
        if (currentDamage > 0) {
            int newDamage = (int) (currentDamage - (int)(MyGoConfig.steeleaf_soul_stone_number.get()*1));
            armorStack.setDamageValue(Math.max(0, newDamage));
        }
    }
}
