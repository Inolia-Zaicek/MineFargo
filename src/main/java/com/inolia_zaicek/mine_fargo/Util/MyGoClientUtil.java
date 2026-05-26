package com.inolia_zaicek.mine_fargo.Util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class MyGoClientUtil {
    public static Player isCurioEquipped() {
        if(Minecraft.getInstance().player!=null) {
            return Minecraft.getInstance().player;
        }
        return null;
    }
}