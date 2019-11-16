package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.api.RAAWorldAPI;
import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;

public class MaterialWorldSpawning {

    public static void init() {
        Registry.BIOME.forEach(biome -> {
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.ANDESITE);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.CLAY);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.COARSE_DIRT);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.DIORITE);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.DIRT_ANY);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.DIRT_SURFACE);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.DIRT_UNDERGROUND);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.END_STONE);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.GRANITE);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.GRASS_BLOCK);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.GRAVEL);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.NETHERRACK);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.PODZOL);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.RED_SAND);
            if (biome != Biomes.BEACH) RAAWorldAPI.addRandomOres(biome, GeneratesIn.SAND_ANY);
            if (biome == Biomes.BEACH) RAAWorldAPI.addRandomOres(biome, GeneratesIn.SAND_BEACH);
            RAAWorldAPI.addRandomOres(biome, OreFeatureConfig.Target.STONE);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.DIMENSION_STONE);
        });
    }

}
