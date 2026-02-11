package com.inolia_zaicek.mine_fargo.Mixins.Create;

import com.simibubi.create.content.equipment.armor.DivingBootsItem;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.gen.Accessor;

// 标记为伪Mixin，确保不影响原始类（特别是在不存在的类或环境下使用）
@Pseudo
@Mixin(BlazeBurnerBlockEntity.class)
public interface BlazeBurnerMixin {
    @Accessor("activeFuel")
    void setActiveFuel(BlazeBurnerBlockEntity.FuelType fuel);
    @Accessor("remainingBurnTime")
    void setRemainingBurnTime(int ticks);
}
