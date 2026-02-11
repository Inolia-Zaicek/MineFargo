package com.inolia_zaicek.mine_fargo.Item.Twilight;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
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
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class SoulOfTwilightItem extends Item implements ICurioItem {
    public SoulOfTwilightItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilight.naga_soul_stone",
                (float)(MyGoConfig.naga_soul_stone_speed.get()*100),(float)(MyGoConfig.naga_soul_stone_heal.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x568056))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilight.zinc_soul_stone",
                (int)(MyGoConfig.zombie_scepter_soul_stone_strength.get()*1),(int)(MyGoConfig.zombie_scepter_soul_stone_hp_boost.get()*1),
                (float)(MyGoConfig.zombie_scepter_soul_stone_time.get()*1),(float)(MyGoConfig.zombie_scepter_soul_stone_cooldown.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb74a47))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilight.lifedrain_soul_stone",
                (float)(MyGoConfig.lifedrain_soul_stone_heal.get()*100),(float)(MyGoConfig.lifedrain_soul_stone_food.get()*100),
                (float)(MyGoConfig.lifedrain_soul_stone_sat.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x73af51))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilight.fortification_soul_stone",
                (int)(MyGoConfig.fortification_soul_stone_cooldown.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xf8e63a))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilight.minoshroom_soul_stone",
                (float)(MyGoConfig.minoshroom_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xba4944))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilight.twilight_hydra_soul_stone",
                (int)(MyGoConfig.twilight_hydra_soul_stone_range.get()*1),(int)(MyGoConfig.twilight_hydra_soul_stone_range.get()*1),
                (float)(MyGoConfig.twilight_hydra_soul_stone_armor.get()*100),(float)(MyGoConfig.twilight_hydra_soul_stone_last_hp.get()*100),
                (float)(MyGoConfig.twilight_hydra_soul_stone_heal.get()*1),(float)(MyGoConfig.twilight_hydra_soul_stone_damage.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x57b1c4))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilight.knight_phantom_soul_stone",
                (float)(MyGoConfig.knight_phantom_soul_stone_armor.get()*1),(float)(MyGoConfig.knight_phantom_soul_stone_damage.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xa9b991))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilight.ur_ghast_soul_stone",
                (float)(MyGoConfig.ur_ghast_soul_stone_number.get()*100),(float)(MyGoConfig.ur_ghast_soul_stone_time.get()*1),
                (float)(MyGoConfig.ur_ghast_soul_stone_armor.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xbd6d66))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilight.alpha_yeti_soul_stone",
                (float)(MyGoConfig.alpha_yeti_soul_stone_hp.get()*100),
                (int)(MyGoConfig.alpha_yeti_soul_stone_range.get()*1),(int)(MyGoConfig.alpha_yeti_soul_stone_range.get()*1),
                (float)(MyGoConfig.alpha_yeti_soul_stone_damage.get()*100),(float)(MyGoConfig.alpha_yeti_soul_stone_cooldown.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x5ab0d6))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilight.snow_queen_soul_stone",
                (int)(MyGoConfig.snow_queen_soul_stone_range.get()*1),(int)(MyGoConfig.snow_queen_soul_stone_range.get()*1),
                (float)(MyGoConfig.snow_queen_soul_stone_damage.get()*100),(float)(MyGoConfig.snow_queen_soul_stone_cooldown.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x84b0c1))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasTwilight(slotContext.entity(), TwilightST.class);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.naga_soul_stone_speed.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        return atts;
    }
}
