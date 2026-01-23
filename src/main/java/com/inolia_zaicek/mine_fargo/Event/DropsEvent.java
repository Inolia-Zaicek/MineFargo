package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Ars.DrygmySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Entity.AnimalSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;
import java.util.Objects;

public class DropsEvent {
    @SubscribeEvent
    public static void entityKilled(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            if (event.getSource().getEntity() instanceof LivingEntity livingEntity) {
                int number = 0;
                if (MyGoUtil.hasEntity(livingEntity, AnimalSoulStoneItem.class)) {
                    number += (int)(MyGoConfig.animal_soul_stone.get()*1);
                }
                if (MyGoUtil.hasArs(livingEntity, DrygmySoulStoneItem.class)) {
                    number += (int)(MyGoConfig.drygmy_soul_stone_drop.get()*1);
                }
                if (number > 0) {
                    for (int i = 0; i < number; i++) {
                        Level level = livingEntity.level();
                        LootTable loot = ((MinecraftServer) Objects.requireNonNull(level.getServer())).getLootData().getLootTable(event.getEntity().getType().getDefaultLootTable());
                        LootParams context = (new LootParams.Builder((ServerLevel) level)).withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(event.getEntity().blockPosition())).withParameter(LootContextParams.THIS_ENTITY, event.getEntity()).withParameter(LootContextParams.DAMAGE_SOURCE, livingEntity.damageSources().playerAttack((Player) livingEntity)).create(LootContextParamSets.ENTITY);
                        List<ItemStack> drops = loot.getRandomItems(context);
                        for (ItemStack drop : drops) {
                            ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(), event.getEntity().getY(), event.getEntity().getZ(), drop);
                            itementity.setDefaultPickUpDelay();
                            itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F), (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                            level.addFreshEntity(itementity);
                        }
                    }
                }
            }
        }
    }
}