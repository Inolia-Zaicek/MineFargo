package com.inolia_zaicek.mine_fargo.Item.Goety.Entity;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.GoetyItemST;
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
public class SoulOfGoetyEntityItem extends Item implements ICurioItem {
    public SoulOfGoetyEntityItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_goety_entity.apostle_soul_stone",
                (float)(MyGoConfig.apostle_soul_stone_damage_restriction.get()*100),(float)(MyGoConfig.apostle_soul_stone_damage_down.get()*100),
                (float)(MyGoConfig.apostle_soul_stone_dead_heal.get()*100),(float)(MyGoConfig.apostle_soul_stone_buff_time.get()*1),
                (int)(MyGoConfig.apostle_soul_stone_buff_lvl.get()*1),(float)(MyGoConfig.apostle_soul_stone_cooldown.get()*1),
                (float)(MyGoConfig.apostle_soul_stone_heal.get()*100),(float)(MyGoConfig.apostle_soul_stone_half.get()*100),
                (float)(MyGoConfig.apostle_soul_stone_up.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xffe300))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_goety_entity.nether_apostle_soul_stone",
                (float)(MyGoConfig.nether_apostle_soul_stone_heal_time.get()*1),
                (float)(MyGoConfig.nether_apostle_soul_stone_heal_number.get()*100),
                (float)(MyGoConfig.nether_apostle_soul_stone_heal_time_fast.get()*100),
                (float)(MyGoConfig.nether_apostle_soul_stone_heal_half.get()*100),
                (int)(MyGoConfig.nether_apostle_soul_stone_range.get()*1),
                (int)(MyGoConfig.nether_apostle_soul_stone_range.get()*1),
                (float)(MyGoConfig.nether_apostle_soul_stone_damage_down.get()*100),
                (float)(MyGoConfig.nether_apostle_soul_stone_time.get()*1),
                (float)(MyGoConfig.nether_apostle_soul_stone_chance.get()*100),
                (int)(MyGoConfig.nether_apostle_soul_stone_lvl.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xff3800))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_goety_entity.vizier_soul_stone",
                (int)(MyGoConfig.vizier_soul_stone_lvl.get()*1),
                (float)(MyGoConfig.vizier_soul_stone_hp.get()*100),(float)(MyGoConfig.vizier_soul_stone_hp_cooldown.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xecbcff))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_goety_entity.redstone_monstrosity_soul_stone",
                (float)(MyGoConfig.redstone_monstrosity_soul_stone_hp_up.get()*100),(float)(MyGoConfig.redstone_monstrosity_soul_stone_hp_damage.get()*100),
                (float)(MyGoConfig.redstone_monstrosity_soul_stone_cooldown.get()*1),(float)(MyGoConfig.redstone_monstrosity_soul_stone_max_damage.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xff0000))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_goety_entity.ender_keeper_soul_stone",
                (float)(MyGoConfig.ender_keeper_soul_stone_time.get()*1),(int)(MyGoConfig.ender_keeper_soul_stone_level.get()*1),
                (int)(MyGoConfig.ender_keeper_soul_stone_range.get()*1),(int)(MyGoConfig.ender_keeper_soul_stone_range.get()*1),
                (float)(MyGoConfig.ender_keeper_soul_stone_damage_down.get()*100),(float)(MyGoConfig.ender_keeper_soul_stone_time_up.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb924ad))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_goety_entity.minister_soul_stone",
                (int)(MyGoConfig.minister_soul_stone_range.get()*1),(int)(MyGoConfig.minister_soul_stone_range.get()*1),
                (int)(MyGoConfig.minister_soul_stone_re_lvl.get()*1),(int)(MyGoConfig.minister_soul_stone_heal_lvl.get()*1),
                (int)(MyGoConfig.minister_soul_stone_wi_lvl.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xa1adad))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MAX_HEALTH, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.redstone_monstrosity_soul_stone_hp_up.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! (MyGoUtil.hasGoetyEntity(slotContext.entity(), ApostleSoulStone.get())
                ||MyGoUtil.hasGoetyEntity(slotContext.entity(), NetherApostleSoulStone.get())
                ||MyGoUtil.hasGoetyEntity(slotContext.entity(), VizierSoulStone.get())
                ||MyGoUtil.hasGoetyEntity(slotContext.entity(), RedstoneMonstrositySoulStone.get())
                ||MyGoUtil.hasGoetyEntity(slotContext.entity(), EnderKeeperSoulStone.get())
                ||MyGoUtil.hasGoetyEntity(slotContext.entity(), MinisterSoulStone.get())
        );
    }
}
