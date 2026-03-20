package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.L2.Hostility.ZoneHostilitySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Register.MyGoEffectsRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.EntityTeleportEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class TeleportEvent {
    @SubscribeEvent(priority = EventPriority.HIGH)
    public static void event(EntityTeleportEvent event) {
        //有抑影，阻止传送
        if (event.getEntity() instanceof LivingEntity livingEntity&& livingEntity.hasEffect(MyGoEffectsRegister.Enderference.get())) {
            event.setCanceled(true);
        }

        if (ModList.get().isLoaded("l2hostility")&&event.getEntity() instanceof LivingEntity livingEntity&&!(livingEntity instanceof Player) ) {
            var mobList = MyGoUtil.mobList((int) ((MyGoConfig.zone_hostility_soul_stone_range.get() + 1) / 2), livingEntity);
            var playerList = MyGoUtil.PlayerList((int) ((MyGoConfig.zone_hostility_soul_stone_range.get() + 1) / 2), livingEntity);
            for (Mob mobs : mobList) {
                if (MyGoUtil.hasL2Hostility(mobs, ZoneHostilitySoulStoneItem.class)&& mobs!=livingEntity){
                    event.setCanceled(true);
                }
            }
            for (Player player : playerList) {
                if (MyGoUtil.hasL2Hostility(player, ZoneHostilitySoulStoneItem.class)&& player!=livingEntity){
                    event.setCanceled(true);
                }
            }
        }
    }
}
