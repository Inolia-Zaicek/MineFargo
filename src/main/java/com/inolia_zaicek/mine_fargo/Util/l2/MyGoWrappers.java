//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.inolia_zaicek.mine_fargo.Util.l2;

import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

public class MyGoWrappers {
    public static <T> T parse(Supplier<T> sup) {
        return (T)sup.get();
    }

    public static <A, B> B cast(A a) {
        return (B)a;
    }

    public static void run(ExcRun run) {
        try {
            run.get();
        } catch (Throwable e) {
            LogManager.getLogger().throwing(Level.ERROR, e);
        }

    }

    @Nullable
    public static <T> T get(ExcSup<T> sup) {
        try {
            return sup.get();
        } catch (Throwable e) {
            LogManager.getLogger().throwing(Level.ERROR, e);
            return null;
        }
    }

    @Nullable
    public static <T> T ignore(ExcSup<T> sup) {
        try {
            return sup.get();
        } catch (Exception var2) {
            return null;
        }
    }

    public static void ignore(ExcRun sup) {
        try {
            sup.get();
        } catch (Exception var2) {
        }

    }

    @FunctionalInterface
    public interface ExcRun {
        void get() throws Exception;
    }

    @FunctionalInterface
    public interface ExcSup<T> {
        @Nullable
        T get() throws Exception;
    }
}
