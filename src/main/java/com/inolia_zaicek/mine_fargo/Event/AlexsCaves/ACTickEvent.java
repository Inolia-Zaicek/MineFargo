package com.inolia_zaicek.mine_fargo.Event.AlexsCaves;

import com.github.alexmodguy.alexscaves.server.entity.ACEntityRegistry;
import com.github.alexmodguy.alexscaves.server.entity.item.DinosaurSpiritEntity;
import com.github.alexmodguy.alexscaves.server.misc.ACKeybindRegistry;
import com.github.alexmodguy.alexscaves.server.misc.ACSoundRegistry;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.AlexsCaves.ForlornSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.AlexsCaves.PrimitiveSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.AlexsCaves.ToxicSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Network.Packet.ClientToServerPacket;
import com.inolia_zaicek.mine_fargo.Network.TerraRayChannel;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;

import static com.inolia_zaicek.mine_fargo.Event.TickEvent.forlorn_soul_stone_c_to_s;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Set;

public class ACTickEvent {
    public static final String forlorn_soul_stone_buff = MineFargo.MODID + ":forlorn_soul_stone_buff";
    public static final String primitive_soul_stone_damage = MineFargo.MODID + ":primitive_soul_stone_damage";
    public static final String primitive_soul_stone_buff = MineFargo.MODID + ":primitive_soul_stone_buff";
    public static final String forlorn_soul_stone = MineFargo.MODID + ":forlorn_soul_stone";
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        if (ModList.get().isLoaded("alexscaves")) {
            if (!event.getEntity().isAlive())
                return;
            LivingEntity livingEntity = event.getEntity();
            Level level = livingEntity.level();
            Set<Item> curios = MyGoUtil.getCuriosItems(livingEntity);
            if (MyGoUtil.hasAlexsCaves(curios,livingEntity, ToxicSoulStone.get())) {
                livingEntity.removeEffect(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("alexscaves", "irradiated"))));
            }
            if (livingEntity.getPersistentData().getInt(primitive_soul_stone_damage) > 0) {
                livingEntity.getPersistentData().putInt(primitive_soul_stone_damage,
                        livingEntity.getPersistentData().getInt(primitive_soul_stone_damage) - 1);
            }
            if (livingEntity.getPersistentData().getInt(forlorn_soul_stone) > 0) {
                livingEntity.getPersistentData().putInt(forlorn_soul_stone,
                        livingEntity.getPersistentData().getInt(forlorn_soul_stone) - 1);
            }
            if (livingEntity.getPersistentData().getInt(primitive_soul_stone_buff) > 0) {
                livingEntity.getPersistentData().putInt(primitive_soul_stone_buff,
                        livingEntity.getPersistentData().getInt(primitive_soul_stone_buff) - 1);
            }
            if (MyGoUtil.hasAlexsCaves(curios,livingEntity, PrimitiveSoulStone.get())) {
                livingEntity.addEffect(new MobEffectInstance(
                        Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("alexscaves", "rage"))),
                        100, (int) (MyGoConfig.primitive_soul_stone_buff.get() - 1)));
            }
            //异寂——客户端or服务端
            if (livingEntity.level().isClientSide()) {
                if (MyGoUtil.hasAlexsCaves(curios,livingEntity, ForlornSoulStone.get()) && livingEntity.getPersistentData().getInt(forlorn_soul_stone_buff) == 0 &&
                        ACKeybindRegistry.KEY_SPECIAL_ABILITY.isDown()) {
                    //客户端冷却
                    livingEntity.getPersistentData().putInt(forlorn_soul_stone_buff, (int) (MyGoConfig.forlorn_soul_stone_buff.get() * 20 * 2));
                    //发包
                    TerraRayChannel.CHANNEL.sendToServer(new ClientToServerPacket(5));
                }
                //异寂——冷却
                if (livingEntity.getPersistentData().getInt(forlorn_soul_stone_buff) > 0) {
                    livingEntity.getPersistentData().putInt(forlorn_soul_stone_buff,
                            livingEntity.getPersistentData().getInt(forlorn_soul_stone_buff) - 1);
                }
            } else {
                //收到信息，归零
                if (livingEntity.getPersistentData().getInt(forlorn_soul_stone_c_to_s) > 0) {
                    livingEntity.getPersistentData().putInt(forlorn_soul_stone_c_to_s, 0);
                    //服务端，上buff，冷却处理位于客户端
                    livingEntity.addEffect(new MobEffectInstance(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("alexscaves", "darkness_incarnate"))),
                            (int) (MyGoConfig.forlorn_soul_stone_time.get() * 20), 0));
                }
            }
        }
    }
}
