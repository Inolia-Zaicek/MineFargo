package com.inolia_zaicek.mine_fargo.Register;

import com.inolia_zaicek.mine_fargo.Item.MineCraft.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static com.inolia_zaicek.mine_fargo.MineFargo.MODID;

public class MyGoItemRegister {
    public static final DeferredRegister<Item> ZeroingITEM = DeferredRegister.create(Registries.ITEM, MODID);
    public static final DeferredRegister<Item> IronItem = DeferredRegister.create(Registries.ITEM, MODID);
    public static List<RegistryObject<Item>> CommonItem = new ArrayList<>(List.of());

    public static RegistryObject<Item> registerCommonMaterials(DeferredRegister<Item> register, String name, Supplier<? extends Item> sup){
        RegistryObject<Item> object = register.register(name,sup);
        CommonItem.add(object);
        return object;
    }
    //合成素材
    public static RegistryObject<Item> EmptySoulStone = registerCommonMaterials(ZeroingITEM,"empty_soul_stone", () -> new Item(new Item.Properties().stacksTo(64).fireResistant() ));
    public static RegistryObject<Item> IronSoulStone=registerCommonMaterials(ZeroingITEM,"iron_soul_stone", IronSoulStoneItem::new);
    public static RegistryObject<Item> GoldSoulStone=registerCommonMaterials(ZeroingITEM,"gold_soul_stone", GoldSoulStoneItem::new);
    public static RegistryObject<Item> DiamondSoulStone=registerCommonMaterials(ZeroingITEM,"diamond_soul_stone", DiamondSoulStoneItem::new);
    public static RegistryObject<Item> LapisLazuliSoulStone=registerCommonMaterials(ZeroingITEM,"lapis_lazuli_soul_stone", LapisLazuliSoulStoneItem::new);
    public static RegistryObject<Item> RedstoneSoulStone=registerCommonMaterials(ZeroingITEM,"redstone_soul_stone", RedstoneSoulStoneItem::new);
    public static RegistryObject<Item> EmeraldSoulStone=registerCommonMaterials(ZeroingITEM,"emerald_soul_stone", EmeraldSoulStoneItem::new);

    public static RegistryObject<Item> SoulOfFlight;

    public static void register(IEventBus bus){
        ZeroingITEM.register(bus);
        if (ModList.get().isLoaded("irons_spellbooks")) {
            IronItem.register(bus);
            SoulOfFlight = registerCommonMaterials(ZeroingITEM,"soul_of_flight", () -> new TooltipItem(new Item.Properties().stacksTo(64).fireResistant() ));
        }
    }
}