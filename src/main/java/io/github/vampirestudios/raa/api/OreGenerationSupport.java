package io.github.vampirestudios.raa.api;

import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.biome.Biome;

public class OreGenerationSupport {

    private Biome generationBiome;
    private OreFeatureConfig.Target target;

    public OreGenerationSupport(Biome generationBiome, OreFeatureConfig.Target target) {
        this.generationBiome = generationBiome;
        this.target = target;
    }

    public Biome getGenerationBiome() {
        return generationBiome;
    }

    public OreFeatureConfig.Target getTarget() {
        return target;
    }

}