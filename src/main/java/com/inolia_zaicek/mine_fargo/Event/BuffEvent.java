package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Ars.WixieSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Util.MyGoEntityHelper;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.entity.living.MobEffectEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

import java.util.Set;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MineFargo.MODID)
public class BuffEvent {
    @SubscribeEvent
    public static void buff(MobEffectEvent.Added event) {
        LivingEntity livingEntity = event.getEntity();
        MobEffectInstance mobEffectInstance = event.getEffectInstance();
        MobEffect mobEffect = mobEffectInstance.getEffect();
        int baseTime = mobEffectInstance.getDuration();
        //正面时长
        float goodTime = 0;
        float bedTime = 0;
        Set<Item> curios = MyGoUtil.getCuriosItems(livingEntity);
        if (ModList.get().isLoaded("ars_nouveau")) {
            if (MyGoUtil.hasArs(curios,livingEntity, WixieSoulStone.get())) {
                goodTime += MyGoConfig.wixie_soul_stone_up.get();
                bedTime -= MyGoConfig.wixie_soul_stone_down.get();
            }
        }
        if (ModList.get().isLoaded("malum")) {
            if (MyGoUtil.hasMalum(curios,livingEntity, TaintedSoulStone.get()) ) {
                bedTime-=MyGoConfig.tainted_soul_stone_time.get();
            }
            if (MyGoUtil.hasMalum(curios,livingEntity, VoidTabletSoulStone.get()) ) {
                goodTime+=MyGoConfig.void_tablet_soul_stone_time.get();
            }
        }
        //正面结算
        if (mobEffect.getCategory().equals(MobEffectCategory.BENEFICIAL) ) {
            if (goodTime > 0) {
                MyGoEntityHelper.extendEffect(mobEffectInstance, livingEntity, (int) (baseTime*goodTime));
            }
            if (goodTime < 0) {
                MyGoEntityHelper.shortenEffect(mobEffectInstance, livingEntity, (int) (baseTime*(goodTime*-1) ));
            }
        }
        //负面结算
        else{
            if (bedTime > 0) {
                MyGoEntityHelper.extendEffect(mobEffectInstance, livingEntity, (int) (baseTime*bedTime));
            }
            if (bedTime < 0) {
                MyGoEntityHelper.shortenEffect(mobEffectInstance, livingEntity, (int) (baseTime*(bedTime*-1) ));
            }
        }
    }
}
