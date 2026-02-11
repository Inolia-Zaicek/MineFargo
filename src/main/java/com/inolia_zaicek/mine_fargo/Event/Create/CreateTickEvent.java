package com.inolia_zaicek.mine_fargo.Event.Create;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Create.RoseQuartzSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import vazkii.botania.api.mana.ManaItemHandler;

public class CreateTickEvent {
    public static final TagKey<Item> sandpaper = TagKey.create(Registries.ITEM,new ResourceLocation("create","sandpaper"));
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        if (ModList.get().isLoaded("create")) {
            if (!event.getEntity().isAlive())
                return;
            LivingEntity livingEntity = event.getEntity();
            if (livingEntity.level().getGameTime() % 20L == 0) {
                if(MyGoUtil.hasCreate(livingEntity, RoseQuartzSoulStoneItem.class)) {
                    ItemStack mainHandItem = livingEntity.getMainHandItem();
                    ItemStack offHandItem = livingEntity.getOffhandItem();
                    hand(mainHandItem);
                    hand(offHandItem);
                }
            }
        }
    }
    // 处理单个工具或装备的修复
    private static void hand(ItemStack stack) {
        int currentDamage = stack.getDamageValue();
        if (currentDamage > 0&&stack.is(sandpaper)) {
            //修复1耐久
            int newDamage = (int) (currentDamage - (int)(MyGoConfig.rose_quartz_soul_stone_number.get()*1));
            stack.setDamageValue(Math.max(0, newDamage));
        }
    }
}
