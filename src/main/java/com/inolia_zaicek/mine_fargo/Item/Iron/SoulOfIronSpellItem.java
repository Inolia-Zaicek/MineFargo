package com.inolia_zaicek.mine_fargo.Item.Iron;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.OresST;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class SoulOfIronSpellItem extends Item implements ICurioItem {
    public SoulOfIronSpellItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iron_spell.fire_sect_soul_stone",
                (float)(MyGoConfig.fire_sect_soul_stone_fire_power.get()*100),(float)(MyGoConfig.fire_sect_soul_stone_fire_min_damage.get()*100),
                (float)(MyGoConfig.fire_sect_soul_stone_fire_max_damage.get()*100), (float)(MyGoConfig.fire_sect_soul_stone_other.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xce4f31))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iron_spell.ice_sect_soul_stone",
                (float)(MyGoConfig.ice_sect_soul_stone_ice_power.get()*100),(int)(MyGoConfig.ice_sect_soul_stone_time.get()*1),
                (float)(MyGoConfig.ice_sect_soul_stone_ice_damage.get()*100), (float)(MyGoConfig.ice_sect_soul_stone_other.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x56b8dc))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iron_spell.lightning_sect_soul_stone",
                (float)(MyGoConfig.lightning_sect_soul_stone_power.get()*100),(float)(MyGoConfig.lightning_sect_soul_stone_damage.get()*100),
                (int)(MyGoConfig.lightning_sect_soul_stone_lvl.get()*1),
                (float)(MyGoConfig.lightning_sect_soul_stone_time.get()*1), (float)(MyGoConfig.lightning_sect_soul_stone_other.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x205dc5))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iron_spell.evocation_sect_soul_stone",
                (float)(MyGoConfig.evocation_sect_soul_stone_power.get()*100),(float)(MyGoConfig.evocation_sect_soul_stone_damage.get()*100)
                ,(float)(MyGoConfig.evocation_sect_soul_stone_armor.get()*100),(float)(MyGoConfig.evocation_sect_soul_stone_owner_damage.get()*100),
                (float)(MyGoConfig.evocation_sect_soul_stone_other.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x4bbc4d))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iron_spell.holy_sect_soul_stone",
                (float)(MyGoConfig.holy_sect_soul_stone_power.get()*100),(int)(MyGoConfig.holy_sect_soul_stone_time.get()*1),
                (float)(MyGoConfig.holy_sect_soul_stone_damage.get()*100), (float)(MyGoConfig.holy_sect_soul_stone_other.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xe5c756))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iron_spell.blood_sect_soul_stone",
                (float)(MyGoConfig.blood_sect_soul_stone_power.get()*100),(float)(MyGoConfig.blood_sect_soul_stone_heal.get()*100),
                (float)(MyGoConfig.blood_sect_soul_stone_owner.get()*100),(float)(MyGoConfig.blood_sect_soul_stone_other.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb62828))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iron_spell.nature_sect_soul_stone",
                (float)(MyGoConfig.nature_sect_soul_stone_power.get()*100),(float)(MyGoConfig.nature_sect_soul_stone_time.get()*100),
                (float)(MyGoConfig.nature_sect_soul_stone_chance.get()*100),(int)(MyGoConfig.nature_sect_soul_stone_level.get()*1),
                (float)(MyGoConfig.nature_sect_soul_stone_other.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xa6ff29))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iron_spell.ender_sect_soul_stone",
                (float)(MyGoConfig.ender_sect_soul_stone_power.get()*100),(int)(MyGoConfig.ender_sect_soul_stone_time.get()*1),
                (float)(MyGoConfig.ender_sect_soul_stone_damage.get()*100), (float)(MyGoConfig.ender_sect_soul_stone_other.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xa85bd6))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iron_spell.eldritch_sect_soul_stone",
                (float)(MyGoConfig.eldritch_sect_soul_stone_power.get()*100),(int)(MyGoConfig.eldritch_sect_soul_stone_time.get()*1),
                (float)(MyGoConfig.eldritch_sect_soul_stone_damage.get()*100), (float)(MyGoConfig.eldritch_sect_soul_stone_other.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x3a777f))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(AttributeRegistry.FIRE_SPELL_POWER.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.fire_sect_soul_stone_fire_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(AttributeRegistry.ICE_SPELL_POWER.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.ice_sect_soul_stone_ice_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(AttributeRegistry.LIGHTNING_SPELL_POWER.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.lightning_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(AttributeRegistry.EVOCATION_SPELL_POWER.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.nature_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(AttributeRegistry.HOLY_SPELL_POWER.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.holy_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(AttributeRegistry.BLOOD_SPELL_POWER.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.blood_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(AttributeRegistry.NATURE_SPELL_POWER.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.nature_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(AttributeRegistry.ENDER_SPELL_POWER.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.ender_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(AttributeRegistry.ELDRITCH_SPELL_POWER.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.eldritch_sect_soul_stone_power.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "max_mana"))),
                new AttributeModifier(uuid, getTooltipItemName(), MyGoConfig.iron_mana.get()*9, AttributeModifier.Operation.ADDITION));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasIron(slotContext.entity(), MyGoItemRegister.SoulOfIronSpell.get() );
    }
    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        return Collections.emptyList();
    }
}
