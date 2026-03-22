package com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Entity;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
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

@SuppressWarnings({"all", "removal"})
public class SoulOfLegendaryEntityItem extends Item implements ICurioItem {
    public SoulOfLegendaryEntityItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_legendary_entity.dune_sentinel_soul_stone",
                (float)(MyGoConfig.dune_sentinel_soul_stone_up.get()*100),(float)(MyGoConfig.dune_sentinel_soul_stone_down.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xc1aa58))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_legendary_entity.endersent_soul_stone",
                (float)(MyGoConfig.endersent_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x9775a4))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_legendary_entity.lava_eater_soul_stone",
                (float)(MyGoConfig.lava_eater_soul_stone_time.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb14430))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_legendary_entity.withered_abomination_soul_stone",
                (float)(MyGoConfig.withered_abomination_soul_stone_atk.get()*100),(float)(MyGoConfig.withered_abomination_soul_stone_time.get()*1),
                (int)(MyGoConfig.withered_abomination_soul_stone_level.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x8d8d8d))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_legendary_entity.shulker_mimic_soul_stone",
                (float)(MyGoConfig.shulker_mimic_soul_stone_chance.get()*100),(float)(MyGoConfig.shulker_mimic_soul_stone_time.get()*1),
                (int)(MyGoConfig.shulker_mimic_soul_stone_level.get()*1),(float)(MyGoConfig.shulker_mimic_soul_stone_cooldown.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x7e5c8b))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_legendary_entity.overgrown_colossus_soul_stone",
                (float)(MyGoConfig.overgrown_colossus_soul_stone_time.get()*1),(int)(MyGoConfig.overgrown_colossus_soul_stone_level.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x78c059))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.withered_abomination_soul_stone_atk.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasLegendaryEntity(slotContext.entity(), SoulOfLegendaryEntity.get());
    }
}
