package io.github.vampirestudios.raa.generation.dimensions;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.ContinentLayer;
import net.minecraft.world.biome.layer.IncreaseEdgeCurvatureLayer;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.type.ParentedLayer;
import net.minecraft.world.biome.layer.util.*;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.level.LevelGeneratorType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.LongFunction;

public class DimensionalBiomeLayers {
    //TODO: refactor this disaster
    public static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(LevelGeneratorType levelGeneratorType_1, OverworldChunkGeneratorConfig overworldChunkGeneratorConfig_1, LongFunction<C> longFunction_1, Set<Biome> biomes) {
        LayerFactory<T> layerFactory_1 = ContinentLayer.INSTANCE.create(longFunction_1.apply(1L));
        layerFactory_1 = ScaleLayer.FUZZY.create(longFunction_1.apply(2000L), layerFactory_1);
        layerFactory_1 = IncreaseEdgeCurvatureLayer.INSTANCE.create(longFunction_1.apply(1L), layerFactory_1);
        layerFactory_1 = ScaleLayer.NORMAL.create(longFunction_1.apply(2001L), layerFactory_1);
        layerFactory_1 = ScaleLayer.NORMAL.create(longFunction_1.apply(2001L), layerFactory_1);
        layerFactory_1 = IncreaseEdgeCurvatureLayer.INSTANCE.create(longFunction_1.apply(2L), layerFactory_1);
        layerFactory_1 = IncreaseEdgeCurvatureLayer.INSTANCE.create(longFunction_1.apply(50L), layerFactory_1);
        layerFactory_1 = IncreaseEdgeCurvatureLayer.INSTANCE.create(longFunction_1.apply(70L), layerFactory_1);
        layerFactory_1 = IncreaseEdgeCurvatureLayer.INSTANCE.create(longFunction_1.apply(3L), layerFactory_1);
        layerFactory_1 = ScaleLayer.NORMAL.create(longFunction_1.apply(2002L), layerFactory_1);
        layerFactory_1 = ScaleLayer.NORMAL.create(longFunction_1.apply(2003L), layerFactory_1);
        layerFactory_1 = IncreaseEdgeCurvatureLayer.INSTANCE.create(longFunction_1.apply(4L), layerFactory_1);
        layerFactory_1 = stack(1000L, ScaleLayer.NORMAL, layerFactory_1, 0, longFunction_1);
        LayerFactory<T> layerFactory_4 = (new BiomesLayer(levelGeneratorType_1, overworldChunkGeneratorConfig_1.getForcedBiome(), biomes)).create(longFunction_1.apply(200L), layerFactory_1);
        for(int int_3 = 0; int_3 < 8; ++int_3) {
            layerFactory_4 = ScaleLayer.NORMAL.create(longFunction_1.apply(1000 + int_3), layerFactory_4);
        }
        return layerFactory_4;
    }

    // returns the biome sampler for this dimension
    public static BiomeLayerSampler build(long long_1, LevelGeneratorType levelGeneratorType_1, OverworldChunkGeneratorConfig overworldChunkGeneratorConfig_1, Set<Biome> biomes) {
        int int_1 = 1;
        LayerFactory<CachingLayerSampler> layerFactory_1 = build(levelGeneratorType_1, overworldChunkGeneratorConfig_1, (long_2) -> {
            return new CachingLayerContext(25, long_1, long_2);
        }, biomes);
        return new BiomeLayerSampler(layerFactory_1);
    }

    //copypasted from vanilla, this stacks together two layers
    private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(long long_1, ParentedLayer parentedLayer_1, LayerFactory<T> layerFactory_1, int int_1, LongFunction<C> longFunction_1) {
        LayerFactory<T> layerFactory_2 = layerFactory_1;

        for(int int_2 = 0; int_2 < int_1; ++int_2) {
            layerFactory_2 = parentedLayer_1.create(longFunction_1.apply(long_1 + (long)int_2), layerFactory_2);
        }

        return layerFactory_2;
    }

    //Custom sampler that is more extensible than vanilla's
    private static class BiomesLayer implements IdentitySamplingLayer {
        private final LevelGeneratorType levelGeneratorType_1;
        private final int int_1;
        private final Set<Biome> biomes;

        public BiomesLayer(LevelGeneratorType levelGeneratorType_1, int int_1, Set<Biome> biomes) {
            this.levelGeneratorType_1 = levelGeneratorType_1;
            this.int_1 = int_1;
            this.biomes = biomes;
        }

        @Override
        public int sample(LayerRandomnessSource context, int value) {
            List<Biome> biomeList = new ArrayList<>(biomes);
            return Registry.BIOME.getRawId(biomeList.get(context.nextInt(biomeList.size())));
        }
    }
}
