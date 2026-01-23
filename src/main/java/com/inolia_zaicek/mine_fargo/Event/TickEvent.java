package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Ars.AmethystGolemSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Ars.ArchwoodSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Ars.FirenandoSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Entity.AquaticSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.ForestSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.LavaSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.LushSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MineFargo.MODID)
public class TickEvent {
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        if (!event.getEntity().isAlive())
            return;
        LivingEntity livingEntity = event.getEntity();
        if (MyGoUtil.hasNature(livingEntity, LavaSoulStoneItem.class)&&livingEntity.getRemainingFireTicks()>0){
            livingEntity.setRemainingFireTicks(0);
        }
        if (livingEntity.level().getGameTime() % 10L == 0) {
            if (MyGoUtil.hasEntity(livingEntity, AquaticSoulStoneItem.class)){
                livingEntity.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING,300,0,false,false,false));
            }
            //新生魔艺部分
            if (ModList.get().isLoaded("ars_nouveau")) {
                if (ModList.get().isLoaded("ars_elemental")) {
                    if (MyGoUtil.hasSpecificItem(livingEntity, ArchwoodSoulStoneItem.class)) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.DOLPHINS_GRACE, 300, 1, false, false, false));
                    }
                    if (MyGoUtil.hasSpecificItem(livingEntity, FirenandoSoulStoneItem.class)) {
                        livingEntity.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300, 0, false, false, false));
                    }
                }
                int shielding = 0;
                //护盾等级分开计算
                if(MyGoUtil.hasArs(livingEntity, AmethystGolemSoulStoneItem.class)){
                    shielding+=(int)(MyGoConfig.amethyst_golem_soul_stone_level.get()*1);
                }
                if(MyGoUtil.hasArs(livingEntity, ArchwoodSoulStoneItem.class)){
                    shielding+=(int)(MyGoConfig.archwood_soul_stone_purple.get()*1);
                }
                if (shielding>0) {
                    livingEntity.addEffect(new MobEffectInstance(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "shielding"))),
                            300, shielding-1, false, false, false));
                }
                //康复
                if(MyGoUtil.hasArs(livingEntity, ArchwoodSoulStoneItem.class)){
                    livingEntity.addEffect(new MobEffectInstance(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("ars_nouveau", "recovery"))),
                            300, (int)(MyGoConfig.archwood_soul_stone_green.get()*1-1), false, false, false));
                }
            }
        }
        if (livingEntity.level().getGameTime() % 60L == 0) {
            if(livingEntity instanceof Player player&& MyGoUtil.hasNature(player, LushSoulStoneItem.class)) {
                float saturation = player.getFoodData().getSaturationLevel();
                player.getFoodData().setSaturation((float) Math.min(saturation + MyGoConfig.lush_soul_stone_saturation.get(), 20));
                int food = player.getFoodData().getFoodLevel();
                player.getFoodData().setFoodLevel((int) Math.min(food + MyGoConfig.lush_soul_stone_food.get(), 20));
            }
        }

        //森林催熟
        if(livingEntity.level().getGameTime() % (20L*MyGoConfig.forest_soul_stone.get()) == 0
               &&  MyGoUtil.hasNature(livingEntity, ForestSoulStoneItem.class)&&livingEntity.isShiftKeyDown()) {
            Level world = livingEntity.level();
            Random random = new Random();
            float rx = random.nextFloat() * 5.0F - 3.0F;
            float rz = random.nextFloat() * 5.0F - 3.0F;
            float ry = random.nextFloat() * 2.0F - 2.0F;
            float ry0 = random.nextFloat() * 2.0F;

            // **修复点1：粒子效果的处理**
            // 粒子效果通常在客户端显示。
            // 如果是服务器端，ServerLevel 会将粒子发送到客户端。
            // 如果是客户端，ClientLevel 可以直接渲染粒子。
            if (world instanceof ServerLevel serverLevel) { // 如果是服务器端，安全地转换为 ServerLevel
                serverLevel.sendParticles(ParticleTypes.HAPPY_VILLAGER, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 1, 0.5D, 1.0D, 0.5D, 0.1D);
            } else { // 如果是客户端（ClientLevel），则在本地生成粒子
                world.addParticle(ParticleTypes.HAPPY_VILLAGER, livingEntity.getX(), livingEntity.getY(), livingEntity.getZ(), 0.5D, 1.0D, 0.5D); // 参数可能需要调整
            }


            // **修复点2：骨粉效果只在服务器端执行**
            // BonemealableBlock 的 performBonemeal 方法会修改方块状态，这是服务器端的行为。
            // 因此，必须确保只在服务器端调用此逻辑。
            if (!world.isClientSide) { // 只有在服务器端才执行骨粉逻辑
                BlockPos posBelow = livingEntity.blockPosition().below();
                BlockState blockStateBelow = world.getBlockState(posBelow);
                Block below = blockStateBelow.getBlock();

                if (below == Blocks.GRASS_BLOCK) {
                    BlockPos pos = new BlockPos((int) ((float) livingEntity.blockPosition().getX() + rx), (int) ((float) livingEntity.blockPosition().getY() + ry), (int) ((float) livingEntity.blockPosition().getZ() + rz));
                    BlockState blockstate = world.getBlockState(pos);
                    Block var17 = blockstate.getBlock();
                    if (var17 instanceof BonemealableBlock) {
                        BonemealableBlock igrowable = (BonemealableBlock) var17;
                        // 确保 world 是 ServerLevel 才能调用 performBonemeal
                        if (igrowable.isValidBonemealTarget(world, pos, blockstate, false) // isClientSide 应该为 false 在服务端
                                && world instanceof ServerLevel serverWorld // 再次确认是 ServerLevel
                                && igrowable.isBonemealSuccess(world, world.random, pos, blockstate)) {
                            igrowable.performBonemeal(serverWorld, world.random, pos, blockstate);
                        }
                    }
                } else {
                    BlockPos pos = new BlockPos((int) ((float) livingEntity.blockPosition().getX() + rx), (int) ((float) livingEntity.blockPosition().getY() + ry0), (int) ((float) livingEntity.blockPosition().getZ() + rz));
                    BlockState blockstate = world.getBlockState(pos);
                    Block var22 = blockstate.getBlock();
                    if (var22 instanceof BonemealableBlock) {
                        BonemealableBlock igrowable = (BonemealableBlock) var22;
                        // 确保 world 是 ServerLevel 才能调用 performBonemeal
                        if (igrowable.isValidBonemealTarget(world, pos, blockstate, false) // isClientSide 应该为 false 在服务端
                                && world instanceof ServerLevel serverWorld // 再次确认是 ServerLevel
                                && igrowable.isBonemealSuccess(world, world.random, pos, blockstate)) {
                            igrowable.performBonemeal(serverWorld, world.random, pos, blockstate);
                        }
                    }
                }
            }
        }
    }
}
