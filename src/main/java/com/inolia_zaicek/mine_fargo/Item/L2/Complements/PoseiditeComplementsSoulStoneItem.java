package com.inolia_zaicek.mine_fargo.Item.L2.Complements;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"all", "removal"})
public class PoseiditeComplementsSoulStoneItem extends Item implements ICurioItem, L2ComplementsST {
    public PoseiditeComplementsSoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text",
                (float)(MyGoConfig.poseidite_complements_soul_stone_atk.get()*100), (float)(MyGoConfig.poseidite_complements_soul_stone_speed.get()*100)
                , (float)(MyGoConfig.poseidite_complements_soul_stone_damage.get()*100)
        ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        if(slotContext.entity()!=null) {
            LivingEntity livingEntity = slotContext.entity();
            if(livingEntity.isUnderWater() || livingEntity.isInWater() || livingEntity.isInWaterOrRain() || livingEntity.isInWaterRainOrBubble() ) {
                atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.poseidite_complements_soul_stone_atk.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
                atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.poseidite_complements_soul_stone_speed.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
            }
        }
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasL2Complements(slotContext.entity(), PoseiditeComplementsSoulStoneItem.class);
    }
}
