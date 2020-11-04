package io.github.vampirestudios.raa.generation.chunkgenerator;

import io.github.vampirestudios.raa.api.TerrainPostProcessor;
import io.github.vampirestudios.raa.api.noise.NoiseModifier;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public abstract class BaseChunkGenerator<T extends ChunkGeneratorConfig> extends SurfaceChunkGenerator<T> {
    private static final ChunkRandom reuseableRandom = new ChunkRandom();
    private long seed;
    public static final Collection<TerrainPostProcessor> postProcessors = new ArrayList<>();
    public static final Collection<NoiseModifier> noiseModifiers = new ArrayList<>();
    public HashMap<Long, int[]> noiseCache = new HashMap<>();

    public BaseChunkGenerator(IWorld world, BiomeSource biomeSource, int verticalNoiseResolution, int horizontalNoiseResolution, int worldHeight, T config, boolean useSimplexNoise, long seed) {
        super(world, biomeSource, verticalNoiseResolution, horizontalNoiseResolution, worldHeight, config, useSimplexNoise);
        this.seed = seed;
        postProcessors.forEach(postProcessor -> postProcessor.init(this.seed));
        noiseModifiers.forEach(noiseModifier -> noiseModifier.init(this.seed, reuseableRandom));
    }

    public static void addTerrainPostProcessor(TerrainPostProcessor postProcessor) {
        postProcessors.add(postProcessor);
    }

    public static void addNoiseModifier(NoiseModifier noiseModifier) {
        noiseModifiers.add(noiseModifier);
    }

}