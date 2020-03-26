package io.github.vampirestudios.raa.api;

import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.Utils;
import io.github.vampirestudios.raa.world.gen.feature.OreFeature;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import io.github.vampirestudios.raa.world.gen.feature.SimpleRangeDecoratorConfig;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;

public class RAAWorldAPI {

    /**
     * Goes through each of the materials and generates them in the world based on the biome and target block
     *
     * @param biome  The biome to generate the ores in.
     * @param target The block targeted by the ore generator.
     */
    public static void generateOresForTarget(Biome biome, OreFeatureConfig.Target target) {
        Materials.MATERIALS.forEach(material -> {
            if (Registry.BLOCK.get(material.getOreInformation().getTargetId()) == target.getBlock()) {
                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        new OreFeature(OreFeatureConfig::deserialize).configure(new OreFeatureConfig(target,
                                Registry.BLOCK.get(Utils.addSuffixToPath(material.getId(), "_ore")).getDefaultState(), material.getOreInformation().getOreClusterSize()))
                                .createDecoratedFeature(Decorator.COUNT_RANGE.configure(new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(),
                                        0, 256))));
            }
        });
        Materials.DIMENSION_MATERIALS.forEach(material -> {
            if (Registry.BLOCK.get(material.getOreInformation().getTargetId()) == target.getBlock()) {
                biome.addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        new OreFeature(OreFeatureConfig::deserialize).configure(new OreFeatureConfig(target,
                                Registry.BLOCK.get(Utils.addSuffixToPath(material.getId(), "_ore")).getDefaultState(), material.getOreInformation().getOreClusterSize()))
                                .createDecoratedFeature(Decorator.COUNT_RANGE.configure(new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(),
                                        0, 256))));
            }
        });
    }
}
