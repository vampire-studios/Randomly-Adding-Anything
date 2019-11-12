package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.DimensionChunkGenerators;
import io.github.vampirestudios.raa.api.enums.PlayerPlacementHandlers;
import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;
import io.github.vampirestudios.raa.blocks.DimensionalBlock;
import io.github.vampirestudios.raa.blocks.PortalBlock;
import io.github.vampirestudios.raa.generation.dimensions.*;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Dimensions {
    public static final Set<Identifier> DIMENSION_NAMES = new HashSet<>();
    public static final Registry<DimensionData> DIMENSIONS = new DefaultedRegistry<>("raa:dimensions");

    public static boolean ready = false;

    public static void generate() {
        for (int a = 0; a < RandomlyAddingAnything.CONFIG.dimensionNumber; a++) {
            int difficulty = 0;
            int flags = generateDimensionFlags();
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
            }
            if (Utils.checkBitFlag(flags, Utils.LUSH)) saturation = Rands.randFloatRange(0.7F, 1.0F);
            if (Utils.checkBitFlag(flags, Utils.CORRUPTED)) difficulty+=2;
            if (Utils.checkBitFlag(flags, Utils.MOLTEN)) difficulty++;
            if (Utils.checkBitFlag(flags, Utils.DRY)) difficulty++;
            if (Utils.checkBitFlag(flags, Utils.TECTONIC)) difficulty++;
            float value = Rands.randFloatRange(0.5F, 1.0F);
            Color GRASS_COLOR = new Color(Color.HSBtoRGB(hue, saturation, value));
            Color FOLIAGE_COLOR = new Color(Color.HSBtoRGB(foliageColor, saturation, value));
            Color FOG_COLOR = new Color(Color.HSBtoRGB(fogHue, saturation, value));
            Color SKY_COLOR = new Color(Color.HSBtoRGB(skyHue, saturation, value));
            Color WATER_COLOR = new Color(Color.HSBtoRGB(Rands.randFloatRange(0.0F, 1.0F), saturation, Rands.randFloatRange(0.5F, 1.0F)));
            Color STONE_COLOR = new Color(Color.HSBtoRGB(stoneColor, stoneSaturation, value));

            INameGenerator nameGenerator = RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionNameGenerator();
            Pair<String, Identifier> name = nameGenerator.generateUnique(DIMENSION_NAMES, RandomlyAddingAnything.MOD_ID);
            if (!DIMENSION_NAMES.contains(name.getRight()))
                DIMENSION_NAMES.add(name.getRight());

            Pair<Integer, HashMap<String, int[]>> difficultyAndMobs = generateDimensionMobs(flags, difficulty);
            DimensionChunkGenerators gen = Utils.randomCG(Rands.randIntRange(0, 100));
            if (gen == DimensionChunkGenerators.FLOATING) difficulty++;
            if (gen == DimensionChunkGenerators.CAVE) difficulty+=2;
            float scale = Rands.randFloat(2F);
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
				.mobs(difficultyAndMobs.getRight());
            DimensionBiomeData biomeData = DimensionBiomeData.Builder.create(Utils.append(name.getRight(), "_biome"), name.getLeft())
                .surfaceBuilderVariantChance(Rands.randInt(100))
                .depth(depth)
                .scale(scale)
                .temperature(Rands.randFloat(2.0F))
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
                .desiredRawId(dimension.getDimensionId())
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
            if (Rands.chance(4)) {
                flags |= Utils.ABANDONED;
            }
            if (Rands.chance(5)) {
                flags |= Utils.DEAD;
            }
            if (Rands.chance(3)) {
                flags |= Utils.MOLTEN;
            }
            if (Rands.chance(5)) {
                flags |= Utils.DRY;
            }
        } else {
            if (Rands.chance(5)) {
                flags |= Utils.DEAD;
                if (Rands.chance(3)) {
                    flags |= Utils.ABANDONED;
                }
                if (Rands.chance(3)) {
                    flags |= Utils.MOLTEN;
                }
                if (Rands.chance(5)) {
                    flags |= Utils.DRY;
                }
            } else {
                if (Rands.chance(4)) {
                    flags |= Utils.LUSH;
                }
                if (Rands.chance(8)) {
                    flags |= Utils.CIVILIZED;
                } else {
                    if (Rands.chance(4)) {
                        flags |= Utils.ABANDONED;
                    }
                }
            }
        }
        if (Rands.chance(15)) {
            flags |= Utils.TECTONIC;
        }
        return flags;
    }
}