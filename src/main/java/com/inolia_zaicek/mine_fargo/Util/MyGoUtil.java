package com.inolia_zaicek.mine_fargo.Util;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
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
    //无饰品装备时返回 true——————————
    public static boolean noSameCurio(LivingEntity living, Item item) {
        return noSameCurio(living, (Predicate<ItemStack>) (itemStack) -> itemStack.getItem() == item);
    }

    public static boolean noSameCurio(LivingEntity living, Predicate<ItemStack> predicate) {
        AtomicBoolean isEmpty = new AtomicBoolean(true);
        CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();

                for (int i = 0; i < stackHandler.getSlots(); ++i) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && predicate.test(stack)) {
                        isEmpty.set(false);
                        return;
                    }
                }
            }
        });
        return isEmpty.get();
    }
    //获取玩家身上饰品xxNBT的最大数额
    public static int getMaxCommunityNumber(LivingEntity livingEntity, ResourceLocation resourceLocation, Item item) {
        int maxNumber = 0;
        Optional<ICuriosItemHandler> curiosOpt = CuriosApi.getCuriosInventory(livingEntity).resolve();
        if (!curiosOpt.isPresent()) {
            return 0;
        }

        ICuriosItemHandler curiosHandler = curiosOpt.get();
        IItemHandlerModifiable itemHandler = curiosHandler.getEquippedCurios();
        if (itemHandler == null) {
            return 0;
        }

        int slots = itemHandler.getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == item) { // 只处理Item相等的
                CompoundTag tag = stack.getTag();
                if (tag != null && tag.contains(resourceLocation.toString())) {
                    int value = tag.getInt(resourceLocation.toString());
                    if (value > maxNumber) {
                        maxNumber = value;
                    }
                }
            }
        }
        return maxNumber;
    }
    //为玩家身上所有有的Item饰品的xxNBT设置数额
    public static void setCommunityNumberForAllItems(LivingEntity livingEntity, ResourceLocation resourceLocation, int number, Item item) {
        Optional<ICuriosItemHandler> curiosOpt = CuriosApi.getCuriosInventory(livingEntity).resolve();
        if (!curiosOpt.isPresent()) {
            return;
        }

        ICuriosItemHandler curiosHandler = curiosOpt.get();
        IItemHandlerModifiable itemHandler = curiosHandler.getEquippedCurios();
        if (itemHandler == null) {
            return;
        }

        int slots = itemHandler.getSlots();
        for (int i = 0; i < slots; i++) {
            ItemStack stack = itemHandler.getStackInSlot(i);
            if (!stack.isEmpty() && stack.getItem() == item) { // 只处理Item相等的
                CompoundTag tag = stack.getOrCreateTag();
                tag.putInt(resourceLocation.toString(), number);
            }
        }
    }
    public static boolean isMeleeAttack(DamageSource source) {
        return !source.isIndirect() && (source.is(DamageTypes.MOB_ATTACK) || source.is(DamageTypes.PLAYER_ATTACK) || source.is(DamageTypes.MOB_ATTACK_NO_AGGRO));
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
    // 定义一个方法，用于判断Item是否实现IMagicQuiver
    public static boolean hasIMagicQuiver(LivingEntity living) {
        AtomicBoolean hasQuiver = new AtomicBoolean(false);
        CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
            for(ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();

                for(int i = 0; i < stackHandler.getSlots(); ++i) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && stack.getItem() instanceof IMagicQuiver) {
                        hasQuiver.set(true);
                        return;
                    }
                }
            }
        });
        return hasQuiver.get();
    }
    // 泛型参数，用于传入目标接口或类————用于判断Item是否实现targetClass————有无穿戴这类饰品
    public static boolean hasSpecificItem(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); ++i) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                        hasItem.set(true);
                        return;
                    }
                }
            }
        });
        return hasItem.get();
    }
    //矿石之力系列专用判断——【有矿石之力的情况下返回true
    public static boolean hasOre(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); ++i) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                        hasItem.set(true);
                        return;
                    }
                }
            }
        });
        //有矿石之力，直接返回true
        if(MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfOres.get() )){
            hasItem.set(true);
        }
        return hasItem.get();
    }
    public static boolean hasSupernatural(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); ++i) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                        hasItem.set(true);
                        return;
                    }
                }
            }
        });
        //有矿石之力，直接返回true
        if(MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfSupernatural.get() )){
            hasItem.set(true);
        }
        return hasItem.get();
    }
    public static boolean hasSoulOre(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); ++i) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                        hasItem.set(true);
                        return;
                    }
                }
            }
        });
        //有矿石之力，直接返回true
        if(MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfOres.get() )){
            hasItem.set(true);
        }
        return hasItem.get();
    }
    //自然之魂系列专用判断——【有集合的情况下返回true
    public static boolean hasNature(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); ++i) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                        hasItem.set(true);
                        return;
                    }
                }
            }
        });
        //有矿石之力，直接返回true
        if(MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfNature.get() )){
            hasItem.set(true);
        }
        return hasItem.get();
    }
    public static boolean hasSoulNature(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); ++i) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                        hasItem.set(true);
                        return;
                    }
                }
            }
        });
        //有矿石之力，直接返回true
        if(MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfNature.get() )){
            hasItem.set(true);
        }
        return hasItem.get();
    }
    public static boolean hasEntity(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); ++i) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                        hasItem.set(true);
                        return;
                    }
                }
            }
        });
        //有矿石之力，直接返回true
        if(MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfEntity.get() )){
            hasItem.set(true);
        }
        return hasItem.get();
    }
    public static boolean hasSoulEntity(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
            for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                for (int i = 0; i < stackHandler.getSlots(); ++i) {
                    ItemStack stack = stackHandler.getStackInSlot(i);
                    if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                        hasItem.set(true);
                        return;
                    }
                }
            }
        });
        //有矿石之力，直接返回true
        if(MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfEntity.get() )){
            hasItem.set(true);
        }
        return hasItem.get();
    }
    public static boolean hasIron(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("irons_spellbooks")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfIronSpell.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasSoulIron(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("irons_spellbooks")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfIronSpell.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasArs(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("ars_nouveau")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfArsNouveau.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasSoulArs(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("ars_nouveau")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfArsNouveau.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasTacz(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("tacz")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfTacz.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    //这里检测“魂”构成魂石都继承的类，避免按小后按大
    public static boolean hasSoulTacz(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("tacz")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfTacz.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasCataclysm(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("cataclysm")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfCataclysm.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasBotania(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("botania")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfBotania.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasCreate(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("create")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfCreate.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
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
    public static boolean hasTwilight(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("twilightforest")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfTwilight.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    //巫妖魂石及其合成材料使用【有巫妖魂石或者暮色魂石都能触发
    public static boolean hasTwilightLich(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("twilightforest")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfTwilight.get())||
                    MyGoUtil.isCurioEquipped(living, MyGoItemRegister.TwilightLichSoulStone.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasTwilightForest(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("twilightforest")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfTwilightForest.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasLegendaryMonsters(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("legendary_monsters")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfLegendaryMonsters.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasLegendaryEntity(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("legendary_monsters")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfLegendaryEntity.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasMalum(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("malum")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfMalum.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasGoetyItem(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("goety")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfGoetyItem.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasGoetyEntity(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("goety")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfGoetyEntity.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasIAFDragon(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("iceandfire")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfIAFDragon.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasIAFEntity(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("iceandfire")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfIAFEntity.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasSonsOfSins(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("sons_of_sins")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfSonsOfSins.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasL2Hostility(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("l2hostility")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfL2Hostility.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasL2Complements(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("l2complements")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfL2Complements.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    public static boolean hasAlexsCaves(LivingEntity living, Class<?> targetClass) {
        AtomicBoolean hasItem = new AtomicBoolean(false);
        if (ModList.get().isLoaded("alexscaves")) {
            CuriosApi.getCuriosInventory(living).ifPresent((handler) -> {
                for (ICurioStacksHandler curioStacksHandler : handler.getCurios().values()) {
                    IDynamicStackHandler stackHandler = curioStacksHandler.getStacks();
                    for (int i = 0; i < stackHandler.getSlots(); ++i) {
                        ItemStack stack = stackHandler.getStackInSlot(i);
                        if (!stack.isEmpty() && targetClass.isAssignableFrom(stack.getItem().getClass())) {
                            hasItem.set(true);
                            return;
                        }
                    }
                }
            });
            //有集合，直接返回true
            if (MyGoUtil.isCurioEquipped(living, MyGoItemRegister.SoulOfAlexsCaves.get())) {
                hasItem.set(true);
            }
        }else{
            hasItem.set(false);
        }
        return hasItem.get();
    }
    //可不可以揍（判断是不是随从+非潜行时不攻击
    public static boolean canAttack(LivingEntity attacked,LivingEntity attacker) {
        boolean can = true;
        //生物属于非敌对  同时  自身未蹲下——同时开启了功能
        if(can&&!(attacked instanceof Enemy)&&!attacker.isCrouching() && MyGoConfig.can_attack.get() ){
            can=false;
        }
        //攻击者与挨打者一致
        if(can&&attacker==attacked){
            can=false;
        }
        //有铁魔法的情况下，识别魔法随从
        if (can&&ModList.get().isLoaded("irons_spellbooks")) {
            can = IronUtil.canAttack(attacked,attacker);
        }
        //如果是假人，true
        if(EntityType.getKey(attacked.getType()).toString().equals("dummmmmmy:target_dummy") ){
            can=true;
        }
        //生物属于随从且主人是攻击者，强制不打
        if(attacked instanceof OwnableEntity ownableEntity && ownableEntity.getOwnerUUID() != null && ownableEntity.getOwner() == attacker){
            can=false;
        }
        return can;
    }
}
