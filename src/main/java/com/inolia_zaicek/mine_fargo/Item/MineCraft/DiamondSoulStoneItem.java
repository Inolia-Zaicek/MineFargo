package com.inolia_zaicek.mine_fargo.Item.MineCraft;

import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.loot.LootContext;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

@SuppressWarnings({"all", "removal"})
public class DiamondSoulStoneItem extends Item implements ICurioItem {
    public DiamondSoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text")
                .withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    //提升抢夺等级
    @Override
    public int getLootingLevel(SlotContext slotContext, DamageSource source, LivingEntity target, int baseLooting, ItemStack stack) {
        return 1;
    }
    //提升时运等级
    @Override
    public int getFortuneLevel(SlotContext slotContext, LootContext lootContext, ItemStack stack) {
        return 1 ;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasSpecificItem(slotContext.entity(), DiamondSoulStoneItem.class);
    }
}
