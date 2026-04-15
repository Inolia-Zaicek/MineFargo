package com.inolia_zaicek.mine_fargo.Item.Malum;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
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

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.StarvedSoulStone;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.TotemicSoulStone;

@SuppressWarnings({"all", "removal"})
public class TotemicSoulStoneItem extends Item implements ICurioItem, MalumST {
    public TotemicSoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text",
                (int)(MyGoConfig.zephyrs_courage.get()*1),(int)(MyGoConfig.poseidons_grasp.get()*1),
                (int)(MyGoConfig.gaias_bulwark.get()*1), (int)(MyGoConfig.miners_rage.get()*1),
                (int)(MyGoConfig.aethers_charm.get()*1), (int)(MyGoConfig.anglers_lure.get()*1),
                (int)(MyGoConfig.earthen_might.get()*1),(int)(MyGoConfig.ifrits_embrace.get()*1)
        ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasMalum(slotContext.entity(), TotemicSoulStone.get());
    }
}
