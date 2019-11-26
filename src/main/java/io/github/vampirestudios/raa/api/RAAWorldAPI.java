package io.github.vampirestudios.raa.api;

import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.utils.Utils;
import io.github.vampirestudios.raa.world.gen.feature.OreFeature;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import io.github.vampirestudios.raa.world.gen.feature.SimpleRangeDecoratorConfig;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;

public class RAAWorldAPI {

    /**
     * @param biome  The biome you generate the ores in.
     * @param target The block targeted by the ore generator.
     */
    public static void addRandomOres(Biome biome, OreFeatureConfig.Target target) {
        addRandomOres(new OreGenerationSupport(biome, target, DimensionType.OVERWORLD));
    }

    /**
     * @param biome       The biome you generate the ores in.
     * @param generatesIn The block targeted by the ore generator.
     */
//    @Deprecated
    public static void addRandomOres(Biome biome, GeneratesIn generatesIn) {
        addRandomOres(biome, generatesIn.getTarget());
    }

    /**
     * Goes through each of the materials and generates them in the world based on the biome and target block set in ${@link OreGenerationSupport}
     *
     * @param generationSupport Adds support for generation of ores in the world
     */
    public static void addRandomOres(OreGenerationSupport generationSupport) {
        Materials.MATERIALS.forEach(material -> {
            if (material.getOreInformation().getGeneratesIn().getTarget() == generationSupport.getTarget()) {
                generationSupport.getGenerationBiome().addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        new OreFeature(OreFeatureConfig::deserialize).configure(new OreFeatureConfig(generationSupport.getTarget(),
                                Registry.BLOCK.get(Utils.appendToPath(material.getId(), "_ore")).getDefaultState(), 9))
                                .createDecoratedFeature(Decorator.COUNT_RANGE.configure(new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256))));
            }
        });
        Materials.DIMENSION_MATERIALS.forEach(material -> {
            if (material.getOreInformation().getGeneratesIn().getTarget() == generationSupport.getTarget()) {
                generationSupport.getGenerationBiome().addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        new OreFeature(OreFeatureConfig::deserialize).configure(new OreFeatureConfig(generationSupport.getTarget(),
                                Registry.BLOCK.get(Utils.appendToPath(material.getId(), "_ore")).getDefaultState(), 9))
                                .createDecoratedFeature(Decorator.COUNT_RANGE.configure(new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256))));
            }
        });
    }
}
