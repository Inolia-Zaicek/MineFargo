package com.inolia_zaicek.mine_fargo;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Event.*;
import com.inolia_zaicek.mine_fargo.Event.Ars.ArsHurtEvent;
import com.inolia_zaicek.mine_fargo.Event.Botania.ManaRepair;
import com.inolia_zaicek.mine_fargo.Event.Botania.PixieSummon;
import com.inolia_zaicek.mine_fargo.Event.Botania.TerraRay;
import com.inolia_zaicek.mine_fargo.Event.BuffEvent;
import com.inolia_zaicek.mine_fargo.Event.Create.*;
import com.inolia_zaicek.mine_fargo.Event.Create.CreateTickEvent;
import com.inolia_zaicek.mine_fargo.Event.Goety.*;
import com.inolia_zaicek.mine_fargo.Event.IceAndFire.*;
import com.inolia_zaicek.mine_fargo.Event.Iron.AFHurtEvent;
import com.inolia_zaicek.mine_fargo.Event.Iron.FEHurtEvent;
import com.inolia_zaicek.mine_fargo.Event.Iron.IronHurtEvent;
import com.inolia_zaicek.mine_fargo.Event.Iron.TOHurtEvent;
import com.inolia_zaicek.mine_fargo.Event.Tacz.TaczHurtByGunEvent;
import com.inolia_zaicek.mine_fargo.Event.Tacz.TaczHurtEvent;
import com.inolia_zaicek.mine_fargo.Event.Tacz.TaczShootEvent;
import com.inolia_zaicek.mine_fargo.Event.Tacz.TaczTickEvent;
import com.inolia_zaicek.mine_fargo.Event.Twilight.TwilightEvent;
import com.inolia_zaicek.mine_fargo.Event.Twilight.TwilightRepairEvent;
import com.inolia_zaicek.mine_fargo.Event.Twilight.TwilightTickEvent;
import com.inolia_zaicek.mine_fargo.ModelProvider.ZeroingModRecipesGen;
import com.inolia_zaicek.mine_fargo.Network.TerraRayChannel;
import com.inolia_zaicek.mine_fargo.Register.MyGoEffectsRegister;
import com.inolia_zaicek.mine_fargo.Register.MyGoItemRegister;
import com.inolia_zaicek.mine_fargo.Register.Tab;
import com.inolia_zaicek.mine_fargo.loot.ModLootModifiers;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.*;


@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE,modid = MineFargo.MODID)
@Mod(MineFargo.MODID)
public class MineFargo {

    public static final String MODID = "mine_fargo";
    public MineFargo() {
        init();
        ModLoadingContext modLoadingContext = ModLoadingContext.get();
        modLoadingContext.registerConfig(ModConfig.Type.COMMON, MyGoConfig.SPEC);
    }

    public void init(){
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        // 注册 Item、Tab、Entity 类型
        Tab.register(bus);
        MyGoItemRegister.register(bus);
        ModLootModifiers.register(bus);
        // 注册 CommonSetup 事件
        bus.addListener(this::commonSetup);
        // !!! 注册 ClientSetup 事件 !!!
        bus.addListener(this::clientSetup);
        MyGoEffectsRegister.INOEFFECT.register(bus);

        MinecraftForge.EVENT_BUS.register(HurtEvent.class);
        MinecraftForge.EVENT_BUS.register(HealEvent.class);
        MinecraftForge.EVENT_BUS.register(DropsEvent.class);
        MinecraftForge.EVENT_BUS.register(BreakSpeedEvent.class);
        MinecraftForge.EVENT_BUS.register(ExpEvent.class);
        MinecraftForge.EVENT_BUS.register(UseItemEvent.class);
        MinecraftForge.EVENT_BUS.register(FluidCollisionEvent.class);
        MinecraftForge.EVENT_BUS.register(TeleportEvent.class);
        MinecraftForge.EVENT_BUS.register(BuffEvent.class);
        MinecraftForge.EVENT_BUS.register(DeathAndCloneEvent.class);
        MinecraftForge.EVENT_BUS.register(CriticalHitEvent.class);
        MinecraftForge.EVENT_BUS.register(TickEvent.class);
        MinecraftForge.EVENT_BUS.register(ShieldEvent.class);
        if (ModList.get().isLoaded("irons_spellbooks")) {
            MinecraftForge.EVENT_BUS.register(IronHurtEvent.class);
            if (ModList.get().isLoaded("traveloptics")) {
                MinecraftForge.EVENT_BUS.register(TOHurtEvent.class);
            }
            if (ModList.get().isLoaded("alshanex_familiars")) {
                MinecraftForge.EVENT_BUS.register(AFHurtEvent.class);
            }
            if (ModList.get().isLoaded("fantasy_ending")) {
                MinecraftForge.EVENT_BUS.register(FEHurtEvent.class);
            }
        }
        if (ModList.get().isLoaded("ars_nouveau")) {
            MinecraftForge.EVENT_BUS.register(ArsHurtEvent.class);
        }
        if (ModList.get().isLoaded("l2hostility")) {
            MinecraftForge.EVENT_BUS.register(L2Hurt.class);
        }
        if (ModList.get().isLoaded("iceandfire")) {
            if (!ModList.get().isLoaded("jupiter")&&!ModList.get().isLoaded("uranus")) {
                MinecraftForge.EVENT_BUS.register(IAFHurtEvent.class);
            }else{
                MinecraftForge.EVENT_BUS.register(IAFCEHurtEvent.class);
            }
        }
        if (ModList.get().isLoaded("tacz")) {
            MinecraftForge.EVENT_BUS.register(TaczShootEvent.class);
            MinecraftForge.EVENT_BUS.register(TaczTickEvent.class);
            MinecraftForge.EVENT_BUS.register(TaczHurtByGunEvent.class);
            MinecraftForge.EVENT_BUS.register(TaczHurtEvent.class);
        }
        if (ModList.get().isLoaded("botania")) {
            MinecraftForge.EVENT_BUS.register(ManaRepair.class);
            MinecraftForge.EVENT_BUS.register(PixieSummon.class);
            MinecraftForge.EVENT_BUS.register(TerraRay.class);
        }
        if (ModList.get().isLoaded("create")) {
            MinecraftForge.EVENT_BUS.register(CreateTickEvent.class);
            MinecraftForge.EVENT_BUS.register(CreateRightBlockEvent.class);
        }
        if (ModList.get().isLoaded("twilightforest")) {
            MinecraftForge.EVENT_BUS.register(TwilightEvent.class);
            MinecraftForge.EVENT_BUS.register(TwilightTickEvent.class);
            MinecraftForge.EVENT_BUS.register(TwilightRepairEvent.class);
        }
        if (ModList.get().isLoaded("goety")) {
            String versionString = ModList.get().getModFileById("goety").versionString();
            MinecraftForge.EVENT_BUS.register(GoetyUseEvent.class);
            MinecraftForge.EVENT_BUS.register(GoetyKillEvent.class);
                MinecraftForge.EVENT_BUS.register(GoetyDeathEvent.class);
        }
        if (ModList.get().isLoaded("sons_of_sins")) {
            MinecraftForge.EVENT_BUS.register(SonsOfSinsDropEvent.class);
        }
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
            if(ModList.get().isLoaded("botania")) {
                TerraRayChannel.init();
            }
        });
    }

    // 客户端设置事件，用于注册渲染器和GUI屏幕
    // 加上 @SubscribeEvent，使其成为 Mod 事件总线上的监听器
    @SubscribeEvent
    public void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
        });
    }

    //注册掉落物
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        var lookupProvider = event.getLookupProvider();
        if (event.includeServer()) {
            generator.addProvider(event.includeServer(), new ZeroingModRecipesGen(output));
        }
    }

    public static ResourceLocation prefix(String name){
        return new ResourceLocation(MODID,name.toLowerCase(Locale.ROOT));
    }
    public static ResourceLocation getResource(String id) {
        return new ResourceLocation("mine_fargo", id);
    }
}