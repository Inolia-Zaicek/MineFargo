package com.inolia_zaicek.mine_fargo.Item.Cataclysm;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Tacz.TaczST;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
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
public class SoulOfCataclysmItem extends Item implements ICurioItem {
    public SoulOfCataclysmItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_cataclysm.ignis_soul_stone",
                (float)(MyGoConfig.ignis_soul_stone_give_chance.get()*100),(float)(MyGoConfig.ignis_soul_stone_up_chance.get()*100),
                (int)(MyGoConfig.ignis_soul_stone_max_level.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xe9a32f))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_cataclysm.the_leviathan_soul_stone",
                (int)(MyGoConfig.the_leviathan_soul_stone_time.get()*1),(int)(MyGoConfig.the_leviathan_soul_stone_level.get()*1),
                (float)(MyGoConfig.the_leviathan_soul_stone_armor.get()*100),(float)(MyGoConfig.the_leviathan_soul_stone_damage.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x562fe9))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_cataclysm.maledictus_soul_stone",
                (float)(MyGoConfig.maledictus_soul_stone_chance.get()*100),(float)(MyGoConfig.maledictus_soul_stone_arrow_chance.get()*100),
                (float)(MyGoConfig.maledictus_soul_stone_heal.get()*100),(int)(MyGoConfig.maledictus_soul_stone_time.get()*1),
                (int)(MyGoConfig.maledictus_soul_stone_fire_time.get()*1),(int)(MyGoConfig.maledictus_soul_stone_cooldown_time.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x43d69f))));
        
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_cataclysm.ender_guardian_soul_stone",
                (float)(MyGoConfig.ender_guardian_soul_stone_chance.get()*100),
                (int)(MyGoConfig.ender_guardian_soul_stone_time.get()*1),(int)(MyGoConfig.ender_guardian_soul_stone_cooldown.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xa843d6))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_cataclysm.ancient_remnant_soul_stone",
                (float)(MyGoConfig.ancient_remnant_soul_stone_armor.get()*1),
                (int)(MyGoConfig.ancient_remnant_soul_stone_time.get()*1),(int)(MyGoConfig.ancient_remnant_soul_stone_lvl.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xbfc454))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_cataclysm.the_harbinger_soul_stone",
                (float)(MyGoConfig.the_harbinger_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xc84242))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_cataclysm.netherite_monstrosity_soul_stone",
                (float)(MyGoConfig.netherite_monstrosity_soul_stone_chance.get()*100),
                (int)(MyGoConfig.netherite_monstrosity_soul_stone_time.get()*1),(int)(MyGoConfig.netherite_monstrosity_soul_stone_max_lvl.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xd87140))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_cataclysm.scylla_soul_stone",
                (int)(MyGoConfig.scylla_soul_stone_time.get()*1),(int)(MyGoConfig.scylla_soul_stone_level.get()*1),
                (float)(MyGoConfig.scylla_soul_stone_damage.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x3fb8cc))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.ancient_remnant_soul_stone_armor.get(), AttributeModifier.Operation.ADDITION));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasCataclysm(slotContext.entity(), MyGoItemRegister.SoulOfCataclysm.get());
    }
}
