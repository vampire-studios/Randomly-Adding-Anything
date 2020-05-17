package io.github.vampirestudios.raa.utils;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.dimension.DimensionChunkGenerators;
import io.github.vampirestudios.raa.effects.MaterialEffects;
import io.github.vampirestudios.raa.generation.surface.random.SurfaceBuilderGenerator;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.WeightedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

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
    public static final int FROZEN = 256; //Makes the dimension frozen (snow instead of rain)
    public static final int LUCID = 512; //Makes the dimension be lucid, have faster time and more crazy skybox

    public static final int POST_APOCALYPTIC = CORRUPTED | DEAD | ABANDONED | DRY | TECTONIC; //A combination of corrupted, dead, abandoned, dry, and tectonic

    //maps
    private static List<String> surfaceBuilders = new ArrayList<>();

    public static final WeightedList<MaterialEffects> EFFECT_LIST = new WeightedList<>();

    static {
        surfaceBuilders.add("raa:patchy_desert");
        surfaceBuilders.add("raa:dark_patchy_badlands");
        surfaceBuilders.add("raa:patchy_badlands");
        surfaceBuilders.add("raa:classic_cliffs");
        surfaceBuilders.add("raa:stratified_cliffs");
        surfaceBuilders.add("raa:sandy_dunes");
        surfaceBuilders.add("raa:dunes");
        surfaceBuilders.add("minecraft:default");

        EFFECT_LIST.add(MaterialEffects.LIGHTNING, 2);
        EFFECT_LIST.add(MaterialEffects.EFFECT, 4);
        EFFECT_LIST.add(MaterialEffects.FIREBALL, 2);
        EFFECT_LIST.add(MaterialEffects.FREEZE, 1);
    }

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

    public static Identifier addPrefixToPath(Identifier identifier, String prefix) {
        return new Identifier(identifier.getNamespace(), prefix + identifier.getPath());
    }

    public static Identifier addPrefixAndSuffixToPath(Identifier identifier, String prefix, String suffix) {
        return new Identifier(identifier.getNamespace(), prefix + identifier.getPath() + suffix);
    }

    public static TernarySurfaceConfig randomSurfaceBuilderConfig() {
        if (Rands.chance(6)) return SurfaceBuilder.GRASS_CONFIG;

        Map<String, TernarySurfaceConfig> surfaceBuilders = new HashMap<>();
        surfaceBuilders.put("minecraft:gravel_config", SurfaceBuilder.GRAVEL_CONFIG);
        surfaceBuilders.put("minecraft:grass_config", SurfaceBuilder.GRASS_CONFIG);
        surfaceBuilders.put("minecraft:dirt_config", SurfaceBuilder.DIRT_CONFIG);
        surfaceBuilders.put("minecraft:stone_config", SurfaceBuilder.STONE_CONFIG);
        surfaceBuilders.put("minecraft:coarse_dirt_config", SurfaceBuilder.COARSE_DIRT_CONFIG);
        surfaceBuilders.put("minecraft:sand_config", SurfaceBuilder.SAND_CONFIG);
        surfaceBuilders.put("minecraft:grass_sand_underwater_config", SurfaceBuilder.GRASS_SAND_UNDERWATER_CONFIG);
        surfaceBuilders.put("minecraft:sand_sand_underwater_config", SurfaceBuilder.SAND_SAND_UNDERWATER_CONFIG);
        surfaceBuilders.put("minecraft:badlands_config", SurfaceBuilder.BADLANDS_CONFIG);
        surfaceBuilders.put("minecraft:mycelium_config", SurfaceBuilder.MYCELIUM_CONFIG);
        surfaceBuilders.put("minecraft:nether_config", SurfaceBuilder.NETHER_CONFIG);
        surfaceBuilders.put("minecraft:soul_sand_config", SurfaceBuilder.SOUL_SAND_CONFIG);
        surfaceBuilders.put("minecraft:end_config", SurfaceBuilder.END_CONFIG);
        surfaceBuilders.put("minecraft:crimson_nylium_config", SurfaceBuilder.CRIMSON_NYLIUM_CONFIG);
        surfaceBuilders.put("minecraft:warped_nylium_config", SurfaceBuilder.WARPED_NYLIUM_CONFIG);
        return Rands.map(surfaceBuilders).getValue();
    }

    public static TernarySurfaceConfig fromIdentifierToConfig(Identifier name) {
        if (name.equals(new Identifier("gravel_config"))) return SurfaceBuilder.GRAVEL_CONFIG;
        if (name.equals(new Identifier("grass_config"))) return SurfaceBuilder.GRASS_CONFIG;
        if (name.equals(new Identifier("dirt_config"))) return SurfaceBuilder.DIRT_CONFIG;
        if (name.equals(new Identifier("stone_config"))) return SurfaceBuilder.STONE_CONFIG;
        if (name.equals(new Identifier("coarse_dirt_config"))) return SurfaceBuilder.COARSE_DIRT_CONFIG;
        if (name.equals(new Identifier("sand_config"))) return SurfaceBuilder.SAND_CONFIG;
        if (name.equals(new Identifier("grass_sand_underwater_config"))) return SurfaceBuilder.GRASS_SAND_UNDERWATER_CONFIG;
        if (name.equals(new Identifier("sand_sand_underwater_config"))) return SurfaceBuilder.SAND_SAND_UNDERWATER_CONFIG;
        if (name.equals(new Identifier("badlands_config"))) return SurfaceBuilder.BADLANDS_CONFIG;
        if (name.equals(new Identifier("mycelium_config"))) return SurfaceBuilder.MYCELIUM_CONFIG;
        if (name.equals(new Identifier("nether_config"))) return SurfaceBuilder.NETHER_CONFIG;
        if (name.equals(new Identifier("soul_sand_config"))) return SurfaceBuilder.SOUL_SAND_CONFIG;
        if (name.equals(new Identifier("end_config"))) return SurfaceBuilder.END_CONFIG;

        return SurfaceBuilder.GRASS_CONFIG;
    }

    public static Identifier fromConfigToIdentifier(TernarySurfaceConfig config) {
        if (config.equals(SurfaceBuilder.GRAVEL_CONFIG)) return new Identifier("gravel_config");

        if (config.equals(SurfaceBuilder.GRASS_CONFIG)) return new Identifier("grass_config");
        if (config.equals(SurfaceBuilder.DIRT_CONFIG)) return new Identifier("dirt_config");
        if (config.equals(SurfaceBuilder.STONE_CONFIG)) return new Identifier("stone_config");
        if (config.equals(SurfaceBuilder.COARSE_DIRT_CONFIG)) return new Identifier("coarse_dirt_config");
        if (config.equals(SurfaceBuilder.SAND_CONFIG)) return new Identifier("sand_config");
        if (config.equals(SurfaceBuilder.GRASS_SAND_UNDERWATER_CONFIG)) return new Identifier("grass_sand_underwater_config");
        if (config.equals(SurfaceBuilder.SAND_SAND_UNDERWATER_CONFIG)) return new Identifier("sand_sand_underwater_config");
        if (config.equals(SurfaceBuilder.BADLANDS_CONFIG)) return new Identifier("badlands_config");
        if (config.equals(SurfaceBuilder.MYCELIUM_CONFIG)) return new Identifier("mycelium_config");
        if (config.equals(SurfaceBuilder.NETHER_CONFIG)) return new Identifier("nether_config");
        if (config.equals(SurfaceBuilder.SOUL_SAND_CONFIG)) return new Identifier("soul_sand_config");
        if (config.equals(SurfaceBuilder.END_CONFIG)) return new Identifier("end_config");
        if (config.equals(SurfaceBuilder.CRIMSON_NYLIUM_CONFIG)) return new Identifier("crimson_nylium_config");
        if (config.equals(SurfaceBuilder.WARPED_NYLIUM_CONFIG)) return new Identifier("warped_nylium_config");

        return new Identifier("grass_config");
    }

    public static SurfaceBuilder<?> newRandomSurfaceBuilder() {
        //random surface builder
        if (Rands.chance(2)) {
            return SurfaceBuilderGenerator.RANDOM_SURFACE_BUILDER.getRandom(Rands.getRandom());
        }

        //choose the default type of surface builder
        SurfaceBuilder<?> sb = Registry.SURFACE_BUILDER.get(new Identifier(Rands.list(surfaceBuilders)));
        if (sb == null) { //dunno why it's null sometimes, but if it is, default to the default
            sb = SurfaceBuilder.DEFAULT; //TODO: fix this instead of a hack
        }
        return sb;
    }

    public static DimensionChunkGenerators randomCG(int chance) {
        //enable for testing
        if (false) {
            return DimensionChunkGenerators.TEST;
        }

        if (chance < 15) {
            if (chance <= 5) {
                return DimensionChunkGenerators.FLAT_CAVES;
            } else if (chance <= 10) {
                return DimensionChunkGenerators.HIGH_CAVES;
            }
            return DimensionChunkGenerators.CAVES;
        } else if (chance > 15 && chance < 30) {
            if (chance <= 20) {
                return DimensionChunkGenerators.LAYERED_FLOATING;
            } else if (chance <= 25) {
                return DimensionChunkGenerators.PRE_CLASSIC_FLOATING;
            }
            return DimensionChunkGenerators.FLOATING;
        } else {
            if (chance <= 35) {
                return DimensionChunkGenerators.QUADRUPLE_AMPLIFIED;
            } else if (chance <= 40) {
                return DimensionChunkGenerators.PILLAR_WORLD;
            } else if (chance <= 50) {
                return DimensionChunkGenerators.LAYERED_OVERWORLD;
            } else if (chance <= 60) {
                return DimensionChunkGenerators.TOTALLY_CUSTOM;
            } else if (chance <= 70) {
                return DimensionChunkGenerators.SMOOTH_OVERWORLD;
            } else if (chance <= 80) {
                return DimensionChunkGenerators.CHAOS;
            } else if (chance <= 90) {
                return DimensionChunkGenerators.ROLLING_HILLS;
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

    public static void createSpawnsFile(String name, WorldAccess world, BlockPos pos) {
        /*try {
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
        }*/
    }

}
