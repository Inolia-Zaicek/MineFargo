package com.inolia_zaicek.mine_fargo.Register;

import com.inolia_zaicek.mine_fargo.Item.MineCraft.Entity.*;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.*;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.*;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.*;
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

    public static RegistryObject<Item> SoulOfOres =registerCommonMaterials(ZeroingITEM,"soul_of_ores", SoulOfOresItem::new);
    public static RegistryObject<Item> CoalSoulStone=registerCommonMaterials(ZeroingITEM,"coal_soul_stone", CoalSoulStoneItem::new);
    public static RegistryObject<Item> CopperSoulStone=registerCommonMaterials(ZeroingITEM,"copper_soul_stone", CopperSoulStoneItem::new);
    public static RegistryObject<Item> LapisLazuliSoulStone=registerCommonMaterials(ZeroingITEM,"lapis_lazuli_soul_stone", LapisLazuliSoulStoneItem::new);
    public static RegistryObject<Item> IronSoulStone=registerCommonMaterials(ZeroingITEM,"iron_soul_stone", IronSoulStoneItem::new);
    public static RegistryObject<Item> RedstoneSoulStone=registerCommonMaterials(ZeroingITEM,"redstone_soul_stone", RedstoneSoulStoneItem::new);
    public static RegistryObject<Item> GoldSoulStone=registerCommonMaterials(ZeroingITEM,"gold_soul_stone", GoldSoulStoneItem::new);
    public static RegistryObject<Item> EmeraldSoulStone=registerCommonMaterials(ZeroingITEM,"emerald_soul_stone", EmeraldSoulStoneItem::new);
    public static RegistryObject<Item> DiamondSoulStone=registerCommonMaterials(ZeroingITEM,"diamond_soul_stone", DiamondSoulStoneItem::new);
    public static RegistryObject<Item> NetheriteSoulStone=registerCommonMaterials(ZeroingITEM,"netherite_soul_stone", NetheriteSoulStoneItem::new);

    public static RegistryObject<Item> SoulOfNature =registerCommonMaterials(ZeroingITEM,"soul_of_nature", SoulOfNatureItem::new);
    public static RegistryObject<Item> SnowSoulStone=registerCommonMaterials(ZeroingITEM,"snow_soul_stone", SnowSoulStoneItem::new);
    public static RegistryObject<Item> LavaSoulStone=registerCommonMaterials(ZeroingITEM,"lava_soul_stone", LavaSoulStoneItem::new);
    public static RegistryObject<Item> MushroomSoulStone=registerCommonMaterials(ZeroingITEM,"mushroom_soul_stone", MushroomSoulStoneItem::new);
    public static RegistryObject<Item> NetherSoulStone=registerCommonMaterials(ZeroingITEM,"nether_soul_stone", NetherSoulStoneItem::new);
    public static RegistryObject<Item> EnderSoulStone=registerCommonMaterials(ZeroingITEM,"ender_soul_stone", EnderSoulStoneItem::new);
    public static RegistryObject<Item> OceanSoulStone=registerCommonMaterials(ZeroingITEM,"ocean_soul_stone", OceanSoulStoneItem::new);
    public static RegistryObject<Item> LushSoulStone=registerCommonMaterials(ZeroingITEM,"lush_soul_stone", LushSoulStoneItem::new);
    public static RegistryObject<Item> ForestSoulStone=registerCommonMaterials(ZeroingITEM,"forest_soul_stone", ForestSoulStoneItem::new);

    public static RegistryObject<Item> SoulOfEntity =registerCommonMaterials(ZeroingITEM,"soul_of_entity", SoulOfEntityItem::new);
    public static RegistryObject<Item> BlazeSoulStone=registerCommonMaterials(ZeroingITEM,"blaze_soul_stone", BlazeSoulStoneItem::new);
    public static RegistryObject<Item> DeathSoulStone=registerCommonMaterials(ZeroingITEM,"death_soul_stone", DeathSoulStoneItem::new);
    public static RegistryObject<Item> ArthropodSoulStone=registerCommonMaterials(ZeroingITEM,"arthropod_soul_stone", ArthropodSoulStoneItem::new);
    public static RegistryObject<Item> AnimalSoulStone=registerCommonMaterials(ZeroingITEM,"animal_soul_stone", AnimalSoulStoneItem::new);
    public static RegistryObject<Item> AquaticSoulStone=registerCommonMaterials(ZeroingITEM,"aquatic_soul_stone", AquaticSoulStoneItem::new);
    public static RegistryObject<Item> WingSoulStone=registerCommonMaterials(ZeroingITEM,"wing_soul_stone", WingSoulStoneItem::new);

    public static RegistryObject<Item> SoulOfFlight;

    public static void register(IEventBus bus){
        ZeroingITEM.register(bus);
        if (ModList.get().isLoaded("irons_spellbooks")) {
            IronItem.register(bus);
            SoulOfFlight = registerCommonMaterials(ZeroingITEM,"soul_of_flight", () -> new TooltipItem(new Item.Properties().stacksTo(64).fireResistant() ));
        }
    }
}