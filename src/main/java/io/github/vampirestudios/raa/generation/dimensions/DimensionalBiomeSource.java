package io.github.vampirestudios.raa.generation.dimensions;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.layer.BiomeLayerSampler;
import net.minecraft.world.biome.layer.BiomeLayers;
import net.minecraft.world.biome.source.BiomeSource;

import java.util.Set;

public class DimensionalBiomeSource extends BiomeSource {
    private final BiomeLayerSampler noiseLayer;
    private static Set<Biome> BIOMES;

    protected DimensionalBiomeSource(DimensionalBiomeSourceConfig config) {
        super(config.getBiomes());
        this.noiseLayer = BiomeLayers.build(config.getSeed(), config.getGeneratorType(), config.getGeneratorSettings());
    }

    @Override
    public Biome getStoredBiome(int biomeX, int biomeY, int biomeZ) {
        return this.noiseLayer.sample(biomeX, biomeZ);
    }
}
