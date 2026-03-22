package com.inolia_zaicek.mine_fargo.Item.Ars;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.hollingsworth.arsnouveau.common.items.curios.DiscountRing;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
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
public class SoulOfArsNouveauItem extends DiscountRing implements ICurioItem  {
    public SoulOfArsNouveauItem() {super();}
    @Override
    public int getManaDiscount() {
        return (int)(MyGoConfig.whirlisprig_soul_stone_cast.get()+MyGoConfig.bookwyrm_soul_stone_cast.get());
    }
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ars_nouveau.ars_source_soul_stone",
                (int)(MyGoConfig.ars_source_soul_stone_re.get()*1),(int)(MyGoConfig.ars_source_soul_stone_mana.get()*1),
                (int)(MyGoConfig.ars_source_soul_stone_damage.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xab52c7))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ars_nouveau.drygmy_soul_stone",
                (int)(MyGoConfig.drygmy_soul_stone_damage.get()*1),(int)(MyGoConfig.drygmy_soul_stone_drop.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xd68543))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ars_nouveau.whirlisprig_soul_stone",
                (int)(MyGoConfig.whirlisprig_soul_stone_cast.get()*1),(float)(MyGoConfig.whirlisprig_soul_stone_food.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x47d147))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ars_nouveau.starbuncle_soul_stone",
                (float)(MyGoConfig.starbuncle_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xd6b943))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ars_nouveau.bookwyrm_soul_stone",
                (int)(MyGoConfig.bookwyrm_soul_stone_cast.get()*1),(int)(MyGoConfig.bookwyrm_soul_stone_damage.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xa661b7))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ars_nouveau.wixie_soul_stone",
                (float)(MyGoConfig.wixie_soul_stone_up.get()*100),(float)(MyGoConfig.wixie_soul_stone_down.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xd643c1))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ars_nouveau.amethyst_golem_soul_stone",
                (float)(MyGoConfig.amethyst_golem_soul_stone.get()*100),(int)(MyGoConfig.amethyst_golem_soul_stone_level.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x9744a2))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ars_nouveau.archwood_soul_stone",
                (int)(MyGoConfig.archwood_soul_stone_purple.get()*1),(int)(MyGoConfig.archwood_soul_stone_green.get()*1),
                (float)(MyGoConfig.archwood_soul_stone_chance.get()*100),
                (int)(MyGoConfig.archwood_soul_stone_time.get()*1),
                (int)(MyGoConfig.archwood_soul_stone_red.get()*1),(int)(MyGoConfig.archwood_soul_stone_blue.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x969383))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ars_nouveau.wilden_soul_stone",
                (float)(MyGoConfig.wilden_soul_stone_re.get()*100),(float)(MyGoConfig.wilden_soul_stone_mana.get()*100),
                (int)(MyGoConfig.wilden_soul_stone_damage.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x7742a4))));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                        new ResourceLocation("ars_nouveau", "ars_nouveau.perk.mana_regen")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.ars_source_soul_stone_re.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                        new ResourceLocation("ars_nouveau", "ars_nouveau.perk.max_mana")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.ars_source_soul_stone_mana.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                new ResourceLocation("ars_nouveau", "ars_nouveau.perk.mana_regen")))
                , new AttributeModifier(UUID.fromString("1972C929-F79B-8B1B-877B-D903544CB348"), this.getTooltipItemName(), MyGoConfig.wilden_soul_stone_re.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                        new ResourceLocation("ars_nouveau", "ars_nouveau.perk.max_mana")))
                , new AttributeModifier(UUID.fromString("1972C929-F79B-8B1B-877B-D903544CB348"), this.getTooltipItemName(), MyGoConfig.wilden_soul_stone_mana.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                        new ResourceLocation("ars_nouveau", "ars_nouveau.perk.spell_damage")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.wilden_soul_stone_damage.get()+
                        MyGoConfig.ars_source_soul_stone_damage.get()+MyGoConfig.drygmy_soul_stone_damage.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.starbuncle_soul_stone.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.amethyst_golem_soul_stone.get(), AttributeModifier.Operation.ADDITION));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasArs(slotContext.entity(), MyGoItemRegister.SoulOfArsNouveau.get());
    }
}
