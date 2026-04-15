package com.inolia_zaicek.mine_fargo.Item.Tacz;

import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.ChatFormatting;
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
public class SoulOfTaczItem extends Item implements ICurioItem {
    public SoulOfTaczItem() {super((new Properties()).stacksTo(1).fireResistant());}
    protected String getTooltipItemName() {
        return BuiltInRegistries.ITEM.getKey(this).getPath();
    }
    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_tacz.ammo_soul_stone",
                (int)(MyGoConfig.ammo_soul_stone_time.get()*1),(int)(MyGoConfig.ammo_soul_stone_number.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0x8bcc97))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_tacz.handgun_soul_stone",
                (float)(MyGoConfig.handgun_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xf4ecbe))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_tacz.rifle_soul_stone",
                (int)(MyGoConfig.rifle_soul_stone_number.get()*1),(float)(MyGoConfig.rifle_soul_stone_damage.get()*100),(int)(MyGoConfig.rifle_soul_stone_time.get()*1)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xf4cabe))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_tacz.sniper_rifle_soul_stone",
                (int)(MyGoConfig.sniper_rifle_soul_stone_time.get()*1),(float)(MyGoConfig.sniper_rifle_soul_stone_damage.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xbedcf4))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_tacz.shotgun_soul_stone",
                (float)(MyGoConfig.shotgun_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xd6ffb3))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_tacz.submachine_gun_soul_stone",
                (float)(MyGoConfig.submachine_gun_soul_stone_speed.get()*100),(float)(MyGoConfig.submachine_gun_soul_stone_chance.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xe7b6fc))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_tacz.heavy_machine_gun_soul_stone",
                (float)(MyGoConfig.heavy_machine_gun_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb3beff))));
        pTooltipComponents.add(Component.translatable("tooltip.mine_fargo.soul_of_tacz.machine_gun_soul_stone",
                (float)(MyGoConfig.machine_gun_soul_stone.get()*100)
        ).withStyle(style -> style.withColor(TextColor.fromRgb(0xb3fff2))));
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
    @Override
    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(SlotContext slotContext, UUID uuid, ItemStack stack) {
        Multimap<Attribute, AttributeModifier> atts = LinkedHashMultimap.create();
        atts.put(Attributes.MOVEMENT_SPEED, new AttributeModifier(uuid, this.getTooltipItemName(), MyGoConfig.submachine_gun_soul_stone_speed.get(), AttributeModifier.Operation.MULTIPLY_TOTAL));
        return atts;
    }
    @Override
    public boolean canEquip(SlotContext slotContext, ItemStack stack) {
        return ! (MyGoUtil.hasTacz(slotContext.entity(), MyGoItemRegister.AmmoSoulStone.get())
                ||MyGoUtil.hasTacz(slotContext.entity(), MyGoItemRegister.HandgunSoulStone.get())
                ||MyGoUtil.hasTacz(slotContext.entity(), MyGoItemRegister.RifleSoulStone.get())
                ||MyGoUtil.hasTacz(slotContext.entity(), MyGoItemRegister.SniperRifleSoulStone.get())
                ||MyGoUtil.hasTacz(slotContext.entity(), MyGoItemRegister.ShotgunSoulStone.get())
                ||MyGoUtil.hasTacz(slotContext.entity(), MyGoItemRegister.SubmachineGunSoulStone.get())
                ||MyGoUtil.hasTacz(slotContext.entity(), MyGoItemRegister.HeavyMachineGunSoulStone.get())
                ||MyGoUtil.hasTacz(slotContext.entity(), MyGoItemRegister.MachineGunSoulStone.get())
        );
    }
}
