package com.inolia_zaicek.mine_fargo.Item.MineCraft;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Entity.EntityST;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.OresST;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
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

public class SoulOfEntityItem extends Item implements ICurioItem {
    public SoulOfEntityItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_entity.blaze_soul_stone",
                (float)(MyGoConfig.blaze_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xFFCC51))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_entity.death_soul_stone",
                (float)(MyGoConfig.death_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x00cc00))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_entity.arthropod_soul_stone",
                (float)(MyGoConfig.arthropod_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xEEEE31))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_entity.animal_soul_stone",
                (int)(MyGoConfig.animal_soul_stone.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xccff99))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_entity.aquatic_soul_stone",
                (float)(MyGoConfig.aquatic_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x66B2FF))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_entity.wing_soul_stone",
                (float)(MyGoConfig.wing_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xE0E0E0))));


        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.FLYING_SPEED, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.wing_soul_stone.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! (MyGoUtil.hasEntity(slotContext.entity(), MyGoItemRegister.BlazeSoulStone.get())
                ||MyGoUtil.hasEntity(slotContext.entity(), MyGoItemRegister.DeathSoulStone.get())
                ||MyGoUtil.hasEntity(slotContext.entity(), MyGoItemRegister.ArthropodSoulStone.get())
                ||MyGoUtil.hasEntity(slotContext.entity(), MyGoItemRegister.AnimalSoulStone.get())
                ||MyGoUtil.hasEntity(slotContext.entity(), MyGoItemRegister.AquaticSoulStone.get())
                ||MyGoUtil.hasEntity(slotContext.entity(), MyGoItemRegister.WingSoulStone.get())
        );
    }
}
