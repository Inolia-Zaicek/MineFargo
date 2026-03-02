package com.inolia_zaicek.mine_fargo.Item.L2.Hostility;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.SonsOfSins.SonsOfSinsST;
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
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@SuppressWarnings({"all", "removal"})
public class SoulOfL2HostilityItem extends Item implements ICurioItem {
    public SoulOfL2HostilityItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_l2hostility.body_hostility_soul_stone",
                (float)(MyGoConfig.body_hostility_soul_stone_hp.get()*100),
                (float)(MyGoConfig.body_hostility_soul_stone_armor.get()*1),(float)(MyGoConfig.body_hostility_soul_stone_armor_t.get()*1),
                (int)(MyGoConfig.body_hostility_soul_stone_buff_lvl.get()*1),
                (float)(MyGoConfig.body_hostility_soul_stone_heal_hp.get()*100),
                (int)(MyGoConfig.body_hostility_soul_stone_adaptive_number.get()*1),
                (float)(MyGoConfig.body_hostility_soul_stone_adaptive_re.get()*100), (float)(MyGoConfig.body_hostility_soul_stone_adaptive_max_re.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xcccccc))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_l2hostility.corrosion_hostility_soul_stone",
                (float)(MyGoConfig.corrosion_hostility_soul_stone_time.get()*1),
                (int)(MyGoConfig.corrosion_hostility_soul_stone_weakness.get()*1),(int)(MyGoConfig.corrosion_hostility_soul_stone_slowness.get()*1),
                (int)(MyGoConfig.corrosion_hostility_soul_stone_poison.get()*1), (int)(MyGoConfig.corrosion_hostility_soul_stone_wither.get()*1),
                (int)(MyGoConfig.corrosion_hostility_soul_stone_flame.get()*1), (int)(MyGoConfig.corrosion_hostility_soul_stone_frozen.get()*1),
                (int)(MyGoConfig.corrosion_hostility_soul_stone_gravity.get()*1), (int)(MyGoConfig.corrosion_hostility_soul_stone_curse.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x999999))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_l2hostility.resistance_hostility_soul_stone",
                (float)(MyGoConfig.resistance_hostility_soul_stone_fire.get()*100), (float)(MyGoConfig.resistance_hostility_soul_stone_magic.get()*100),
                (float)(MyGoConfig.resistance_hostility_soul_stone_atk.get()*100),(float)(MyGoConfig.resistance_hostility_soul_stone_pre.get()*100),
                (float)(MyGoConfig.resistance_hostility_soul_stone_time.get()*1),(float)(MyGoConfig.resistance_hostility_soul_stone_armor_p.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xcccccc))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_l2hostility.aqua_hostility_soul_stone",
                (int)(MyGoConfig.aqua_hostility_soul_stone_range.get()*1),(int)(MyGoConfig.aqua_hostility_soul_stone_range.get()*1),
                (float)(MyGoConfig.aqua_hostility_soul_stone_my_damage.get()*100), (float)(MyGoConfig.aqua_hostility_soul_stone_my_armor.get()*100),
                (float)(MyGoConfig.aqua_hostility_soul_stone_own_damage.get()*100), (float)(MyGoConfig.aqua_hostility_soul_stone_own_armor.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x999999))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_l2hostility.zone_hostility_soul_stone",
                (float)(MyGoConfig.zone_hostility_soul_stone_damage.get()*100),
                (int)(MyGoConfig.zone_hostility_soul_stone_range.get()*1),(int)(MyGoConfig.zone_hostility_soul_stone_range.get()*1),
                (float)(MyGoConfig.zone_hostility_soul_stone_speed.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xcccccc))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_l2hostility.destroy_hostility_soul_stone",
                (int)(MyGoConfig.destroy_hostility_soul_stone_range.get()*1),(int)(MyGoConfig.destroy_hostility_soul_stone_range.get()*1),
                (float)(MyGoConfig.destroy_hostility_soul_stone_damage.get()*100), (float)(MyGoConfig.destroy_hostility_soul_stone_armor.get()*100),
                (float)(MyGoConfig.destroy_hostility_soul_stone_buff.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x999999))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_l2hostility.ultra_hostility_soul_stone",
                (float)(MyGoConfig.ultra_hostility_soul_stone_heal.get()*100),(float)(MyGoConfig.ultra_hostility_soul_stone_time.get()*1),
                (int)(MyGoConfig.ultra_hostility_soul_stone_range.get()*1),(int)(MyGoConfig.ultra_hostility_soul_stone_range.get()*1),
                (float)(MyGoConfig.ultra_hostility_soul_stone_base_atk.get()*1), (float)(MyGoConfig.ultra_hostility_soul_stone_atk.get()*100),
                (float)(MyGoConfig.ultra_hostility_soul_stone_damage.get()*100), (int)(MyGoConfig.ultra_hostility_soul_stone_number.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xcccccc))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, this.getTooltipItemName(),
                MyGoConfig.body_hostility_soul_stone_hp.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, this.getTooltipItemName(),
                MyGoConfig.body_hostility_soul_stone_armor.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, this.getTooltipItemName(),
                MyGoConfig.body_hostility_soul_stone_armor_t.get(), AttributeModifier.Operation.ADDITION));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasL2Hostility(slotContext.entity(), L2HostilityST.class);
    }
    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        return Collections.emptyList();
    }
}
