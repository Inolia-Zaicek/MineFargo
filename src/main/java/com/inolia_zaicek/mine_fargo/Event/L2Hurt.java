package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Item.L2.Curios.RiderHostilitySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.L2.Hostility.UltraHostilitySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.xkmc.l2damagetracker.contents.attack.AttackListener;
import dev.xkmc.l2damagetracker.contents.attack.CreateSourceEvent;
import dev.xkmc.l2damagetracker.contents.damage.DamageTypeWrapper;
import dev.xkmc.l2damagetracker.contents.damage.DefaultDamageState;
import dev.xkmc.l2damagetracker.init.data.L2DamageTypes;
import dev.xkmc.l2hostility.content.capability.mob.MobTraitCap;
import dev.xkmc.l2hostility.content.capability.player.PlayerDifficulty;
import dev.xkmc.l2hostility.content.item.curio.core.CurseCurioItem;
import dev.xkmc.l2hostility.content.logic.MobDifficultyCollector;
import dev.xkmc.l2hostility.content.traits.base.MobTrait;
import dev.xkmc.l2hostility.content.traits.legendary.RagnarokTrait;
import dev.xkmc.l2hostility.init.data.HostilityDamageState;
import dev.xkmc.l2hostility.init.loot.EnvyLootModifier;
import dev.xkmc.l2hostility.init.registrate.LHMiscs;
import dev.xkmc.l2library.capability.player.PlayerCapabilityTemplate;
import dev.xkmc.l2library.util.code.GenericItemStack;
import dev.xkmc.l2serial.serialization.SerialClass;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import dev.xkmc.l2hostility.compat.curios.CurioCompat;
import dev.xkmc.l2hostility.compat.curios.EntitySlotAccess;
import dev.xkmc.l2hostility.content.item.traits.SealedItem;
import dev.xkmc.l2hostility.init.data.LHConfig;
import dev.xkmc.l2hostility.init.data.LHTagGen;
import dev.xkmc.l2hostility.init.registrate.LHItems;
import dev.xkmc.l2library.init.events.GeneralEventHandler;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

@SerialClass
public class L2Hurt  extends LootModifier implements AttackListener {
    protected L2Hurt(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }
    @SubscribeEvent
    public void hurt(LivingHurtEvent event) {
        LivingEntity attacked = event.getEntity();
        if (event.getSource().getEntity() instanceof LivingEntity attacker && attacked != null) {
            if (ModList.get().isLoaded("l2hostility")&& MyGoConfig.ultra_hostility_soul_stone.get()) {
                if (MyGoUtil.hasL2Hostility(attacker, UltraHostilitySoulStoneItem.class)) {
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
    @Override
    public void onCreateSource(CreateSourceEvent event) {
        LivingEntity mob = event.getAttacker();
        if (MobTraitCap.HOLDER.isProper(mob)) {
            ((MobTraitCap)MobTraitCap.HOLDER.get(mob)).traitEvent((k, v) -> k.onCreateSource(v, event.getAttacker(), event));
        }

        DamageTypeWrapper type = event.getResult();
        if (type != null&&MyGoUtil.hasL2Curios(mob, RiderHostilitySoulStoneItem.class)) {
            event.enable(DefaultDamageState.BYPASS_MAGIC);
            event.enable(HostilityDamageState.BYPASS_COOLDOWN);
        }
    }
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> list, LootContext context) {
        Object var4 = context.getParam(LootContextParams.THIS_ENTITY);
        if (var4 instanceof LivingEntity le) {
            if (le.getType().is(LHTagGen.NO_DROP)) {
                return list;
            }

            if (MobTraitCap.HOLDER.isProper(le)) {
                MobTraitCap cap = (MobTraitCap)MobTraitCap.HOLDER.get(le);
                double factor = cap.dropRate;
                if (context.hasParam(LootContextParams.LAST_DAMAGE_PLAYER)) {
                    Player player = (Player)context.getParam(LootContextParams.LAST_DAMAGE_PLAYER);
                    PlayerDifficulty pl = (PlayerDifficulty)PlayerDifficulty.HOLDER.get(player);

                    for(GenericItemStack<CurseCurioItem> stack : CurseCurioItem.getFromPlayer(player)) {
                        factor *= ((CurseCurioItem)stack.item()).getLootFactor(stack.stack(), pl, cap);
                    }
                }

                for(Map.Entry<MobTrait, Integer> entry : cap.traits.entrySet()) {
                    double chance = factor * (double)(Integer)entry.getValue() * (Double)LHConfig.COMMON.envyDropRate.get();
                    if (cap.fullDrop || context.getRandom().nextDouble() < chance) {
                        list.add(((MobTrait)entry.getKey()).asItem().getDefaultInstance());
                    }
                }
            }
        }

        return list;
    }
    public static final Codec<EnvyLootModifier> CODEC = RecordCodecBuilder.create((i) -> codecStart(i).apply(i, EnvyLootModifier::new));
    public Codec<EnvyLootModifier> codec() {
        return CODEC;
    }
}