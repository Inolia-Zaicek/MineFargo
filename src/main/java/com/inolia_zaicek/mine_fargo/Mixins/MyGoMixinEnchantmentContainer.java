package com.inolia_zaicek.mine_fargo.Mixins;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.GoldSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoEnchantmentUtil;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EnchantmentMenu.class)
public abstract class MyGoMixinEnchantmentContainer extends AbstractContainerMenu {

    protected MyGoMixinEnchantmentContainer(MenuType<?> type, int id) {
        super(type, id);
    }

    @Inject(
            at = @At("HEAD"),
            method = {"clickMenuButton(Lnet/minecraft/world/entity/player/Player;I)Z"},
            cancellable = true
    )
    private void onEnchantedItem(Player player, int clickedID, CallbackInfoReturnable<Boolean> info) {
        EnchantmentMenu container = null;
        try {
            container = EnchantmentMenu.class.cast(this);
        } catch (Exception ignored) {}
        if(container == null) return;
        //检查是否有饰品
        if (MyGoUtil.hasOre(player, GoldSoulStoneItem.class)) {
            ItemStack inputItem = container.enchantSlots.getItem(0);
            int levelsRequired = clickedID + 1;
            if (container.costs[clickedID] > 0 && !inputItem.isEmpty() && (player.experienceLevel >= levelsRequired && player.experienceLevel >= container.costs[clickedID] || player.getAbilities().instabuild)) {
                EnchantmentMenu finalContainer = container;
                container.access.execute((world, blockPos) -> {
                    ItemStack enchantedItem = inputItem;
                    List<EnchantmentInstance> rolledEnchantments = finalContainer.getEnchantmentList(inputItem, clickedID, finalContainer.costs[clickedID]);
                    if (!rolledEnchantments.isEmpty()) {
                        int number = 0;
                        if( MyGoUtil.hasOre(player, GoldSoulStoneItem.class) ){
                            number+=(int)(MyGoConfig.gold_soul_stone.get()*1);
                        }
                        //提升等级
                        ItemStack doubleRoll = EnchantmentHelper.enchantItem(player.getRandom(), inputItem.copy(),
                                //附魔等级
                                finalContainer.costs[clickedID] + number,
                                //是否运用宝藏附魔
                                false
                        );
                        player.onEnchantmentPerformed(inputItem, levelsRequired);
                        boolean isBookStack = inputItem.getItem() == Items.BOOK;
                        if (isBookStack) {
                            enchantedItem = new ItemStack(Items.ENCHANTED_BOOK);
                            CompoundTag compoundnbt = inputItem.getTag();
                            if (compoundnbt != null) {
                                enchantedItem.setTag(compoundnbt.copy());
                            }

                            finalContainer.enchantSlots.setItem(0, enchantedItem);
                        }

                        for(EnchantmentInstance enchantmentdata : rolledEnchantments) {
                            if (isBookStack) {
                                EnchantedBookItem.addEnchantment(enchantedItem, enchantmentdata);
                            } else {
                                enchantedItem.enchant(enchantmentdata.enchantment, enchantmentdata.level);
                            }
                        }

                        enchantedItem = MyGoEnchantmentUtil.mergeEnchantments(enchantedItem, doubleRoll, false, false);
                        finalContainer.enchantSlots.setItem(0, enchantedItem);
                        player.awardStat(Stats.ENCHANT_ITEM);
                        if (player instanceof ServerPlayer) {
                            CriteriaTriggers.ENCHANTED_ITEM.trigger((ServerPlayer)player, enchantedItem, levelsRequired);
                        }

                        finalContainer.enchantSlots.setChanged();
                        finalContainer.enchantmentSeed.set(player.getEnchantmentSeed());
                        finalContainer.slotsChanged(finalContainer.enchantSlots);
                        world.playSound((Player)null, blockPos, SoundEvents.ENCHANTMENT_TABLE_USE, SoundSource.BLOCKS, 1.0F, world.random.nextFloat() * 0.1F + 0.9F);
                    }

                });
                info.setReturnValue(true);
                return;
            }

            info.setReturnValue(false);
            return;

            ///
        }

    }
}