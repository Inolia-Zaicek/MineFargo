package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.MushroomSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.Random;

public class UseItemEvent {
    @SubscribeEvent
    public static void useItemEvent(LivingEntityUseItemEvent.Finish event) {
        //蘑菇煲
        if (MyGoUtil.hasNature(event.getEntity(), MushroomSoulStoneItem.class) &&
                //碗装食物
                (event.getItem().getItem() instanceof BowlFoodItem)) {
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,400,0));
            double chance = 1+ MyGoConfig.mushroom_soul_stone.get();
            if (new Random().nextInt(100) <= ((chance - 1) * 100)) {
                event.setResultStack(event.getItem());
            }
        }
    }
}
