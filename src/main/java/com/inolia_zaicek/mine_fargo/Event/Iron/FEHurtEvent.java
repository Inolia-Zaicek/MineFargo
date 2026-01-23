package com.inolia_zaicek.mine_fargo.Event.Iron;

import com.gametechbc.gtbcs_geomancy_plus.util.GGDamageTypes;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Iron.EarthSectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Iron.FantacySectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Iron.SoundSectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import com.mega.uom.common.damagesource.ModDamageSources;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class FEHurtEvent {

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (ModList.get().isLoaded("fantasy_ending")) {
            LivingEntity attacked = event.getEntity();
            if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != null) {
                double number = 1;
                double overNumber = 1;
                double fixedNumber = 0;
                var map = attacked.getActiveEffectsMap();
                if (
                        (event.getSource().is(ModDamageSources.DS_SOURCE)||event.getSource().is(ModDamageSources.FE_SOURCE))
                        &&MyGoUtil.hasSpecificItem(attacker, FantacySectSoulStoneItem.class)
                ) {
                    number += MyGoConfig.fantasy_sect_soul_stone_damage.get();
                }
                double damage = (event.getAmount() * number + fixedNumber) * overNumber;
                event.setAmount((float) damage);
            }
        }
    }
}