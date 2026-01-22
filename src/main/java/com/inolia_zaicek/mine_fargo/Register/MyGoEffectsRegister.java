package com.inolia_zaicek.mine_fargo.Register;

import com.inolia_zaicek.mine_fargo.Register.Effect.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.inolia_zaicek.mine_fargo.MineFargo.MODID;

public class MyGoEffectsRegister {
    public static final DeferredRegister<MobEffect> INOEFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS,MODID);
    public static final RegistryObject<MobEffect> Enderference = INOEFFECT.register("enderference", Enderference::new);
}
