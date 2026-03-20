package com.inolia_zaicek.mine_fargo.Mixins.Create;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Create.CardboardSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import com.simibubi.create.content.equipment.armor.CardboardArmorHandler;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// 标记为伪Mixin，确保不影响原始类（特别是在不存在的类或环境下使用）
@Pseudo
@Mixin(CardboardArmorHandler.class)
public class CardboardMixin {
    @Inject(
            method = "testForStealth",
            at = @At("RETURN"),
            cancellable = true,
            remap = false
    )
    private static void testForStealth(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity livingEntity) {
            if (MyGoUtil.hasCreate(livingEntity, CardboardSoulStoneItem.class)&& MyGoConfig.cardboard_soul_stone.get()
            &&livingEntity.isCrouching() ) {
                cir.setReturnValue(true);
            }
        }
    }
}
