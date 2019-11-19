package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.feature.*;
import io.github.vampirestudios.raa.generation.feature.config.CorruptedFeatureConfig;
import net.fabricmc.fabric.api.registry.CommandRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class Features {
    public static NetherrackFeature CORRUPTED_NETHRRACK;
    public static CraterFeature CRATER_FEATURE;
    public static OutpostFeature OUTPOST;
    public static CampfireFeature CAMPFIRE;
    public static TowerFeature TOWER;

    public static void init() {
        CommandRegistry.INSTANCE.register(false, dispatcher -> CommandLocateRAAStructure.register(dispatcher));
        CORRUPTED_NETHRRACK = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "corrupted_netherrack"), new NetherrackFeature(DefaultFeatureConfig::deserialize));
        CRATER_FEATURE = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "crater_feature"), new CraterFeature(CorruptedFeatureConfig::deserialize));
        OUTPOST = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "outpost"), new OutpostFeature(DefaultFeatureConfig::deserialize));
        CAMPFIRE = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "campfire"), new CampfireFeature(DefaultFeatureConfig::deserialize));
        TOWER = Registry.register(Registry.FEATURE, new Identifier(MOD_ID, "tower"), new TowerFeature(DefaultFeatureConfig::deserialize));
    }
}
