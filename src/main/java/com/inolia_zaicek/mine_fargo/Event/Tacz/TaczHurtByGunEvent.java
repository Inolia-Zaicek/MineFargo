package com.inolia_zaicek.mine_fargo.Event.Tacz;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Tacz.HandgunSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Tacz.MachineGunSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;
import java.util.Set;

public class TaczHurtByGunEvent {
    @SubscribeEvent
    public static void hurt(EntityHurtByGunEvent.Pre event) {
        LivingEntity attacker = event.getAttacker();
        if (attacker != null) {
            Set<Item> curios = MyGoUtil.getCuriosItems(attacker);
            //基础爆头伤害
            if (MyGoUtil.hasTacz(curios,attacker, HandgunSoulStone.get())) {
                event.setHeadshotMultiplier((float) (event.getHeadshotMultiplier() + MyGoConfig.handgun_soul_stone.get()));
            }
            Random random = new Random();
            //强制爆头
            if (MyGoUtil.hasTacz(curios,attacker, HandgunSoulStone.get()) && random.nextInt(100) <= MyGoConfig.heavy_machine_gun_soul_stone.get() * 100) {
                event.setHeadshot(true);
            }
            //基础增伤
            if (MyGoUtil.hasTacz(curios,attacker, MachineGunSoulStone.get())) {
                event.setBaseAmount((float) (event.getBaseAmount() * (1 + MyGoConfig.machine_gun_soul_stone.get())));
            }
        }
    }
}