package io.github.vampirestudios.raa.api;

import io.github.vampirestudios.raa.mixins.AccessorRegistryCreate;
import io.github.vampirestudios.raa.registries.CustomTargets;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.util.registry.Registry;

public class RAARegisteries {

    public static final Registry<OreFeatureConfig.Target> TARGET_REGISTRY = AccessorRegistryCreate.create("ore_target", "raa:stone",
            () -> CustomTargets.STONE);

}
