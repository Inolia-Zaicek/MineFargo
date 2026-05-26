package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Item.Twilight.MinoshroomSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Twilight.ZombieScepterSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.ShieldBlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.Set;

public class ShieldEvent {
    @SubscribeEvent
    public static void event(ShieldBlockEvent event) {
        boolean breakShield = false;
        if (event.getDamageSource().getEntity() instanceof LivingEntity attacker) {
            if (ModList.get().isLoaded("twilightforest")) {
                Set<Item> curios = MyGoUtil.getCuriosItems(attacker);
                if (MyGoUtil.hasTwilight(curios,attacker, MinoshroomSoulStone.get())) {
                    breakShield=true;
                }
            }
        }
        if(breakShield){
            event.setCanceled(true);
        }
    }
}