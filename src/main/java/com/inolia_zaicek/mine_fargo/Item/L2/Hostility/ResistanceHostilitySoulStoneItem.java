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
public class ResistanceHostilitySoulStoneItem extends Item implements ICurioItem, L2HostilityST {
    public ResistanceHostilitySoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text",
                (float)(MyGoConfig.resistance_hostility_soul_stone_fire.get()*100), (float)(MyGoConfig.resistance_hostility_soul_stone_magic.get()*100),
                (float)(MyGoConfig.resistance_hostility_soul_stone_atk.get()*100),(float)(MyGoConfig.resistance_hostility_soul_stone_pre.get()*100),
                (float)(MyGoConfig.resistance_hostility_soul_stone_time.get()*1),(float)(MyGoConfig.resistance_hostility_soul_stone_armor_p.get()*100)
        ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasL2Hostility(slotContext.entity(), ResistanceHostilitySoulStoneItem.class);
    }
}
