package com.inolia_zaicek.mine_fargo.Item.EnigmaticLegacy;

import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
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

import static com.inolia_zaicek.mine_fargo.Item.EnigmaticLegacy.AbyssSoulStoneItem.abyss_soul_stone_nbt;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.SoulOfBotania;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;

@SuppressWarnings({"all", "removal"})
public class SoulOfEnigmaticLegacyItem extends Item implements ICurioItem {
    public SoulOfEnigmaticLegacyItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_enigmatic_legacy.curses_soul_stone",
                (float)(MyGoConfig.curses_soul_stone.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x9a3bde))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_enigmatic_legacy.abyss_soul_stone",
                (float)(MyGoConfig.abyss_soul_stone_heal.get()*100),
                (int)(MyGoConfig.abyss_soul_stone_slow.get()*1),(int)(MyGoConfig.abyss_soul_stone_weakness.get()*1),
                (float)(MyGoConfig.abyss_soul_stone_atk_up.get()*1),(float)(MyGoConfig.abyss_soul_stone_armor_up.get()*1),
                (float)(MyGoConfig.abyss_soul_stone_atk_max.get()*1),(float)(MyGoConfig.abyss_soul_stone_armor_max.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x93659e))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_enigmatic_legacy.etherium_soul_stone",
                (float)(MyGoConfig.etherium_soul_stone_hp.get()*100),(float)(MyGoConfig.etherium_soul_stone_damage.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x40e4eb))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! (MyGoUtil.hasEnigmaticLegacy(slotContext.entity(), CursesSoulStone.get())
                ||MyGoUtil.hasEnigmaticLegacy(slotContext.entity(), AbyssSoulStone.get())
                ||MyGoUtil.hasEnigmaticLegacy(slotContext.entity(), EtheriumSoulStone.get())
        );
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> attributes = ICurioItem.super.getAttributeModifiers(slotContext, uuid, stack);
        CompoundTag persistentData = stack.getOrCreateTag();
        //击杀数量
        double killNumber = persistentData.getInt(String.valueOf(abyss_soul_stone_nbt));
        attributes.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, getTooltipItemName(), MyGoConfig.abyss_soul_stone_atk.get()+
                Math.min(MyGoConfig.abyss_soul_stone_atk_max.get(),MyGoConfig.abyss_soul_stone_atk_up.get()*killNumber), AttributeModifier.Operation.ADDITION));
        attributes.put(Attributes.ARMOR, new AttributeModifier(uuid, getTooltipItemName(),
                Math.min(MyGoConfig.abyss_soul_stone_armor_max.get(),MyGoConfig.abyss_soul_stone_armor_up.get()*killNumber), AttributeModifier.Operation.ADDITION));
        return attributes;
    }
}
