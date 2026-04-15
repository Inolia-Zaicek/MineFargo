package com.inolia_zaicek.mine_fargo.Event.Iron;

import com.gametechbc.gtbcs_geomancy_plus.util.GGDamageTypes;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Iron.EarthSectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Iron.FantacySectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Iron.SoundSectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import com.mega.uom.common.damagesource.ModDamageSources;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
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
                        &&MyGoUtil.hasSupernatural(attacker, FantacySectSoulStone.get())
                ) {
                    number += MyGoConfig.fantasy_sect_soul_stone_damage.get();
                }else if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC)||event.getSource().is(ISSDamageTypes.ICE_MAGIC)
                        ||event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC)||event.getSource().is(ISSDamageTypes.EVOCATION_MAGIC)
                        ||event.getSource().is(ISSDamageTypes.BLOOD_MAGIC)||event.getSource().is(ISSDamageTypes.HOLY_MAGIC)
                        ||event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC)||event.getSource().is(ISSDamageTypes.ENDER_MAGIC)
                        ||event.getSource().is(ISSDamageTypes.NATURE_MAGIC)
                        || (ModList.get().isLoaded("traveloptics")
                        && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                        && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                ) || (ModList.get().isLoaded("familiarslib")
                                && event.getSource().type().msgId().equals(new ResourceLocation("familiarslib", "sound_magic"))
                )
                ) {
                    number += MyGoConfig.fantasy_sect_soul_stone_damage.get()*MyGoConfig.fantacy_sect_soul_stone_other.get();
                }
                double damage = (event.getAmount() * number + fixedNumber) * overNumber;
                event.setAmount((float) damage);
            }
        }
    }
}