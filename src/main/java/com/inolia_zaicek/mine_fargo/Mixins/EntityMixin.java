//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.inolia_zaicek.mine_fargo.Mixins;

import com.inolia_zaicek.mine_fargo.Item.L2.Curios.GuardHostilitySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import com.inolia_zaicek.mine_fargo.Util.l2.MyGoWrappers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Entity.class})
public class EntityMixin {
    @Inject(
            at = {@At("HEAD")},
            method = {"isInRain"},
            cancellable = true
    )
    public void l2hostility$isInRain$ringOfOcean(CallbackInfoReturnable<Boolean> cir) {
        Entity self = (Entity) MyGoWrappers.cast(this);
        if (self instanceof LivingEntity livingEntity) {
            if (MyGoUtil.hasL2Curios(livingEntity, GuardHostilitySoulStoneItem.class)) {
                cir.setReturnValue(true);
            }
        }

    }
}
