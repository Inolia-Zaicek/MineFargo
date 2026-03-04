package com.inolia_zaicek.mine_fargo.Item.L2;

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
public class BodyHostilitySoulStoneItem extends Item implements ICurioItem, L2HostilityST {
    public BodyHostilitySoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text",
                (float)(MyGoConfig.body_hostility_soul_stone_hp.get()*100),
                (float)(MyGoConfig.body_hostility_soul_stone_armor.get()*1),(float)(MyGoConfig.body_hostility_soul_stone_armor_t.get()*1),
                (int)(MyGoConfig.body_hostility_soul_stone_buff_lvl.get()*1),
                (float)(MyGoConfig.body_hostility_soul_stone_heal_hp.get()*100),
                (int)(MyGoConfig.body_hostility_soul_stone_adaptive_number.get()*1),
                (float)(MyGoConfig.body_hostility_soul_stone_adaptive_re.get()*100), (float)(MyGoConfig.body_hostility_soul_stone_adaptive_max_re.get()*100)
        ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, this.getTooltipItemName(),
                MyGoConfig.body_hostility_soul_stone_hp.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, this.getTooltipItemName(),
                MyGoConfig.body_hostility_soul_stone_armor.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, this.getTooltipItemName(),
                MyGoConfig.body_hostility_soul_stone_armor_t.get(), AttributeModifier.Operation.ADDITION));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasL2Hostility(slotContext.entity(), BodyHostilitySoulStoneItem.class);
    }
}
