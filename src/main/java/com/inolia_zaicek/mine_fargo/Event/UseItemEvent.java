package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Ars.WhirlisprigSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.MushroomSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;

public class UseItemEvent {
    @SubscribeEvent
    public static void useItemEvent(LivingEntityUseItemEvent.Finish event) {
        //蘑菇煲
        if (MyGoUtil.hasNature(event.getEntity(), MushroomSoulStone.get()) &&
                //碗装食物
                (event.getItem().getItem() instanceof BowlFoodItem)) {
            event.getEntity().addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST,400,0));
            double chance = 1+ MyGoConfig.mushroom_soul_stone.get();
            if (new Random().nextInt(100) <= ((chance - 1) * 100)) {
                event.setResultStack(event.getItem());
            }
        }
        if (ModList.get().isLoaded("ars_nouveau")) {
            if (MyGoUtil.hasNature(event.getEntity(), WhirlisprigSoulStone.get()) &&
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
        //农夫乐事
        if (ModList.get().isLoaded("farmersdelight")) {
            if (MyGoUtil.isCurioEquipped(event.getEntity(), MyGoItemRegister.FarmerDelightSoulStone.get() ) && event.getItem().getItem().isEdible() ) {
                if (new Random().nextInt(100) <= (MyGoConfig.farmers_delight_soul_stone_chance_1.get() * 100)) {
                    event.getEntity().addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                            new ResourceLocation("farmersdelight", "nourishment"))),
                            (int) (MyGoConfig.farmers_delight_soul_stone_time_1.get() * 20), 0));
                }
                if (new Random().nextInt(100) <= (MyGoConfig.farmers_delight_soul_stone_chance_2.get() * 100)) {
                    event.getEntity().addEffect(new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(
                            new ResourceLocation("farmersdelight", "comfort"))),
                            (int) (MyGoConfig.farmers_delight_soul_stone_time_2.get() * 20), 0));
                }
            }
        }
    }
}
