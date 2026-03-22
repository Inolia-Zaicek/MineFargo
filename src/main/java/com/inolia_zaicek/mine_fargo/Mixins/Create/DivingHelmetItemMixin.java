package com.inolia_zaicek.mine_fargo.Mixins.Create;

import com.inolia_zaicek.mine_fargo.Item.Create.RoseQuartzSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.OresST;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import com.simibubi.create.content.equipment.armor.DivingBootsItem;
import com.simibubi.create.content.equipment.armor.DivingHelmetItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// 标记为伪Mixin，确保不影响原始类（特别是在不存在的类或环境下使用）
@Pseudo
@Mixin(DivingHelmetItem.class)
public class DivingHelmetItemMixin {

    @Inject(
            method = "isWornBy",
            at = @At("RETURN"),
            cancellable = true,
            remap = false
    )
    private static void alterIsWornBy(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if (entity instanceof LivingEntity livingEntity) {
            if (MyGoUtil.hasCreate(livingEntity, RoseQuartzSoulStone.get())) {
                cir.setReturnValue(true);
            }
        }
    }
}