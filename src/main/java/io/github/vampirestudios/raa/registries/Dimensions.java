package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.client.Color;
import io.github.vampirestudios.raa.generation.dimensions.CustomDimension;
import io.github.vampirestudios.raa.generation.dimensions.Dimension;
import io.github.vampirestudios.raa.generation.dimensions.DimensionBuilder;
import io.github.vampirestudios.raa.utils.Rands;
import net.fabricmc.fabric.api.dimension.v1.FabricDimensionType;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.class_4547;
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
    public static final Registry<Dimension> DIMENSIONS = new DefaultedRegistry<>("raa:dimensions");

    public static boolean isReady = false;

    public static void init() {
        for (int a = 0; a < RandomlyAddingAnything.CONFIG.materialNumber; a++) {
            Color FOG_COLOR = new Color(Rands.randIntRange(0, 255),Rands.randIntRange(0, 255),Rands.randIntRange(0, 255));
            Color FOLIAGE_COLOR = new Color(Rands.randIntRange(0, 255),Rands.randIntRange(0, 255),Rands.randIntRange(0, 255));
            Color GRASS_COLOR = new Color(Rands.randIntRange(0, 255),Rands.randIntRange(0, 255),Rands.randIntRange(0, 255));
            Dimension material = DimensionBuilder.create()
                    .fogColor(FOG_COLOR.getColor()).grassColor(GRASS_COLOR.getColor()).foliageColor(FOLIAGE_COLOR.getColor())
                    .hasLight(Rands.chance(2)).name(RandomlyAddingAnything.CONFIG.namingLanguage.generateDimensionNames())
                    .hasSky(Rands.chance(1)).build();
            String id = material.getName().toLowerCase();
            for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getMaterialCharMap().entrySet()) {
                id = id.replace(entry.getKey(), entry.getValue());
            }
            DIMENSION_NAME_LIST.add(new Identifier(RandomlyAddingAnything.MOD_ID, id));
            // Debug Only
            if (RandomlyAddingAnything.CONFIG.debug) {
                System.out.println(
                        "\nName : " + material.getName() +
                        "\nFog Color : " + FOG_COLOR.getRed() + "," + FOG_COLOR.getGreen() + "," + FOG_COLOR.getBlue() +
                        "\nGrass Color : " + GRASS_COLOR.getRed() + "," + GRASS_COLOR.getGreen() + "," + GRASS_COLOR.getBlue() +
                        "\nFoliage Color : " + FOLIAGE_COLOR.getRed() + "," + FOLIAGE_COLOR.getGreen() + "," + FOLIAGE_COLOR.getBlue() +
                        "\nHas Skylight : " + material.hasSkyLight() +
                        "\nHas Sky : " + material.hasSky()
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
            if (!DIMENSION_NAME_LIST.contains(id)) {
                Registry.register(Registry.DIMENSION, id, type);
                if (Registry.DIMENSION.containsId(id)) {
                    System.out.println(String.format("Registered a dimension called: %s", dimension.getName().toLowerCase()));
                }
            }
        });
    }

}