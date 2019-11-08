package io.github.vampirestudios.raa.utils;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.DimensionChunkGenerators;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Locale;
import java.util.Map;

public class Utils {

    public static String toTitleCase(String lowerCase) {
        return "" + Character.toUpperCase(lowerCase.charAt(0))+lowerCase.substring(1);
    }

    public static String nameToId(String name, Map<String, String> specialCharMap) {
        // strip name of special chars
        for(Map.Entry<String, String> specialChar : specialCharMap.entrySet()) {
            name = name.replace(specialChar.getKey(), specialChar.getValue());
        }
        return name.toLowerCase(Locale.ENGLISH);
    }

    public static Identifier append(Identifier identifier, String suffix) {
        return new Identifier(identifier.getNamespace(), identifier.getPath() + suffix);
    }

    public static SurfaceBuilder<TernarySurfaceConfig> random(int chance) {
        /*if (chance == 10) {
            return SurfaceBuilder.BADLANDS;
        } else if(chance == 5) {
            return SurfaceBuilder.GIANT_TREE_TAIGA;
        } else if(chance == 30) {
            return SurfaceBuilder.SHATTERED_SAVANNA;
        } else if(chance == 2) {
            return SurfaceBuilder.MOUNTAIN;
        } else if(chance == 40) {
            return SurfaceBuilder.WOODED_BADLANDS;
        } else {
            return RandomlyAddingAnything.SURFACE_BUILDER;
        }*/
        return RandomlyAddingAnything.SURFACE_BUILDER;
    }

    public static DimensionChunkGenerators randomCG(int chance) {
        if (chance < 15) {
            return DimensionChunkGenerators.CAVE;
        } else if(chance > 15 && chance < 30) {
            return DimensionChunkGenerators.FLOATING;
        } else {
            return DimensionChunkGenerators.OVERWORLD;
        }
    }

}
