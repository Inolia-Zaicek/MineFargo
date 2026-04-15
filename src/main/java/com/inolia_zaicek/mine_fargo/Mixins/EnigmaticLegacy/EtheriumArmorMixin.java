package com.inolia_zaicek.mine_fargo.Mixins.EnigmaticLegacy;

import com.aizistral.etherium.items.EtheriumArmor;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.CursesSoulStone;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.EtheriumSoulStone;


// 标记为伪Mixin，确保不影响原始类（特别是在不存在的类或环境下使用）
@Pseudo
// 指定混入目标为WandUtil类
@Mixin(EtheriumArmor.class)
public class EtheriumArmorMixin {
    // 在WandUtil类的getLevels方法返回后插入代码（钩子）
    @Inject(
            method = "hasShield",
            at = @At("RETURN"), // 在方法返回点插入（即调用getLevels后）
            cancellable = true, // 允许修改返回值
            remap = false // 不进行混淆映射
    )
    private static void hasShieldMixin(Player player, CallbackInfoReturnable<Boolean> cir){
        if(MyGoUtil.hasEnigmaticLegacy(player, EtheriumSoulStone.get())) {
            cir.setReturnValue(true);
        }
    }
}