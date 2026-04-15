package com.inolia_zaicek.mine_fargo.Item.Malum;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
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
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.SoulOfMalum;

@SuppressWarnings({"all", "removal"})
public class SoulOfMalumItem extends Item implements ICurioItem {
    public SoulOfMalumItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_malum.starved_soul_stone",
                (float)(MyGoConfig.starved_soul_stone_hp.get()*100),(int)(MyGoConfig.starved_soul_stone_lvl.get()*1),
                (float)(MyGoConfig.starved_soul_stone_time.get()*1),
                (int)(MyGoConfig.starved_soul_stone_max_lvl.get()*1),(float)(MyGoConfig.starved_soul_stone_max_time.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x9a965f))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_malum.totemic_soul_stone",
                (int)(MyGoConfig.zephyrs_courage.get()*1),(int)(MyGoConfig.poseidons_grasp.get()*1),
                (int)(MyGoConfig.gaias_bulwark.get()*1), (int)(MyGoConfig.miners_rage.get()*1),
                (int)(MyGoConfig.aethers_charm.get()*1), (int)(MyGoConfig.anglers_lure.get()*1),
                (int)(MyGoConfig.earthen_might.get()*1),(int)(MyGoConfig.ifrits_embrace.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x6d5b53))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_malum.tainted_soul_stone",
                (float)(MyGoConfig.tainted_soul_stone_heal.get()*100),
                (float)(MyGoConfig.tainted_soul_stone_min_damage.get()*100),
                (float)(MyGoConfig.tainted_soul_stone_max_damage.get()*100),
                (float)(MyGoConfig.tainted_soul_stone_damage_up_chance.get()*100),
                (float)(MyGoConfig.tainted_soul_stone_damage_up_number.get()*100),
                (float)(MyGoConfig.tainted_soul_stone_dig.get()*100),
                (float)(MyGoConfig.tainted_soul_stone_time.get()*100),
                (float)(MyGoConfig.tainted_soul_stone_buff_chance.get()*100),
                (float)(MyGoConfig.tainted_soul_stone_buff_time.get()*1),
                (int)(MyGoConfig.tainted_soul_stone_buff_lvl.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x644e72))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_malum.void_tablet_soul_stone",
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
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x6b2d94))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_malum.arcana_soul_stone",
                (float)(MyGoConfig.arcana_soul_stone_hp.get()*100),(int)(MyGoConfig.arcana_soul_stone_lvl.get()*1),
                (float)(MyGoConfig.arcana_soul_stone_time.get()*1),
                (int)(MyGoConfig.arcana_soul_stone_lvl_max.get()*1),(float)(MyGoConfig.arcana_soul_stone_time_max.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x88a2f7))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_malum.spirit_soul_stone",
                (int)(MyGoConfig.spirit_soul_stone.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xFFFFFF))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_malum.scythe_soul_stone",
                (int)(MyGoConfig.scythe_soul_stone_lvl.get()*1),(float)(MyGoConfig.scythe_soul_stone_time.get()*1),
                (int)(MyGoConfig.scythe_soul_stone_max_lvl.get()*1),(float)(MyGoConfig.scythe_soul_stone_att.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xa173a5))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_malum.soul_ward_soul_stone",
                (float)(MyGoConfig.soul_ward_capacity.get()*1),
                (float)(MyGoConfig.soul_ward_recovery_rate.get()*100),
                (float)(MyGoConfig.soul_ward_integrity.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb174ee))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! (MyGoUtil.hasMalum(slotContext.entity(), StarvedSoulStone.get())
                ||MyGoUtil.hasMalum(slotContext.entity(), TotemicSoulStone.get())
                ||MyGoUtil.hasMalum(slotContext.entity(), TaintedSoulStone.get())
                ||MyGoUtil.hasMalum(slotContext.entity(), VoidTabletSoulStone.get())
                ||MyGoUtil.hasMalum(slotContext.entity(), ArcanaSoulStone.get())
                ||MyGoUtil.hasMalum(slotContext.entity(), SpiritSoulStone.get())
                ||MyGoUtil.hasMalum(slotContext.entity(), ScytheSoulStone.get())
                ||MyGoUtil.hasMalum(slotContext.entity(), SoulWardSoulStone.get())
        );
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        float amount = (float) (MyGoConfig.tainted_soul_stone_buff_speed.get() * 1);
        if (slotContext.entity() != null) {
            float health = slotContext.entity().getHealth();
            float maxHealth = slotContext.entity().getMaxHealth();
            float pct = health / maxHealth;
            amount *= (2 - pct);
        }
        atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, this.getTooltipItemName(),
                amount+MyGoConfig.void_tablet_soul_stone_att_speed.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        //灵魂护盾
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "soul_ward_capacity")))
                , new AttributeModifier(uuid, this.getTooltipItemName(),
                        MyGoConfig.tainted_soul_stone_soul_shield_a.get()+MyGoConfig.soul_ward_capacity.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "soul_ward_integrity")))
                , new AttributeModifier(uuid, this.getTooltipItemName(),
                        MyGoConfig.tainted_soul_stone_soul_shield_b.get()+MyGoConfig.soul_ward_integrity.get(), AttributeModifier.Operation.MULTIPLY_BASE));

        ///法强
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("lodestone", "magic_proficiency")))
                , new AttributeModifier(uuid, this.getTooltipItemName(),
                        MyGoConfig.tainted_soul_stone_magic.get()+MyGoConfig.void_tablet_soul_stone_att_magic.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        //铁魔法
        if (ModList.get().isLoaded("irons_spellbooks")) {
            atts.put(Objects.requireNonNull(
                            ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("irons_spellbooks", "spell_power")))
                    , new AttributeModifier(uuid, this.getTooltipItemName(),
                            MyGoConfig.tainted_soul_stone_magic.get()+MyGoConfig.void_tablet_soul_stone_att_magic.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        }
        atts.put(Attributes.ARMOR_TOUGHNESS, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.void_tablet_soul_stone_att_armor.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.void_tablet_soul_stone_att_hp.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        //灵魂护盾
        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "reserve_staff_charges")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.void_tablet_soul_stone_att_staff.get(), AttributeModifier.Operation.ADDITION));

        atts.put(
                Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "spirit_spoils")))
                , new AttributeModifier(uuid, getTooltipItemName(), MyGoConfig.spirit_soul_stone.get(), AttributeModifier.Operation.ADDITION));
        atts.put(
                Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "scythe_proficiency")))
                , new AttributeModifier(uuid, getTooltipItemName(), MyGoConfig.scythe_soul_stone_att.get(), AttributeModifier.Operation.MULTIPLY_BASE));

        atts.put(Objects.requireNonNull(ForgeRegistries.ATTRIBUTES.getValue(new ResourceLocation("malum", "soul_ward_recovery_rate")))
                , new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.soul_ward_recovery_rate.get(), AttributeModifier.Operation.MULTIPLY_BASE));
        return atts;
    }
    @Override
    public List<Component> getAttributesTooltip(List<Component> tooltips, ItemStack stack) {
        return Collections.emptyList();
    }
}
