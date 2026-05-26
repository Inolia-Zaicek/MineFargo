package com.inolia_zaicek.mine_fargo.Event;

import be.florens.expandability.api.forge.LivingFluidCollisionEvent;
import com.inolia_zaicek.mine_fargo.Item.Cataclysm.IgnisSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Entity.LavaEaterSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.LavaSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.OceanSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.Set;

public class FluidCollisionEvent {
    @SubscribeEvent
    public static void onFluidCollisionLava(LivingFluidCollisionEvent event) {
        if (event.getEntity() instanceof Player player) {
            Set<Item> curios = MyGoUtil.getCuriosItems(player);
            boolean lava = false;
            boolean water = false;
            if(MyGoUtil.hasNature(curios,player, LavaSoulStone.get())){
                lava = true;
            }
            if (ModList.get().isLoaded("aquaculture")) {
                if (MyGoUtil.isCurioEquipped(player, MyGoItemRegister.NeptuniumSoulStone.get())  ) {
                    water = true;
                }
            }
            if (ModList.get().isLoaded("cataclysm")) {
                if (MyGoUtil.hasCataclysm(curios,player, IgnisSoulStone.get())) {
                    lava = true;
                }
            }
            if (ModList.get().isLoaded("legendary_monsters")) {
                if (MyGoUtil.hasLegendaryEntity(curios,player, LavaEaterSoulStone.get()) ) {
                    lava = true;
                }
            }
            if(MyGoUtil.hasNature(curios,player, OceanSoulStone.get())){
                water = true;
            }
            if (!player.isInLava() && !player.isCrouching() && event.getFluidState().is(FluidTags.LAVA)&&lava) {
                event.setResult(Event.Result.ALLOW);
            }
            if (!player.isUnderWater() && !player.isInWater() && !player.isCrouching() && event.getFluidState().is(FluidTags.WATER)&&water) {
                event.setResult(Event.Result.ALLOW);
            }
        }
    }
}
