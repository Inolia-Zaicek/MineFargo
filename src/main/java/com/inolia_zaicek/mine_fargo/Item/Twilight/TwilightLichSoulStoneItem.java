package com.inolia_zaicek.mine_fargo.Item.Twilight;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

import javax.annotation.Nullable;
import java.util.List;

public class TwilightLichSoulStoneItem extends Item implements ICurioItem, TwilightST {
    public TwilightLichSoulStoneItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        String itemName = getTooltipItemName();
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilight.zinc_soul_stone",
                (int)(MyGoConfig.zombie_scepter_soul_stone_strength.get()*1),(int)(MyGoConfig.zombie_scepter_soul_stone_hp_boost.get()*1),
                (float)(MyGoConfig.zombie_scepter_soul_stone_time.get()*1),(float)(MyGoConfig.zombie_scepter_soul_stone_cooldown.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb74a47))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilight.lifedrain_soul_stone",
                (float)(MyGoConfig.lifedrain_soul_stone_heal.get()*100),(float)(MyGoConfig.lifedrain_soul_stone_food.get()*100),
                (float)(MyGoConfig.lifedrain_soul_stone_sat.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x73af51))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_twilight.fortification_soul_stone",
                (int)(MyGoConfig.fortification_soul_stone_cooldown.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xf8e63a))));


        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        //有暮色魂、巫妖石、巫妖石附属，无法安装
        return !(MyGoUtil.hasTwilightLich(slotContext.entity(), ZombieScepterSoulStone.get())
                ||MyGoUtil.hasTwilightLich(slotContext.entity(), LifedrainSoulStone.get())
                ||MyGoUtil.hasTwilightLich(slotContext.entity(), FortificationSoulStone.get())
                ||MyGoUtil.hasTwilightLich(slotContext.entity(), TwilightLichSoulStone.get())
        );
    }
}
