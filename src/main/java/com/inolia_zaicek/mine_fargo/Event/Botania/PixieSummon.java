package com.inolia_zaicek.mine_fargo.Event.Botania;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Botania.ElementiumSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import vazkii.botania.common.entity.PixieEntity;

import java.util.Random;
import java.util.Set;

import static com.inolia_zaicek.mine_fargo.Event.TickEvent.elementium_soul_stone;


public class PixieSummon {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if(ModList.get().isLoaded("botania")) {
            Random random = new Random();
            var attacked=event.getEntity();
            var map = attacked.getActiveEffectsMap();
            //进攻
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                var mob = event.getEntity();
                Set<Item> curios = MyGoUtil.getCuriosItems(livingEntity);
                if ( MyGoUtil.hasBotania(curios,livingEntity, ElementiumSoulStone.get())
                        && random.nextInt(100) <= MyGoConfig.elementium_soul_stone_chance.get()*100
                && livingEntity.getPersistentData().getInt(elementium_soul_stone) == 0) {
                    livingEntity.getPersistentData().putInt(elementium_soul_stone,(int)(MyGoConfig.elementium_soul_stone_cooldown.get()*20*2));
                    float atk = (float) livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    float number= (float) (atk*MyGoConfig.elementium_soul_stone_atk.get());
                    PixieEntity entity =new PixieEntity(livingEntity.level());
                    //仇恨目标————主人————？————(伤害？)
                    entity.setProps(mob,livingEntity,0,number);
                    entity.setPos(livingEntity.getRandomX(4),livingEntity.getRandomY()+0.5*livingEntity.getBbHeight(),livingEntity.getRandomZ(4));
                    livingEntity.level().addFreshEntity(entity);

                    attacked.addEffect(new MobEffectInstance(MobEffects.WITHER, (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MobEffects.WITHER)) {
                        map.put(MobEffects.WITHER, new MobEffectInstance(MobEffects.WITHER,
                                (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    }
                    attacked.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MobEffects.WEAKNESS)) {
                        map.put(MobEffects.WEAKNESS, new MobEffectInstance(MobEffects.WEAKNESS,
                                (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    }
                    attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        map.put(MobEffects.MOVEMENT_SLOWDOWN, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                                (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    }
                    attacked.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MobEffects.BLINDNESS)) {
                        map.put(MobEffects.BLINDNESS, new MobEffectInstance(MobEffects.BLINDNESS,
                                (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    }

                }
            }
            //挨打
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                Set<Item> curios = MyGoUtil.getCuriosItems(livingEntity);
                LivingEntity mob = event.getSource().getEntity().getControllingPassenger();
                if ( MyGoUtil.hasBotania(curios,livingEntity, ElementiumSoulStone.get())
                        && random.nextInt(100) <= MyGoConfig.elementium_soul_stone_chance.get()*100
                        && livingEntity.getPersistentData().getInt(elementium_soul_stone) == 0) {
                    livingEntity.getPersistentData().putInt(elementium_soul_stone,(int)(MyGoConfig.elementium_soul_stone_cooldown.get()*20*2));
                    float atk = (float) livingEntity.getAttributeValue(Attributes.ATTACK_DAMAGE);
                    //伤害数额
                    float number= (float) (atk*MyGoConfig.elementium_soul_stone_atk.get());
                    PixieEntity entity =new PixieEntity(livingEntity.level());
                    //仇恨目标————主人————？————(伤害？)
                    entity.setProps(mob,livingEntity,0,number);
                    entity.setPos(livingEntity.getRandomX(4),livingEntity.getRandomY()+0.5*livingEntity.getBbHeight(),livingEntity.getRandomZ(4));
                    livingEntity.level().addFreshEntity(entity);

                    attacked.addEffect(new MobEffectInstance(MobEffects.WITHER, (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MobEffects.WITHER)) {
                        map.put(MobEffects.WITHER, new MobEffectInstance(MobEffects.WITHER,
                                (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    }
                    attacked.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MobEffects.WEAKNESS)) {
                        map.put(MobEffects.WEAKNESS, new MobEffectInstance(MobEffects.WEAKNESS,
                                (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    }
                    attacked.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)) {
                        map.put(MobEffects.MOVEMENT_SLOWDOWN, new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,
                                (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    }
                    attacked.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    if (!attacked.hasEffect(MobEffects.BLINDNESS)) {
                        map.put(MobEffects.BLINDNESS, new MobEffectInstance(MobEffects.BLINDNESS,
                                (int) (MyGoConfig.elementium_soul_stone_time.get()*20), 0));
                    }

                }
            }
        }
    }
}
