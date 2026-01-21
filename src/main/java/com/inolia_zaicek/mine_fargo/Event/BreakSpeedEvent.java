package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.CopperSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MineFargo.MODID)
public class BreakSpeedEvent {
    @SubscribeEvent
    public static void breakSpeed(PlayerEvent.BreakSpeed event) {
        float number = 0;
        if (MyGoUtil.hasOre(event.getEntity(), CopperSoulStoneItem.class)) {
            number+=MyGoConfig.copper_soul_stone.get();
        }
        if (number!=0) {
            event.setNewSpeed(event.getNewSpeed() * (1+number) );
        }
    }
}
