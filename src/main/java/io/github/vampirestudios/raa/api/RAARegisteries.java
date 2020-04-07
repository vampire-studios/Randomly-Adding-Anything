package io.github.vampirestudios.raa.api;

import io.github.vampirestudios.raa.registries.CustomTargets;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

public class RAARegisteries {

    public static final DefaultedRegistry<OreFeatureConfig.Target> TARGET_REGISTRY = Registry.create("raa:ore_target", "ore_target",
            () -> CustomTargets.GRASS_BLOCK);

}
