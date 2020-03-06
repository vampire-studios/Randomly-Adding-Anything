package io.github.vampirestudios.raa.generation.dimensions;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.ContinentLayer;
import net.minecraft.world.biome.layer.IncreaseEdgeCurvatureLayer;
import net.minecraft.world.biome.layer.ScaleLayer;
import net.minecraft.world.biome.layer.type.IdentitySamplingLayer;
import net.minecraft.world.biome.layer.util.*;
import net.minecraft.world.biome.source.BiomeLayerSampler;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.LongFunction;

public class DimensionalBiomeLayers {
    //TODO: refactor this disaster
    public static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> build(LongFunction<C> apply, Set<Biome> biomes) {
        LayerFactory<T> factory = ContinentLayer.INSTANCE.create(apply.apply(1L));
        factory = ScaleLayer.FUZZY.create(apply.apply(2000L), factory);
        factory = IncreaseEdgeCurvatureLayer.INSTANCE.create(apply.apply(1L), factory);
        factory = ScaleLayer.NORMAL.create(apply.apply(2001L), factory);
        factory = ScaleLayer.NORMAL.create(apply.apply(2001L), factory);
        factory = IncreaseEdgeCurvatureLayer.INSTANCE.create(apply.apply(2L), factory);
        factory = IncreaseEdgeCurvatureLayer.INSTANCE.create(apply.apply(50L), factory);
        factory = IncreaseEdgeCurvatureLayer.INSTANCE.create(apply.apply(70L), factory);
        factory = IncreaseEdgeCurvatureLayer.INSTANCE.create(apply.apply(3L), factory);
        factory = ScaleLayer.NORMAL.create(apply.apply(2002L), factory);
        factory = ScaleLayer.NORMAL.create(apply.apply(2003L), factory);
        factory = IncreaseEdgeCurvatureLayer.INSTANCE.create(apply.apply(4L), factory);
        factory = stack(factory, apply);
        LayerFactory<T> layerFactory = (new BiomesLayer(biomes)).create(apply.apply(200L), factory);
        for (int i = 0; i < 8; ++i) {
            layerFactory = ScaleLayer.NORMAL.create(apply.apply(1000 + i), layerFactory);
        }
        return layerFactory;
    }

    // returns the biome sampler for this dimension
    public static BiomeLayerSampler build(long long_1, Set<Biome> biomes) {
        LayerFactory<CachingLayerSampler> layerFactory_1 = build((long_2) -> new CachingLayerContext(25, long_1, long_2), biomes);
        return new BiomeLayerSampler(layerFactory_1);
    }

    //copypasted from vanilla, this stacks together two layers
    private static <T extends LayerSampler, C extends LayerSampleContext<T>> LayerFactory<T> stack(LayerFactory<T> layerFactory_1, LongFunction<C> longFunction_1) {
        LayerFactory<T> layerFactory_2 = layerFactory_1;

        for (int int_2 = 0; int_2 < 8; ++int_2) {
            layerFactory_2 = ScaleLayer.NORMAL.create(longFunction_1.apply((long) 1000 + (long) int_2), layerFactory_2);
        }

        return layerFactory_2;
    }

    //Custom sampler that is more extensible than vanilla's
    private static class BiomesLayer implements IdentitySamplingLayer {
        private final Set<Biome> biomes;

        public BiomesLayer(Set<Biome> biomes) {
            this.biomes = biomes;
        }

        @Override
        public int sample(LayerRandomnessSource context, int value) {
            List<Biome> biomeList = new ArrayList<>(biomes);
            return Registry.BIOME.getRawId(biomeList.get(context.nextInt(biomeList.size())));
        }
    }
}
