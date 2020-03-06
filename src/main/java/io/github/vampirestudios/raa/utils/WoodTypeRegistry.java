//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package io.github.vampirestudios.raa.utils;

import net.minecraft.util.StringIdentifiable;

import java.util.ArrayList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class WoodTypeRegistry implements StringIdentifiable {
    public static ArrayList<WoodType> woodTypes = new ArrayList();
    private static Queue<WoodTypeRegistry.ModdedTypeListener> listeners = new ConcurrentLinkedQueue();

    public WoodTypeRegistry() {
    }

    static WoodType registerVanilla(WoodType woodType) {
        woodTypes.add(woodType);
        System.out.println(woodType.getIdentifier().toString());
        return woodType;
    }

    public static WoodType registerModded(WoodType woodType, float hardness, float resistance) {
        registerVanilla(woodType);
        listeners.forEach((listener) -> listener.onModdedWoodTypeRegistered(woodType, hardness, resistance));
        System.out.println(woodType.getIdentifier().toString());
        return woodType;
    }

    public static void registerModdedTypeListener(WoodTypeRegistry.ModdedTypeListener listener) {
        listeners.add(listener);
    }

    public interface ModdedTypeListener {
        void onModdedWoodTypeRegistered(WoodType var1, float var2, float var3);
    }
}
