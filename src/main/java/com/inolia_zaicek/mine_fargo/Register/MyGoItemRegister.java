package com.inolia_zaicek.mine_fargo.Register;

import com.inolia_zaicek.mine_fargo.Item.Ars.*;
import com.inolia_zaicek.mine_fargo.Item.Botania.*;
import com.inolia_zaicek.mine_fargo.Item.Cataclysm.*;
import com.inolia_zaicek.mine_fargo.Item.Create.*;
import com.inolia_zaicek.mine_fargo.Item.Goety.Item.*;
import com.inolia_zaicek.mine_fargo.Item.Iron.*;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Entity.LavaEaterSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Entity.*;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Monsters.*;
import com.inolia_zaicek.mine_fargo.Item.LegendaryMonsters.Monsters.SkeletosaurusSoulStoneItem;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Entity.*;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.*;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Nature.*;
import com.inolia_zaicek.mine_fargo.Item.MineCraft.Ores.*;
import com.inolia_zaicek.mine_fargo.Item.Tacz.*;
import com.inolia_zaicek.mine_fargo.Item.Twilight.*;
import com.inolia_zaicek.mine_fargo.Item.Twilight.TwilightForest.*;
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

    public static RegistryObject<Item> SoulOfIronSpell;
    public static RegistryObject<Item> FireSectSoulStone;
    public static RegistryObject<Item> IceSectSoulStone;
    public static RegistryObject<Item> LightningSectSoulStone;
    public static RegistryObject<Item> EvocationSectSoulStone;
    public static RegistryObject<Item> HolySectSoulStone;
    public static RegistryObject<Item> BloodSectSoulStone;
    public static RegistryObject<Item> NatureSectSoulStone;
    public static RegistryObject<Item> EnderSectSoulStone;
    public static RegistryObject<Item> EldritchSectSoulStone;
    public static RegistryObject<Item> AquaSectSoulStone;
    public static RegistryObject<Item> SoundSectSoulStone;
    public static RegistryObject<Item> EarthSectSoulStone;
    public static RegistryObject<Item> FantacySectSoulStone;

    public static RegistryObject<Item> SoulOfArsNouveau;
    public static RegistryObject<Item> ArsSourceSoulStone;
    public static RegistryObject<Item> DrygmySoulStone;
    public static RegistryObject<Item> WhirlisprigSoulStone;
    public static RegistryObject<Item> StarbuncleSoulStone;
    public static RegistryObject<Item> BookwyrmSoulStone;
    public static RegistryObject<Item> WixieSoulStone;
    public static RegistryObject<Item> AmethystGolemSoulStone;
    public static RegistryObject<Item> ArchwoodSoulStone;
    public static RegistryObject<Item> WildenSoulStone;
    public static RegistryObject<Item> SirenSoulStone;
    public static RegistryObject<Item> FirenandoSoulStone;

    public static RegistryObject<Item> SoulOfTacz;
    public static RegistryObject<Item> AmmoSoulStone;
    public static RegistryObject<Item> HandgunSoulStone;
    public static RegistryObject<Item> RifleSoulStone;
    public static RegistryObject<Item> SniperRifleSoulStone;
    public static RegistryObject<Item> ShotgunSoulStone;
    public static RegistryObject<Item> SubmachineGunSoulStone;
    public static RegistryObject<Item> HeavyMachineGunSoulStone;
    public static RegistryObject<Item> MachineGunSoulStone;

    public static RegistryObject<Item> SoulOfCataclysm;
    public static RegistryObject<Item> IgnisSoulStone;
    public static RegistryObject<Item> TheLeviathanSoulStone;
    public static RegistryObject<Item> MaledictusSoulStone;
    public static RegistryObject<Item> EnderGuardianSoulStone;
    public static RegistryObject<Item> AncientRemnantSoulStone;
    public static RegistryObject<Item> TheHarbingerSoulStone;
    public static RegistryObject<Item> NetheriteMonstrositySoulStone;
    public static RegistryObject<Item> ScyllaSoulStone;

    public static RegistryObject<Item> SoulOfBotania;
    public static RegistryObject<Item> ManasteelSoulStone;
    public static RegistryObject<Item> ElementiumSoulStone;
    public static RegistryObject<Item> TerrasteelSoulStone;
    public static RegistryObject<Item> GaiaSoulStone;

    public static RegistryObject<Item> SoulOfCreate;
    public static RegistryObject<Item> ZincSoulStone;
    public static RegistryObject<Item> AndesiteAlloySoulStone;
    public static RegistryObject<Item> BrassSoulStone;
    public static RegistryObject<Item> SturdySheetSoulStone;
    public static RegistryObject<Item> CardboardSoulStone;
    public static RegistryObject<Item> BlazeCakeSoulStone;
    public static RegistryObject<Item> RoseQuartzSoulStone;

    public static RegistryObject<Item> SoulOfTwilight;
    public static RegistryObject<Item> NagaSoulStoneItem;
    public static RegistryObject<Item> ZombieScepterSoulStone;
    public static RegistryObject<Item> LifedrainSoulStone;
    public static RegistryObject<Item> FortificationSoulStone;
    public static RegistryObject<Item> TwilightLichSoulStone;
    public static RegistryObject<Item> MinoshroomSoulStone;
    public static RegistryObject<Item> TwilightHydraSoulStone;
    public static RegistryObject<Item> KnightPhantomSoulStone;
    public static RegistryObject<Item> UrGhastSoulStone;
    public static RegistryObject<Item> AlphaYetiSoulStone;
    public static RegistryObject<Item> SnowQueenSoulStone;

    public static RegistryObject<Item> SoulOfTwilightForest;
    public static RegistryObject<Item> IronwoodSoulStone;
    public static RegistryObject<Item> SteeleafSoulStone;
    public static RegistryObject<Item> FieryIronSoulStone;
    public static RegistryObject<Item> FluffyCloudSoulStone;
    public static RegistryObject<Item> TwilightGiantSoulStone;
    public static RegistryObject<Item> QuestRamSoulStone;


    public static RegistryObject<Item> SoulOfLegendaryMonsters;
    public static RegistryObject<Item> CloudGolemSoulStone;
    public static RegistryObject<Item> FrostbittenGolemSoulStone;
    public static RegistryObject<Item> AncientGuardianSoulStone;
    public static RegistryObject<Item> PosessedPaladinSoulStone;
    public static RegistryObject<Item> SkeletosaurusSoulStone;

    public static RegistryObject<Item> SoulOfLegendaryEntity;
    public static RegistryObject<Item> DuneSentinelSoulStone;
    public static RegistryObject<Item> EndersentSoulStone;
    public static RegistryObject<Item> LavaEaterSoulStone;
    public static RegistryObject<Item> WitheredAbominationSoulStone;
    public static RegistryObject<Item> ShulkerMimicSoulStone;
    public static RegistryObject<Item> OvergrownColossusSoulStone;

    public static RegistryObject<Item> SoulOfMalum;

    public static RegistryObject<Item> SoulOfGoetyItem;
    public static RegistryObject<Item> EctoplasmSoulStone;
    public static RegistryObject<Item> GoetyFocusSoulStone;
    public static RegistryObject<Item> OrderAboutSoulStone;
    public static RegistryObject<Item> EscortSoulStone;
    public static RegistryObject<Item> BrewSoulStone;
    public static RegistryObject<Item> LegionSoulStone;
    public static RegistryObject<Item> GoetyDarkSoulStone;

    public static void register(IEventBus bus){
        ZeroingITEM.register(bus);
        if (ModList.get().isLoaded("goety")) {
            SoulOfGoetyItem = registerCommonMaterials(ZeroingITEM, "soul_of_goety_item", SoulOfGoetyItemItem::new);
            EctoplasmSoulStone = registerCommonMaterials(ZeroingITEM, "ectoplasm_soul_stone", EctoplasmSoulStoneItem::new);
            GoetyFocusSoulStone = registerCommonMaterials(ZeroingITEM, "goety_focus_soul_stone", GoetyFocusSoulStoneItem::new);
            OrderAboutSoulStone = registerCommonMaterials(ZeroingITEM, "order_about_soul_stone", OrderAboutSoulStoneItem::new);
            EscortSoulStone = registerCommonMaterials(ZeroingITEM, "escort_soul_stone", EscortSoulStoneItem::new);
            BrewSoulStone = registerCommonMaterials(ZeroingITEM, "brew_soul_stone", BrewSoulStoneItem::new);
            LegionSoulStone = registerCommonMaterials(ZeroingITEM, "legion_soul_stone", LegionSoulStoneItem::new);
            GoetyDarkSoulStone = registerCommonMaterials(ZeroingITEM, "goety_dark_soul_stone", GoetyDarkSoulStoneItem::new);
        }
        if (ModList.get().isLoaded("legendary_monsters")) {
            SoulOfLegendaryMonsters = registerCommonMaterials(ZeroingITEM, "soul_of_legendary_monsters", SoulOfLegendaryMonstersItem::new);
            CloudGolemSoulStone = registerCommonMaterials(ZeroingITEM, "cloud_golem_soul_stone", CloudGolemSoulStoneItem::new);
            FrostbittenGolemSoulStone = registerCommonMaterials(ZeroingITEM, "frostbitten_golem_soul_stone", FrostbittenGolemSoulStoneItem::new);
            AncientGuardianSoulStone = registerCommonMaterials(ZeroingITEM, "ancient_guardian_soul_stone", AncientGuardianSoulStoneItem::new);
            PosessedPaladinSoulStone = registerCommonMaterials(ZeroingITEM, "posessed_paladin_soul_stone", PosessedPaladinSoulStoneItem::new);
            SkeletosaurusSoulStone = registerCommonMaterials(ZeroingITEM, "skeletosaurus_soul_stone", SkeletosaurusSoulStoneItem::new);

            SoulOfLegendaryEntity = registerCommonMaterials(ZeroingITEM, "soul_of_legendary_entity", SoulOfLegendaryEntityItem::new);
            DuneSentinelSoulStone = registerCommonMaterials(ZeroingITEM, "dune_sentinel_soul_stone", DuneSentinelSoulStoneItem::new);
            EndersentSoulStone = registerCommonMaterials(ZeroingITEM, "endersent_soul_stone", EndersentSoulStoneItem::new);
            LavaEaterSoulStone = registerCommonMaterials(ZeroingITEM, "lava_eater_soul_stone", LavaEaterSoulStoneItem::new);
            WitheredAbominationSoulStone = registerCommonMaterials(ZeroingITEM, "withered_abomination_soul_stone", WitheredAbominationSoulStoneItem::new);
            ShulkerMimicSoulStone = registerCommonMaterials(ZeroingITEM, "shulker_mimic_soul_stone", ShulkerMimicSoulStoneItem::new);
            OvergrownColossusSoulStone = registerCommonMaterials(ZeroingITEM, "overgrown_colossus_soul_stone", OvergrownColossusSoulStoneItem::new);
        }
        if (ModList.get().isLoaded("twilightforest")) {
            SoulOfTwilight = registerCommonMaterials(ZeroingITEM, "soul_of_twilight", SoulOfTwilightItem::new);
            NagaSoulStoneItem = registerCommonMaterials(ZeroingITEM, "naga_soul_stone", NagaSoulStoneItem::new);
            ZombieScepterSoulStone = registerCommonMaterials(ZeroingITEM, "zombie_scepter_soul_stone", ZombieScepterSoulStoneItem::new);
            LifedrainSoulStone = registerCommonMaterials(ZeroingITEM, "lifedrain_soul_stone", LifedrainSoulStoneItem::new);
            FortificationSoulStone = registerCommonMaterials(ZeroingITEM, "fortification_soul_stone", FortificationSoulStoneItem::new);
            TwilightLichSoulStone = registerCommonMaterials(ZeroingITEM, "twilight_lich_soul_stone", TwilightLichSoulStoneItem::new);
            MinoshroomSoulStone = registerCommonMaterials(ZeroingITEM, "minoshroom_soul_stone", MinoshroomSoulStoneItem::new);
            TwilightHydraSoulStone = registerCommonMaterials(ZeroingITEM, "twilight_hydra_soul_stone", TwilightHydraSoulStoneItem::new);
            KnightPhantomSoulStone = registerCommonMaterials(ZeroingITEM, "knight_phantom_soul_stone", KnightPhantomSoulStoneItem::new);
            UrGhastSoulStone = registerCommonMaterials(ZeroingITEM, "ur_ghast_soul_stone", UrGhastSoulStoneItem::new);
            AlphaYetiSoulStone = registerCommonMaterials(ZeroingITEM, "alpha_yeti_soul_stone", AlphaYetiSoulStoneItem::new);
            SnowQueenSoulStone = registerCommonMaterials(ZeroingITEM, "snow_queen_soul_stone", SnowQueenSoulStoneItem::new);

            SoulOfTwilightForest = registerCommonMaterials(ZeroingITEM, "soul_of_twilightforest", SoulOfTwilightForestItem::new);
            IronwoodSoulStone = registerCommonMaterials(ZeroingITEM, "ironwood_soul_stone", IronwoodSoulStoneItem::new);
            SteeleafSoulStone = registerCommonMaterials(ZeroingITEM, "steeleaf_soul_stone", SteeleafSoulStoneItem::new);
            FieryIronSoulStone = registerCommonMaterials(ZeroingITEM, "fiery_iron_soul_stone", FieryIronSoulStoneItem::new);
            FluffyCloudSoulStone = registerCommonMaterials(ZeroingITEM, "fluffy_cloud_soul_stone", FluffyCloudSoulStoneItem::new);
            TwilightGiantSoulStone = registerCommonMaterials(ZeroingITEM, "twilight_giant_soul_stone", TwilightGiantSoulStoneItem::new);
            QuestRamSoulStone = registerCommonMaterials(ZeroingITEM, "quest_ram_soul_stone", QuestRamSoulStoneItem::new);
        }
        if (ModList.get().isLoaded("create")) {
            SoulOfCreate = registerCommonMaterials(ZeroingITEM, "soul_of_create", SoulOfCreateItem::new);
            ZincSoulStone = registerCommonMaterials(ZeroingITEM, "zinc_soul_stone", ZincSoulStoneItem::new);
            AndesiteAlloySoulStone = registerCommonMaterials(ZeroingITEM, "andesite_alloy_soul_stone", AndesiteAlloySoulStoneItem::new);
            BrassSoulStone = registerCommonMaterials(ZeroingITEM, "brass_soul_stone", BrassSoulStoneItem::new);
            SturdySheetSoulStone = registerCommonMaterials(ZeroingITEM, "sturdy_sheet_soul_stone", SturdySheetSoulStoneItem::new);
            CardboardSoulStone = registerCommonMaterials(ZeroingITEM, "cardboard_soul_stone", CardboardSoulStoneItem::new);
            BlazeCakeSoulStone = registerCommonMaterials(ZeroingITEM, "blaze_cake_soul_stone", BlazeCakeSoulStoneItem::new);
            RoseQuartzSoulStone = registerCommonMaterials(ZeroingITEM, "rose_quartz_soul_stone", RoseQuartzSoulStoneItem::new);
        }
        if (ModList.get().isLoaded("botania")) {
            SoulOfBotania = registerCommonMaterials(ZeroingITEM, "soul_of_botania", SoulOfBotaniaItem::new);
            ManasteelSoulStone = registerCommonMaterials(ZeroingITEM, "manasteel_soul_stone", ManasteelSoulStoneItem::new);
            ElementiumSoulStone = registerCommonMaterials(ZeroingITEM, "elementium_soul_stone", ElementiumSoulStoneItem::new);
            TerrasteelSoulStone = registerCommonMaterials(ZeroingITEM, "terrasteel_soul_stone", TerrasteelSoulStoneItem::new);
            GaiaSoulStone = registerCommonMaterials(ZeroingITEM, "gaia_soul_stone", GaiaSoulStoneItem::new);
        }
        if (ModList.get().isLoaded("cataclysm")) {
            SoulOfCataclysm = registerCommonMaterials(ZeroingITEM, "soul_of_cataclysm", SoulOfCataclysmItem::new);
            IgnisSoulStone = registerCommonMaterials(ZeroingITEM, "ignis_soul_stone", IgnisSoulStoneItem::new);
            TheLeviathanSoulStone = registerCommonMaterials(ZeroingITEM, "the_leviathan_soul_stone", TheLeviathanSoulStoneItem::new);
            MaledictusSoulStone = registerCommonMaterials(ZeroingITEM, "maledictus_soul_stone", MaledictusSoulStoneItem::new);
            EnderGuardianSoulStone = registerCommonMaterials(ZeroingITEM, "ender_guardian_soul_stone", EnderGuardianSoulStoneItem::new);
            AncientRemnantSoulStone = registerCommonMaterials(ZeroingITEM, "ancient_remnant_soul_stone", AncientRemnantSoulStoneItem::new);
            TheHarbingerSoulStone    = registerCommonMaterials(ZeroingITEM, "the_harbinger_soul_stone", TheHarbingerSoulStoneItem::new);
            NetheriteMonstrositySoulStone = registerCommonMaterials(ZeroingITEM, "netherite_monstrosity_soul_stone", NetheriteMonstrositySoulStoneItem::new);
            ScyllaSoulStone = registerCommonMaterials(ZeroingITEM, "scylla_soul_stone", ScyllaSoulStoneItem::new);
        }
        if (ModList.get().isLoaded("irons_spellbooks")) {
            IronItem.register(bus);
            SoulOfIronSpell = registerCommonMaterials(ZeroingITEM,"soul_of_iron_spell", SoulOfIronSpellItem::new);
            FireSectSoulStone = registerCommonMaterials(ZeroingITEM,"fire_sect_soul_stone", FireSectSoulStoneItem::new);
            IceSectSoulStone = registerCommonMaterials(ZeroingITEM,"ice_sect_soul_stone", IceSectSoulStoneItem::new);
            LightningSectSoulStone = registerCommonMaterials(ZeroingITEM,"lightning_sect_soul_stone", LightningSectSoulStoneItem::new);
            EvocationSectSoulStone = registerCommonMaterials(ZeroingITEM,"evocation_sect_soul_stone", EvocationSectSoulStoneItem::new);
            HolySectSoulStone = registerCommonMaterials(ZeroingITEM,"holy_sect_soul_stone", HolySectSoulStoneItem::new);
            BloodSectSoulStone = registerCommonMaterials(ZeroingITEM,"blood_sect_soul_stone", BloodSectSoulStoneItem::new);
            NatureSectSoulStone = registerCommonMaterials(ZeroingITEM,"nature_sect_soul_stone", NatureSectSoulStoneItem::new);
            EnderSectSoulStone = registerCommonMaterials(ZeroingITEM,"ender_sect_soul_stone", EnderSectSoulStoneItem::new);
            EldritchSectSoulStone = registerCommonMaterials(ZeroingITEM,"eldritch_sect_soul_stone", EldritchSectSoulStoneItem::new);
            if (ModList.get().isLoaded("traveloptics")) {
                AquaSectSoulStone = registerCommonMaterials(ZeroingITEM,"aqua_sect_soul_stone", AquaSectSoulStoneItem::new);
            }
            if (ModList.get().isLoaded("alshanex_familiars")) {
                SoundSectSoulStone = registerCommonMaterials(ZeroingITEM,"sound_sect_soul_stone", SoundSectSoulStoneItem::new);
            }
            if (ModList.get().isLoaded("gtbcs_geomancy_plus")) {
                EarthSectSoulStone = registerCommonMaterials(ZeroingITEM,"earth_sect_soul_stone", EarthSectSoulStoneItem::new);
            }
            if (ModList.get().isLoaded("fantasy_ending")) {
                FantacySectSoulStone = registerCommonMaterials(ZeroingITEM,"fantacy_sect_soul_stone", FantacySectSoulStoneItem::new);
            }
        }
        if (ModList.get().isLoaded("ars_nouveau")) {
            SoulOfArsNouveau = registerCommonMaterials(ZeroingITEM,"soul_of_ars_nouveau", SoulOfArsNouveauItem::new);
            ArsSourceSoulStone = registerCommonMaterials(ZeroingITEM,"ars_source_soul_stone", ArsSourceSoulStoneItem::new);
            DrygmySoulStone = registerCommonMaterials(ZeroingITEM,"drygmy_soul_stone", DrygmySoulStoneItem::new);
            WhirlisprigSoulStone = registerCommonMaterials(ZeroingITEM,"whirlisprig_soul_stone", WhirlisprigSoulStoneItem::new);
            StarbuncleSoulStone = registerCommonMaterials(ZeroingITEM,"starbuncle_soul_stone", StarbuncleSoulStoneItem::new);
            BookwyrmSoulStone = registerCommonMaterials(ZeroingITEM,"bookwyrm_soul_stone", BookwyrmSoulStoneItem::new);
            WixieSoulStone = registerCommonMaterials(ZeroingITEM,"wixie_soul_stone", WixieSoulStoneItem::new);
            AmethystGolemSoulStone = registerCommonMaterials(ZeroingITEM,"amethyst_golem_soul_stone", AmethystGolemSoulStoneItem::new);
            ArchwoodSoulStone = registerCommonMaterials(ZeroingITEM,"archwood_soul_stone", ArchwoodSoulStoneItem::new);
            WildenSoulStone = registerCommonMaterials(ZeroingITEM,"wilden_soul_stone", WildenSoulStoneItem::new);
            if (ModList.get().isLoaded("ars_elemental")) {
                SirenSoulStone = registerCommonMaterials(ZeroingITEM, "siren_soul_stone", SirenSoulStoneItem::new);
                FirenandoSoulStone = registerCommonMaterials(ZeroingITEM, "firenando_soul_stone", FirenandoSoulStoneItem::new);
            }
        }
        if (ModList.get().isLoaded("tacz")) {
            SoulOfTacz = registerCommonMaterials(ZeroingITEM, "soul_of_tacz", SoulOfTaczItem::new);
            AmmoSoulStone = registerCommonMaterials(ZeroingITEM, "ammo_soul_stone", AmmoSoulStoneItem::new);
            HandgunSoulStone = registerCommonMaterials(ZeroingITEM, "handgun_soul_stone", HandgunSoulStoneItem::new);
            RifleSoulStone = registerCommonMaterials(ZeroingITEM, "rifle_soul_stone", RifleSoulStoneItem::new);
            SniperRifleSoulStone = registerCommonMaterials(ZeroingITEM, "sniper_rifle_soul_stone", SniperRifleSoulStoneItem::new);
            ShotgunSoulStone = registerCommonMaterials(ZeroingITEM, "shotgun_soul_stone", ShotgunSoulStoneItem::new);
            SubmachineGunSoulStone = registerCommonMaterials(ZeroingITEM, "submachine_gun_soul_stone", SubmachineGunSoulStoneItem::new);
            HeavyMachineGunSoulStone = registerCommonMaterials(ZeroingITEM, "heavy_machine_gun_soul_stone", HeavyMachineGunSoulStoneItem::new);
            MachineGunSoulStone = registerCommonMaterials(ZeroingITEM, "machine_gun_soul_stone", MachineGunSoulStoneItem::new);
        }
    }
}