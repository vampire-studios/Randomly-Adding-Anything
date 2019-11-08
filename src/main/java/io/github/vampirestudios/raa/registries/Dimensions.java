package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.PlayerPlacementHandlers;
import io.github.vampirestudios.raa.api.namegeneration.INameGenerator;
import io.github.vampirestudios.raa.blocks.PortalBlock;
import io.github.vampirestudios.raa.generation.dimensions.*;
import io.github.vampirestudios.raa.utils.*;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensionType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.HorizontalVoronoiBiomeAccessType;
import net.minecraft.world.dimension.DimensionType;

import java.util.HashSet;
import java.util.Set;

public class Dimensions {
    public static final Set<Identifier> DIMENSION_NAMES = new HashSet<>();
    public static final Registry<DimensionData> DIMENSIONS = new DefaultedRegistry<>("raa:dimensions");

    public static boolean isReady = false;

    public static void init() {
        for (int a = 0; a < RandomlyAddingAnything.CONFIG.dimensionNumber; a++) {
            float hue = Rands.randFloatRange(0, 1.0F);
            float foliageColor = hue + 0.05F;
            float fogHue = hue + 0.3333f;
            float skyHue = fogHue + 0.3333f;

            float waterHue = hue + 0.3333f;
            float stoneHue = hue + 0.3333f;

            float saturation = Rands.randFloatRange(0.5F, 1.0F);
            float value = Rands.randFloatRange(0.5F, 1.0F);
            Color GRASS_COLOR = new Color(Color.HSBtoRGB(hue, saturation, value));
            Color FOLIAGE_COLOR = new Color(Color.HSBtoRGB(foliageColor, saturation, value));
            Color FOG_COLOR = new Color(Color.HSBtoRGB(fogHue, saturation, value));
            Color SKY_COLOR = new Color(Color.HSBtoRGB(skyHue, saturation, value));
            Color WATER_COLOR = new Color(Color.HSBtoRGB(Rands.randFloatRange(0.0F, 1.0F), Rands.randFloatRange(0.5F, 1.0F), Rands.randFloatRange(0.5F, 1.0F)));
            Color STONE_COLOR = new Color(Color.HSBtoRGB(foliageColor, saturation, value));

            INameGenerator nameGenerator = RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionNameGenerator();
            Pair<String, Identifier> name = nameGenerator.generateUnique(DIMENSION_NAMES, RandomlyAddingAnything.MOD_ID);
            DIMENSION_NAMES.add(name.getRight());

            DimensionDataBuilder dimensionDataBuilder = DimensionDataBuilder.create(name.getRight(), name.getLeft())
                .dimensionId(Rands.randIntRange(1000, 30000))
                .hasLight(Rands.chance(1))
                .hasSky(!Rands.chance(2))
                .canSleep(Rands.chance(10))
                .doesWaterVaporize(Rands.chance(100))
                .shouldRenderFog(Rands.chance(100))
                .chunkGenerator(Utils.randomCG(Rands.randIntRange(0, 100)))
                .isCorrupted(Rands.chance(20));
            DimensionBiomeData biomeData = DimensionBiomeDataBuilder.create(Utils.append(name.getRight(), "_biome"), name.getLeft())
                .surfaceBuilderVariantChance(Rands.randInt(100))
                .depth(Rands.randFloatRange(-3F, 3F))
                .scale(Rands.randFloat(2F))
                .temperature(Rands.randFloat(2.0F))
                .downfall(Rands.randFloat(1F))
                .waterColor(WATER_COLOR.getColor())
                .build();
            dimensionDataBuilder.biome(biomeData);
            DimensionColorPallet colorPallet = DimensionColorPalletBuilder.create()
                .skyColor(SKY_COLOR.getColor())
                .grassColor(GRASS_COLOR.getColor())
                .fogColor(FOG_COLOR.getColor())
                .foliageColor(FOLIAGE_COLOR.getColor())
                .stoneColor(FOLIAGE_COLOR.getColor()).build();
            dimensionDataBuilder.colorPallet(colorPallet);

            DimensionData dimensionData = dimensionDataBuilder.build();

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
            Identifier biomeId = dimension.getBiomeData().getId();
            DimensionType type = FabricDimensionType.builder()
                .biomeAccessStrategy(HorizontalVoronoiBiomeAccessType.INSTANCE)
                .desiredRawId(dimension.getDimensionId())
                .skyLight(dimension.hasSkyLight())
                .factory((world, dimensionType) -> new CustomDimension(world, dimensionType, dimension, biome))
                .defaultPlacer(PlayerPlacementHandlers.SURFACE_WORLD.getEntityPlacer())
                .buildAndRegister(dimension.getId());
            DimensionType dimensionType = null;
            if (Registry.DIMENSION.get(dimension.getId()) == null)
                dimensionType = Registry.register(Registry.DIMENSION, dimension.getId(), type);

            RegistryUtils.register(new Block(Block.Settings.copy(Blocks.STONE)), Utils.append(dimension.getId(), "_stone"), ItemGroup.BUILDING_BLOCKS);
            RegistryUtils.register(new PortalBlock(dimensionType), Utils.append(dimension.getId(), "_portal"), ItemGroup.TRANSPORTATION);
        });
    }

}