package com.inolia_zaicek.mine_fargo.Item.Botania;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Cataclysm.CataclysmST;
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
public class SoulOfBotaniaItem extends Item implements ICurioItem {
    public SoulOfBotaniaItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_botania.manasteel_soul_stone",
                (float)(MyGoConfig.manasteel_soul_stone_time.get()*1),
                (int) (MyGoConfig.manasteel_soul_stone_mana.get() * 1),(int)(MyGoConfig.manasteel_soul_stone_number.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x0061ff))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_botania.elementium_soul_stone",
                (float)(MyGoConfig.elementium_soul_stone_chance.get()*100),(float)(MyGoConfig.elementium_soul_stone_cooldown.get()*1),
                (float) (MyGoConfig.elementium_soul_stone_atk.get() * 100),(float)(MyGoConfig.elementium_soul_stone_time.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xff56d4))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_botania.terrasteel_soul_stone",
                (int) (MyGoConfig.terrasteel_soul_stone_mana.get() * 1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x00ff00))));

        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_botania.gaia_soul_stone",
                (float)(MyGoConfig.gaia_soul_stone_chance.get()*100),(float)(MyGoConfig.gaia_soul_stone_damage.get()*100),
                (float) (MyGoConfig.gaia_soul_stone_time.get() * 1),(float)(MyGoConfig.gaia_soul_stone_heal.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xE4E4E4))));

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! (MyGoUtil.hasBotania(slotContext.entity(), ManasteelSoulStone.get())
                ||MyGoUtil.hasBotania(slotContext.entity(), ElementiumSoulStone.get())
                ||MyGoUtil.hasBotania(slotContext.entity(), TerrasteelSoulStone.get())
                ||MyGoUtil.hasBotania(slotContext.entity(), GaiaSoulStone.get())
        );
    }
}
