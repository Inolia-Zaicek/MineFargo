package com.inolia_zaicek.mine_fargo.Mixins;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.EmeraldSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import org.spongepowered.asm.mixin.injection.At;

@Mixin({Villager.class})
public abstract class VillagerMixin {
    @ModifyExpressionValue(
            method = {"updateSpecialPrices"},
            at = {@At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/npc/Villager;getPlayerReputation(Lnet/minecraft/world/entity/player/Player;)I"
            )}
    )
    private int updateSpecialPrices(int original, Player player) {
        if (MyGoUtil.hasOre(player, EmeraldSoulStone.get())) {
            //声望*0.2=折扣数量
            original += MyGoConfig.emerald_soul_stone.get();
        }
        return original;
    }
}