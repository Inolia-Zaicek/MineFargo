package com.inolia_zaicek.mine_fargo.Mixins.Ino;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.Piglin;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

/**
 * 修复 Apotheosis Modern Ragnarok 造成的猪灵空指针崩溃。
 * 优先级 2000 高于原模组的 1001，因此本 WrapOperation 会先执行。
 * - 如果是猪灵，直接调用原始 serverAiStep()，完全绕过原模组的架势破损拦截。
 * - 如果是其他生物，将调用链交给原模组，维持其原有逻辑。
 */
@Mixin(value = LivingEntity.class, priority = 2000)
public abstract class PiglinAiFix {

    /** 声明目标类的 protected 方法，以便在 Mixin 中调用 */
    @Shadow
    protected abstract void serverAiStep();

    @WrapOperation(
            method = "aiStep",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/LivingEntity;serverAiStep()V"
            )
    )
    private void amr$wrapServerAiStep(LivingEntity instance, Operation<Void> original) {
        if (MyGoConfig.InoIntegrationPack.get()) {
            if ((Object) this instanceof Piglin) {
                // 猪灵直接调用原版 AI，确保记忆不会因跳过而丢失
                this.serverAiStep();
            } else {
                // 其他生物走原模组的拦截逻辑
                original.call(instance);
            }
        }
    }
}