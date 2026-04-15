package com.inolia_zaicek.mine_fargo.Mixins.EnigmaticLegacy;

import com.aizistral.enigmaticlegacy.handlers.SuperpositionHandler;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;


// 标记为伪Mixin，确保不影响原始类（特别是在不存在的类或环境下使用）
@Pseudo
// 指定混入目标为WandUtil类
@Mixin(SuperpositionHandler.class)
public class SuperpositionHandlerMixin {
    // 在WandUtil类的getLevels方法返回后插入代码（钩子）
    @Inject(
            method = "getCurseAmount(Lnet/minecraft/world/entity/player/Player;)I",
            at = @At("RETURN"), // 在方法返回点插入（即调用getLevels后）
            cancellable = true, // 允许修改返回值
            remap = false // 不进行混淆映射
    )
    private static void getCurseAmountMixin(Player player, CallbackInfoReturnable<Integer> cir){
        if(MyGoUtil.hasEnigmaticLegacy(player, CursesSoulStone.get())) {
            int number = cir.getReturnValue();
            cir.setReturnValue((int) (number*MyGoConfig.curses_soul_stone.get()));
        }
    }
}