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

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.TaintedSoulStone;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.VoidTabletSoulStone;

@SuppressWarnings({"all", "removal"})
public class VoidTabletSoulStoneItem extends Item implements ICurioItem, MalumST {
    public VoidTabletSoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip." + "mine_fargo" + "." + itemName + ".text",
                (int)(MyGoConfig.void_tablet_soul_stone_kill_lvl.get()*1),
                (float)(MyGoConfig.void_tablet_soul_stone_kill_time.get()*1),
                (int)(MyGoConfig.void_tablet_soul_stone_kill_lvl_max.get()*1),
                (float)(MyGoConfig.void_tablet_soul_stone_kill_time_max.get()*1),

                (int)(MyGoConfig.void_tablet_soul_stone_hit_lvl.get()*1),
                (float)(MyGoConfig.void_tablet_soul_stone_hit_time.get()*1),
                (int)(MyGoConfig.void_tablet_soul_stone_hit_lvl_max.get()*1),
                (float)(MyGoConfig.void_tablet_soul_stone_hit_time_max.get()*1),

                (float)(MyGoConfig.void_tablet_soul_stone_time.get()*100),
                (float)(MyGoConfig.void_tablet_soul_stone_hit.get()*100)
        ).withStyle(style -> style.withColor(ChatFormatting.GRAY)));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! MyGoUtil.hasMalum(slotContext.entity(), VoidTabletSoulStone.get());
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.void_tablet_soul_stone_att_speed.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.void_tablet_soul_stone_att_armor.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.void_tablet_soul_stone_att_hp.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        //灵魂护盾
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "reserve_staff_charges")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.void_tablet_soul_stone_att_staff.get(), AttributeModifier.Operation.ADDITION));

        ///法强
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("lodestone", "magic_proficiency")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.void_tablet_soul_stone_att_magic.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        //铁魔法
        if (ModList.get().isLoaded("irons_spellbooks")) {
            atts.put(Objects.requireNonNull(
                            ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "spell_power")))
                    , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.void_tablet_soul_stone_att_magic.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        }
        return atts;
    }
}
