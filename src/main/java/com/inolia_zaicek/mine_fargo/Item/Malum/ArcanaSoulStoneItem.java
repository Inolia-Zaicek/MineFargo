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

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.ArcanaSoulStone;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.StarvedSoulStone;

@SuppressWarnings({"all", "removal"})
public class ArcanaSoulStoneItem extends Item implements ICurioItem, MalumST {
    public ArcanaSoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text",
                (float)(MyGoConfig.arcana_soul_stone_hp.get()*100),(int)(MyGoConfig.arcana_soul_stone_lvl.get()*1),
                (float)(MyGoConfig.arcana_soul_stone_time.get()*1),
                (int)(MyGoConfig.arcana_soul_stone_lvl_max.get()*1),(float)(MyGoConfig.arcana_soul_stone_time_max.get()*1)
        ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasMalum(slotContext.entity(), ArcanaSoulStone.get());
    }
}
