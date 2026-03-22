package com.inolia_zaicek.mine_fargo.Event.Tacz;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Tacz.HandgunSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Tacz.MachineGunSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import com.tacz.guns.api.event.common.EntityHurtByGunEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class TaczHurtByGunEvent {
    @SubscribeEvent
    public static void hurt(EntityHurtByGunEvent.Pre event) {
        //基础爆头伤害
        if( MyGoUtil.hasTacz(event.getAttacker(), HandgunSoulStone.get()) ) {
            event.setHeadshotMultiplier((float) (event.getHeadshotMultiplier() + MyGoConfig.handgun_soul_stone.get()));
        }
        Random random = new Random();
        //强制爆头
        if( MyGoUtil.hasTacz(event.getAttacker(), HandgunSoulStone.get()) && random.nextInt(100) <= MyGoConfig.heavy_machine_gun_soul_stone.get()*100) {
            event.setHeadshot(true);
        }
        //基础增伤
        if( MyGoUtil.hasTacz(event.getAttacker(), MachineGunSoulStone.get()) ) {
            event.setBaseAmount((float) (event.getBaseAmount()*(1+MyGoConfig.machine_gun_soul_stone.get())));
        }
    }
}
