package com.inolia_zaicek.mine_fargo.Mixins.Goety;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import com.mega.revelationfix.common.config.*;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// 标记为伪Mixin，确保不影响原始类（特别是在不存在的类或环境下使用）
@Pseudo
@Mixin(CommonConfig.class)
public class CommonConfigMixin {
    @Inject(
            method = "inWhitelist(Lnet/minecraft/world/item/Item;)Z",
            at = @At("RETURN"),
            cancellable = true,
            remap = false
    )
    private static void inWhitelistMixin(Item item, CallbackInfoReturnable<Boolean> cir) {
        if (MyGoConfig.over_revelation.get() && MyGoItemRegister.isMyRegisteredItem(item) ) {
            cir.setReturnValue(true);
        }
    }
}