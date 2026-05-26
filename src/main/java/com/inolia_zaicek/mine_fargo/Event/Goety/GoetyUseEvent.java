package com.inolia_zaicek.mine_fargo.Event.Goety;

import com.Polarice3.Goety.common.items.brew.BrewItem;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.BrewSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.PotionItem;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.Random;
import java.util.Set;

public class GoetyUseEvent {
    @SubscribeEvent
    public static void UseItemEvent(LivingEntityUseItemEvent.Finish event) {
        if (ModList.get().isLoaded("goety")) {
            Set<Item> curios = MyGoUtil.getCuriosItems(event.getEntity());
            //药水
            if (MyGoUtil.hasGoetyItem(curios,event.getEntity(), BrewSoulStone.get()) && event.getItem().getItem() instanceof BrewItem
                    && new Random().nextInt(100) < MyGoConfig.brew_soul_stone_chance_a.get()) {
                event.setResultStack(event.getItem());
            } else if (MyGoUtil.hasGoetyItem(curios,event.getEntity(), BrewSoulStone.get()) && event.getItem().getItem() instanceof PotionItem
                    && new Random().nextInt(100) < MyGoConfig.brew_soul_stone_chance_b.get()) {
                event.setResultStack(event.getItem());
            }
        }
    }
}
