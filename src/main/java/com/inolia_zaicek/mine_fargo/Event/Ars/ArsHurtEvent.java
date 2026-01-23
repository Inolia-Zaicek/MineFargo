package com.inolia_zaicek.mine_fargo.Event.Ars;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Ars.ArchwoodSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;

public class ArsHurtEvent {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (ModList.get().isLoaded("ars_nouveau")) {
            LivingEntity attacked = event.getEntity();
            if (event.getSource().getEntity() instanceof LivingEntity attacker&&attacked!=null) {
                var map = attacked.getActiveEffectsMap();
                if(MyGoUtil.hasArs(attacker, ArchwoodSoulStoneItem.class)) {
                    Random random = new Random();
                    if (random.nextInt(100) <= MyGoConfig.archwood_soul_stone_chance.get() * 100) {
                        //爆炸
                        if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "blasting"))))) {
                            attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "blasting"))), (int) (MyGoConfig.archwood_soul_stone_time.get() * 20),
                                    (int) (MyGoConfig.archwood_soul_stone_red.get() - 1)));
                            if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "blasting"))))) {
                                map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "blasting"))), new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "blasting"))),
                                        (int) (MyGoConfig.archwood_soul_stone_time.get() * 20), (int) (MyGoConfig.archwood_soul_stone_red.get() - 1)));
                            }
                        }
                        //冻结
                        attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "freezing"))), (int) (MyGoConfig.archwood_soul_stone_time.get() * 20),
                                (int) (MyGoConfig.archwood_soul_stone_red.get() - 1)));
                        if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "freezing"))))) {
                            map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "freezing"))), new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "freezing"))),
                                    (int) (MyGoConfig.archwood_soul_stone_time.get() * 20), (int) (MyGoConfig.archwood_soul_stone_red.get() - 1)));
                        }
                    }
                }
            }
        }
    }
}
