package com.inolia_zaicek.mine_fargo.Item.Create;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Iron.IronST;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import io.redspace.ironsspellbooks.api.registry.AttributeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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

public class SoulOfCreateItem extends Item implements ICurioItem {
    public SoulOfCreateItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_create.zinc_soul_stone",
                (float)(MyGoConfig.zinc_soul_stone_speed.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x66b389))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_create.andesite_alloy_soul_stone"
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x819784))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_create.brass_soul_stone",
                (float)(MyGoConfig.brass_soul_stone_damage.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xdea83b))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_create.sturdy_sheet_soul_stone",
                (float)(MyGoConfig.sturdy_sheet_soul_stone_damage.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x696070))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_create.cardboard_soul_stone"
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xc8a47d))));

        if(MyGoConfig.blaze_cake_soul_stone_super.get()) {
            pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_create.blaze_cake_soul_stone1",
                    (int) (MyGoConfig.blaze_cake_soul_stone_time.get() * 1),(int) (MyGoConfig.blaze_cake_soul_stone_max_time.get() * 1)
            ).withStyle(style -> style.withColor(TextColor.fromRgb(0x7b84e4))));
        }else {
            pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_create.blaze_cake_soul_stone2",
                    (int) (MyGoConfig.blaze_cake_soul_stone_time.get() * 1),(int) (MyGoConfig.blaze_cake_soul_stone_max_time.get() * 1)
            ).withStyle(style -> style.withColor(TextColor.fromRgb(0x7b84e4))));
        }

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_create.rose_quartz_soul_stone",
                (int)(MyGoConfig.rose_quartz_soul_stone_number.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xda3e77))));


        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasCreate(slotContext.entity(), CreateST.class);
    }
}
