package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Create.SturdySheetSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Create.ZincSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.CopperSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MineFargo.MODID)
public class BreakSpeedEvent {
    @SubscribeEvent
    public static void breakSpeed(PlayerEvent.BreakSpeed event) {
        LivingEntity livingEntity =  event.getEntity();
        float number = 0;
        if (MyGoUtil.hasOre(livingEntity, CopperSoulStoneItem.class)) {
            number+=MyGoConfig.copper_soul_stone.get();
        }
        if (ModList.get().isLoaded("create")) {
            if (MyGoUtil.hasCreate(livingEntity, ZincSoulStoneItem.class)) {
                number += MyGoConfig.zinc_soul_stone_speed.get();
            }
        }
        if (number!=0) {
            event.setNewSpeed(event.getNewSpeed() * (1+number) );
        }
    }
}
