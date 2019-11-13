package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.DimensionChunkGenerators;
import io.github.vampirestudios.raa.api.enums.PlayerPlacementHandlers;
import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;
import io.github.vampirestudios.raa.blocks.DimensionalBlock;
import io.github.vampirestudios.raa.blocks.PortalBlock;
import io.github.vampirestudios.raa.generation.dimensions.*;
import io.github.vampirestudios.raa.history.Civilization;
import io.github.vampirestudios.raa.history.ProtoDimension;
import io.github.vampirestudios.raa.utils.*;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensionType;
import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.HorizontalVoronoiBiomeAccessType;
import net.minecraft.world.dimension.DimensionType;

import java.util.*;

public class Dimensions {
    public static final Set<Identifier> DIMENSION_NAMES = new HashSet<>();
    public static final Registry<DimensionData> DIMENSIONS = new DefaultedRegistry<>("raa:dimensions");

    public static boolean ready = false;

    public static void generate() {
        //pre generation of dimensions: basic data, flags, and name
        //This is only the data needed for civilization simulation
        ArrayList<ProtoDimension> protoDimensions = new ArrayList<>();
        for (int a = 0; a < RandomlyAddingAnything.CONFIG.dimensionNumber; a++) {
            float temperature = Rands.randFloat(2.0F);
            int flags = generateDimensionFlags();

            INameGenerator nameGenerator = RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionNameGenerator();
            Pair<String, Identifier> name = nameGenerator.generateUnique(DIMENSION_NAMES, RandomlyAddingAnything.MOD_ID);
            DIMENSION_NAMES.add(name.getRight());

            protoDimensions.add(new ProtoDimension(name, flags, temperature, Rands.randFloat(2F)));
        }

        for (ProtoDimension dimension : protoDimensions) {
            dimension.setXandY(Rands.randFloatRange(0, 1), Rands.randFloatRange(0, 1));
        }

        //perform the civilization handling

        //generate the civilizations
        ArrayList<Civilization> civs = new ArrayList<>();
        Set<Identifier> civNames = new HashSet<>();
        Set<ProtoDimension> usedDimensions = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            INameGenerator nameGenerator = RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionNameGenerator();
            Pair<String, Identifier> name = nameGenerator.generateUnique(civNames, RandomlyAddingAnything.MOD_ID);
            civNames.add(name.getRight());
            ProtoDimension generatedDimension = Rands.list(protoDimensions);
            if (usedDimensions.contains(generatedDimension)) continue;
            else usedDimensions.add(generatedDimension);
            civs.add(new Civilization(name.getLeft(), generatedDimension));
        }

        //tick the civs and get their influence
        civs.forEach(Civilization::simulate);

        for (Civilization civ : civs) {
//            System.out.println("++++++++++++++++++++++++++++");
//            System.out.println(civ.getName());
//            System.out.println("r: " + civ.getInfluenceRadius());
//            System.out.println("l: " +  civ.getTechLevel());

            //tech level 0 civs get no influence
            if (civ.getTechLevel() == 0) continue;

//            System.out.println("=== " + civ.getName() + " ===");
            for (ProtoDimension dimension : protoDimensions) {
                if (dimension != civ.getHomeDimension()) {
                    double d = Utils.dist(dimension.getX(), dimension.getY(), civ.getHomeDimension().getX(), civ.getHomeDimension().getY());
                    if (d <= civ.getInfluenceRadius()) {
                        double percent = (civ.getInfluenceRadius() - d)/civ.getInfluenceRadius();
                        dimension.addInfluence(civ.getName(), percent);
                        if (percent > 0.40) {
                            if (civ.getTechLevel() >= 2) if (Rands.chance(5)) dimension.setAbandoned();
                        }
                        if (percent > 0.60) {
                            if (civ.getTechLevel() >= 2) if (Rands.chance(4)) dimension.setAbandoned();
                            if (civ.getTechLevel() >= 3) if (Rands.chance(5)) dimension.setDead();
                        }
                        if (percent > 0.80) {
                            if (civ.getTechLevel() >= 2) if (Rands.chance(3)) dimension.setAbandoned();
                            if (civ.getTechLevel() >= 3) if (Rands.chance(4)) dimension.setDead();
                        }
                        if (percent > 0.70) {
                            if (civ.getTechLevel() >= 3) dimension.setCivilized();
                        }
//                        System.out.println(dimension.getName().getLeft() + ": " + (int)Math.ceil(((civ.getInfluenceRadius() - d)/civ.getInfluenceRadius())*100) +"%");
                    }
                } else {
                    //a civ's home dimension has 100% influence by that civ
                    dimension.addInfluence(civ.getName(), 1.0);
                }

                //Ensure that both dead and lush flags don't coexist
                if (Utils.checkBitFlag(dimension.getFlags(), Utils.DEAD) && Utils.checkBitFlag(dimension.getFlags(), Utils.LUSH)) dimension.removeLush();
            }
        }

        //post generation of dimensions: do everything to actually register the dimension
        for (ProtoDimension dimension : protoDimensions) {
            int difficulty = 0;
            int flags = dimension.getFlags();
            Pair<String, Identifier> name = dimension.getName();
            float hue = Rands.randFloatRange(0, 1.0F);
            float foliageColor = hue + Rands.randFloatRange(-0.15F, 0.15F);
            float stoneColor = hue + Rands.randFloatRange(-0.45F, 0.45F);
            float fogHue = hue + 0.3333f;
            float skyHue = fogHue + 0.3333f;

            float waterHue = hue + 0.3333f;
            float stoneHue = hue + 0.3333f;

            float saturation = Rands.randFloatRange(0.5F, 1.0F);
            float stoneSaturation = Rands.randFloatRange(0.2F, 1.0F);
            if (Utils.checkBitFlag(flags, Utils.DEAD)) {
                saturation = Rands.randFloatRange(0.0F, 0.2F);
                stoneSaturation = saturation;
                difficulty+=2;
                if (Utils.checkBitFlag(flags, Utils.CIVILIZED)) difficulty++;
            }
            if (Utils.checkBitFlag(flags, Utils.LUSH)) saturation = Rands.randFloatRange(0.7F, 1.0F);
            if (Utils.checkBitFlag(flags, Utils.CORRUPTED)) difficulty+=2;
            if (Utils.checkBitFlag(flags, Utils.MOLTEN)) difficulty+=2;
            if (Utils.checkBitFlag(flags, Utils.DRY)) difficulty+=2;
            if (Utils.checkBitFlag(flags, Utils.TECTONIC)) difficulty++;
            float value = Rands.randFloatRange(0.5F, 1.0F);
            Color GRASS_COLOR = new Color(Color.HSBtoRGB(hue, saturation, value));
            Color FOLIAGE_COLOR = new Color(Color.HSBtoRGB(foliageColor, saturation, value));
            Color FOG_COLOR = new Color(Color.HSBtoRGB(fogHue, saturation, value));
            Color SKY_COLOR = new Color(Color.HSBtoRGB(skyHue, saturation, value));
            Color WATER_COLOR = new Color(Color.HSBtoRGB(Rands.randFloatRange(0.0F, 1.0F), saturation, Rands.randFloatRange(0.5F, 1.0F)));
            Color STONE_COLOR = new Color(Color.HSBtoRGB(stoneColor, stoneSaturation, value));



            Pair<Integer, HashMap<String, int[]>> difficultyAndMobs = generateDimensionMobs(flags, difficulty);
            DimensionChunkGenerators gen = Utils.randomCG(Rands.randIntRange(0, 100));
            if (gen == DimensionChunkGenerators.FLOATING) difficulty++;
            if (gen == DimensionChunkGenerators.CAVE) difficulty+=2;
            float scale = dimension.getScale();
            float depth = Rands.randFloatRange(-1F, 3F);
            if (depth < -0.5F) difficulty++;
            if (scale > 0.8) difficulty++;
            if (scale > 1.6) difficulty++;

            DimensionData.Builder builder = DimensionData.Builder.create(name.getRight(), name.getLeft())
                .hasSkyLight(Rands.chance(1))
                .hasSky(!Rands.chance(2))
                .canSleep(Rands.chance(10))
                .waterVaporize(Rands.chance(100))
                .shouldRenderFog(Rands.chance(100))
                .chunkGenerator(gen)
				.flags(flags)
                .difficulty(difficultyAndMobs.getLeft())
				.mobs(difficultyAndMobs.getRight())
                .setCivilizationInfluences(dimension.getCivilizationInfluences())
                .setSurfaceBuilder(Rands.randInt(100));
            DimensionBiomeData biomeData = DimensionBiomeData.Builder.create(Utils.append(name.getRight(), "_biome"), name.getLeft())
                .surfaceBuilderVariantChance(Rands.randInt(100))
                .depth(depth)
                .scale(scale)
                .temperature(dimension.getTemperature())
                .downfall(Rands.randFloat(1F))
                .waterColor(WATER_COLOR.getColor())
                .build();
            builder.biome(biomeData);
            DimensionColorPalette colorPalette = DimensionColorPalette.Builder.create()
                .skyColor(SKY_COLOR.getColor())
                .grassColor(GRASS_COLOR.getColor())
                .fogColor(FOG_COLOR.getColor())
                .foliageColor(FOLIAGE_COLOR.getColor())
                .stoneColor(STONE_COLOR.getColor()).build();
            builder.colorPalette(colorPalette);

            DimensionData dimensionData = builder.build();

            Registry.register(DIMENSIONS, dimensionData.getId(), dimensionData);
            Registry.register(Registry.BIOME, dimensionData.getId(), new CustomDimensionalBiome(dimensionData));

            // Debug Only
            if (RandomlyAddingAnything.CONFIG.debug) {
                DebugUtils.dimensionDebug(dimensionData);
            }
        }
        ready = true;
    }

    public static boolean isReady() {
        return ready;
    }

    public static void createDimensions() {
        DIMENSIONS.forEach(dimension -> {
            CustomDimensionalBiome biome = new CustomDimensionalBiome(dimension);
            Block stoneBlock = RegistryUtils.register(new DimensionalBlock(), new Identifier(RandomlyAddingAnything.MOD_ID, dimension.getName().toLowerCase() + "_stone"),
                    RandomlyAddingAnything.RAA_DIMENSION_BLOCKS, dimension.getName(), "stone");
            DimensionType type = FabricDimensionType.builder()
                .biomeAccessStrategy(HorizontalVoronoiBiomeAccessType.INSTANCE)
                .skyLight(dimension.hasSkyLight())
                .factory((world, dimensionType) -> new CustomDimension(world, dimensionType, dimension, biome, stoneBlock))
                .defaultPlacer(PlayerPlacementHandlers.SURFACE_WORLD.getEntityPlacer())
                .buildAndRegister(dimension.getId());
            DimensionType dimensionType;
            if (Registry.DIMENSION.get(dimension.getId()) == null)
                dimensionType = Registry.register(Registry.DIMENSION, dimension.getId(), type);
            else
                dimensionType = Registry.DIMENSION.get(dimension.getId());


            RegistryUtils.register(new DimensionalBlock(), new Identifier(RandomlyAddingAnything.MOD_ID, dimension.getName().toLowerCase() + "_stone_bricks"),
                    RandomlyAddingAnything.RAA_DIMENSION_BLOCKS, dimension.getName(), "stoneBricks");
            RegistryUtils.register(new DimensionalBlock(), new Identifier(RandomlyAddingAnything.MOD_ID, dimension.getName().toLowerCase() + "_cobblestone"),
                    RandomlyAddingAnything.RAA_DIMENSION_BLOCKS, dimension.getName(), "cobblestone");
            RegistryUtils.register(new DimensionalBlock(), new Identifier(RandomlyAddingAnything.MOD_ID, "chiseled_" + dimension.getName().toLowerCase()),
                    RandomlyAddingAnything.RAA_DIMENSION_BLOCKS, dimension.getName(), "chiseled");
            RegistryUtils.register(new DimensionalBlock(), new Identifier(RandomlyAddingAnything.MOD_ID, "polished_" + dimension.getName().toLowerCase()),
                    RandomlyAddingAnything.RAA_DIMENSION_BLOCKS, dimension.getName(), "polished");
            RegistryUtils.register(new PortalBlock(dimension, dimensionType), new Identifier(RandomlyAddingAnything.MOD_ID, dimension.getName().toLowerCase() + "_portal"),
                    ItemGroup.TRANSPORTATION);
        });
    }

    public static Pair<Integer, HashMap<String, int[]>> generateDimensionMobs(int flags, int difficulty) {
        HashMap<String, int[]> list = new HashMap<>();
        if (Utils.checkBitFlag(flags, Utils.LUSH)) {
            String[] names = new String[]{"cow", "pig", "chicken", "horse", "donkey","sheep","llama"};
            for (String name : names) {
                int spawnSize = Rands.randIntRange(4, 16);
                list.put(name, new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize + Rands.randIntRange(2, 8)});
            }
        } else {
            if (!Utils.checkBitFlag(flags, Utils.DEAD)) {
                if (Rands.chance(2)) {
                    int spawnSize = Rands.randIntRange(2, 12);
                    list.put("cow", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize + Rands.randIntRange(2, 4)});
                } else {
                    difficulty++;
                }
                if (Rands.chance(2)) {
                    int spawnSize = Rands.randIntRange(2, 12);
                    list.put("pig", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize + Rands.randIntRange(2, 4)});
                } else {
                    difficulty++;
                }
                if (Rands.chance(2)) {
                    int spawnSize = Rands.randIntRange(2, 12);
                    list.put("chicken", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize + Rands.randIntRange(2, 4)});
                } else {
                    difficulty++;
                }
                if (Rands.chance(2)) {
                    int spawnSize = Rands.randIntRange(2, 8);
                    list.put("horse", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize + Rands.randIntRange(2, 4)});
                }
                if (Rands.chance(2)) {
                    int spawnSize = Rands.randIntRange(2, 8);
                    list.put("donkey", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize + Rands.randIntRange(2, 4)});
                }
                if (Rands.chance(2)) {
                    int spawnSize = Rands.randIntRange(2, 12);
                    list.put("sheep", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize + Rands.randIntRange(2, 4)});
                } else {
                    difficulty++;
                }
                if (Rands.chance(2)) {
                    int spawnSize = Rands.randIntRange(2, 8);
                    list.put("llama", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize + Rands.randIntRange(2, 4)});
                }
            } else {
                difficulty+=4;
            }
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 12);
            list.put("bat", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 8);
            list.put("spider", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        } else {
            difficulty--;
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 12);
            list.put("zombie", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        } else {
            difficulty--;
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 4);
            list.put("zombie_villager", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+1});
        } else {
            --difficulty;
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 12);
            list.put("skeleton", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        } else {
            difficulty--;
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 8);
            list.put("creeper", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        } else {
            difficulty-=2;
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 4);
            list.put("slime", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        } else {
            difficulty--;
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 4);
            list.put("enderman", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize});
        } else {
            difficulty-=2;
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 3);
            list.put("witch", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize});
        } else {
            difficulty-=2;
        }
        return new Pair<>(difficulty, list);
    }

    public static int generateDimensionFlags() {
        int flags = 0;
        if (Rands.chance(30)) {
            flags = Utils.POST_APOCALYPTIC;
            return flags;
        }
        if (Rands.chance(20)) {
            flags |= Utils.CORRUPTED;
            if (Rands.chance(5)) {
                flags |= Utils.DEAD;
            }
            if (Rands.chance(3)) {
                flags |= Utils.MOLTEN;
            }
            if (Rands.chance(4)) {
                flags |= Utils.DRY;
            }
        } else {
            if (Rands.chance(8)) {
                flags |= Utils.DEAD;
                if (Rands.chance(5)) {
                    flags |= Utils.MOLTEN;
                }
                if (Rands.chance(5)) {
                    flags |= Utils.DRY;
                }
            } else {
                if (Rands.chance(4)) {
                    flags |= Utils.LUSH;
                }
            }
        }
        if (Rands.chance(10)) {
            flags |= Utils.TECTONIC;
        }
        return flags;
    }
}