package com.inolia_zaicek.mine_fargo.Mixins.AlexsCaves;

import com.inolia_zaicek.mine_fargo.Item.AlexsCaves.AbyssalChasmSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import dev.xkmc.l2serial.util.Wrappers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.fml.ModList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({Entity.class})
public class AlexsCavesEntityMixin {
    @Inject(
            at = {@At("HEAD")},
            method = {"isInRain"},
            cancellable = true
    )
    public void l2hostility$isInRain$ringOfOcean(CallbackInfoReturnable<Boolean> cir) {
        Entity self = (Entity) Wrappers.cast(this);
        if (self instanceof LivingEntity livingEntity) {
            if (ModList.get().isLoaded("alexscaves")) {
                if (MyGoUtil.hasAlexsCaves(livingEntity, AbyssalChasmSoulStoneItem.class)) {
                    cir.setReturnValue(true);
                }
            }
        }

    }
}
