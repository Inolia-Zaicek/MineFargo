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
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
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

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Predicate;

import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.SoulOfInolia;

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
    /*
    public static boolean isCurioEquipped(LivingEntity entity, Item itemStackSupplier) {
        Optional<SlotResult> slotResult = CuriosApi.getCuriosHelper().findFirstCurio(entity,itemStackSupplier);
        return slotResult.isPresent();
    }
     */
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
        //对怪物判断：如果怪物仇恨在攻击者身上，true
        if(attacked instanceof Mob mob&&mob.getTarget()==attacker){
            can = true;
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
            if (ModList.get().isLoaded("terra_entity")) {
                can = TerraUtil.canAttack(attacked);
            }
        }
        return can;
    }
    //获取饰品栏第一个Item的CompoundTag（persistentData）
    public static CompoundTag getFirstCurioCompoundTag(LivingEntity livingEntity, Item item) {
        Optional<ICuriosItemHandler> curiosOpt = CuriosApi.getCuriosInventory(livingEntity).resolve();
        if (!curiosOpt.isPresent()) {
            return null; // 装备栏为空或无法访问
        }
        ICuriosItemHandler curiosHandler = curiosOpt.get();
        IItemHandlerModifiable itemHandler = curiosHandler.getEquippedCurios();
        if (itemHandler == null) {
            return null; // 没有装备
        }

        int slots = itemHandler.getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty()) {
                if (item == null || stack.getItem() == item) {
                    // 返回此 ItemStack 的 CompoundTag
                    return stack.getOrCreateTag();
                }
            }
        }
        return null; // 所有饰品都为空或没有匹配项
    }
    // ================= Curios饰品缓存 =================
    private static final Map<LivingEntity, CuriosCachedData> CURIOS_CACHE =
            Collections.synchronizedMap(new WeakHashMap<>());
    private static final long CURIOS_CACHE_EXPIRE_MS = 500; // 0.5秒
    private static class CuriosCachedData {
        Set<Item> items;
        Map<Item, Integer> counts;
        long timestamp;

        CuriosCachedData(Set<Item> items, Map<Item, Integer> counts, long time) {
            this.items = items;
            this.counts = counts;
            this.timestamp = time;
        }
    }
    public static Set<Item> getCuriosItems(LivingEntity entity) {
        if (entity == null) {
            return Collections.emptySet();
        }
        long now = System.currentTimeMillis();
        CuriosCachedData cached = CURIOS_CACHE.get(entity);
        if (cached != null && (now - cached.timestamp) < CURIOS_CACHE_EXPIRE_MS) {
            return cached.items;
        }
        // ===== 扫描Curios =====
        Set<Item> items = new HashSet<>();
        Map<Item, Integer> counts = new HashMap<>();
        top.theillusivec4.curios.api.CuriosApi.getCuriosInventory(entity).ifPresent(handler -> {
            var stacks = handler.getEquippedCurios();
            for (int i = 0; i < stacks.getSlots(); i++) {
                var stack = stacks.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    Item item = stack.getItem();
                    items.add(item);
                    counts.merge(item, 1, Integer::sum);
                }
            }
        });
        CURIOS_CACHE.put(entity, new CuriosCachedData(items, counts, now));
        return items;
    }

    //修改后的判断方法
    public static boolean hasOre(Set<Item> curios, LivingEntity living, Item item) {
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfOres.get());
    }

    public static boolean hasSupernatural(Set<Item> curios, LivingEntity living, Item item) {
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfSupernatural.get());
    }

    public static boolean hasNature(Set<Item> curios, LivingEntity living, Item item) {
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfNature.get());
    }

    public static boolean hasEntity(Set<Item> curios, LivingEntity living, Item item) {
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfEntity.get());
    }

    public static boolean hasIron(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("irons_spellbooks")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfIronSpell.get());
    }

    public static boolean hasArs(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("ars_nouveau")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfArsNouveau.get());
    }

    public static boolean hasTacz(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("tacz")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfTacz.get());
    }

    public static boolean hasCataclysm(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("cataclysm")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfCataclysm.get());
    }

    public static boolean hasBotania(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("botania")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfBotania.get());
    }

    public static boolean hasCreate(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("create")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfCreate.get());
    }

    public static boolean hasTwilight(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("twilightforest")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfTwilight.get());
    }

    public static boolean hasTwilightLich(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("twilightforest")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfTwilight.get())
                || curios.contains(MyGoItemRegister.TwilightLichSoulStone.get());
    }

    public static boolean hasTwilightForest(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("twilightforest")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfTwilightForest.get());
    }

    public static boolean hasLegendaryMonsters(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("legendary_monsters")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfLegendaryMonsters.get());
    }

    public static boolean hasLegendaryEntity(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("legendary_monsters")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfLegendaryEntity.get());
    }

    public static boolean hasGoetyItem(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("goety")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfGoetyItem.get());
    }

    public static boolean hasGoetyEntity(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("goety")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfGoetyEntity.get());
    }

    public static boolean hasIAFDragon(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("iceandfire")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfIAFDragon.get());
    }

    public static boolean hasIAFEntity(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("iceandfire")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfIAFEntity.get());
    }

    public static boolean hasSonsOfSins(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("sons_of_sins")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfSonsOfSins.get());
    }

    public static boolean hasL2Hostility(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("l2hostility")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfL2Hostility.get());
    }

    public static boolean hasL2Complements(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("l2complements")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfL2Complements.get());
    }

    public static boolean hasAlexsCaves(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("alexscaves")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfAlexsCaves.get());
    }

    public static boolean hasMalum(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("malum")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfMalum.get());
    }

    //以下是两个参数的类型，专门用于判断物品（三参数用于事件
    public static boolean hasOre(LivingEntity living, Item item) {
        return MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfOres.get());
    }

    public static boolean hasSupernatural(LivingEntity living, Item item) {
        return MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfSupernatural.get());
    }

    public static boolean hasNature(LivingEntity living, Item item) {
        return MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfNature.get());
    }

    public static boolean hasEntity(LivingEntity living, Item item) {
        return MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfEntity.get());
    }
    public static boolean hasIron(LivingEntity living, Item item) {
        return ModList.get().isLoaded("irons_spellbooks")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfIronSpell.get()));
    }

    public static boolean hasArs(LivingEntity living, Item item) {
        return ModList.get().isLoaded("ars_nouveau")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfArsNouveau.get()));
    }

    public static boolean hasTacz(LivingEntity living, Item item) {
        return ModList.get().isLoaded("tacz")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfTacz.get()));
    }

    public static boolean hasCataclysm(LivingEntity living, Item item) {
        return ModList.get().isLoaded("cataclysm")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfCataclysm.get()));
    }

    public static boolean hasBotania(LivingEntity living, Item item) {
        return ModList.get().isLoaded("botania")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfBotania.get()));
    }

    public static boolean hasCreate(LivingEntity living, Item item) {
        return ModList.get().isLoaded("create")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfCreate.get()));
    }

    public static boolean hasTwilight(LivingEntity living, Item item) {
        return ModList.get().isLoaded("twilightforest")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfTwilight.get()));
    }

    public static boolean hasTwilightLich(LivingEntity living, Item item) {
        return ModList.get().isLoaded("twilightforest")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfTwilight.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.TwilightLichSoulStone.get()));
    }

    public static boolean hasTwilightForest(LivingEntity living, Item item) {
        return ModList.get().isLoaded("twilightforest")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfTwilightForest.get()));
    }

    public static boolean hasLegendaryMonsters(LivingEntity living, Item item) {
        return ModList.get().isLoaded("legendary_monsters")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfLegendaryMonsters.get()));
    }

    public static boolean hasLegendaryEntity(LivingEntity living, Item item) {
        return ModList.get().isLoaded("legendary_monsters")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfLegendaryEntity.get()));
    }

    public static boolean hasGoetyItem(LivingEntity living, Item item) {
        return ModList.get().isLoaded("goety")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfGoetyItem.get()));
    }

    public static boolean hasGoetyEntity(LivingEntity living, Item item) {
        return ModList.get().isLoaded("goety")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfGoetyEntity.get()));
    }

    public static boolean hasIAFDragon(LivingEntity living, Item item) {
        return ModList.get().isLoaded("iceandfire")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfIAFDragon.get()));
    }

    public static boolean hasIAFEntity(LivingEntity living, Item item) {
        return ModList.get().isLoaded("iceandfire")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfIAFEntity.get()));
    }

    public static boolean hasSonsOfSins(LivingEntity living, Item item) {
        return ModList.get().isLoaded("sons_of_sins")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfSonsOfSins.get()));
    }

    public static boolean hasL2Hostility(LivingEntity living, Item item) {
        return ModList.get().isLoaded("l2hostility")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfL2Hostility.get()));
    }

    public static boolean hasL2Complements(LivingEntity living, Item item) {
        return ModList.get().isLoaded("l2complements")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfL2Complements.get()));
    }

    public static boolean hasAlexsCaves(LivingEntity living, Item item) {
        return ModList.get().isLoaded("alexscaves")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfAlexsCaves.get()));
    }
    public static boolean hasMalum(LivingEntity living, Item item) {
        return ModList.get().isLoaded("malum")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfMalum.get()));
    }
    //神秘遗物的
    public static boolean hasEnigmaticLegacy(Set<Item> curios, LivingEntity living, Item item) {
        if (!ModList.get().isLoaded("enigmaticlegacy")) return false;
        return curios.contains(item)
                || curios.contains(SoulOfInolia.get())
                || curios.contains(MyGoItemRegister.SoulOfEnigmaticLegacy.get());
    }
    public static boolean hasEnigmaticLegacy(LivingEntity living, Item item) {
        return ModList.get().isLoaded("enigmaticlegacy")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfEnigmaticLegacy.get()));
    }
    //施加药水方法总结
    public static void addEffect(LivingEntity entity, MobEffect effect, int time, int level) {
        // 构造效果实例
        MobEffectInstance instance = new MobEffectInstance(effect, time, level);
        // 常规添加（会自动合并同类效果或更新等级/时长）
        entity.addEffect(instance);

        // 排除指定实体，并确保效果强制存在于 ActiveEffectsMap 中
        if (!EntityType.getKey(entity.getType()).toString().equals("eeeabsmobs:immortal")
                && !entity.hasEffect(effect)) {
            // 直接操作底层映射，绕过某些检查
            entity.getActiveEffectsMap().put(effect, instance);
        }
    }
    //迎战
    public static boolean hasMeetFight(LivingEntity living, Item item) {
        return ModList.get().isLoaded("meetyourfight")
                && (MyGoUtil.isCurioEquipped(living, item)
                || MyGoUtil.isCurioEquipped(living, SoulOfInolia.get())
                || MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfMeetFight.get()));
    }
}
