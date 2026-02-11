package com.inolia_zaicek.mine_fargo.Event.Goety;

import com.Polarice3.Goety.api.items.magic.ITotem;
import com.Polarice3.Goety.utils.TotemFinder;
import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.EctoplasmSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static com.Polarice3.Goety.utils.SEHelper.*;

public class GoetyKillEvent {
    @SubscribeEvent
    public static void entityKilled(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            if (event.getSource().getEntity() instanceof Player player && !EntityType.getKey(event.getEntity().getType()).toString().equals("goety:obsidian_monolith")) {
                if( MyGoUtil.hasGoetyItem(player, EctoplasmSoulStoneItem.class) ){
                    //判断是否有灵魂方舟
                    if (getSEActive(player)) {
                        sendSEUpdatePacket(player);
                        increaseSESouls(player, (int)(MyGoConfig.ectoplasm_soul_stone.get()*1));
                    } else {
                        ItemStack foundStack = TotemFinder.FindTotem(player);
                        if (foundStack != null&&foundStack.getTag() != null) {
                            ITotem.increaseSouls(foundStack, (int)(MyGoConfig.ectoplasm_soul_stone.get()*1));
                        }
                    }
                }
            }
            //如果攻击者是随从
            else if (event.getSource().getEntity() instanceof LivingEntity && !EntityType.getKey(event.getEntity().getType()).toString().equals("goety:obsidian_monolith")) {
                if (event.getSource().getEntity() instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() instanceof Player player) {
                    if( MyGoUtil.hasGoetyItem(player, EctoplasmSoulStoneItem.class) ){
                        //判断是否有灵魂方舟
                        if (getSEActive(player)) {
                            sendSEUpdatePacket(player);
                            increaseSESouls(player, (int)(MyGoConfig.ectoplasm_soul_stone.get()*1));
                        } else {
                            ItemStack foundStack = TotemFinder.FindTotem(player);
                            if (foundStack != null&&foundStack.getTag() != null) {
                                ITotem.increaseSouls(foundStack, (int)(MyGoConfig.ectoplasm_soul_stone.get()*1));
                            }
                        }
                    }
                }
            }
        }
    }
}