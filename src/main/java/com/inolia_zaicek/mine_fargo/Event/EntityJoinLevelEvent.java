package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.AlexsCaves.MagneticSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import com.inolia_zaicek.mine_fargo.Util.WTCTargetMode;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;

import java.util.Random;
import java.util.function.Predicate;

public class EntityJoinLevelEvent {
    @SubscribeEvent
    public static void addMode(net.minecraftforge.event.entity.EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof Projectile projectile) {
            // 如果实体是投射物（如箭），尝试找到它的发射者
            Entity owner = projectile.getOwner();
            if (owner instanceof LivingEntity shooter) {
                double chance = 0;
                if (ModList.get().isLoaded("alexscaves")) {
                    if (MyGoUtil.hasAlexsCaves(shooter, MagneticSoulStone.get())) {
                        chance += MyGoConfig.magnetic_soul_stone_chance.get();
                    }
                }
                Random random = new Random();
                if (chance > 0 && random.nextInt(100) <= (100 * chance)) {
                    //设置目标筛选
                    Predicate<Entity> targetPredicate = (target) -> target instanceof LivingEntity && !(target instanceof Player)
                            //自己随从不锁
                            && !(target instanceof OwnableEntity ownableEntity && ownableEntity.getOwner() != null && ownableEntity.getOwner() == shooter);

                    if (projectile instanceof WTCTargetMode modeObj) {
                        modeObj.mmt$setMode(targetPredicate);
                    }
                }
            }
        }
    }
}