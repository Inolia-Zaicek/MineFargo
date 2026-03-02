package com.inolia_zaicek.mine_fargo.Item.IceAndFire.Entity;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.IceAndFire.Dragon.IAFDragonST;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
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
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"all", "removal"})
public class SoulOfIAFEntityItem extends Item implements ICurioItem {
    public SoulOfIAFEntityItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_entity.cyclops_soul_stone",
                (int)(MyGoConfig.cyclops_soul_stone_range.get()*1), (int)(MyGoConfig.cyclops_soul_stone_range.get()*1),
                (int)(MyGoConfig.cyclops_soul_stone_lvl.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb65142))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_entity.gorgon_soul_stone",
                (int)(MyGoConfig.gorgon_soul_stone_range.get()*1), (int)(MyGoConfig.gorgon_soul_stone_lvl.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x40a351))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_entity.iaf_hydra_soul_stone",
                (float)(MyGoConfig.iaf_hydra_soul_stone_heal.get()*100), (float)(MyGoConfig.iaf_hydra_soul_stone_hp.get()*100) ,
                (float)(MyGoConfig.iaf_hydra_soul_stone_up.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x6fc831))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_entity.iaf_siren_soul_stone",
                (float)(MyGoConfig.iaf_siren_soul_stone_time.get()*1), (float)(MyGoConfig.iaf_siren_soul_stone_cooldown.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x31c1c8))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_entity.sea_serpent_soul_stone",
                (int)(MyGoConfig.sea_serpent_soul_stone.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x317fc8))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_entity.troll_soul_stone",
                (float)(MyGoConfig.troll_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x768d6b))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_entity.hippocampus_soul_stone",
                (float)(MyGoConfig.hippocampus_soul_stone_speed.get()*100), (float)(MyGoConfig.hippocampus_soul_stone_time.get()*1),
                (int)(MyGoConfig.hippocampus_soul_stone_lvl1.get()*1),(int)(MyGoConfig.hippocampus_soul_stone_lvl2.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x35c4a8))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_entity.death_worm_soul_stone",
                (float)(MyGoConfig.death_worm_soul_stone.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x929067))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(uuid, this.getTooltipItemName(),
                MyGoConfig.hippocampus_soul_stone_speed.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(uuid, this.getTooltipItemName(),
                MyGoConfig.death_worm_soul_stone.get(), AttributeModifier.Operation.ADDITION));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasIAFEntity(slotContext.entity(), IAFEntityST.class);
    }
}
