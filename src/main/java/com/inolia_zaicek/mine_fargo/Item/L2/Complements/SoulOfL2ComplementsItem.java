package com.inolia_zaicek.mine_fargo.Item.L2.Complements;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.L2.Hostility.L2HostilityST;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"all", "removal"})
public class SoulOfL2ComplementsItem extends Item implements ICurioItem {
    public SoulOfL2ComplementsItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_l2_complements.totemic_complements_soul_stone",
                (float)(MyGoConfig.totemic_complements_soul_stone_damage.get()*100), (float)(MyGoConfig.totemic_complements_soul_stone_heal.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xd9bf40))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_l2_complements.poseidite_complements_soul_stone",
                (float)(MyGoConfig.poseidite_complements_soul_stone_atk.get()*100), (float)(MyGoConfig.poseidite_complements_soul_stone_speed.get()*100)
                , (float)(MyGoConfig.poseidite_complements_soul_stone_damage.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x40c3d9))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_l2_complements.shulkerate_complements_soul_stone",
                (float)(MyGoConfig.shulkerate_complements_soul_stone_entity.get()*1), (float)(MyGoConfig.shulkerate_complements_soul_stone_block.get()*1)
                , (float)(MyGoConfig.shulkerate_complements_soul_stone_armor.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xa466b3))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_l2_complements.sculkium_complements_soul_stone",
                (int)(MyGoConfig.sculkium_complements_soul_stone_light.get()*1), (int)(MyGoConfig.sculkium_complements_soul_stone_high.get()*1),
                (float)(MyGoConfig.sculkium_complements_soul_stone_damage.get()*100), (float)(MyGoConfig.sculkium_complements_soul_stone_armor.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x338e98))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_l2_complements.eternium_complements_soul_stone",
                (float)(MyGoConfig.eternium_complements_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x3d44b7))));

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
        atts.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.shulkerate_complements_soul_stone_entity.get(), AttributeModifier.Operation.ADDITION));
        atts.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.shulkerate_complements_soul_stone_block.get(), AttributeModifier.Operation.ADDITION));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! (MyGoUtil.hasL2Complements(slotContext.entity(), TotemicComplementsSoulStone.get())
                ||MyGoUtil.hasL2Complements(slotContext.entity(), PoseiditeComplementsSoulStone.get())
                ||MyGoUtil.hasL2Complements(slotContext.entity(), ShulkerateComplementsSoulStone.get())
                ||MyGoUtil.hasL2Complements(slotContext.entity(), SculkiumComplementsSoulStone.get())
                ||MyGoUtil.hasL2Complements(slotContext.entity(), EterniumComplementsSoulStone.get())
        );
    }
    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        return Collections.emptyList();
    }
}
