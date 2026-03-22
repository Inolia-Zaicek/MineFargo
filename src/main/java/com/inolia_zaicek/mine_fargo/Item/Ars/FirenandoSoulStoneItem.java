package com.inolia_zaicek.mine_fargo.Item.Ars;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.hollingsworth.arsnouveau.common.items.curios.DiscountRing;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
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

@SuppressWarnings({"all", "removal"})
public class FirenandoSoulStoneItem extends DiscountRing implements ICurioItem {
    public FirenandoSoulStoneItem() {super();}
    @Override
    public int getManaDiscount() {
        return (int)(MyGoConfig.firenando_soul_stone_cast.get()*1);
    }
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text",
                (int)(MyGoConfig.firenando_soul_stone_cast.get()*1),(int)(MyGoConfig.firenando_soul_stone_damage.get()*1)
        ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(
                        new ResourceLocation("ars_nouveau", "ars_nouveau.perk.spell_damage")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.firenando_soul_stone_damage.get(), AttributeModifier.Operation.ADDITION));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasSupernatural(slotContext.entity(), FirenandoSoulStone.get());
    }
}
