package io.github.vampirestudios.raa.generation.dimensions;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DimensionalBiomeSource extends BiomeSource {
    private static Set<Biome> BIOMES;
    private final BiomeLayerSampler noiseLayer;

    public DimensionalBiomeSource(long seed, Set<Biome> biomes) {
        super(biomes);
        this.noiseLayer = DimensionalBiomeLayers.build(seed, biomes);
        BIOMES = biomes;
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        return this.noiseLayer.sample(biomeX, biomeZ);
    }

    @Override
    public BiomeSource create(long seed) {
        return new DimensionalBiomeSource(seed, BIOMES);
    }

    @Override
    public List<Biome> getSpawnBiomes() {
        return new ArrayList<>(BIOMES);
    }

}
