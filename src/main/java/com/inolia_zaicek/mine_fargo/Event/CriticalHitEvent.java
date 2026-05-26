package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Botania.GaiaSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class CriticalHitEvent {
    /// 暴击事件
    @SubscribeEvent
    public static void onLivingAttack(net.minecraftforge.event.entity.player.CriticalHitEvent event) {
        /// 挨打的
        Entity attacked = event.getTarget();
        /// 攻击的
        LivingEntity attacker = event.getEntity();
        Random random = new Random();
        ItemStack weapon = attacker.getMainHandItem();
        double criChance = 0;
        float criDamage = event.getDamageModifier();
        Set<Item> curios = MyGoUtil.getCuriosItems(attacker);
        if (ModList.get().isLoaded("botania")) {
            if (MyGoUtil.hasBotania(curios,attacker, GaiaSoulStone.get())) {
                criChance += MyGoConfig.gaia_soul_stone_chance.get();
            }
        }
        //修改爆伤event.setDamageModifier(1.5F+effectLevel/100);
        //强制暴击
        //event.isVanillaCritical()是原版暴击检测
        boolean cri = false;
        if ( !event.isVanillaCritical() && criChance>0&&random.nextInt(100) <= criChance*100) {
            cri = true;
            //使用默认爆伤
            event.setDamageModifier((float) (criDamage+MyGoConfig.gaia_soul_stone_chance.get()-1));
            event.setResult(Event.Result.ALLOW);
        }
        if(event.isVanillaCritical()){
            cri = true;
        }
        if(cri){
            if (ModList.get().isLoaded("legendary_monsters")) {
                if (random.nextInt(100) <= MyGoConfig.posessed_paladin_soul_stone_chance.get() * 100&&attacked instanceof LivingEntity livingEntity) {
                    livingEntity.addEffect(new MobEffectInstance(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "bleeding")))
                            , (int) (MyGoConfig.posessed_paladin_soul_stone_time.get() * 20), 0));
                    if (!EntityType.getKey(livingEntity.getType()).toString().equals("eeeabsmobs:immortal") &&  !livingEntity.hasEffect(
                            Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "bleeding")))
                    )) {
                        var map = livingEntity.getActiveEffectsMap();
                        map.put(
                                Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "bleeding")))
                                , new MobEffectInstance(Objects.requireNonNull(ForgeRegistries.MOB_EFFECTS.getValue(new ResourceLocation("legendary_monsters", "bleeding"))), (int) (MyGoConfig.posessed_paladin_soul_stone_time.get() * 20), 0));
                    }
                }
            }
        }
    }
}
