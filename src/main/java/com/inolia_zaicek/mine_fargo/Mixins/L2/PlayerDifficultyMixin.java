package com.inolia_zaicek.mine_fargo.Mixins.L2;

import com.inolia_zaicek.mine_fargo.Item.L2.Curios.RiderHostilitySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import dev.xkmc.l2hostility.content.capability.player.PlayerDifficulty;
import dev.xkmc.l2hostility.content.logic.MobDifficultyCollector;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

// 标记为伪Mixin，确保不影响原始类（特别是在不存在的类或环境下使用）
@Pseudo
@Mixin(PlayerDifficulty.class)
public class PlayerDifficultyMixin {

    private net.minecraft.world.entity.player.Player mineFargo$player;

    @Inject(
            method = "apply",
            at = @At("RETURN"),
            remap = false
    )
    public void applyMixin(MobDifficultyCollector instance, CallbackInfo ci) {
        if (MyGoUtil.hasL2Curios(mineFargo$player, RiderHostilitySoulStoneItem.class)) {
            instance.setFullChance();
            instance.setFullDrop();
        }
    }

    public void setMineFargo$player(Player mineFargo$player) {
        this.mineFargo$player = mineFargo$player;
    }
}