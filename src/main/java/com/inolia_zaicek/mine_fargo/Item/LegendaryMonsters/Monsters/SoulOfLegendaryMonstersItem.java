package com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Monsters;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Entity.LegendaryEntityST;
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
public class SoulOfLegendaryMonstersItem extends Item implements ICurioItem {
    public SoulOfLegendaryMonstersItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_legendary_monsters.cloud_golem_soul_stone",
                (float)(MyGoConfig.cloud_golem_soul_stone_damage.get()*100),(float)(MyGoConfig.cloud_golem_soul_stone_cooldown.get()*1),
                (float)(MyGoConfig.cloud_golem_soul_stone_time.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xc3d5d9))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_legendary_monsters.frostbitten_golem_soul_stone",
                (int)(MyGoConfig.frostbitten_golem_soul_stone_range.get()*1),(int)(MyGoConfig.frostbitten_golem_soul_stone_range.get()*1),
                (float)(MyGoConfig.frostbitten_golem_soul_stone_damage.get()*100),(float)(MyGoConfig.frostbitten_golem_soul_stone_time.get()*1),
                (int)(MyGoConfig.frostbitten_golem_soul_stone_level.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x33afe6))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_legendary_monsters.ancient_guardian_soul_stone",
                (float)(MyGoConfig.ancient_guardian_soul_stone_fixed_damage.get()*1),(float)(MyGoConfig.ancient_guardian_soul_stone_damage.get()*100),
                (float)(MyGoConfig.ancient_guardian_soul_stone_chance.get()*100),(float)(MyGoConfig.ancient_guardian_soul_stone_time.get()*1),
                (float)(MyGoConfig.ancient_guardian_soul_stone_cooldown.get()*1),
                (float)(MyGoConfig.ancient_guardian_soul_stone_hp.get()*100),(float)(MyGoConfig.ancient_guardian_soul_stone_armor.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x9c887d))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_legendary_monsters.posessed_paladin_soul_stone",
                (float)(MyGoConfig.posessed_paladin_soul_stone_chance.get()*100),(float)(MyGoConfig.posessed_paladin_soul_stone_time.get()*1),
                (int)(MyGoConfig.posessed_paladin_soul_stone_range.get()*1),(int)(MyGoConfig.posessed_paladin_soul_stone_range.get()*1),
                (int)(MyGoConfig.posessed_paladin_soul_stone_level.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xf0e367))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_legendary_monsters.skeletosaurus_soul_stone",
                (float)(MyGoConfig.skeletosaurus_soul_stone_atk.get()*100),(float)(MyGoConfig.skeletosaurus_soul_stone_armor.get()*1),
                (float)(MyGoConfig.skeletosaurus_soul_stone_knockback_resistance.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xf4f4f2))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.ATTACK_DAMAGE, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.skeletosaurus_soul_stone_atk.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        atts.put(Attributes.ARMOR, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.skeletosaurus_soul_stone_armor.get(), AttributeModifier.Operation.ADDITION));
        atts.put(Attributes.KNOCKBACK_RESISTANCE, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.skeletosaurus_soul_stone_knockback_resistance.get(), AttributeModifier.Operation.ADDITION));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! (MyGoUtil.hasLegendaryMonsters(slotContext.entity(), CloudGolemSoulStone.get())
                ||MyGoUtil.hasLegendaryMonsters(slotContext.entity(), FrostbittenGolemSoulStone.get())
                ||MyGoUtil.hasLegendaryMonsters(slotContext.entity(), AncientGuardianSoulStone.get())
                ||MyGoUtil.hasLegendaryMonsters(slotContext.entity(), PosessedPaladinSoulStone.get())
                ||MyGoUtil.hasLegendaryMonsters(slotContext.entity(), SkeletosaurusSoulStone.get())
        );
    }
}
