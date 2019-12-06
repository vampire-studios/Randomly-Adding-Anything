package io.github.vampirestudios.raa.utils;

import io.github.vampirestudios.raa.api.dimension.DimensionChunkGenerators;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.registries.SurfaceBuilders;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Locale;
import java.util.Map;

public class Utils {
    //dimension bit flags
    public static final int CORRUPTED = 1; //nether corruption, same as the old corruption feature
    public static final int DEAD = 2; //No plants or passive animals at all, very harsh
    public static final int ABANDONED = 4; //only ruins of old civilizations, no living "smart" creatures (like villagers)
    public static final int LUSH = 8; //A lush overgrowth of plants
    public static final int CIVILIZED = 16; //Villages/towns of "smart" creatures who will trade with you
    public static final int MOLTEN = 32; //Instead of water oceans, there are lava oceans.
    public static final int DRY = 64; //No oceans exist at all.
    public static final int TECTONIC = 128; //Creates lots of caves and ravines. Usually not visible on the surface.
    public static final int FROZEN = 256; //Makes the dimension frozen

    public static final int POST_APOCALYPTIC = CORRUPTED | DEAD | ABANDONED | DRY | TECTONIC; //A combination of corrupted, dead, abandoned, dry, and tectonic

    public static String toTitleCase(String lowerCase) {
        return "" + Character.toUpperCase(lowerCase.charAt(0)) + lowerCase.substring(1);
    }

    public static String nameToId(String name, Map<String, String> specialCharMap) {
        // strip name of special chars
        for (Map.Entry<String, String> specialChar : specialCharMap.entrySet()) {
            name = name.replace(specialChar.getKey(), specialChar.getValue());
        }
        return name.toLowerCase(Locale.ENGLISH);
    }

    public static Identifier appendToPath(Identifier identifier, String suffix) {
        return new Identifier(identifier.getNamespace(), identifier.getPath() + suffix);
    }

    public static Identifier prependToPath(Identifier identifier, String prefix) {
        return new Identifier(identifier.getNamespace(), prefix + identifier.getPath());
    }

    public static Identifier appendAndPrependToPath(Identifier identifier, String prefix, String suffix) {
        return new Identifier(identifier.getNamespace(), prefix + identifier.getPath() + suffix);
    }

    public static SurfaceBuilder<TernarySurfaceConfig> randomSurfaceBuilder(int chance, DimensionData data) {
        //30% default
        //10% all others

        if (data.getDimensionChunkGenerator() == DimensionChunkGenerators.OVERWORLD) {
            if (chance < 20) return SurfaceBuilder.DEFAULT;
            if (chance > 20 && chance <= 30) return SurfaceBuilders.HYPERFLAT;
            if (chance > 30 && chance <= 40) return SurfaceBuilders.PATCHY_DESERT;
            if (chance > 40 && chance <= 50) return SurfaceBuilders.PATCHY_MESA;
            if (chance > 50 && chance <= 60) return SurfaceBuilders.CLASSIC_CLIFFS;
            if (chance > 60 && chance <= 70) return SurfaceBuilders.STRATIFIED_CLIFFS;
            if (chance > 70 && chance <= 80) return SurfaceBuilders.FLOATING_ISLANDS;
            if (chance > 80 && chance <= 90) return SurfaceBuilders.DUNES;
            if (chance > 90 && chance <= 100) return SurfaceBuilders.LAZY_NOISE;
        }

        return SurfaceBuilder.DEFAULT;
    }

    public static DimensionChunkGenerators randomCG(int chance) {
        if (chance < 15) {
            if (chance <= 5) {
                return DimensionChunkGenerators.FLAT_CAVES;
            } else if (chance <= 10) {
                return DimensionChunkGenerators.HIGH_CAVES;
            }
            return DimensionChunkGenerators.CAVE;
        } else if (chance > 15 && chance < 30) {
            if (chance <= 20) {
                return DimensionChunkGenerators.LAYERED_FLOATING;
            } else if (chance <= 25) {
                return DimensionChunkGenerators.PRE_CLASSIC_FLOATING;
            }
            return DimensionChunkGenerators.FLOATING;
        } else {
            if (chance <= 40) {
                return DimensionChunkGenerators.QUADRUPLE_AMPLIFIED;
            } else if (chance <= 50) {
                return DimensionChunkGenerators.PILLAR_WORLD;
            }
            return DimensionChunkGenerators.OVERWORLD;
        }
    }

    public static boolean checkBitFlag(int toCheck, int flag) {
        return (toCheck & flag) == flag;
    }

    public static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }
}
