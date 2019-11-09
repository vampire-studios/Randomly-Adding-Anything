package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.PlayerPlacementHandlers;
import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;
import io.github.vampirestudios.raa.blocks.DimensionalBlock;
import io.github.vampirestudios.raa.blocks.PortalBlock;
import io.github.vampirestudios.raa.generation.dimensions.*;
import io.github.vampirestudios.raa.utils.*;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensionType;
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

    public static boolean isReady = false;

    public static void init() {
        for (int a = 0; a < RandomlyAddingAnything.CONFIG.dimensionNumber; a++) {
            int flags = generateDimensionFlags();
            float hue = Rands.randFloatRange(0, 1.0F);
            float foliageColor = hue + Rands.randFloatRange(0.01F, 0.1F);
            float fogHue = hue + 0.3333f;
            float skyHue = fogHue + 0.3333f;

            float waterHue = hue + 0.3333f;
            float stoneHue = hue + 0.3333f;

            float saturation = Rands.randFloatRange(0.5F, 1.0F);
            if (Utils.checkBitFlag(flags, Utils.DEAD)) saturation = Rands.randFloatRange(0.0F, 0.2F);
            if (Utils.checkBitFlag(flags, Utils.LUSH)) saturation = Rands.randFloatRange(0.7F, 1.0F);
            float value = Rands.randFloatRange(0.5F, 1.0F);
            Color GRASS_COLOR = new Color(Color.HSBtoRGB(hue, saturation, value));
            Color FOLIAGE_COLOR = new Color(Color.HSBtoRGB(foliageColor, saturation, value));
            Color FOG_COLOR = new Color(Color.HSBtoRGB(fogHue, saturation, value));
            Color SKY_COLOR = new Color(Color.HSBtoRGB(skyHue, saturation, value));
            Color WATER_COLOR = new Color(Color.HSBtoRGB(Rands.randFloatRange(0.0F, 1.0F), saturation, Rands.randFloatRange(0.5F, 1.0F)));
            Color STONE_COLOR = new Color(Color.HSBtoRGB(foliageColor, saturation, value));

            INameGenerator nameGenerator = RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionNameGenerator();
            Pair<String, Identifier> name = nameGenerator.generateUnique(DIMENSION_NAMES, RandomlyAddingAnything.MOD_ID);
            if (!DIMENSION_NAMES.contains(name.getRight()))
                DIMENSION_NAMES.add(name.getRight());

            DimensionData.Builder builder = DimensionData.Builder.create(name.getRight(), name.getLeft())
                .dimensionId(Rands.randIntRange(1000, 30000))
                .hasLight(Rands.chance(1))
                .hasSky(!Rands.chance(2))
                .canSleep(Rands.chance(10))
                .doesWaterVaporize(Rands.chance(100))
                .shouldRenderFog(Rands.chance(100))
                .chunkGenerator(Utils.randomCG(Rands.randIntRange(0, 100)))
				.flags(flags)
				.mobs(generateDimensionMobs());
            DimensionBiomeData biomeData = DimensionBiomeData.Builder.create(Utils.append(name.getRight(), "_biome"), name.getLeft())
                .surfaceBuilderVariantChance(Rands.randInt(100))
                .depth(Rands.randFloatRange(-1F, 3F))
                .scale(Rands.randFloat(2F))
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
                .stoneColor(FOLIAGE_COLOR.getColor()).build();
            builder.colorPalette(colorPalette);

            DimensionData dimensionData = builder.build();

            Registry.register(DIMENSIONS, dimensionData.getId(), dimensionData);
            Registry.register(Registry.BIOME, dimensionData.getId(), new CustomDimensionalBiome(dimensionData));

            // Debug Only
            if (RandomlyAddingAnything.CONFIG.debug) {
                DebugUtils.dimensionDebug(dimensionData);
            }
        }
        isReady = true;
    }

    public static boolean isReady() {
        return isReady;
    }

    public static void createDimensions() {
        DIMENSIONS.forEach(dimension -> {
            CustomDimensionalBiome biome = new CustomDimensionalBiome(dimension);
            DimensionType type = FabricDimensionType.builder()
                .biomeAccessStrategy(HorizontalVoronoiBiomeAccessType.INSTANCE)
                .desiredRawId(dimension.getDimensionId())
                .skyLight(dimension.hasSkyLight())
                .factory((world, dimensionType) -> new CustomDimension(world, dimensionType, dimension, biome))
                .defaultPlacer(PlayerPlacementHandlers.SURFACE_WORLD.getEntityPlacer())
                .buildAndRegister(dimension.getId());
            DimensionType dimensionType = null;
            if (Registry.DIMENSION_TYPE.get(dimension.getId()) == null)
                dimensionType = Registry.register(Registry.DIMENSION_TYPE, dimension.getId(), type);
            else
                dimensionType = Registry.DIMENSION_TYPE.get(dimension.getId());

            RegistryUtils.register(new DimensionalBlock(), new Identifier(RandomlyAddingAnything.MOD_ID, dimension.getName().toLowerCase() + "_stone"),
                    RandomlyAddingAnything.RAA_DIMENSION_BLOCKS, dimension.getName(), "stone");
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

    public static HashMap<String, int[]> generateDimensionMobs() {
        HashMap<String, int[]> list = new HashMap<>();
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 12);
            list.put("cow", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 12);
            list.put("pig", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 12);
            list.put("chicken", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 8);
            list.put("horse", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 8);
            list.put("donkey", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 12);
            list.put("sheep", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 8);
            list.put("llama", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 12);
            list.put("bat", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 8);
            list.put("spider", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 12);
            list.put("zombie", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 4);
            list.put("zombie_villager", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+1});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 12);
            list.put("skeleton", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 8);
            list.put("creeper", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 4);
            list.put("slime", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize+Rands.randIntRange(2, 4)});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 4);
            list.put("enderman", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize});
        }
        if (Rands.chance(2))  {
            int spawnSize = Rands.randIntRange(2, 3);
            list.put("cow", new int[]{Rands.randIntRange(1, 300), spawnSize, spawnSize});
        }
        return list;
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