package com.inolia_zaicek.mine_fargo.Item.AlexsCaves;

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
public class SoulOfAlexsCavesItem extends Item implements ICurioItem {
    public SoulOfAlexsCavesItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_alexs_caves.magnetic_soul_stone",
                (int)(MyGoConfig.magnetic_soul_stone_range.get()*1),(int)(MyGoConfig.magnetic_soul_stone_range.get()*1),
                (float) (MyGoConfig.magnetic_soul_stone_base_atk.get() * 1),(float)(MyGoConfig.magnetic_soul_stone_atk.get()*100),
                (float) (MyGoConfig.magnetic_soul_stone_time.get() * 1),(float)(MyGoConfig.magnetic_soul_stone_chance.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xeb2e2e))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_alexs_caves.primitive_soul_stone",
                (float)(MyGoConfig.primitive_soul_stone_time.get()*1),(float)(MyGoConfig.primitive_soul_stone_cooldown.get()*1),
                (float) (MyGoConfig.primitive_soul_stone_damage.get() * 100),(float)(MyGoConfig.primitive_soul_stone_damage_cooldown.get()*1),
                (int)(MyGoConfig.primitive_soul_stone_buff.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb88a58))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_alexs_caves.toxic_soul_stone",
                (float)(MyGoConfig.toxic_soul_stone_buff_time.get()*1),(int)(MyGoConfig.toxic_soul_stone_buff_lvl.get()*1),
                (float) (MyGoConfig.toxic_soul_stone_time.get() * 100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x82bb65))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_alexs_caves.abyssal_chasm_soul_stone"
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x40a9d8))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_alexs_caves.forlorn_soul_stone",
                (float)(MyGoConfig.forlorn_soul_stone_damage.get()*100),(float)(MyGoConfig.forlorn_soul_stone_cooldown.get()*1),
                (float) (MyGoConfig.forlorn_soul_stone_dig.get() * 100),
                (float)(MyGoConfig.forlorn_soul_stone_time.get()*1),(float)(MyGoConfig.forlorn_soul_stone_buff.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xbc3131))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_alexs_caves.candy_cavity_soul_stone",
                (float)(MyGoConfig.candy_cavity_soul_stone_base.get()*100),(float)(MyGoConfig.candy_cavity_soul_stone_speed.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xf541c1))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! (MyGoUtil.hasAlexsCaves(slotContext.entity(), MagneticSoulStone.get())
                ||MyGoUtil.hasAlexsCaves(slotContext.entity(), PrimitiveSoulStone.get())
                ||MyGoUtil.hasAlexsCaves(slotContext.entity(), ToxicSoulStone.get())
                ||MyGoUtil.hasAlexsCaves(slotContext.entity(), AbyssalChasmSoulStone.get())
                ||MyGoUtil.hasAlexsCaves(slotContext.entity(), ForlornSoulStone.get())
                ||MyGoUtil.hasAlexsCaves(slotContext.entity(), CandyCavitySoulStone.get())
        );
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.candy_cavity_soul_stone_base.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        if(slotContext.entity()!=null&&slotContext.entity().isSprinting()) {
            atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.candy_cavity_soul_stone_speed.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        }
        return atts;
    }
}
