package com.inolia_zaicek.mine_fargo.Event.Goety;

import com.Polarice3.Goety.common.items.brew.BrewItem;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.BrewSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.Random;

public class GoetyUseEvent {
    @SubscribeEvent
    public static void UseItemEvent(LivingEntityUseItemEvent.Finish event) {
        if (ModList.get().isLoaded("goety")) {
            //药水
            if (MyGoUtil.hasGoetyItem(event.getEntity(), BrewSoulStoneItem.class) && event.getItem().getItem() instanceof BrewItem
                    && new Random().nextInt(100) < MyGoConfig.brew_soul_stone_chance_a.get()) {
                event.setResultStack(event.getItem());
            } else if (MyGoUtil.hasGoetyItem(event.getEntity(), BrewSoulStoneItem.class) && event.getItem().getItem() instanceof PotionItem
                    && new Random().nextInt(100) < MyGoConfig.brew_soul_stone_chance_b.get()) {
                event.setResultStack(event.getItem());
            }
        }
    }
}
