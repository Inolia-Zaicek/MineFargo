package com.inolia_zaicek.mine_fargo.Event.IceAndFire;

import com.github.alexthe666.iceandfire.entity.props.EntityDataProvider;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.IceAndFire.Dragon.IceDragonBloodSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.IceAndFire.Dragon.IceDragonSteelSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.IceAndFire.Entity.IAFSirenSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.Set;

import static com.inolia_zaicek.mine_fargo.Event.TickEvent.iaf_siren_soul_stone;

public class IAFHurtEvent {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (ModList.get().isLoaded("iceandfire")) {
            LivingEntity attacked = event.getEntity();
            if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != null) {
                double freezeTime = 0;
                Set<Item> curios = MyGoUtil.getCuriosItems(attacker);
                if (MyGoUtil.hasIAFDragon(curios,attacker, IceDragonBloodSoulStone.get())) {
                    freezeTime += MyGoConfig.ice_dragon_blood_soul_stone_freeze_time.get() * 20;
                }
                if (MyGoUtil.hasIAFDragon(curios,attacker, IceDragonSteelSoulStone.get())) {
                    freezeTime += MyGoConfig.ice_dragon_steel_soul_stone_time.get() * 20;
                }
                if (freezeTime > 0 && MyGoConfig.iaf_can_freeze.get()) {
                    double finalFreezeTime = freezeTime;
                    EntityDataProvider.getCapability(attacked).ifPresent(data -> data.frozenData.setFrozen(attacked, (int) finalFreezeTime));
                }
                if (MyGoUtil.hasIAFEntity(curios,attacker, IAFSirenSoulStone.get())&&attacker.getPersistentData().getInt(iaf_siren_soul_stone)==0) {
                    attacker.getPersistentData().putInt(iaf_siren_soul_stone,(int) (MyGoConfig.iaf_siren_soul_stone_cooldown.get()*20*2));
                    EntityDataProvider.getCapability(attacked).ifPresent(data -> data.miscData.setLoveTicks( (int) (MyGoConfig.iaf_siren_soul_stone_time.get()*20)) );
                }
            }
        }
    }
}