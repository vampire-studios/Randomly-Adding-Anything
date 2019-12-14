package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.RAARegistery;
import io.github.vampirestudios.raa.api.RAAWorldAPI;
import io.github.vampirestudios.raa.generation.dimensions.Csoct;
import io.github.vampirestudios.raa.generation.dimensions.DimensionBiomeData;
import io.github.vampirestudios.raa.registries.Dimensions;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;

public class MaterialWorldSpawning {

    public static void init() {
        Registry.BIOME.forEach(biome -> {
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.ANDESITE);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.CLAY);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.COARSE_DIRT);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.DIORITE);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.DIRT);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.END_STONE);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.GRANITE);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.GRASS_BLOCK);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.GRAVEL);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.NETHERRACK);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.PODZOL);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.SAND);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.RED_SAND);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.SANDSTONE);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.RED_SANDSTONE);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.STONE);
            Dimensions.DIMENSIONS.forEach(dimensionData -> {
                for (DimensionBiomeData biomeData : dimensionData.getBiomeData()) {
                    Biome biome1 = Registry.BIOME.get(biomeData.getId());
                    Csoct csoct = Registry.register(RAARegistery.TARGET_REGISTRY,
                            new Identifier(RandomlyAddingAnything.MOD_ID, String.format("%s_stone", dimensionData.getId().getPath())),
                            new Csoct(String.format("%s_stone", dimensionData.getId().getPath()), blockState -> {
                                Block block = Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, String.format("%s_stone", dimensionData.getId().getPath())));
                                return blockState.getBlock() == block;
                            }, Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, String.format("%s_stone", dimensionData.getId().getPath())))));
                    RAAWorldAPI.addRandomOres(biome1, csoct);
                }
            });
        });
    }

}
