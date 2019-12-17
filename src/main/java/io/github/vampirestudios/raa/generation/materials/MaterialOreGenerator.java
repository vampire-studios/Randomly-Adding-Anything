package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.api.RAARegisteries;
import io.github.vampirestudios.raa.api.RAAWorldAPI;
import net.minecraft.util.registry.Registry;

public class MaterialOreGenerator {

    public static void init() {
        Registry.BIOME.forEach(biome -> {
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.ANDESITE);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.CLAY);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.COARSE_DIRT);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.DIORITE);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.DIRT);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.END_STONE);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.GRANITE);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.GRASS_BLOCK);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.GRAVEL);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.NETHERRACK);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.PODZOL);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.SAND);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.RED_SAND);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.SANDSTONE);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.RED_SANDSTONE);
//            RAAWorldAPI.generateOresForTarget(biome, CustomTargets.STONE);
            RAARegisteries.TARGET_REGISTRY.forEach(target -> RAAWorldAPI.generateOresForTarget(biome, target));
            /*Dimensions.DIMENSIONS.forEach(dimensionData -> {
                for (DimensionBiomeData biomeData : dimensionData.getBiomeData()) {
                    Biome biome1 = Registry.BIOME.get(biomeData.getId());
                    OreFeatureConfig.Target csoct = RAARegisteries.TARGET_REGISTRY.get(Utils.appendToPath(dimensionData.getId(), "_stone"));
                    RAAWorldAPI.generateOresForTarget(biome1, csoct);
                }
            });*/
        });
    }

}
