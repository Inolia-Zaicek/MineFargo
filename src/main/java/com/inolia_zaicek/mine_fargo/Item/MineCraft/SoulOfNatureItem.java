package com.inolia_zaicek.mine_fargo.Item.MineCraft;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.NatureST;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.OresST;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.SoulOfNature;

public class SoulOfNatureItem extends Item implements ICurioItem {
    public SoulOfNatureItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_nature.snow_soul_stone"
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x6a9ce7))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_nature.lava_soul_stone",
                (float)(MyGoConfig.lava_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xcc4628))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_nature.mushroom_soul_stone",
                (int)(MyGoConfig.mushroom_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xcd8c6f))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_nature.nether_soul_stone",
                (float)(MyGoConfig.nether_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x814442))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_nature.ender_soul_stone",
                (float)(MyGoConfig.ender_soul_stone_attack.get()*100),(float)(MyGoConfig.ender_soul_stone_armor.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x8e678d))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_nature.ocean_soul_stone",
                MyGoConfig.ocean_soul_stone.get()*100
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xffc908))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_nature.lush_soul_stone",
                (int)(MyGoConfig.lush_soul_stone_saturation.get()*1),(int)(MyGoConfig.lush_soul_stone_food.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x6cc502))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_nature.forest_soul_stone",
                (float)(MyGoConfig.forest_soul_stone.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x8c684d))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(ForgeMod.SWIM_SPEED.get(), new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.ocean_soul_stone.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasNature(slotContext.entity(), SoulOfNature.get());
    }
}
