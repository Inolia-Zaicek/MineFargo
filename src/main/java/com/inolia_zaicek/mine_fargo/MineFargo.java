package com.inolia_zaicek.mine_fargo;

import com.inolia_zaicek.mine_fargo.Config.MyGoConfig;
import com.inolia_zaicek.mine_fargo.Event.*;
import com.inolia_zaicek.mine_fargo.ModelProvider.ZeroingModRecipesGen;
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
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.*;


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

        MinecraftForge.EVENT_BUS.register(HurtEvent.class);
        MinecraftForge.EVENT_BUS.register(HealEvent.class);
        MinecraftForge.EVENT_BUS.register(DropsEvent.class);
        MinecraftForge.EVENT_BUS.register(BreakSpeedEvent.class);
        MinecraftForge.EVENT_BUS.register(ExpEvent.class);
        MinecraftForge.EVENT_BUS.register(UseItemEvent.class);
        MinecraftForge.EVENT_BUS.register(FluidCollisionEvent.class);
    }

    @SubscribeEvent
    public void commonSetup(FMLCommonSetupEvent event){
        event.enqueueWork(() -> {
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