package com.inolia_zaicek.mine_fargo.Mixins.L2;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.L2.Curios.GuardHostilitySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import dev.xkmc.l2hostility.content.item.curio.ring.RingOfOcean;
import dev.xkmc.l2hostility.content.item.curio.ring.RingOfReflection;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// 标记为伪Mixin，确保不影响原始类（特别是在不存在的类或环境下使用）
@Pseudo
@Mixin(RingOfOcean.class)
public class RingOfOceanMixin {

    @Inject(
            method = "isOn",
            at = @At("RETURN"),
            cancellable = true,
            remap = false
    )
    private void isOnMixin(LivingEntity le, CallbackInfoReturnable<Boolean> cir) {
        if (MyGoUtil.hasCreate(le, GuardHostilitySoulStoneItem.class)) {
            cir.setReturnValue(true);
        }
    }
}