package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.LapisLazuliSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingExperienceDropEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@SuppressWarnings({"all", "removal"})
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MineFargo.MODID)
public class ExpEvent {
    @SubscribeEvent
    public static void even1t(LivingExperienceDropEvent event) {
        LivingEntity livingEntity = event.getEntity();
        Player player = event.getAttackingPlayer();
        if(player!=null&&livingEntity!=null) {
            Set<Item> curios = MyGoUtil.getCuriosItems(player);
            float number = 1;
            if (MyGoUtil.hasOre(curios,player, LapisLazuliSoulStone.get())) {
                number += MyGoConfig.lapis_lazuli_soul_stone.get();
            }
            //迎战
            if (ModList.get().isLoaded("meetyourfight")) {
                //暮光
                if (MyGoUtil.hasMeetFight(player, VioletSoulStone.get())) {
                    number += MyGoConfig.violet_soul_stone_exp.get();
                }
            }
            event.setDroppedExperience((int) (event.getDroppedExperience() * number));
        }
    }
}
