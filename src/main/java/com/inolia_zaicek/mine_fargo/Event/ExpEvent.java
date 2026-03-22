package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.LapisLazuliSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@SuppressWarnings({"all", "removal"})
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MineFargo.MODID)
public class ExpEvent {
    @SubscribeEvent
    public static void even1t(LivingExperienceDropEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Player player = event.getAttackingPlayer();
        if(player!=null&&livingEntity!=null) {
            float number = 1;
            if (MyGoUtil.hasOre(player, LapisLazuliSoulStoneItem.class)) {
                number += MyGoConfig.lapis_lazuli_soul_stone.get();
            }
            event.setDroppedExperience((int) (event.getDroppedExperience() * number));
        }
    }
}
