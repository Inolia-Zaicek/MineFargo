package com.inolia_zaicek.mine_fargo.Item.IceAndFire.Dragon;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Botania.BotaniaST;
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
public class SoulOfIAFDragonItem extends Item implements ICurioItem {
    public SoulOfIAFDragonItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_dragon.dragon_bone_soul_stone",
                (float)(MyGoConfig.dragon_bone_soul_stone_damage.get()*100),(int)(MyGoConfig.dragon_bone_soul_stone_fixed.get()*1),
                (float)(MyGoConfig.dragon_bone_soul_stone_armor_p.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x7b766a))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_dragon.fire_dragon_blood_soul_stone",
                (float)(MyGoConfig.fire_dragon_blood_soul_stone_time.get()*1),(float)(MyGoConfig.fire_dragon_blood_soul_stone_damage.get()*100),
                (float)(MyGoConfig.fire_dragon_blood_soul_stone_dragon.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xd33a12))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_dragon.ice_dragon_blood_soul_stone",
                (float)(MyGoConfig.ice_dragon_blood_soul_stone_time.get()*1), (int)(MyGoConfig.ice_dragon_blood_soul_stone_ms_lvl.get()*1),
                (int)(MyGoConfig.ice_dragon_blood_soul_stone_dig_lvl.get()*1), (float)(MyGoConfig.ice_dragon_blood_soul_stone_freeze_time.get()*1),
                (float)(MyGoConfig.ice_dragon_blood_soul_stone_dragon.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x0acbda))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_dragon.lightning_dragon_blood_soul_stone",
                (float)(MyGoConfig.lightning_dragon_blood_soul_stone_damage.get()*100),
                (float)(MyGoConfig.lightning_dragon_blood_soul_stone_dragon.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x9a0ada))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_dragon.fire_dragon_steel_soul_stone",
                (float)(MyGoConfig.fire_dragon_steel_soul_stone_armor.get()*1),(float)(MyGoConfig.fire_dragon_steel_soul_stone_armor_t.get()*1),
                (float)(MyGoConfig.fire_dragon_steel_soul_stone_damage.get()*100),(float)(MyGoConfig.fire_dragon_steel_soul_stone_dragon.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x943a3a))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_dragon.ice_dragon_steel_soul_stone",
                (float)(MyGoConfig.ice_dragon_steel_soul_stone_armor.get()*1),(float)(MyGoConfig.ice_dragon_steel_soul_stone_armor_t.get()*1),
                (float)(MyGoConfig.ice_dragon_steel_soul_stone_damage.get()*100),(float)(MyGoConfig.ice_dragon_steel_soul_stone_time.get()*1),
                (float)(MyGoConfig.ice_dragon_steel_soul_stone_dragon.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x6a97c5))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_iaf_dragon.lightning_dragon_steel_soul_stone",
                (float)(MyGoConfig.lightning_dragon_steel_soul_stone_armor.get()*1),(float)(MyGoConfig.lightning_dragon_steel_soul_stone_armor_t.get()*1),
                (float)(MyGoConfig.lightning_dragon_steel_soul_stone_damage.get()*100),(float)(MyGoConfig.lightning_dragon_steel_soul_stone_dragon.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x9f6ac5))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, this.getTooltipItemName(),
                MyGoConfig.fire_dragon_steel_soul_stone_armor.get()+MyGoConfig.ice_dragon_steel_soul_stone_armor.get()
                +MyGoConfig.lightning_dragon_steel_soul_stone_armor.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, this.getTooltipItemName(),
                MyGoConfig.fire_dragon_steel_soul_stone_armor_t.get()+MyGoConfig.ice_dragon_steel_soul_stone_armor_t.get()
                +MyGoConfig.lightning_dragon_steel_soul_stone_armor_t.get(), AttributeModifier.Operation.ADDITION));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasIAFDragon(slotContext.entity(), SoulOfIAFDragon.get());
    }
}
