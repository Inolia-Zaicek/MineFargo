package com.inolia_zaicek.mine_fargo.Item.L2.Hostility;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"all", "removal"})
public class CorrosionHostilitySoulStoneItem extends Item implements ICurioItem, L2HostilityST {
    public CorrosionHostilitySoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text",
                (float)(MyGoConfig.corrosion_hostility_soul_stone_time.get()*1),
                (int)(MyGoConfig.corrosion_hostility_soul_stone_weakness.get()*1),(int)(MyGoConfig.corrosion_hostility_soul_stone_slowness.get()*1),
                (int)(MyGoConfig.corrosion_hostility_soul_stone_poison.get()*1), (int)(MyGoConfig.corrosion_hostility_soul_stone_wither.get()*1),
                (int)(MyGoConfig.corrosion_hostility_soul_stone_flame.get()*1), (int)(MyGoConfig.corrosion_hostility_soul_stone_frozen.get()*1),
                (int)(MyGoConfig.corrosion_hostility_soul_stone_gravity.get()*1), (int)(MyGoConfig.corrosion_hostility_soul_stone_curse.get()*1)
        ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasL2Hostility(slotContext.entity(), CorrosionHostilitySoulStoneItem.class);
    }
}
