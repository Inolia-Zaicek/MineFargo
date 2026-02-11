package com.inolia_zaicek.mine_fargo.Mixins.Create;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Create.AndesiteAlloySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.simibubi.create.content.equipment.goggles.GogglesItem;

// 标记为伪Mixin，确保不影响原始类（特别是在不存在的类或环境下使用）
@Pseudo
@Mixin(GogglesItem.class)
public class GogglesItemMixin {

    @Inject(
            method = "isWearingGoggles",
            at = @At("RETURN"),
            cancellable = true,
            remap = false
    )
    private static void modifyIsWearingGoggles(Player player, CallbackInfoReturnable<Boolean> cir) {
        if (MyGoUtil.hasCreate(player, AndesiteAlloySoulStoneItem.class)&& MyGoConfig.andesite_alloy_soul_stone_eyes.get() ) {
            cir.setReturnValue(true);
        }
    }
}