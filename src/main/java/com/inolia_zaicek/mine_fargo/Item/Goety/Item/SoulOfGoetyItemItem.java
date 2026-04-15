package com.inolia_zaicek.mine_fargo.Item.Goety.Item;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Botania.BotaniaST;
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

@SuppressWarnings({"all", "removal"})
public class SoulOfGoetyItemItem extends Item implements ICurioItem {
    public SoulOfGoetyItemItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_goety_item.ectoplasm_soul_stone",
                (int)(MyGoConfig.ectoplasm_soul_stone.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x42afd7))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_goety_item.goety_focus_soul_stone",
                (int)(MyGoConfig.goety_focus_soul_stone.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x777777))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_goety_item.order_about_soul_stone",
                (float)(MyGoConfig.order_about_soul_stone_damage.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x5762c9))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_goety_item.escort_soul_stone",
                (float)(MyGoConfig.escort_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x608ba7))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_goety_item.brew_soul_stone",
                (float)(MyGoConfig.brew_soul_stone_chance_a.get()*100),(float)(MyGoConfig.brew_soul_stone_chance_b.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xc388de))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_goety_item.legion_soul_stone",
                (float)(MyGoConfig.legion_soul_stone_hp.get()*100),(float)(MyGoConfig.legion_soul_stone_atk.get()*100),
                (float)(MyGoConfig.legion_soul_stone_speed.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x743c33))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_goety_item.goety_dark_soul_stone",
                (float)(MyGoConfig.goety_dark_soul_stone_fire.get()*100),
                (float)(MyGoConfig.goety_dark_soul_stone_magic.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x617d82))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! (MyGoUtil.hasGoetyItem(slotContext.entity(), EctoplasmSoulStone.get())
                ||MyGoUtil.hasGoetyItem(slotContext.entity(), GoetyFocusSoulStone.get())
                ||MyGoUtil.hasGoetyItem(slotContext.entity(), OrderAboutSoulStone.get())
                ||MyGoUtil.hasGoetyItem(slotContext.entity(), EscortSoulStone.get())
                ||MyGoUtil.hasGoetyItem(slotContext.entity(), BrewSoulStone.get())
                ||MyGoUtil.hasGoetyItem(slotContext.entity(), LegionSoulStone.get())
                ||MyGoUtil.hasGoetyItem(slotContext.entity(), GoetyDarkSoulStone.get())
        );
    }
}
