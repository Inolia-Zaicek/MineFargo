package com.inolia_zaicek.mine_fargo.Event.Botania;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Botania.TerrasteelSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Network.Packet.TerraRayPacket;
import com.inolia_zaicek.mine_fargo.Network.TerraRayChannel;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import vazkii.botania.common.item.BotaniaItems;

public class TerraRay {
    @SubscribeEvent
    public static void hurt(PlayerInteractEvent.LeftClickEmpty event) {
        if(ModList.get().isLoaded("botania")) {
            var player = event.getEntity();
            if (MyGoUtil.hasBotania(player, TerrasteelSoulStoneItem.class)
            &&!player.getMainHandItem().is(BotaniaItems.terraSword)&& MyGoConfig.terrasteel_soul_stone_left.get()) {
                    TerraRayChannel.CHANNEL.sendToServer(new TerraRayPacket());
            }
        }
    }
}
