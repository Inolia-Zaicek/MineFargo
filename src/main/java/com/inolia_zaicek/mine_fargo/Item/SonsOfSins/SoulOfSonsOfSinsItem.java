package com.inolia_zaicek.mine_fargo.Item.SonsOfSins;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
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
public class SoulOfSonsOfSinsItem extends Item implements ICurioItem {
    public SoulOfSonsOfSinsItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_sons_of_sins.envy_sin_soul_stone",
                (float) (MyGoConfig.envy_sin_soul_stone_atk_speed.get() * 100), (float) (MyGoConfig.envy_sin_soul_stone_atk.get() * 100),
                (float) (MyGoConfig.envy_sin_soul_stone_speed.get() * 100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xe1ced3))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_sons_of_sins.gluttony_sin_soul_stone",
                (float) (MyGoConfig.gluttony_sin_soul_stone_kill.get() * 100), (float) (MyGoConfig.gluttony_sin_soul_stone_max.get() * 100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb88996))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_sons_of_sins.greed_sin_soul_stone",
                (float) (MyGoConfig.greed_sin_soul_stone_down.get() * 100), (int) (MyGoConfig.greed_sin_soul_stone_drop.get() * 1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xe1ced3))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_sons_of_sins.lust_sin_soul_stone",
                (float) (MyGoConfig.lust_sin_soul_stone_down.get() * 100), (float) (MyGoConfig.lust_sin_soul_stone_armor_p.get() * 100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb88996))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_sons_of_sins.pride_sin_soul_stone",
                (float) (MyGoConfig.pride_sin_soul_stone.get() * 100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xe1ced3))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_sons_of_sins.sloth_sin_soul_stone",
                (float) (MyGoConfig.sloth_sin_soul_stone_hp.get() * 100), (float) (MyGoConfig.sloth_sin_soul_stone_time.get() * 1),
                (int) (MyGoConfig.sloth_sin_soul_stone_lvl.get() * 1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb88996))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_sons_of_sins.wrath_sin_soul_stone",
                (float) (MyGoConfig.wrath_sin_soul_stone_hp.get() * 100), (float) (MyGoConfig.wrath_sin_soul_stone_damage.get() * 100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xe1ced3))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ATTACK_SPEED, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.envy_sin_soul_stone_atk_speed.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.sloth_sin_soul_stone_hp.get()-MyGoConfig.wrath_sin_soul_stone_hp.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        if(slotContext.entity()!=null&&slotContext.entity().isSprinting()) {
            atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.envy_sin_soul_stone_speed.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasSonsOfSins(slotContext.entity(), SoulOfSonsOfSins.get());
    }
}
