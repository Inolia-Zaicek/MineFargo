package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Twilight.NagaSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.Set;

public class HealEvent {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void heal(LivingHealEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Set<Item> curios = MyGoUtil.getCuriosItems(livingEntity);
        //基础数值
        double number = 1;
        if (ModList.get().isLoaded("twilightforest")) {
            if(MyGoUtil.hasTwilight(curios,livingEntity, NagaSoulStone.get())){
                number+= MyGoConfig.naga_soul_stone_heal.get();
            }
        }
        double finish = (event.getAmount()*number);
        //以太压血
        if (ModList.get().isLoaded("enigmaticlegacy")) {
            if (MyGoUtil.hasEnigmaticLegacy(curios,livingEntity, EtheriumSoulStone.get())) {
                if(livingEntity.getHealth()/livingEntity.getMaxHealth()>MyGoConfig.etherium_soul_stone_hp.get()) {
                    number = 0;
                }
                //如果回血数额+当前血量大于阈值
                if( (event.getAmount()*number + livingEntity.getHealth() )/livingEntity.getMaxHealth()>MyGoConfig.etherium_soul_stone_hp.get()) {
                    //超额部分
                    double over = event.getAmount()*number + livingEntity.getHealth() - livingEntity.getMaxHealth()*MyGoConfig.etherium_soul_stone_hp.get();
                    if (over <= 0) {
                        number = 0;
                    } else {
                        //治疗量-超出量
                        finish-=over;
                    }
                }
            }
        }
        if(number!=1){
            if(number<=0){
                event.setAmount(0);
                event.setCanceled(true);
            }else {
                event.setAmount((float) finish);
            }
        }
    }
}
