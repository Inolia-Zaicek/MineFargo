package com.inolia_zaicek.mine_fargo.Event.Create;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Create.BlazeCakeSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Create.ZincSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Mixins.Create.BlazeBurnerMixin;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import com.simibubi.create.content.kinetics.crank.HandCrankBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.inolia_zaicek.mine_fargo.Event.TickEvent.zinc_soul_stone_time;

public class CreateRightBlockEvent {
    @SubscribeEvent
    public static void event(PlayerInteractEvent.RightClickBlock event) {
        if (event.getEntity() instanceof Player) {
            Player player = event.getEntity();
            BlockPos blockPos = event.getPos();
            Level level = player.level();
            if (MyGoUtil.hasCreate(player, ZincSoulStoneItem.class)&&level.getBlockEntity(blockPos) instanceof HandCrankBlockEntity
                    && MyGoConfig.zinc_soul_stone_use.get() && player.getPersistentData().getInt(zinc_soul_stone_time)==0) {
                player.getPersistentData().putInt(zinc_soul_stone_time, 50);
                int food = player.getFoodData().getFoodLevel();
                if(food<20) {
                    player.getFoodData().setFoodLevel((int) Math.min(food + 1, 20));
                }else {
                    float saturation = player.getFoodData().getSaturationLevel();
                    player.getFoodData().setSaturation((float) Math.min(saturation + 1, 20));
                }
            }
            if (MyGoUtil.hasCreate(player, BlazeCakeSoulStoneItem.class)) {
                //【可进行完全燃烧
                if (MyGoConfig.blaze_cake_soul_stone_super.get()) {
                    //未进入完全燃烧
                    if (level.getBlockEntity(blockPos) instanceof BlazeBurnerBlockEntity burner
                            && !burner.getActiveFuel().equals(BlazeBurnerBlockEntity.FuelType.SPECIAL)) {
                        if (level.isClientSide()) {
                            player.swing(event.getHand());
                        }
                        level.playSound(null, burner.getBlockPos(), SoundEvents.BLAZE_SHOOT, SoundSource.BLOCKS,
                                0.6F, 1.0F);
                        ((BlazeBurnerMixin) burner).setActiveFuel(BlazeBurnerBlockEntity.FuelType.SPECIAL);
                        //当前燃烧时间小于最大燃烧时间
                        if(burner.getRemainingBurnTime()<(int) (MyGoConfig.blaze_cake_soul_stone_max_time.get() * 1) ){
                            ((BlazeBurnerMixin) burner).setRemainingBurnTime(Math.min(burner.getRemainingBurnTime() + (int) (MyGoConfig.blaze_cake_soul_stone_time.get() * 1), (int) (MyGoConfig.blaze_cake_soul_stone_max_time.get() * 1)));
                        }
                    }
                }else{
                    //未进入普通燃烧
                    if (level.getBlockEntity(blockPos) instanceof BlazeBurnerBlockEntity burner
                            && !burner.getActiveFuel().equals(BlazeBurnerBlockEntity.FuelType.NORMAL)) {
                        if (level.isClientSide()) {
                            player.swing(event.getHand());
                        }
                        level.playSound(null, burner.getBlockPos(), SoundEvents.BLAZE_SHOOT, SoundSource.BLOCKS,
                                0.6F, 1.0F);
                        ((BlazeBurnerMixin) burner).setActiveFuel(BlazeBurnerBlockEntity.FuelType.NORMAL);
                        //当前燃烧时间小于最大燃烧时间
                        if(burner.getRemainingBurnTime()<(int) (MyGoConfig.blaze_cake_soul_stone_max_time.get() * 1) ){
                            ((BlazeBurnerMixin) burner).setRemainingBurnTime(Math.min(burner.getRemainingBurnTime() + (int) (MyGoConfig.blaze_cake_soul_stone_time.get() * 1), (int) (MyGoConfig.blaze_cake_soul_stone_max_time.get() * 1)));
                        }
                    }
                }
            }
        }
    }
}