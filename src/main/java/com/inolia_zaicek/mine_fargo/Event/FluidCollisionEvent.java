package com.inolia_zaicek.mine_fargo.Event;

import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.LavaSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.OceanSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class FluidCollisionEvent {
    @SubscribeEvent
    public static void onFluidCollisionLava(LivingFluidCollisionEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (player.fallDistance < 6.0F && !player.isUsingItem() && !player.isCrouching() && event.getFluidState().is(FluidTags.LAVA)&&
                    MyGoUtil.hasNature(player, LavaSoulStoneItem.class)) {
                event.setResult(Event.Result.ALLOW);
            }
            if (player.fallDistance < 6.0F && !player.isUsingItem() && !player.isCrouching() && event.getFluidState().is(FluidTags.WATER)&&
                    MyGoUtil.hasNature(player, OceanSoulStoneItem.class)) {
                event.setResult(Event.Result.ALLOW);

            }
        }
    }
}
