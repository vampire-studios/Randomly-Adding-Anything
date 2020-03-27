package io.github.vampirestudios.raa.api;

import io.github.vampirestudios.raa.mixins.AccessorRegistryCreate;
import io.github.vampirestudios.raa.registries.CustomTargets;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.util.registry.Registry;

public class RAARegisteries {

    public static final Registry<OreFeatureConfig.Target> TARGET_REGISTRY = AccessorRegistryCreate.create("ore_target", "minecraft:stone",
            () -> CustomTargets.STONE);

    /*public static final Registry<OreTargetData> TARGET_DATA_REGISTRY = AccessorRegistryCreate.create("ore_data_target", "minecraft:grass_block",
            () -> CustomTargets2.GRASS_BLOCK);*/

}
