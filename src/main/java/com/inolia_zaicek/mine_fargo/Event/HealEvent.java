package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Twilight.NagaSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class HealEvent {
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void heal(LivingHealEvent event) {
        LivingEntity livingEntity = event.getEntity();
        //基础数值
        float number = 1;
        if (ModList.get().isLoaded("twilightforest")) {
            if(MyGoUtil.hasTwilight(livingEntity, NagaSoulStoneItem.class)){
                number+= MyGoConfig.naga_soul_stone_heal.get();
            }
        }
        if(number!=1){
            event.setAmount(event.getAmount()*number);
        }
    }
}
