package io.github.vampirestudios.raa.api;

import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

public class RAARegistery {

    public static final Registry<OreFeatureConfig.Target> TARGET_REGISTRY = new DefaultedRegistry<>("ore_targets");
    public static final Registry<GeneratesIn> GENERATES_IN_REGISTRY = new DefaultedRegistry<>("generates_in");

}
