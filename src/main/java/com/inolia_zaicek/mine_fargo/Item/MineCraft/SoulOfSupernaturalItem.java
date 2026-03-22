package com.inolia_zaicek.mine_fargo.Item.MineCraft;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.OresST;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Supernatural.SupernaturalST;
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

public class SoulOfSupernaturalItem extends Item implements ICurioItem {
    public SoulOfSupernaturalItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_supernatural.anchor_soul_stone",
                (int)(MyGoConfig.anchor_soul_stone_range.get()*1),(int)(MyGoConfig.anchor_soul_stone_range.get()*1),
                (float)(MyGoConfig.anchor_soul_stone_time.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x333333))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_supernatural.magnet_soul_stone",
                (int)(MyGoConfig.magnet_soul_stone_range.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xe77c56))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_supernatural.hazard_soul_stone",
                (float)(MyGoConfig.hazard_soul_stone_time.get()*1),
                (int)(MyGoConfig.hazard_soul_stone_range.get()*1),(int)(MyGoConfig.hazard_soul_stone_range.get()*1),
                (float)(MyGoConfig.hazard_soul_stone_damage.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x5a82e2))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_supernatural.undying_soul_stone",
                (float)(MyGoConfig.undying_soul_stone_hp.get()*1),
                (float)(MyGoConfig.undying_soul_stone_heal_time.get()*1), (int)(MyGoConfig.undying_soul_stone_heal_lvl.get()*1),
                (float)(MyGoConfig.undying_soul_stone_damage_time.get()*1), (int)(MyGoConfig.undying_soul_stone_damage_lvl.get()*1),
                (float)(MyGoConfig.undying_soul_stone_fire_time.get()*1), (float)(MyGoConfig.undying_soul_stone_cooldown.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xd8d8d8))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_supernatural.enchanted_golden_apple_soul_stone",
                (float)(MyGoConfig.ega_soul_stoneoul_stone_hp.get()*100),
                (float)(MyGoConfig.ega_soul_stoneoul_stone_damage_time.get()*1), (int)(MyGoConfig.ega_soul_stoneoul_stone_damage_lvl.get()*1),
                (float)(MyGoConfig.ega_soul_stoneoul_stone_heal_time.get()*1), (int)(MyGoConfig.ega_soul_stoneoul_stone_heal_lvl.get()*1),
                (float)(MyGoConfig.ega_soul_stoneoul_stone_re_time.get()*1), (int)(MyGoConfig.ega_soul_stoneoul_stone_re_lvl.get()*1),
                (float)(MyGoConfig.ega_soul_stoneoul_stone_fire_time.get()*1), (float)(MyGoConfig.ega_soul_stoneoul_stone_cooldown.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xfc0000))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_supernatural.the_sea_soul_stone"
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xfdf55f))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_supernatural.mending_soul_stone",
                (float)(MyGoConfig.mending_soul_stone_time.get()*1),(int)(MyGoConfig.mending_soul_stone_number.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x41f384))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasSupernatural(slotContext.entity(), SoulOfSupernatural.get());
    }
}
