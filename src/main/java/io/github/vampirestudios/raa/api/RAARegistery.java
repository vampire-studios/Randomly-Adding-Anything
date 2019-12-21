package io.github.vampirestudios.raa.api;

import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.mixins.AccessorRegistry;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.util.registry.Registry;

public class RAARegistery {

    public static final Registry<OreFeatureConfig.Target> TARGET_REGISTRY = AccessorRegistry.create("ore_targets", "raa:none", () -> OreFeatureConfig.Target.NONE);
    public static final Registry<GeneratesIn> GENERATES_IN_REGISTRY = AccessorRegistry.create("generates_in", "raa:none", () -> GeneratesIn.NONE);

}
