package com.inolia_zaicek.mine_fargo.Event.Malum;

import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import com.sammy.malum.common.capability.MalumLivingEntityDataCapability;
import com.sammy.malum.core.handlers.SoulDataHandler;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.SoulOfInolia;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.SpiritSoulStone;

public class MalumHurtEvent {
    public static boolean hasMalum(LivingEntity living, Item item) {
        if(ModList.get().isLoaded("malum")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                    || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfMalum.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    @SubscribeEvent
    public static void exposeSoul(LivingHurtEvent event) {
        if (!event.isCanceled() && !(event.getAmount() <= 0.0F)) {
            LivingEntity target = event.getEntity();
            DamageSource source = event.getSource();
            //自己有胸针
            if (source.getEntity() instanceof LivingEntity attacker) {
                if (hasMalum(attacker, SpiritSoulStone.get())){
                    exposeSoul(target);
                }
            }
            //随从击杀，主人有胸针
            if (event.getSource().getEntity() instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() != null) {
                LivingEntity owner = ownableEntity.getOwner();
                if (hasMalum(owner, SpiritSoulStone.get())){
                    exposeSoul(target);
                }
            }
        }
    }
    public static void exposeSoul(LivingEntity entity) {
        SoulDataHandler soulData = MalumLivingEntityDataCapability.getCapability(entity).soulData;
        soulData.exposedSoulDuration = 200.0F;
    }
}
