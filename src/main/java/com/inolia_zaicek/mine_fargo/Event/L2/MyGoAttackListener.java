package com.inolia_zaicek.mine_fargo.Event.L2;

import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import dev.xkmc.l2damagetracker.contents.attack.AttackListener;
import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2damagetracker.contents.damage.DamageTypeWrapper;
import dev.xkmc.l2damagetracker.contents.damage.DefaultDamageState;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import dev.xkmc.l2hostility.compat.curios.CurioCompat;
import dev.xkmc.l2hostility.content.capability.mob.MobTraitCap;
import dev.xkmc.l2hostility.init.data.HostilityDamageState;
import dev.xkmc.l2hostility.init.registrate.LHItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;

public class MyGoAttackListener implements AttackListener {

    public void onCreateSource(CreateSourceEvent event) {
        LivingEntity attacker = event.getAttacker();
        //让“这个生物身上的所有 Trait（特性）都有机会修改这次伤害”
        if (MobTraitCap.HOLDER.isProper(attacker)) {
            ((MobTraitCap) MobTraitCap.HOLDER.get(attacker)).traitEvent((k, v) -> k.onCreateSource(v, event.getAttacker(), event));
        }
        DamageTypeWrapper type = event.getResult();
        if (type != null) {
            //DamageTypeWrapper root = type.toRoot();
            //if (root == L2DamageTypes.MOB_ATTACK || root == L2DamageTypes.PLAYER_ATTACK) {
            if (CurioCompat.hasItemInCurioOrSlot(attacker, MyGoItemRegister.AnchorSoulStone.get())) {
                event.enable(DefaultDamageState.BYPASS_MAGIC);
                //event.enable(HostilityDamageState.BYPASS_COOLDOWN);
            }
        }
    }
}