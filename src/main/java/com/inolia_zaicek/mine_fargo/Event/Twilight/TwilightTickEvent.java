package com.inolia_zaicek.mine_fargo.Event.Twilight;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Twilight.TwilightHydraSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class TwilightTickEvent {
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        if (!event.getEntity().isAlive())
            return;
        LivingEntity livingEntity = event.getEntity();
        if (ModList.get().isLoaded("twilightforest")) {
            //九头蛇回血
            if (livingEntity.level().getGameTime() % 20L == 0&& MyGoUtil.hasTwilight(livingEntity, TwilightHydraSoulStone.get())) {
                double dhp = (livingEntity.getMaxHealth() - livingEntity.getHealth()) / (livingEntity.getMaxHealth() * (1 - MyGoConfig.twilight_hydra_soul_stone_last_hp.get()));
                livingEntity.heal((float) (MyGoConfig.twilight_hydra_soul_stone_heal.get() * dhp));
            }
        }
    }
}
