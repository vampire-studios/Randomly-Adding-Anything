package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.feature.config.ChanceAndTypeConfig;
import io.github.vampirestudios.raa.generation.feature.placements.LedgeUndersideMiniFeature;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class RAAPlacements {

    public static LedgeUndersideMiniFeature LEDGE_UNDERSIDE_MINI_FEATURE;

    public static void init() {
        LEDGE_UNDERSIDE_MINI_FEATURE = Registry.register(Registry.DECORATOR,
                new Identifier(RandomlyAddingAnything.MOD_ID, "ledge_underside_mini_feature"),
                new LedgeUndersideMiniFeature(ChanceAndTypeConfig::deserialize, null));
    }

}
