package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.api.RAAWorldAPI;
import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biomes;

public class MaterialWorldSpawning {

    public static void init() {
        Registry.BIOME.forEach(biome -> {
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.ANDESITE);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.CLAY);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.COARSE_DIRT);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.DIORITE);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.DIRT_ANY);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.DIRT_SURFACE);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.DIRT_UNDERGROUND);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.END_STONE);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.GRANITE);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.GRASS_BLOCK);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.GRAVEL);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.NETHERRACK);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.PODZOL);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.RED_SAND);
            if (biome != Biomes.BEACH) RAAWorldAPI.addRandomOres(biome, GeneratesIn.SAND_ANY);
            if (biome == Biomes.BEACH) RAAWorldAPI.addRandomOres(biome, GeneratesIn.SAND_BEACH);
            RAAWorldAPI.addRandomOres(biome, GeneratesIn.STONE);
        });
    }

}
