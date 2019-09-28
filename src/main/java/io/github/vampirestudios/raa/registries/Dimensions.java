package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.utils.Color;
import io.github.vampirestudios.raa.generation.dimensions.*;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.RegistryUtils;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensionType;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.class_4547;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.DimensionType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Dimensions {
    public static final List<Identifier> DIMENSION_NAME_LIST = new ArrayList<>();
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
            Color WATER_COLOR = new Color(Color.HSBtoRGB(waterHue, saturation, value));
            Color STONE_COLOR = new Color(Color.HSBtoRGB(stoneHue, saturation, value));
            DimensionBiomeData biomeData = DimensionBiomeBuilder.create()
                    .surfaceBuilderVariantChance(Rands.randInt(100))
                    .depth(Rands.randFloatRange(-0.75F, 3F))
                    .scale(Rands.randFloat(2F))
                    .temperature(Rands.randFloat(1F))
                    .downfall(Rands.randFloat(1F))
                    .waterColor(WATER_COLOR.getColor()).build();
            DimensionData dimensionData = DimensionBuilder.create()
                    .fogColor(FOG_COLOR.getColor()).grassColor(GRASS_COLOR.getColor()).foliageColor(FOLIAGE_COLOR.getColor())
                    .hasLight(Rands.chance(1)).name(RandomlyAddingAnything.CONFIG.namingLanguage.generateDimensionNames())
                    .hasSky(!Rands.chance(2)).canSleep(Rands.chance(10)).doesWaterVaporize(Rands.chance(100))
                    .shouldRenderFog(Rands.chance(100)).skyColor(SKY_COLOR.getColor()).stoneColor(STONE_COLOR.getColor())
                    .biome(biomeData).build();
            String id = dimensionData.getName().toLowerCase();
            for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getDimensionCharMap().entrySet()) {
                id = id.replace(entry.getKey(), entry.getValue());
            }
            if (!DIMENSION_NAME_LIST.contains(new Identifier(RandomlyAddingAnything.MOD_ID, id)))
                Registry.register(DIMENSIONS, new Identifier(RandomlyAddingAnything.MOD_ID, id), dimensionData);
            DIMENSION_NAME_LIST.add(new Identifier(RandomlyAddingAnything.MOD_ID, id));
            // Debug Only
            if (RandomlyAddingAnything.CONFIG.debug) {
                System.out.println(
                        "\nName : " + dimensionData.getName() +
                        "\nFog Color : " + FOG_COLOR.getRed() + "," + FOG_COLOR.getGreen() + "," + FOG_COLOR.getBlue() +
                        "\nGrass Color : " + GRASS_COLOR.getRed() + "," + GRASS_COLOR.getGreen() + "," + GRASS_COLOR.getBlue() +
                        "\nFoliage Color : " + FOLIAGE_COLOR.getRed() + "," + FOLIAGE_COLOR.getGreen() + "," + FOLIAGE_COLOR.getBlue() +
                        "\nSky Color : " + SKY_COLOR.getRed() + "," + SKY_COLOR.getGreen() + "," + SKY_COLOR.getBlue() +
                        "\nStone Color : " + STONE_COLOR.getRed() + "," + STONE_COLOR.getGreen() + "," + STONE_COLOR.getBlue() +
                        "\nHas Skylight : " + dimensionData.hasSkyLight() +
                        "\nHas Sky : " + dimensionData.hasSky() +
                        "\nCan Sleep : " + dimensionData.canSleep()
                );
            }
        }
        isReady = true;
    }

    public static boolean isReady() {
        return isReady;
    }

    public static void createDimensions() {
        DIMENSIONS.forEach(dimension -> {
            DimensionType type = FabricDimensionType.builder().biomeAccessStrategy(class_4547.INSTANCE).desiredRawId(Rands.randIntRange(10000, 100000))
                    .skyLight(dimension.hasSkyLight()).factory((world, dimensionType) -> new CustomDimension(world, dimensionType, dimension))
                    .defaultPlacer((teleported, destination, portalDir, horizontalOffset, verticalOffset) ->
                            new BlockPattern.TeleportTarget(new Vec3d(100, destination.getSeaLevel(), 100), teleported.getVelocity(), (int) teleported.yaw))
                    .buildAndRegister(new Identifier(RandomlyAddingAnything.MOD_ID, dimension.getName().toLowerCase()));
            Identifier id = new Identifier(RandomlyAddingAnything.MOD_ID, dimension.getName().toLowerCase());
            if (DIMENSION_NAME_LIST.contains(id)) {
                if (Registry.DIMENSION.get(id) == null)
                Registry.register(Registry.DIMENSION, id, type);
            }
            if (Registry.DIMENSION.containsId(id)) {
                System.out.println(String.format("Registered a dimension called: %s", dimension.getName().toLowerCase()));
            }

            RegistryUtils.register(new Block(Block.Settings.copy(Blocks.STONE)), new Identifier(RandomlyAddingAnything.MOD_ID, dimension.getName().toLowerCase() + "_stone"),
                    ItemGroup.BUILDING_BLOCKS);
        });
    }

}