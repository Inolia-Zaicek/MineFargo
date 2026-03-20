package com.inolia_zaicek.mine_fargo.Event.Tacz;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Entity.DuneSentinelSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Tacz.RifleSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Tacz.ShotgunSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Tacz.SniperRifleSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import com.tacz.guns.init.ModDamageTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static com.inolia_zaicek.mine_fargo.Event.Tacz.TaczTickEvent.sniper_rifle_soul_stone;
import static com.inolia_zaicek.mine_fargo.Event.Tacz.TaczTickEvent.rifle_soul_stone_number;
import static net.minecraft.tags.DamageTypeTags.IS_EXPLOSION;
import static net.minecraft.tags.DamageTypeTags.IS_PROJECTILE;

public class TaczHurtEvent {
    private static final UUID uuid = UUID.fromString("D2E5C4FB-613D-17E9-84B7-CF106C696BAC");
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (ModList.get().isLoaded("tacz")) {
            LivingEntity attacked = event.getEntity();
            if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != null) {
                double number = 1;
                double overNumber = 1;
                double fixedNumber = 0;
                if (event.getSource().is(ModDamageTypes.BULLETS_TAG) || event.getSource().is(ModDamageTypes.BULLET)
                        || event.getSource().is(ModDamageTypes.BULLET_VOID) || event.getSource().is(ModDamageTypes.BULLET_IGNORE_ARMOR)
                        || event.getSource().is(ModDamageTypes.BULLET_VOID_IGNORE_ARMOR)) {
                    if (MyGoUtil.hasLegendaryEntity(attacker, DuneSentinelSoulStoneItem.class) ) {
                        number += MyGoConfig.dune_sentinel_soul_stone_up.get();
                    }
                    if (ModList.get().isLoaded("l2damagetracker")||ModList.get().isLoaded("celestial_artifacts")) {
                        if (attacker.getAttributes().hasAttribute(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("l2damagetracker", "bow_strength"))))) {
                            overNumber *= attacker.getAttributeValue(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("l2damagetracker", "bow_strength"))));
                        }
                    }
                    //霰弹枪
                    if (MyGoUtil.hasTacz(attacker, ShotgunSoulStoneItem.class)){
                        Optional.of(attacked)
                                .map(LivingEntity::getAttributes)
                                .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                                .map(manager -> manager.getInstance(Attributes.ARMOR))
                                .filter(instance -> instance.getModifier(uuid) == null)
                                .ifPresent(instance -> instance.addTransientModifier(
                                        new AttributeModifier(uuid, "shotgun_soul_stone",
                                                MyGoConfig.shotgun_soul_stone.get() * -1, AttributeModifier.Operation.MULTIPLY_TOTAL)));
                    }
                    //暗杀
                    if (MyGoUtil.hasTacz(attacker, SniperRifleSoulStoneItem.class)
                    && attacker.getPersistentData().getInt(sniper_rifle_soul_stone) > 0) {
                        //最高多少秒
                        float max = (float) (MyGoConfig.sniper_rifle_soul_stone_time.get() * 2 * 20 );
                        //当前等了多久
                        float now = attacker.getPersistentData().getInt(sniper_rifle_soul_stone);
                        number += (MyGoConfig.sniper_rifle_soul_stone_damage.get()*now/(max) );
                        //造成伤害才清零
                        attacker.getPersistentData().putInt(sniper_rifle_soul_stone, 0);
                    }
                    //速射
                    if (MyGoUtil.hasTacz(attacker, RifleSoulStoneItem.class)
                            && attacker.getPersistentData().getInt(rifle_soul_stone_number) > 0) {
                        //最高多少次
                        float max = (float) (MyGoConfig.rifle_soul_stone_number.get()*1);
                        //当前射击次数
                        float now = attacker.getPersistentData().getInt(rifle_soul_stone_number);
                        number += (MyGoConfig.rifle_soul_stone_damage.get()*now/max);
                    }
                }
                float damage = (float) ((event.getAmount() * number + fixedNumber) * overNumber);
                event.setAmount(damage);
            }
        }
    }
    @SubscribeEvent
    public static void onLivingDamage(LivingDamageEvent event) {
        if (ModList.get().isLoaded("curios")) {
            if (event.getSource().getEntity() instanceof LivingEntity attacker && MyGoUtil.hasTacz(attacker, ShotgunSoulStoneItem.class)) {
                Optional.of(event.getEntity())
                        .map(LivingEntity::getAttributes)
                        .filter(manager -> manager.hasAttribute(Attributes.ARMOR))
                        .map(manager -> manager.getInstance(Attributes.ARMOR))
                        .ifPresent(instance -> instance.removeModifier(uuid));
            }
        }
    }
}