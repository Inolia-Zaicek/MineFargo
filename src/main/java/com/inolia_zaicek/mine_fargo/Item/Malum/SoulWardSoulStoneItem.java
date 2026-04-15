package com.inolia_zaicek.mine_fargo.Item.Malum;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.SoulWardSoulStone;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.TaintedSoulStone;

@SuppressWarnings({"all", "removal"})
public class SoulWardSoulStoneItem extends Item implements ICurioItem, MalumST {
    public SoulWardSoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text",
                (float)(MyGoConfig.soul_ward_capacity.get()*1),
                (float)(MyGoConfig.soul_ward_recovery_rate.get()*100),
                (float)(MyGoConfig.soul_ward_integrity.get()*100)
        ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasMalum(slotContext.entity(), SoulWardSoulStone.get());
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        //灵魂护盾
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "soul_ward_capacity")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.soul_ward_capacity.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "soul_ward_recovery_rate")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.soul_ward_recovery_rate.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "soul_ward_integrity")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.soul_ward_integrity.get(), AttributeModifier.Operation.MULTIPLY_BASE));

        return atts;
    }
}
