package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Item.MineCraft.RedstoneSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.confluence.mod.misc.ModAttributes;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MineFargo.MODID)
public class BreakSpeedEvent {
    @SubscribeEvent
    public static void breakSpeed(PlayerEvent.BreakSpeed event) {
        if (MyGoUtil.hasSpecificItem(event.getEntity(), RedstoneSoulStoneItem.class)) {
            event.setNewSpeed(event.getNewSpeed() * (1+0.25F) );
        }
    }
}
