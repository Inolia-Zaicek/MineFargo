package com.inolia_zaicek.mine_fargo.Util;

import net.minecraft.world.entity.LivingEntity;
import org.confluence.terraentity.api.entity.ISummonMob;

public class TerraUtil {
    public static boolean canAttack(LivingEntity attacked) {
        //挨打的是铁魔法随从
        if( attacked instanceof ISummonMob ){
            return false;
        }else{
            return true;
        }
    }
}
