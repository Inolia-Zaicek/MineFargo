package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.LapisLazuliSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings({"all", "removal"})
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MineFargo.MODID)
public class ExpEvent {
    //经验值变化事件
    @SubscribeEvent
    public static void event(PlayerXpEvent.XpChange event){
        //是经验值提升
        if(event.getAmount()>0) {
            LivingEntity livingEntity = event.getEntity();
            float number = 1;
            if (MyGoUtil.hasOre(livingEntity, LapisLazuliSoulStoneItem.class)) {
                number+=MyGoConfig.lapis_lazuli_soul_stone.get();
            }
            event.setAmount((int) (event.getAmount() * number));
        }
    }
}
