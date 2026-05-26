package com.inolia_zaicek.mine_fargo.Event.L2;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.L2.Hostility.UltraHostilitySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import dev.xkmc.l2serial.serialization.SerialClass;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import dev.xkmc.l2hostility.compat.curios.CurioCompat;
import dev.xkmc.l2hostility.compat.curios.EntitySlotAccess;
import dev.xkmc.l2hostility.content.item.traits.SealedItem;
import dev.xkmc.l2hostility.init.data.LHConfig;
import dev.xkmc.l2hostility.init.data.LHTagGen;
import dev.xkmc.l2library.init.events.GeneralEventHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

public class L2Hurt {
    @SubscribeEvent
    public void hurt(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != null) {
            Set<Item> curios = MyGoUtil.getCuriosItems(attacker);
            if (ModList.get().isLoaded("l2hostility")&& MyGoConfig.ultra_hostility_soul_stone.get()) {
                if (MyGoUtil.hasL2Hostility(curios,attacker, UltraHostilitySoulStone.get())) {
                    //—————————————————————————————————————————————————————数量与等级
                    GeneralEventHandler.schedule(() -> this.sealItems((int)(MyGoConfig.ultra_hostility_soul_stone_number.get()*1), attacked));
                }
            }
        }
    }
    public void sealItems(int level, LivingEntity target) {
        List<EntitySlotAccess> list = new ArrayList<>(CurioCompat.getItemAccess(target).stream().filter(L2Hurt::allowSeal).toList());
        int count = Math.min(level, list.size());
        int time = (Integer)LHConfig.COMMON.ragnarokTime.get() * level;

        for(int i = 0; i < count; ++i) {
            int index = target.getRandom().nextInt(list.size());
            EntitySlotAccess slot = (EntitySlotAccess)list.remove(index);
            slot.modify((e) -> SealedItem.sealItem(e, time));
        }

    }
    private static boolean allowSeal(EntitySlotAccess access) {
        ItemStack stack = access.get();
        if (stack.isEmpty()) {
            return false;
            //LHItems.SEAL.get()———密封物品
        } else if (stack.is(
                Objects.requireNonNull(ForgeRegistries.ITEMS.getValue(new ResourceLocation("l2hostility", "sealed_item")))
        )) {
            return false;
        } else if (stack.is(LHTagGen.NO_SEAL)) {
            return false;
        } else {
            if (!(Boolean) LHConfig.COMMON.ragnarokSealBackpack.get()) {
                ResourceLocation rl = ForgeRegistries.ITEMS.getKey(stack.getItem());
                if (rl == null) {
                    return false;
                }

                if (rl.toString().contains("backpack")) {
                    return false;
                }
            }

            if (!(Boolean)LHConfig.COMMON.ragnarokSealSlotAdder.get()) {
                return !CurioCompat.isSlotAdder(access);
            } else {
                return true;
            }
        }
    }
}