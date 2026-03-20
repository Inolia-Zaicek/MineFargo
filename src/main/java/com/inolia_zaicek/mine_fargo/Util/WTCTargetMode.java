package com.inolia_zaicek.mine_fargo.Util;

import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public interface WTCTargetMode {
    @Nullable Predicate<Entity> mmt$getMode();

    void mmt$setMode(Predicate<Entity> var1);
}
