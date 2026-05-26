package com.inolia_zaicek.mine_fargo.Event.Iron;

import com.gametechbc.traveloptics.util.TravelopticsDamageTypes;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Iron.SoundSectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Iron.EvocationSectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import io.redspace.ironsspellbooks.damage.ISSDamageTypes;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;
import java.util.Set;

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.SoundSectSoulStone;

public class AFHurtEvent {

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (ModList.get().isLoaded("familiarslib")) {
            LivingEntity attacked = event.getEntity();
            if (attacked != null) {
                double number = 1;
                double overNumber = 1;
                double fixedNumber = 0;
                String killedEntityId = EntityType.getKey(attacked.getType()).toString();
                if(attacked instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null) {
                    var playerList = MyGoUtil.PlayerList(22, attacked);
                    var mobList = MyGoUtil.mobList(22, attacked);
                    boolean up = false;
                    for (Mob mobs : mobList) {
                        if (MyGoUtil.isCurioEquipped(mobs, SoundSectSoulStone.get())&&mobs==ownableEntity.getOwner()) {
                            up = true;
                        }
                    }
                    for (Player players : playerList) {
                        if (MyGoUtil.isCurioEquipped(players, SoundSectSoulStone.get())&&players==ownableEntity.getOwner()) {
                            up = true;
                        }
                    }
                    if (up) {
                        overNumber*=1-MyGoConfig.sound_sect_soul_stone_armor.get();
                    }
                }
                double damage = (event.getAmount() * number + fixedNumber) * overNumber;
                event.setAmount((float) damage);
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != null) {
                double number = 1;
                double overNumber = 1;
                double fixedNumber = 0;
                var map = attacked.getActiveEffectsMap();
                Set<Item> curios = MyGoUtil.getCuriosItems(attacker);
                if (curios.contains( SoundSectSoulStone.get())) {
                    if (event.getSource().type().msgId().equals(new ResourceLocation("familiarslib", "sound_magic"))
                    ) {
                        number += MyGoConfig.sound_sect_soul_stone_owner_damage.get();
                    }else if (event.getSource().is(ISSDamageTypes.FIRE_MAGIC)||event.getSource().is(ISSDamageTypes.ICE_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.LIGHTNING_MAGIC)||event.getSource().is(ISSDamageTypes.EVOCATION_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.BLOOD_MAGIC)||event.getSource().is(ISSDamageTypes.HOLY_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.ELDRITCH_MAGIC)||event.getSource().is(ISSDamageTypes.ENDER_MAGIC)
                            ||event.getSource().is(ISSDamageTypes.NATURE_MAGIC)
                            || (ModList.get().isLoaded("traveloptics")
                            && event.getSource().type().msgId().equals(new ResourceLocation("traveloptics", "aqua_magic"))
                    ) || (ModList.get().isLoaded("gtbcs_geomancy_plus")
                            && event.getSource().type().msgId().equals(new ResourceLocation("gtbcs_geomancy_plus", "geo_magic"))
                    ) || (ModList.get().isLoaded("fantasy_ending")
                            && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "ds_power"))
                    ) || (ModList.get().isLoaded("fantasy_ending")
                            && event.getSource().type().msgId().equals(new ResourceLocation("fantasy_ending", "fe_power"))
                    )
                    ) {
                        number += MyGoConfig.sound_sect_soul_stone_owner_damage.get()*MyGoConfig.sound_sect_soul_stone_other.get();
                    }
                }
                if(attacked instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null) {
                    var playerList = MyGoUtil.PlayerList(22, attacker);
                    var mobList = MyGoUtil.mobList(22, attacker);
                    boolean up = false;
                    for (Mob mobs : mobList) {
                        if (MyGoUtil.isCurioEquipped(mobs, SoundSectSoulStone.get())&&mobs==ownableEntity.getOwner()) {
                            up = true;
                        }
                    }
                    for (Player players : playerList) {
                        if (MyGoUtil.isCurioEquipped(players, SoundSectSoulStone.get())&&players==ownableEntity.getOwner()) {
                            up = true;
                        }
                    }
                    if (up) {
                        number += MyGoConfig.sound_sect_soul_stone_damage.get();
                    }
                }
                double damage = (event.getAmount() * number + fixedNumber) * overNumber;
                event.setAmount((float) damage);
            }
        }
    }
}