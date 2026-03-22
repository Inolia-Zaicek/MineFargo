package com.inolia_zaicek.mine_fargo.Item.Twilight.TwilightForest;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Twilight.TwilightST;
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
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

public class SoulOfTwilightForestItem extends Item implements ICurioItem {
    public SoulOfTwilightForestItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilightforest.ironwood_soul_stone",
                (float)(MyGoConfig.ironwood_soul_stone_armor.get()*1),(float)(MyGoConfig.ironwood_soul_stone_armor_toughness.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xe1f5a4))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilightforest.steeleaf_soul_stone",
                (float)(MyGoConfig.steeleaf_soul_stone_time.get()*1),(int)(MyGoConfig.steeleaf_soul_stone_number.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb7cf87))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilightforest.fiery_iron_soul_stone",
                (float)(MyGoConfig.fiery_iron_soul_stone_time.get()*1),(float)(MyGoConfig.fiery_iron_soul_stone_heal.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xe4941b))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilightforest.fluffy_cloud_soul_stone",
                (float)(MyGoConfig.fluffy_cloud_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xcbd7e9))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilightforest.twilight_giant_soul_stone",
                (float)(MyGoConfig.twilight_giant_soul_stone_entity.get()*1),(float)(MyGoConfig.twilight_giant_soul_stone_block.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x747e82))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilightforest.quest_ram_soul_stone",
                (float)(MyGoConfig.quest_ram_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb9a2d2))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasTwilightForest(slotContext.entity(), SoulOfTwilightForest.get());
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.ironwood_soul_stone_armor.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.ironwood_soul_stone_armor_toughness.get(), AttributeModifier.Operation.ADDITION));
        atts.put(ForgeMod.ENTITY_REACH.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.twilight_giant_soul_stone_entity.get(), AttributeModifier.Operation.ADDITION));
        atts.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.twilight_giant_soul_stone_block.get(), AttributeModifier.Operation.ADDITION));
        return atts;
    }
}
