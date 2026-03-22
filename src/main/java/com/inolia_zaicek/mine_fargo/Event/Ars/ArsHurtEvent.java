package com.inolia_zaicek.mine_fargo.Event.Ars;

import com.hollingsworth.arsnouveau.setup.registry.DamageTypesRegistry;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Ars.ArchwoodSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.EscortSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.GoetyDarkSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
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
            if (attacked != null) {
                double number = 1;
                double overNumber = 1;
                double fixedNumber = 0;
                if (ModList.get().isLoaded("goety")) {
                    if (MyGoUtil.hasGoetyItem(attacked, GoetyDarkSoulStone.get())&&(event.getSource().is(DamageTypesRegistry.GENERIC_SPELL_DAMAGE) || event.getSource().is(DamageTypesRegistry.COLD_SNAP)
                            || event.getSource().is(DamageTypesRegistry.FLARE) || event.getSource().is(DamageTypesRegistry.WINDSHEAR) ||
                            event.getSource().is(DamageTypesRegistry.CRUSH))) {
                        number *= 1 - MyGoConfig.goety_dark_soul_stone_magic.get();
                    }
                }
                double damage = (event.getAmount() * number + fixedNumber) * overNumber;
                event.setAmount((float) damage);
            }
            if (event.getSource().getEntity() instanceof LivingEntity attacker&&attacked!=null) {
                var map = attacked.getActiveEffectsMap();
                if(MyGoUtil.hasArs(attacker, ArchwoodSoulStone.get())) {
                    Random random = new Random();
                    if (random.nextInt(100) <= MyGoConfig.archwood_soul_stone_chance.get() * 100) {
                        //爆炸【有配置文件，
                        if(MyGoConfig.archwood_soul_stone_can_red.get()) {
                            if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "blasting"))))) {
                                attacked.addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "blasting"))), (int) (MyGoConfig.archwood_soul_stone_time.get() * 20),
                                        (int) (MyGoConfig.archwood_soul_stone_red.get() - 1)));
                                if (!attacked.hasEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "blasting"))))) {
                                    map.put(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "blasting"))), new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "blasting"))),
                                            (int) (MyGoConfig.archwood_soul_stone_time.get() * 20), (int) (MyGoConfig.archwood_soul_stone_red.get() - 1)));
                                }
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
