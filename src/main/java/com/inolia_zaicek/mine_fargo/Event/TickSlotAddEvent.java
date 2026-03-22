package com.inolia_zaicek.mine_fargo.Event;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Damage.MyGoDamageType;
import com.inolia_zaicek.mine_fargo.Item.Ars.AmethystGolemSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Ars.ArchwoodSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Ars.FirenandoSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Botania.GaiaSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Entity.MinisterSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Entity.NetherApostleSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.LegionSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.IceAndFire.Entity.CyclopsSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.IceAndFire.Entity.GorgonSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.IceAndFire.Entity.IAFHydraSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.IceAndFire.Entity.SeaSerpentSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.L2.Complements.PoseiditeComplementsSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.L2.Complements.SculkiumComplementsSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.L2.Complements.TotemicComplementsSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.L2.Hostility.AquaHostilitySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.L2.Hostility.BodyHostilitySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.L2.Hostility.UltraHostilitySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.L2.Hostility.ZoneHostilitySoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Entity.*;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Monsters.PosessedPaladinSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Entity.AquaticSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.ForestSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.LavaSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.LushSoulStoneItem;
import com.inolia_zaicek.mine_fargo.MineFargo;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import com.inolia_zaicek.mine_fargo.Util.MyGoUtil;
import static com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import org.confluence.mod.misc.ModConfigs;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

import java.util.*;

import static com.inolia_zaicek.mine_fargo.Event.HurtEvent.*;

public class TickSlotAddEvent {
    @SubscribeEvent
    public static void tick(LivingEvent.LivingTickEvent event) {
        if (!event.getEntity().isAlive())
            return;
        LivingEntity livingEntity = event.getEntity();
        //每秒检查一次杜绝消耗
        //配置文件启动or是整合包模式————给女仆加
        if (MyGoConfig.can_slot_add.get() ) {
            if (livingEntity.level().getGameTime() % 20L == 0) {
                int addNumber = 0;
                if (ModList.get().isLoaded("alexscaves")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("l2complements")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("aquaculture")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("farmersdelight")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("l2hostility")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("sons_of_sins")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("iceandfire")) {
                    addNumber += 2;
                }
                if (ModList.get().isLoaded("goety")) {
                    addNumber += 2;
                }
                if (ModList.get().isLoaded("legendary_monsters")) {
                    addNumber += 2;
                }
                if (ModList.get().isLoaded("twilightforest")) {
                    addNumber += 2;
                }
                if (ModList.get().isLoaded("create")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("botania")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("cataclysm")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("irons_spellbooks")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("traveloptics")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("alshanex_familiars")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("gtbcs_geomancy_plus")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("fantasy_ending")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("ars_nouveau")) {
                    addNumber += 1;
                }
                if (ModList.get().isLoaded("ars_elemental")) {
                    addNumber += 2;
                }
                if (ModList.get().isLoaded("tacz")) {
                    addNumber += 1;
                }
                //需要添加的槽位数量-4后大于0——————魂石总数大于4了
                if (addNumber - 4 > 0) {
                    int finalAddNumber = addNumber;
                    CuriosApi.getCuriosInventory(livingEntity).ifPresent((iCuriosItemHandler) -> {
                        ICurioStacksHandler iCurioStacksHandler = (ICurioStacksHandler) iCuriosItemHandler.getCurios().get("soul_stone");
                        if (iCurioStacksHandler != null
                                //栏位小于上限
                                && iCurioStacksHandler.getSlots() < MyGoConfig.mod_slot_add.get()
                        ) {
                            //+1
                            iCurioStacksHandler.grow(finalAddNumber - 4);
                        }
                    });
                }
            }
        }
    }
}