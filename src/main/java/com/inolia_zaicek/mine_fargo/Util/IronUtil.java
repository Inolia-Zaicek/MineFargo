package com.inolia_zaicek.mine_fargo.Util;

import io.redspace.ironsspellbooks.entity.mobs.MagicSummon;
import net.minecraft.world.entity.LivingEntity;

public class IronUtil {
    public static boolean canAttack(LivingEntity attacked, LivingEntity attacker) {
        //挨打的是铁魔法随从
        if( attacked instanceof MagicSummon magicSummonMob && magicSummonMob.getSummoner()!=null && magicSummonMob.getSummoner() == attacker){
            return false;
        }else{
            return true;
        }
    }
}
