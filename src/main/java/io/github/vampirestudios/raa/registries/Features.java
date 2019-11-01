package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.feature.CraterFeature;
import io.github.vampirestudios.raa.generation.feature.TestFeature;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class Features {
    public static TestFeature TEST_FEATURE;
    public static CraterFeature CRATER_FEATURE;

    public static void init() {
        TEST_FEATURE = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "test_feature"), new TestFeature(DefaultFeatureConfig::deserialize));
        CRATER_FEATURE = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "crater_feature"), new CraterFeature(DefaultFeatureConfig::deserialize));
    }
}
