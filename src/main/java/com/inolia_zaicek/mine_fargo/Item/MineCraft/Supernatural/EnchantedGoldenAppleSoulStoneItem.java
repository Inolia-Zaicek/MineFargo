package com.inolia_zaicek.mine_fargo.Item.MineCraft.Supernatural;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings({"all", "removal"})
public class EnchantedGoldenAppleSoulStoneItem extends Item implements ICurioItem, SupernaturalST {
    public EnchantedGoldenAppleSoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text",
                (float)(MyGoConfig.ega_soul_stoneoul_stone_hp.get()*100),
                (float)(MyGoConfig.ega_soul_stoneoul_stone_damage_time.get()*1), (int)(MyGoConfig.ega_soul_stoneoul_stone_damage_lvl.get()*1),
                (float)(MyGoConfig.ega_soul_stoneoul_stone_heal_time.get()*1), (int)(MyGoConfig.ega_soul_stoneoul_stone_heal_lvl.get()*1),
                (float)(MyGoConfig.ega_soul_stoneoul_stone_re_time.get()*1), (int)(MyGoConfig.ega_soul_stoneoul_stone_re_lvl.get()*1),
                (float)(MyGoConfig.ega_soul_stoneoul_stone_fire_time.get()*1), (float)(MyGoConfig.ega_soul_stoneoul_stone_cooldown.get()*1)
        ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasSupernatural(slotContext.entity(), EnchantedGoldenAppleSoulStone.get());
    }
}
