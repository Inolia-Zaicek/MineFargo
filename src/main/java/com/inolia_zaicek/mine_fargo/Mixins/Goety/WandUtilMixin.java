package com.inolia_zaicek.mine_fargo.Mixins.Goety;

import com.Polarice3.Goety.utils.WandUtil;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.GoetyFocusSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;


// 标记为伪Mixin，确保不影响原始类（特别是在不存在的类或环境下使用）
@Pseudo
// 指定混入目标为WandUtil类
@Mixin(WandUtil.class)
public class WandUtilMixin {
    // 在WandUtil类的getLevels方法返回后插入代码（钩子）
    @Inject(
            method = "getLevels", // 目标方法名称
            at = @At("RETURN"), // 在方法返回点插入（即调用getLevels后）
            cancellable = true, // 允许修改返回值
            remap = false // 不进行混淆映射
    )
    private static void getLevelsMixin(Enchantment enchantment, LivingEntity livingEntity, CallbackInfoReturnable<Integer> cir){
        // 获取原方法的返回值，即返回的等级
        float number = cir.getReturnValue();
        /// 对词条进行判断
        if( MyGoUtil.hasGoetyItem(livingEntity, GoetyFocusSoulStoneItem.class) ){
            int finish = (int) (number+(int)(MyGoConfig.ectoplasm_soul_stone.get()*1) );
            // 设置新的返回值
            cir.setReturnValue((int) finish);
        }else{
            cir.setReturnValue((int) number);
        }
    }
}