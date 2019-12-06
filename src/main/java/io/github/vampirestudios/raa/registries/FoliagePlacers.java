package io.github.vampirestudios.raa.registries;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.feature.tree.foliage.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.function.Function;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class FoliagePlacers {
    public static FoliagePlacerType<CylinderFoliagePlacer> CYLINDER;
    public static FoliagePlacerType<UpsideDownOakFoliagePlacer> UPSIDE_DOWN;
    public static FoliagePlacerType<LongOakFoliagePlacer> LONG_OAK;
    public static FoliagePlacerType<BoringOakFoliagePlacer> BORING_OAK;
    public static FoliagePlacerType<RandomSpruceFoliagePlacer> RANDOM_SPRUCE;

    public static void init() {
        CYLINDER = register("cylinder", CylinderFoliagePlacer::new);
        UPSIDE_DOWN = register("upside_down", UpsideDownOakFoliagePlacer::new);
        LONG_OAK = register("long_oak", LongOakFoliagePlacer::new);
        BORING_OAK = register("boring_oak", BoringOakFoliagePlacer::new);
        RANDOM_SPRUCE = register("random_spruce", RandomSpruceFoliagePlacer::new);
    }

    @SuppressWarnings("unchecked")
    public static FoliagePlacerType register(String string, Function<Dynamic<?>, FoliagePlacer> function) {
        Constructor<FoliagePlacerType> constructor;
        try {
            constructor = FoliagePlacerType.class.getDeclaredConstructor(Function.class);
            constructor.setAccessible(true);

            return Registry.register(Registry.FOLIAGE_PLACER_TYPE, new Identifier(MOD_ID, string), (FoliagePlacerType<?>) constructor.newInstance(function));
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Couldn't register foliage placer type!");
    }
}
