package com.inolia_zaicek.mine_fargo.Item.MineCraft;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.OresST;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
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

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.SoulOfOres;

public class SoulOfOresItem extends Item implements ICurioItem {
    public SoulOfOresItem() {super((new Item.Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ores.coal_soul_stone",
                (float)(MyGoConfig.coal_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x333333))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ores.copper_soul_stone",
                (float)(MyGoConfig.copper_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xe77c56))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ores.lapis_lazuli_soul_stone",
                (float)(MyGoConfig.lapis_lazuli_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x5a82e2))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ores.iron_soul_stone",
                MyGoConfig.iron_soul_stone.get()
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xd8d8d8))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ores.redstone_soul_stone",
                (float)(MyGoConfig.redstone_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xfc0000))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ores.gold_soul_stone",
                (int)(MyGoConfig.gold_soul_stone.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xfdf55f))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ores.emerald_soul_stone",
                (int)(MyGoConfig.emerald_soul_stone.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x41f384))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ores.diamond_soul_stone",
                (int)(MyGoConfig.diamond_soul_stone_looting.get()*1),(int)(MyGoConfig.diamond_soul_stone_fortune.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x49efd7))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_ores.netherite_soul_stone",
                (int)(MyGoConfig.netherite_soul_stone.get()*100),(float)(MyGoConfig.netherite_soul_stone_armor.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x4b4042))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.coal_soul_stone.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.redstone_soul_stone.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.iron_soul_stone.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.netherite_soul_stone.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.netherite_soul_stone_armor.get(), AttributeModifier.Operation.ADDITION));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasOre(slotContext.entity(), SoulOfOres.get());
    }
}
