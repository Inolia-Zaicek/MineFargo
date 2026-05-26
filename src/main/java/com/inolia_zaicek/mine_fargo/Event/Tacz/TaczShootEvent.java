package com.inolia_zaicek.mine_fargo.Event.Tacz;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Tacz.SubmachineGunSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import com.inolia_zaicek.mine_fargo.Util.Tacz.Tacz_WTC_Util;
import com.tacz.guns.api.event.common.GunShootEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;
import java.util.Set;

import static com.inolia_zaicek.mine_fargo.Event.Tacz.TaczTickEvent.rifle_soul_stone_number;
import static com.inolia_zaicek.mine_fargo.Event.Tacz.TaczTickEvent.rifle_soul_stone_time;

public class TaczShootEvent {
    //暗杀瞄准镜时间
    @SubscribeEvent
    public static void hurt(GunShootEvent event) {
        LivingEntity shooter = event.getShooter();
        ItemStack gunItemStack = event.getGunItemStack();
        if (shooter != null && gunItemStack != null) {
            Set<Item> curios = MyGoUtil.getCuriosItems(shooter);
            /// 速射
            //速射清除增伤的计时器归零
            shooter.getPersistentData().putInt(rifle_soul_stone_time,0);
            //当前射击次数+1
            int now = shooter.getPersistentData().getInt(rifle_soul_stone_number);
            shooter.getPersistentData().putInt(rifle_soul_stone_number, (int) Math.min(MyGoConfig.rifle_soul_stone_number.get(),now+1));
            //弹药消耗减免部分
            Random random = new Random();
            double ammoReductionChance = 100;
            //
            if( MyGoUtil.hasTacz(curios,shooter, SubmachineGunSoulStone.get()) ){
                ammoReductionChance *= 1+MyGoConfig.submachine_gun_soul_stone_chance.get();
            }
            if (ammoReductionChance > 100) {
                double chance = ammoReductionChance-100;
                if(random.nextInt(100) <= chance) {
                    Tacz_WTC_Util.reloadGunIfNeeded(gunItemStack, 1);
                }
            }
        }
    }
}
