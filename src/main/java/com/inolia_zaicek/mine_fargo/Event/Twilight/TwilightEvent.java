package com.inolia_zaicek.mine_fargo.Event.Twilight;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Twilight.FortificationSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Twilight.ZombieScepterSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.common.extensions.IForgeItem;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import twilightforest.capabilities.CapabilityList;
import twilightforest.capabilities.shield.IShieldCapability;
import twilightforest.entity.monster.LoyalZombie;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;

import static com.inolia_zaicek.mine_fargo.Event.TickEvent.fortification_soul_stone_cooldown_time;
import static com.inolia_zaicek.mine_fargo.Event.TickEvent.zombie_scepter_soul_stone_cooldown_time;

public class TwilightEvent {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (ModList.get().isLoaded("twilightforest")) {
            LivingEntity attacked = event.getEntity();
            Level attackedLevel = attacked.level();
            if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != attacker) {

                if (!attackedLevel.isClientSide()) {
                    if (MyGoUtil.hasTwilightLich(attacker, ZombieScepterSoulStoneItem.class) && MyGoConfig.zombie_summon_can.get()
                            && attacker.getPersistentData().getInt(zombie_scepter_soul_stone_cooldown_time) == 0) {
                        attacker.getPersistentData().putInt(zombie_scepter_soul_stone_cooldown_time, (int) (MyGoConfig.zombie_scepter_soul_stone_cooldown.get() * 20 * 2));
                        LoyalZombie zombie = (LoyalZombie) ((EntityType<?>) TFEntities.LOYAL_ZOMBIE.get()).create(attackedLevel);
                        if (zombie != null) {
                            zombie.moveTo(attacked.getOnPos().getX(), attacked.getOnPos().getY() + 1, attacked.getOnPos().getZ());
                            zombie.spawnAnim();
                            zombie.setTame(true);
                            zombie.setOwnerUUID(attacker.getUUID());
                            zombie.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,
                                    (int) (MyGoConfig.zombie_scepter_soul_stone_time.get() * 20), (int) (MyGoConfig.zombie_scepter_soul_stone_strength.get() - 1)));
                            zombie.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST,
                                    (int) (MyGoConfig.zombie_scepter_soul_stone_time.get() * 20), (int) (MyGoConfig.zombie_scepter_soul_stone_hp_boost.get() - 1)));
                            attackedLevel.addFreshEntity(zombie);
                            attackedLevel.gameEvent(attacker, GameEvent.ENTITY_PLACE, attacked.getOnPos());
                            zombie.playSound((SoundEvent) TFSounds.LOYAL_ZOMBIE_SUMMON.get(), 1.0F, zombie.getVoicePitch());
                        }
                    }
                    if (MyGoUtil.hasTwilightLich(attacker, FortificationSoulStoneItem.class)
                            && attacker.getPersistentData().getInt(fortification_soul_stone_cooldown_time) == 0) {
                        attacker.getCapability(CapabilityList.SHIELDS).ifPresent(IShieldCapability::replenishShields);
                        attacker.getPersistentData().putInt(fortification_soul_stone_cooldown_time, (int) (MyGoConfig.fortification_soul_stone_cooldown.get() * 20 * 2));
                    }
                }
            }
        }
    }
}
