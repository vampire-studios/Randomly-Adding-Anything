package io.github.vampirestudios.raa.api;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.world.gen.feature.OreFeature;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import io.github.vampirestudios.raa.world.gen.feature.SimpleRangeDecoratorConfig;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.decorator.Decorator;

import java.util.Map;

public class RAAWorldAPI {

    /**
     * @param biome The biome you generate the ores in.
     * @param target The block targeted by the ore generator.
     */
    public static void addRandomOres(Biome biome, OreFeatureConfig.Target target) {
        addRandomOres(new OreGenerationSupport(biome, target));
    }

    /**
     * @param biome The biome you generate the ores in.
     * @param generatesIn The block targeted by the ore generator.
     */
    @Deprecated
    public static void addRandomOres(Biome biome, GeneratesIn generatesIn) {
        addRandomOres(new OreGenerationSupport(biome, generatesIn.getTarget()));
    }

    /**
     * Goes through each of the materials and generates them in the world based on the biome and target block set in ${@link OreGenerationSupport}
        @param generationSupport Adds support for generation of ores in the world
     */
    public static void addRandomOres(OreGenerationSupport generationSupport) {
        Materials.MATERIALS.forEach(material -> {
            String id = material.getName().toLowerCase();
            for (Map.Entry<String, String> entry : RandomlyAddingAnything.CONFIG.namingLanguage.getCharMap().entrySet()) {
                id = id.replace(entry.getKey(), entry.getValue());
            }
            if (material.getOreInformation().getGenerateIn().getTarget() == generationSupport.getTarget()) {
                generationSupport.getGenerationBiome().addFeature(GenerationStep.Feature.UNDERGROUND_ORES,
                        new OreFeature(OreFeatureConfig::deserialize).configure(new OreFeatureConfig(generationSupport.getTarget(),
                                Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, id + "_ore")).getDefaultState(), 9))
                                .createDecoratedFeature(Decorator.COUNT_RANGE.configure(new SimpleRangeDecoratorConfig(material.getOreInformation().getOreCount(), 0, 256))));
            }
        });
    }
}
