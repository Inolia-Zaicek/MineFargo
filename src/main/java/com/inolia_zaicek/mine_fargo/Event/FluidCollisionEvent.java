package com.inolia_zaicek.mine_fargo.Event;

import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import com.inolia_zaicek.mine_fargo.Item.Cataclysm.IgnisSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Entity.LavaEaterSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.LavaSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.OceanSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class FluidCollisionEvent {
    @SubscribeEvent
    public static void onFluidCollisionLava(LivingFluidCollisionEvent event) {
        if (event.getEntity() instanceof Player player) {
            boolean lava = false;
            boolean water = false;
            if(MyGoUtil.hasNature(player, LavaSoulStoneItem.class)){
                lava = true;
            }
            if (ModList.get().isLoaded("cataclysm")) {
                if (MyGoUtil.hasCataclysm(player, IgnisSoulStoneItem.class)) {
                    lava = true;
                }
            }
            if (ModList.get().isLoaded("legendary_monsters")) {
                if (MyGoUtil.hasLegendaryEntity(player, LavaEaterSoulStoneItem.class) ) {
                    lava = true;
                }
            }
            if(MyGoUtil.hasNature(player, OceanSoulStoneItem.class)){
                water = true;
            }
            if (!player.isInLava() && !player.isShiftKeyDown() && event.getFluidState().is(FluidTags.LAVA)&&lava) {
                event.setResult(Event.Result.ALLOW);
            }
            if (!player.isUnderWater() && !player.isInWater() && !player.isShiftKeyDown() && event.getFluidState().is(FluidTags.WATER)&&water) {
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
}
