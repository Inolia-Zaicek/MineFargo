package com.inolia_zaicek.mine_fargo.Item.MeetFight;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
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
import net.minecraftforge.common.ForgeMod;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;

@SuppressWarnings({"all", "removal"})
public class SoulOfMeetFightItem extends Item implements ICurioItem {
    public SoulOfMeetFightItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_meet_fight.fossil_soul_stone",
                (float) (MyGoConfig.fossil_soul_stone_boom.get() * 100),(float) (MyGoConfig.fossil_soul_stone_hp.get() * 100),
                (float) (MyGoConfig.fossil_soul_stone_resi.get() * 100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xcb5340))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_meet_fight.phantoplasm_soul_stone"
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x44bde4))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_meet_fight.ante_soul_stone",
                (float) (MyGoConfig.ante_soul_stone_damage.get() * 100),(float) (MyGoConfig.ante_soul_stone_resi.get() * 100),
                (float) (MyGoConfig.ante_soul_stone_base_chance.get() * 100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xed3c3c))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_meet_fight.violet_soul_stone",
                (float) (MyGoConfig.violet_soul_stone_down_hp.get() * 100),(float) (MyGoConfig.violet_soul_stone_damage.get() * 100),
                (float) (MyGoConfig.violet_soul_stone_exp.get() * 100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xa560c8))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! (MyGoUtil.hasMeetFight(slotContext.entity(), FossilSoulStone.get())
                ||MyGoUtil.hasMeetFight(slotContext.entity(), PhantoplasmSoulStone.get())
                ||MyGoUtil.hasMeetFight(slotContext.entity(), AnteSoulStone.get())
                ||MyGoUtil.hasMeetFight(slotContext.entity(), VioletSoulStone.get())
        );
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.fossil_soul_stone_atk.get(), AttributeModifier.Operation.ADDITION));
        atts.put(ForgeMod.BLOCK_REACH.get(), new AttributeModifier(uuid, this.getTooltipItemName(), 2, AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, this.getTooltipItemName(), -MyGoConfig.violet_soul_stone_down_hp.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        return atts;
    }
}
