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
public class EarthSectSoulStoneItem extends Item implements ICurioItem {
    public EarthSectSoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text",
                (float)(MyGoConfig.earth_sect_soul_stone_power.get()*100),(int)(MyGoConfig.earth_sect_soul_stone_time.get()*1),
                (float)(MyGoConfig.earth_sect_soul_stone_chance.get()*100),(int)(MyGoConfig.earth_sect_soul_stone_level.get()*1),
                (float)(MyGoConfig.earth_sect_soul_stone_other.get()*100)
        ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                new ResourceLocation("gtbcs_geomancy_plus", "geo_spell_power")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.earth_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "max_mana"))),
                new AttributeModifier(uuid, getTooltipItemName(), MyGoConfig.iron_mana.get(), AttributeModifier.Operation.ADDITION));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasSpecificItem(slotContext.entity(), EarthSectSoulStoneItem.class);
    }
}
