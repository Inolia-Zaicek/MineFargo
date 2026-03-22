package com.inolia_zaicek.mine_fargo.Item.Create;

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
public class BlazeCakeSoulStoneItem extends Item implements ICurioItem, CreateST {
    public BlazeCakeSoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        if(MyGoConfig.blaze_cake_soul_stone_super.get()) {
            pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text1",
                    (int) (MyGoConfig.blaze_cake_soul_stone_time.get() * 1),(int) (MyGoConfig.blaze_cake_soul_stone_max_time.get() * 1)
            ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        }else{
            pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text2",
                    (int) (MyGoConfig.blaze_cake_soul_stone_time.get() * 1),(int) (MyGoConfig.blaze_cake_soul_stone_max_time.get() * 1)
            ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        }
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasCreate(slotContext.entity(), BlazeCakeSoulStone.get());
    }
}
