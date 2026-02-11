package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Ars.WhirlisprigSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.MushroomSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

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
        if (ModList.get().isLoaded("ars_nouveau")) {
            if (MyGoUtil.hasNature(event.getEntity(), WhirlisprigSoulStoneItem.class) &&
                    //可食用
                    (event.getItem().getItem().isEdible() && event.getEntity() instanceof Player player)) {
                int food = player.getFoodData().getFoodLevel();
                float saturation = player.getFoodData().getSaturationLevel();
                int itemFood = event.getItem().getItem().getFoodProperties().getNutrition();
                float itemSaturation = event.getItem().getItem().getFoodProperties().getSaturationModifier();
                //自己的食物数据+物品的额外食物数据
                player.getFoodData().setFoodLevel((int) Math.min(food + MyGoConfig.whirlisprig_soul_stone_food.get() * itemFood, 20));
                player.getFoodData().setSaturation((int) Math.min(saturation + MyGoConfig.whirlisprig_soul_stone_food.get() * itemSaturation, 20));
            }
        }
    }
}
