package com.inolia_zaicek.mine_fargo.Item.Malum;

import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.ScyllaSoulStone;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.SpiritSoulStone;

@SuppressWarnings({"all", "removal"})
public class ScytheSoulStoneItem extends Item implements ICurioItem, MalumST {
    public ScytheSoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text",
                (int)(MyGoConfig.scythe_soul_stone_lvl.get()*1),(float)(MyGoConfig.scythe_soul_stone_time.get()*1),
                (int)(MyGoConfig.scythe_soul_stone_max_lvl.get()*1),(float)(MyGoConfig.scythe_soul_stone_att.get()*100)
        ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasMalum(slotContext.entity(), ScyllaSoulStone.get());
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = ICurioItem.super.getAttributeModifiers(slotContext, uuid, stack);
        attributes.put(
                Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "scythe_proficiency")))
                , new AttributeModifier(uuid, getTooltipItemName(), MyGoConfig.scythe_soul_stone_att.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        return attributes;
    }
}
