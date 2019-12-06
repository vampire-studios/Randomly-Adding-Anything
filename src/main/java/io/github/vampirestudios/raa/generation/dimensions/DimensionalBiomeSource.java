package io.github.vampirestudios.raa.generation.dimensions;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeLayerSampler;
import net.minecraft.world.biome.source.BiomeSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DimensionalBiomeSource extends BiomeSource {
    private final BiomeLayerSampler noiseLayer;
    private static Set<Biome> BIOMES;

    public DimensionalBiomeSource(Object o) {
        super(((DimensionalBiomeSourceConfig)o).getBiomes());
        this.noiseLayer = DimensionalBiomeLayers.build(((DimensionalBiomeSourceConfig)o).getSeed(), ((DimensionalBiomeSourceConfig)o).getGeneratorType(), ((DimensionalBiomeSourceConfig)o).getGeneratorSettings(), ((DimensionalBiomeSourceConfig)o).getBiomes());
        this.BIOMES = ((DimensionalBiomeSourceConfig)o).getBiomes();
    }

    @Override
    public Biome getBiomeForNoiseGen(int biomeX, int biomeY, int biomeZ) {
        return this.noiseLayer.sample(biomeX, biomeZ);
    }

    @Override
    public List<Biome> getSpawnBiomes() {
        return new ArrayList<>(BIOMES);
    }

}
