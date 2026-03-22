package com.inolia_zaicek.mine_fargo.Util;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.Ars.ArsST;
import com.inolia_zaicek.mine_fargo.Item.Botania.BotaniaST;
import com.inolia_zaicek.mine_fargo.Item.Cataclysm.CataclysmST;
import com.inolia_zaicek.mine_fargo.Item.Create.CreateST;
import com.inolia_zaicek.mine_fargo.Item.Iron.IronST;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Entity.EntityST;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.NatureST;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.OresST;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Supernatural.SupernaturalST;
import com.inolia_zaicek.mine_fargo.Item.Tacz.TaczST;
import com.inolia_zaicek.mine_fargo.Item.Twilight.TwilightLichST;
import com.inolia_zaicek.mine_fargo.Item.Twilight.TwilightST;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.item.curio.combat.IMagicQuiver;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.capability.ICuriosItemHandler;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

@SuppressWarnings({"all", "removal"})
public class MyGoUtil {
    public static MyGoUtil INSTANCE;
    public static MyGoUtil getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MyGoUtil();
        }
        return INSTANCE;
    }
    //获取周围敌人列表
    public static List<Mob> mobList(double range, LivingEntity entity){
        double x =entity.getX();
        double y =entity.getY();
        double z =entity.getZ();
        return entity.getCommandSenderWorld().getEntitiesOfClass(Mob.class,new AABB(x+range,y+range,z+range,x-range,y-range,z-range));
    }
    //获取周围玩家列表
    public static List<Player> PlayerList(double range, LivingEntity entity){
        double x =entity.getX();
        double y =entity.getY();
        double z =entity.getZ();
        return entity.getCommandSenderWorld().getEntitiesOfClass(Player.class,new AABB(x+range,y+range,z+range,x-range,y-range,z-range));
    }
    public static boolean isCurioEquipped(LivingEntity entity, Item itemStackSupplier) {
        Optional<SlotResult> slotResult = CuriosApi.getCuriosHelper().findFirstCurio(entity,itemStackSupplier);
        return slotResult.isPresent();
    }
    public static boolean isBossEntity(EntityType<?> entity) {
        // 检查 "flame_chase_artifacts:bosses" tag
        boolean isMoreTetraBoss = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(
                TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation("mine_fargo", "bosses"))
        ).contains(entity);
        // 检查 "forge:bosses" tag
        boolean isForgeBoss = Objects.requireNonNull(ForgeRegistries.ENTITY_TYPES.tags()).getTag(
                TagKey.create(ForgeRegistries.ENTITY_TYPES.getRegistryKey(), new ResourceLocation("forge", "bosses"))
        ).contains(entity);
        // 只要满足其中一个 tag 即可
        return isMoreTetraBoss || isForgeBoss;
    }
    public static boolean isMeleeAttack(DamageSource source) {
        return !source.isIndirect() && (source.is(DamageTypes.MOB_ATTACK) || source.is(DamageTypes.PLAYER_ATTACK) || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO));
    }
    //矿石之力系列专用判断——【有矿石之力的情况下返回true
    public static boolean hasOre(LivingEntity living,Item item) {
        if(MyGoUtil.isCurioEquipped(living, item ) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfOres.get() ) ) {
            return true;
        }else{
            return false;
        }
    }
    public static boolean hasSupernatural(LivingEntity living,Item item) {
        if(MyGoUtil.isCurioEquipped(living, item ) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfSupernatural.get() ) ) {
            return true;
        }else{
            return false;
        }
    }
    //自然之魂系列专用判断——【有集合的情况下返回true
    public static boolean hasNature(LivingEntity living,Item item) {
        if(MyGoUtil.isCurioEquipped(living, item ) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfNature.get() ) ) {
            return true;
        }else{
            return false;
        }
    }
    public static boolean hasEntity(LivingEntity living,Item item) {
        if(MyGoUtil.isCurioEquipped(living, item ) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfEntity.get() ) ) {
            return true;
        }else{
            return false;
        }
    }
    public static boolean hasIron(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("irons_spellbooks")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfIronSpell.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasArs(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("ars_nouveau")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfArsNouveau.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasTacz(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("tacz")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfTacz.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasCataclysm(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("cataclysm")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfCataclysm.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasBotania(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("botania")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfBotania.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasCreate(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("create")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfCreate.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    //锁定一定距离内最近的【非自身随从】目标
    public static LivingEntity getNearestNonFollowerOnPath(LivingEntity livingEntity, double range) {
        Vec3 srcVec = livingEntity.getEyePosition();
        Vec3 lookVec = livingEntity.getViewVector(1.0F);
        Vec3 destVec = srcVec.add(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range);
        float borderSize = 0.5F;
        float expandAmount = 1.0F;
        // 获取路径范围内所有实体
        List<Entity> possibleList = livingEntity.level.getEntities(
                livingEntity,
                livingEntity.getBoundingBox()
                        .expandTowards(lookVec.x() * range, lookVec.y() * range, lookVec.z() * range)
                        .inflate(expandAmount, expandAmount, expandAmount)
        );
        LivingEntity nearestEntity = null;
        double nearestDist = Double.MAX_VALUE;
        for (Entity entity : possibleList) {
            // 只锁定LivingEntity且排除玩家自己
            if (!(entity instanceof LivingEntity)) continue;
            if (entity == livingEntity) continue;
            // 过滤玩家自己的随从
            if (entity instanceof OwnableEntity ownable && ownable.getOwner() == livingEntity) {
                continue;
            }
            AABB collisionBB = entity.getBoundingBox().inflate(borderSize, borderSize, borderSize);
            Optional<Vec3> interceptPos = collisionBB.clip(srcVec, destVec);
            boolean isOnPath = false;
            if (collisionBB.contains(srcVec)) {
                isOnPath = true;
            } else if (interceptPos.isPresent()) {
                isOnPath = true;
            }
            if (isOnPath) {
                double dist = entity.distanceTo(livingEntity);
                if (dist <= range && dist < nearestDist) {
                    nearestDist = dist;
                    nearestEntity = (LivingEntity) entity;
                }
            }
        }
        return nearestEntity; // 没找到会返回null
    }
    public static boolean hasTwilight(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("twilightforest")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfTwilight.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    //巫妖魂石——装备自身or巫妖魂or暮色魂——√
    public static boolean hasTwilightLich(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("twilightforest")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfTwilight.get())
                    || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.TwilightLichSoulStone.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasTwilightForest(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("twilightforest")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfTwilightForest.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasLegendaryMonsters(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("legendary_monsters")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfLegendaryMonsters.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasLegendaryEntity(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("legendary_monsters")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfLegendaryEntity.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasMalum(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("malum")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfMalum.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasGoetyItem(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("goety")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfGoetyItem.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasGoetyEntity(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("goety")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfGoetyEntity.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasIAFDragon(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("iceandfire")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfIAFDragon.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasIAFEntity(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("iceandfire")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfIAFEntity.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasSonsOfSins(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("sons_of_sins")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfSonsOfSins.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasL2Hostility(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("l2hostility")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfL2Hostility.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasL2Complements(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("l2complements")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfL2Complements.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    public static boolean hasAlexsCaves(LivingEntity living,Item item) {
        if(ModList.get().isLoaded("alexscaves")) {
            if (MyGoUtil.isCurioEquipped(living, item) || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfAlexsCaves.get()) ) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    //可不可以揍（判断是不是随从+非潜行时不攻击
    public static boolean canAttack(LivingEntity attacked,LivingEntity attacker) {
        boolean can = true;
        //生物属于非敌对  同时  自身未蹲下——同时开启了功能
        if (can && !(attacked instanceof Enemy) && !attacker.isCrouching() && MyGoConfig.can_attack.get()) {
            can = false;
        }
        //攻击者与挨打者一致
        if (can && attacker == attacked) {
            can = false;
        }
        //如果是假人，true
        if (EntityType.getKey(attacked.getType()).toString().equals("dummmmmmy:target_dummy")) {
            can = true;
        }
        //如果一开始就不会被打：false————的情况下，不进入随从判断
        if (can) {
            //生物属于随从且主人是攻击者，强制不打
            if (attacked instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null && ownableEntity.getOwner() == attacker) {
                can = false;
            }
            //有铁魔法的情况下，识别魔法随从，强制不打
            if (ModList.get().isLoaded("irons_spellbooks")) {
                can = IronUtil.canAttack(attacked, attacker);
            }
        }
        return can;
    }
}
