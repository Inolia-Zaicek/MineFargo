package com.inolia_zaicek.mine_fargo.Event.IceAndFire;

import com.iafenvoy.iceandfire.data.component.IafEntityData;
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

public class IAFCEHurtEvent {
    @SubscribeEvent
    public static void hurt(LivingHurtEvent event) {
        if (ModList.get().isLoaded("iceandfire")&&ModList.get().isLoaded("jupiter")&&ModList.get().isLoaded("uranus")) {
            LivingEntity attacked = event.getEntity();
            if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != null) {
                IafEntityData data = IafEntityData.get(attacked);
                double freezeTime = 0;
                Set<Item> curios = MyGoUtil.getCuriosItems(attacker);
                if (MyGoUtil.hasIAFDragon(curios,attacker, IceDragonBloodSoulStone.get())) {
                    freezeTime += MyGoConfig.ice_dragon_blood_soul_stone_freeze_time.get() * 20;
                }
                if (MyGoUtil.hasIAFDragon(curios,attacker, IceDragonSteelSoulStone.get())) {
                    freezeTime += MyGoConfig.ice_dragon_steel_soul_stone_time.get() * 20;
                }
                if (freezeTime > 0 && MyGoConfig.iaf_can_freeze.get()) {
                    data.frozenData.setFrozen(attacked, (int) (freezeTime));
                }
                if (MyGoUtil.hasIAFEntity(curios,attacker, IAFSirenSoulStone.get())&&attacker.getPersistentData().getInt(iaf_siren_soul_stone)==0) {
                    attacker.getPersistentData().putInt(iaf_siren_soul_stone,(int) (MyGoConfig.iaf_siren_soul_stone_cooldown.get()*20*2));
                    data.miscData.setLoveTicks( (int) (MyGoConfig.iaf_siren_soul_stone_time.get()*20) );
                }
            }
        }
    }
}