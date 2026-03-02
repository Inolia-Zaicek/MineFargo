package com.inolia_zaicek.mine_fargo.Item.Iron;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings({"all", "removal"})
public class FantacySectSoulStoneItem extends Item implements ICurioItem {
    public FantacySectSoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text",
                (float)(MyGoConfig.fantasy_sect_soul_stone_power.get()*100),(float)(MyGoConfig.fantasy_sect_soul_stone_fantacy_power.get()*100),
                (int)(MyGoConfig.fantasy_sect_soul_stone_dream_shadow_damage.get()*1),(float)(MyGoConfig.fantasy_sect_soul_stone_dream_shadow_damage_resistance.get()*100),
                (int)(MyGoConfig.fantasy_sect_soul_stone_fantasy_ending_damage.get()*1),(float)(MyGoConfig.fantasy_sect_soul_stone_fantasy_ending_damage_resistance.get()*100),
                (float)(MyGoConfig.fantasy_sect_soul_stone_damage.get()*100),
                (float)(MyGoConfig.fantacy_sect_soul_stone_other.get()*100)
        ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                new ResourceLocation("irons_spellbooks", "spell_power")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.fantasy_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                        new ResourceLocation("fantasy_ending", "fantasy_spell_power")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.fantasy_sect_soul_stone_fantacy_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                        new ResourceLocation("fantasy_ending", "dream_shadow_damage")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.fantasy_sect_soul_stone_dream_shadow_damage.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                        new ResourceLocation("fantasy_ending", "dream_shadow_damage_resistance")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.fantasy_sect_soul_stone_dream_shadow_damage_resistance.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                        new ResourceLocation("fantasy_ending", "fantasy_ending_damage")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.fantasy_sect_soul_stone_fantasy_ending_damage.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                        new ResourceLocation("fantasy_ending", "fantasy_ending_damage_resistance")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.fantasy_sect_soul_stone_fantasy_ending_damage_resistance.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "max_mana"))),
                new AttributeModifier(uuid, getTooltipItemName(), MyGoConfig.iron_mana.get(), AttributeModifier.Operation.ADDITION));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasSpecificItem(slotContext.entity(), FantacySectSoulStoneItem.class);
    }
}
