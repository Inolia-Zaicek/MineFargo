package com.inolia_zaicek.mine_fargo.Event.Iron;

import com.gametechbc.traveloptics.util.TravelopticsDamageTypes;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Iron.SoundSectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Iron.EvocationSectSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
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
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;

public class AFHurtEvent {

    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (ModList.get().isLoaded("alshanex_familiars")) {
            LivingEntity attacked = event.getEntity();
            if (attacked != null) {
                double number = 1;
                double overNumber = 1;
                double fixedNumber = 0;
                String killedEntityId = EntityType.getKey(attacked.getType()).toString();
                if(killedEntityId.equals("alshanex_familiars:archmage_pet")||killedEntityId.equals("alshanex_familiars:bard_pet")
                        ||killedEntityId.equals("alshanex_familiars:cleric_pet")||killedEntityId.equals("alshanex_familiars:druid_pet")
                        ||killedEntityId.equals("alshanex_familiars:hunter_pet")||killedEntityId.equals("alshanex_familiars:illusionist_pet")
                        ||killedEntityId.equals("alshanex_familiars:mage_pet")||killedEntityId.equals("alshanex_familiars:necromancer_pet")
                        ||killedEntityId.equals("alshanex_familiars:plague_pet")||killedEntityId.equals("alshanex_familiars:scorcher_pet")
                        ||killedEntityId.equals("alshanex_familiars:summoner_pet")
                ) {
                    var playerList = MyGoUtil.PlayerList(22, attacked);
                    var mobList = MyGoUtil.mobList(22, attacked);
                    boolean up = false;
                    for (Mob mobs : mobList) {
                        if (MyGoUtil.hasSpecificItem(mobs, SoundSectSoulStoneItem.class)) {
                            up = true;
                        }
                    }
                    for (Player players : playerList) {
                        if (MyGoUtil.hasSpecificItem(players, SoundSectSoulStoneItem.class)) {
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
                if (MyGoUtil.hasSpecificItem(attacker, SoundSectSoulStoneItem.class)) {
                    if (event.getSource().type().msgId().equals(new ResourceLocation("alshanex_familiars", "sound_magic"))
                    ) {
                        number += MyGoConfig.sound_sect_soul_stone_owner_damage.get();
                    }
                }
                String killedEntityId = EntityType.getKey(attacker.getType()).toString();
                if(killedEntityId.equals("alshanex_familiars:archmage_pet")||killedEntityId.equals("alshanex_familiars:bard_pet")
                        ||killedEntityId.equals("alshanex_familiars:cleric_pet")||killedEntityId.equals("alshanex_familiars:druid_pet")
                        ||killedEntityId.equals("alshanex_familiars:hunter_pet")||killedEntityId.equals("alshanex_familiars:illusionist_pet")
                        ||killedEntityId.equals("alshanex_familiars:mage_pet")||killedEntityId.equals("alshanex_familiars:necromancer_pet")
                        ||killedEntityId.equals("alshanex_familiars:plague_pet")||killedEntityId.equals("alshanex_familiars:scorcher_pet")
                        ||killedEntityId.equals("alshanex_familiars:summoner_pet")
                ) {
                    var playerList = MyGoUtil.PlayerList(22, attacker);
                    var mobList = MyGoUtil.mobList(22, attacker);
                    boolean up = false;
                    for (Mob mobs : mobList) {
                        if (MyGoUtil.hasSpecificItem(mobs, SoundSectSoulStoneItem.class)) {
                            up = true;
                        }
                    }
                    for (Player players : playerList) {
                        if (MyGoUtil.hasSpecificItem(players, SoundSectSoulStoneItem.class)) {
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