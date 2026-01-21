package com.inolia_zaicek.mine_fargo.Config;

import net.minecraftforge.common.ForgeConfigSpec;

public class MyGoConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.DoubleValue iron_soul_stone;
    public static final ForgeConfigSpec.DoubleValue gold_soul_stone;
    public static final ForgeConfigSpec.DoubleValue diamond_soul_stone_looting;
    public static final ForgeConfigSpec.DoubleValue diamond_soul_stone_fortune;
    public static final ForgeConfigSpec.DoubleValue lapis_lazuli_soul_stone;
    public static final ForgeConfigSpec.DoubleValue redstone_soul_stone;
    public static final ForgeConfigSpec.DoubleValue emerald_soul_stone;
    public static final ForgeConfigSpec.DoubleValue copper_soul_stone;
    public static final ForgeConfigSpec.DoubleValue coal_soul_stone;
    public static final ForgeConfigSpec.DoubleValue netherite_soul_stone;
    public static final ForgeConfigSpec.DoubleValue netherite_soul_stone_armor;
    public static final ForgeConfigSpec.DoubleValue nether_soul_stone;
    public static final ForgeConfigSpec.DoubleValue mushroom_soul_stone;
    public static final ForgeConfigSpec.DoubleValue ender_soul_stone_attack;
    public static final ForgeConfigSpec.DoubleValue ender_soul_stone_armor;
    public static final ForgeConfigSpec.DoubleValue ocean_soul_stone;
    public static final ForgeConfigSpec.DoubleValue lush_soul_stone_food;
    public static final ForgeConfigSpec.DoubleValue lush_soul_stone_saturation;
    public static final ForgeConfigSpec.DoubleValue forest_soul_stone;
    public static final ForgeConfigSpec.DoubleValue blaze_soul_stone;
    public static final ForgeConfigSpec.DoubleValue death_soul_stone;
    public static final ForgeConfigSpec.DoubleValue arthropod_soul_stone;
    public static final ForgeConfigSpec.DoubleValue animal_soul_stone;
    public static final ForgeConfigSpec.DoubleValue aquatic_soul_stone;
    public static final ForgeConfigSpec.DoubleValue wing_soul_stone;
    static {
        //祝福
        BUILDER.push("base");
        wing_soul_stone = BUILDER.comment("飞翼魂石飞行速度").defineInRange("wing_soul_stone",1,0F,2147483647);
        aquatic_soul_stone = BUILDER.comment("水生魂石水下增伤").defineInRange("aquatic_soul_stone",0.15,0F,2147483647);
        animal_soul_stone = BUILDER.comment("动物魂石额外战利品数量").defineInRange("animal_soul_stone",1,0F,2147483647);
        arthropod_soul_stone = BUILDER.comment("节肢增伤").defineInRange("arthropod_soul_stone",0.15,0F,2147483647);
        death_soul_stone = BUILDER.comment("亡灵增伤").defineInRange("death_soul_stone",0.15,0F,2147483647);
        blaze_soul_stone = BUILDER.comment("狱灵增伤").defineInRange("blaze_soul_stone",0.15,0F,2147483647);
        forest_soul_stone = BUILDER.comment("森林间隔").defineInRange("forest_soul_stone",3,0F,2147483647);
        lush_soul_stone_saturation = BUILDER.comment("自然饱和度").defineInRange("lush_soul_stone_saturation",1,0F,2147483647);
        lush_soul_stone_food = BUILDER.comment("自然饥饿度").defineInRange("lush_soul_stone_food",1,0F,2147483647);
        ocean_soul_stone = BUILDER.comment("海洋游泳速度").defineInRange("ocean_soul_stone",0.50,0F,2147483647);
        ender_soul_stone_attack = BUILDER.comment("末地-末影人特攻").defineInRange("ender_soul_stone_attack",0.5,0F,2147483647);
        ender_soul_stone_armor = BUILDER.comment("末地-末影人特防").defineInRange("ender_soul_stone_armor",0.4,0F,2147483647);
        mushroom_soul_stone = BUILDER.comment("蘑菇魂石概率").defineInRange("mushroom_soul_stone",0.5,0F,2147483647);
        nether_soul_stone = BUILDER.comment("下界魂石增伤").defineInRange("nether_soul_stone",0.15,0F,2147483647);
        netherite_soul_stone = BUILDER.comment("下界合金魂石击退抗性").defineInRange("netherite_soul_stone",0.4,0F,2147483647);
        netherite_soul_stone_armor = BUILDER.comment("下界合金魂石护甲韧性").defineInRange("netherite_soul_stone_armor",3,0F,2147483647);
        coal_soul_stone = BUILDER.comment("煤炭魂石移速").defineInRange("coal_soul_stone",0.2,0F,2147483647);
        copper_soul_stone = BUILDER.comment("铜魂石挖掘速度").defineInRange("copper_soul_stone",0.5,0F,2147483647);
        emerald_soul_stone = BUILDER.comment("绿宝石魂石声望").defineInRange("emerald_soul_stone",100,0F,2147483647);
        redstone_soul_stone = BUILDER.comment("红石攻击速度").defineInRange("redstone_soul_stone",0.25,0F,2147483647);
        iron_soul_stone = BUILDER.comment("铁魂石护甲值").defineInRange("iron_soul_stone",5,0F,2147483647);
        gold_soul_stone = BUILDER.comment("金魂石附魔等级").defineInRange("gold_soul_stone",5,0F,2147483647);
        diamond_soul_stone_looting = BUILDER.comment("钻石魂石抢夺").defineInRange("diamond_soul_stone_looting",2,0F,100);
        diamond_soul_stone_fortune = BUILDER.comment("钻石魂石时运").defineInRange("diamond_soul_stone_fortune",2,0F,100);
        lapis_lazuli_soul_stone = BUILDER.comment("青金石经验倍率").defineInRange("lapis_lazuli_soul_stone",1,0F,100);
        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
