package com.inolia_zaicek.mine_fargo.Mixins.Create;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Create.AndesiteAlloySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import com.simibubi.create.content.equipment.armor.DivingBootsItem;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// 标记为伪Mixin，确保不影响原始类（特别是在不存在的类或环境下使用）
@Pseudo
@Mixin(DivingBootsItem.class)
public class DivingBootsItemMixin {

    @Inject(
            method = "isWornBy",
            at = @At("RETURN"),
            cancellable = true,
            remap = false
    )
    private static void alterIsWornBy(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity livingEntity) {
            if (MyGoUtil.hasCreate(livingEntity, AndesiteAlloySoulStone.get())&& MyGoConfig.andesite_alloy_soul_stone_boot.get() ) {
                cir.setReturnValue(true);
            }
        }
    }
}