package com.inolia_zaicek.mine_fargo.Event.Tacz;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Tacz.AmmoSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Tacz.RifleSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Tacz.SniperRifleSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import com.inolia_zaicek.mine_fargo.Util.Tacz.Tacz_WTC_Util;
import com.tacz.guns.api.item.IGun;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;


public class TaczTickEvent {

    public static final String sniper_rifle_soul_stone = MineFargo.MODID + ":sniper_rifle_soul_stone_a";
    public static final String rifle_soul_stone_number = MineFargo.MODID + ":rifle_soul_stone_number_a";
    public static final String rifle_soul_stone_time = MineFargo.MODID + ":rifle_soul_stone_time_a";
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        if (!event.getEntity().isAlive())
            return;
        LivingEntity livingEntity = event.getEntity();
        if (ModList.get().isLoaded("tacz")&&livingEntity.level().getGameTime() % 1 == 0) {
            //没有狙击枪
            if ( ! MyGoUtil.hasTacz(livingEntity, SniperRifleSoulStoneItem.class) ) {
                livingEntity.getPersistentData().putInt(sniper_rifle_soul_stone, 0);
            } else {
                //最长续
                livingEntity.getPersistentData().putInt(sniper_rifle_soul_stone, (int) Math.min(MyGoConfig.sniper_rifle_soul_stone_time.get() * 2*20, livingEntity.getPersistentData().getInt(sniper_rifle_soul_stone) + 1));
            }
            //没有步枪
            if (! MyGoUtil.hasTacz(livingEntity, RifleSoulStoneItem.class)) {
                //射击次数与计时器都归零
                livingEntity.getPersistentData().putInt(rifle_soul_stone_number, 0);
                livingEntity.getPersistentData().putInt(rifle_soul_stone_time, 0);
            } else {
                //提升计时器
                livingEntity.getPersistentData().putInt(rifle_soul_stone_time, (int) Math.min(MyGoConfig.rifle_soul_stone_time.get() * 2 * 20, livingEntity.getPersistentData().getInt(rifle_soul_stone_time) + 1));
            }
            //速射计时器到达配置文件上限值————射击计数器归零，但计时器不变（等射击
            if (livingEntity.getPersistentData().getInt(rifle_soul_stone_time) >= MyGoConfig.rifle_soul_stone_time.get() * 2 * 20) {
                livingEntity.getPersistentData().putInt(rifle_soul_stone_number, 0);
            }
            if (livingEntity.level().getGameTime() % (20*MyGoConfig.ammo_soul_stone_time.get()) == 0 && MyGoUtil.hasTacz(livingEntity, AmmoSoulStoneItem.class)) {
                // 1. 判断玩家主手武器（枪）是否为枪械（模式内的Gun对象）
                ItemStack mainHand = livingEntity.getMainHandItem();
                if (IGun.getIGunOrNull(mainHand) != null) {
                    // 回复主手枪的子弹
                    Tacz_WTC_Util.reloadGunIfNeeded(mainHand, (int)(MyGoConfig.ammo_soul_stone_number.get()*1));
                }
                // 4. 判断副手
                ItemStack offHand = livingEntity.getOffhandItem();
                if (IGun.getIGunOrNull(offHand) != null) {
                    Tacz_WTC_Util.reloadGunIfNeeded(offHand, (int)(MyGoConfig.ammo_soul_stone_number.get()*1));
                }
            }
        }
    }
}