package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

public class SonsOfSinsDropEvent {

    @SubscribeEvent
    public static void drop(LivingDropsEvent event) {
        if (ModList.get().isLoaded("sons_of_sins")) {
            Level level = event.getEntity().level();
            if (EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:butcher")) {
                ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(),
                        event.getEntity().getY(), event.getEntity().getZ(),
                        MyGoItemRegister.WrathSinsSoulStone.get()
                                .getDefaultInstance());
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                        (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                level.addFreshEntity(itementity);
            }else if (EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:blud")) {
                ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(),
                        event.getEntity().getY(), event.getEntity().getZ(),
                        MyGoItemRegister.GreedSinsSoulStone.get()
                                .getDefaultInstance());
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                        (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                level.addFreshEntity(itementity);
            }else if (EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:curse")) {
                ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(),
                        event.getEntity().getY(), event.getEntity().getZ(),
                        MyGoItemRegister.LustSinsSoulStone.get()
                                .getDefaultInstance());
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                        (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                level.addFreshEntity(itementity);
            }else if (EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:kelvin")) {
                ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(),
                        event.getEntity().getY(), event.getEntity().getZ(),
                        MyGoItemRegister.EnvySinsSoulStone.get()
                                .getDefaultInstance());
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                        (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                level.addFreshEntity(itementity);
            }else if (EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:prowler")) {
                ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(),
                        event.getEntity().getY(), event.getEntity().getZ(),
                        MyGoItemRegister.PrideSinsSoulStone.get()
                                .getDefaultInstance());
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                        (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                level.addFreshEntity(itementity);
            }else if (EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:walking_bed")) {
                ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(),
                        event.getEntity().getY(), event.getEntity().getZ(),
                        MyGoItemRegister.SlothSinsSoulStone.get()
                                .getDefaultInstance());
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                        (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                level.addFreshEntity(itementity);
            }else if (EntityType.getKey(event.getEntity().getType()).toString().equals("sons_of_sins:wistiver")) {
                ItemEntity itementity = new ItemEntity(level, event.getEntity().getX(),
                        event.getEntity().getY(), event.getEntity().getZ(),
                        MyGoItemRegister.GluttonySinsSoulStone.get()
                                .getDefaultInstance());
                itementity.setDeltaMovement(itementity.getDeltaMovement().add((double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F),
                        (double) (level.random.nextFloat() * 0.05F), (double) ((level.random.nextFloat() - level.random.nextFloat()) * 0.1F)));
                level.addFreshEntity(itementity);
            }
        }
    }
}
