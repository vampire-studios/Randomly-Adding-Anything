package io.github.vampirestudios.raa.generation.dimensions;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.biome.source.BiomeSourceType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DimensionalBiomeSource extends BiomeSource {
    private static Set<Biome> BIOMES;
    private final BiomeLayerSampler noiseLayer;

    public DimensionalBiomeSource(Object o) {
        super(((DimensionalBiomeSourceConfig) o).getBiomes());
        this.noiseLayer = DimensionalBiomeLayers.build(((DimensionalBiomeSourceConfig) o).getSeed(), ((DimensionalBiomeSourceConfig) o).getBiomes());
        BIOMES = ((DimensionalBiomeSourceConfig) o).getBiomes();
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        return this.noiseLayer.sample(biomeX, biomeZ);
    }

    @Override
    public List<Biome> getSpawnBiomes() {
        return new ArrayList<>(BIOMES);
    }

    @Override
    public BiomeSourceType<?, ?> method_26467() {
        return RandomlyAddingAnything.DIMENSIONAL_BIOMES;
    }

    @Override
    public <T> Dynamic<T> method_26466(DynamicOps<T> dynamicOps) {
        return new Dynamic<>(dynamicOps, dynamicOps.createMap(ImmutableMap.of()));
    }

}
