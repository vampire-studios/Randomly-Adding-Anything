package io.github.vampirestudios.raa.utils;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.dimension.DimensionChunkGenerators;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.registries.SurfaceBuilders;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

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

    public static Identifier addSuffixToPath(Identifier identifier, String suffix) {
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

        if (data.getDimensionChunkGenerator() == DimensionChunkGenerators.OVERWORLD || data.getDimensionChunkGenerator() == DimensionChunkGenerators.CUSTOM_OVERWORLD) {
            if (chance < 20) return SurfaceBuilder.DEFAULT;
            if (chance > 20 && chance <= 30) return SurfaceBuilders.HYPER_FLAT;
            if (chance > 30 && chance <= 40) return SurfaceBuilders.PATCHY_DESERT;
            if (chance > 40 && chance <= 50) {
                if(Rands.chance(4)) return SurfaceBuilders.DARK_PATCHY_BADLANDS;
                else return SurfaceBuilders.PATCHY_BADLANDS;
            }
            if (chance > 50 && chance <= 60) return SurfaceBuilders.CLASSIC_CLIFFS;
            if (chance > 60 && chance <= 70) return SurfaceBuilders.STRATIFIED_CLIFFS;
            if (chance > 70 && chance <= 80) return SurfaceBuilders.FLOATING_ISLANDS;
            if (chance > 80 && chance <= 90 && FabricLoader.getInstance().isModLoaded("simplexterrain")) {
                if(Rands.chance(10)) return SurfaceBuilders.SANDY_DUNES;
                else return SurfaceBuilders.DUNES;
            }
            if (chance > 90 && chance <= 100) return SurfaceBuilders.LAZY_NOISE;
        }

        return SurfaceBuilder.DEFAULT;
    }

    public static SurfaceBuilder<TernarySurfaceConfig> newRandomSurfaceBuilder(Identifier name, DimensionData data) {
        //30% default
        //10% all others

        if (data.getDimensionChunkGenerator() == DimensionChunkGenerators.OVERWORLD || data.getDimensionChunkGenerator() == DimensionChunkGenerators.CUSTOM_OVERWORLD) {
            switch (name.toString()) {
                case "raa:hyper_flat":
                    return SurfaceBuilders.HYPER_FLAT;
                case "raa:patchy_desert":
                    return SurfaceBuilders.PATCHY_DESERT;
                case "raa:dark_patchy_badlands":
                    return SurfaceBuilders.DARK_PATCHY_BADLANDS;
                case "raa:patchy_badlands":
                    return SurfaceBuilders.PATCHY_BADLANDS;
                case "raa:classic_cliffs":
                    return SurfaceBuilders.CLASSIC_CLIFFS;
                case "raa:stratified_cliffs":
                    return SurfaceBuilders.STRATIFIED_CLIFFS;
                case "raa:floating_islands":
                    return SurfaceBuilders.FLOATING_ISLANDS;
                case "raa:sandy_dunes":
                    return SurfaceBuilders.SANDY_DUNES;
                case "raa:dunes":
                    return SurfaceBuilders.DUNES;
                case "raa:lazy_noise":
                    return SurfaceBuilders.LAZY_NOISE;
                case "minecraft:default":
                    return SurfaceBuilder.DEFAULT;
            }
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
            } else if (chance <= 60 && FabricLoader.getInstance().isModLoaded("simplexterrain")) {
                return DimensionChunkGenerators.CUSTOM_OVERWORLD;
            } else if (chance <= 70) {
                return DimensionChunkGenerators.TOTALLY_CUSTOM;
            }
            return DimensionChunkGenerators.OVERWORLD;
        }
    }

    /*public static DimensionChunkGenerators newRandomCG(Identifier name) {
        if (chance < 15) {
            if (name.equals("raa:flat_caves")) return DimensionChunkGenerators.FLAT_CAVES;
            if (name.equals("raa:high_caves")) return DimensionChunkGenerators.HIGH_CAVES;
            if (name.equals("raa:high_caves")) return DimensionChunkGenerators.CAVE;
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
            } else if (chance <= 60 && FabricLoader.getInstance().isModLoaded("simplexterrain")) {
                return DimensionChunkGenerators.CUSTOM_OVERWORLD;
            } else if (chance <= 70) {
                return DimensionChunkGenerators.TOTALLY_CUSTOM;
            }
            return DimensionChunkGenerators.OVERWORLD;
        }
    }*/

    public static boolean checkBitFlag(int toCheck, int flag) {
        return (toCheck & flag) == flag;
    }

    public static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static String generateCivsName() throws IOException {
        String civilizationName;
        Random rand = new Random();
        Identifier surnames = new Identifier(RandomlyAddingAnything.MOD_ID, "names/civilizations.txt");
        InputStream stream = MinecraftClient.getInstance().getResourceManager().getResource(surnames).getInputStream();
        Scanner scanner = new Scanner(Objects.requireNonNull(stream));
        StringBuilder builder = new StringBuilder();
        while (scanner.hasNextLine()) {
            builder.append(scanner.nextLine());
            builder.append(",");
        }
        String[] strings = builder.toString().split(",");
        civilizationName = strings[rand.nextInt(strings.length)];
        stream.close();
        scanner.close();
        return civilizationName;
    }

    public static void createSpawnsFile(String name, IWorld world, BlockPos pos) {
        try {
            String path;
            World world2 = world.getWorld();
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT)
                path = "saves/" + ((ServerWorld) world2).getSaveHandler().getWorldDir().getName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/" + name + "_spawns.txt";
            else
                path = world.getLevelProperties().getLevelName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/" + name + "_spawns.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.append(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
