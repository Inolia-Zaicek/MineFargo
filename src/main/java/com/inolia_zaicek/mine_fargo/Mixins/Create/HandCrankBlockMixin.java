package com.inolia_zaicek.mine_fargo.Mixins.Create;

import com.inolia_zaicek.mine_fargo.Item.Create.ZincSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import com.simibubi.create.content.kinetics.crank.HandCrankBlock;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

/**
 * Mixin to modify use()方法，让在满足A>B条件时不消耗食物
 */
// 标记为伪Mixin，确保不影响原始类（特别是在不存在的类或环境下使用）
@Pseudo
@Mixin(HandCrankBlock.class)
public class HandCrankBlockMixin {

    @Redirect(
            method = "use",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/player/Player;causeFoodExhaustion(F)V"
            ),
            remap = false
    )
    private void redirectCauseFoodExhaustion(Player player, float exhaustion) {
        // 判断条件A>B
        if (MyGoUtil.hasCreate(player, ZincSoulStone.get())) {
            // 满足A>B，则不执行食物消耗
            return;
        }
        // 否则执行原本的消耗逻辑
        player.causeFoodExhaustion(exhaustion);
    }
}