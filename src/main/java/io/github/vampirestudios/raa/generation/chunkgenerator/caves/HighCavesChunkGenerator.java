package io.github.vampirestudios.raa.generation.chunkgenerator.caves;

import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

public class HighCavesChunkGenerator extends SurfaceChunkGenerator<CavesChunkGeneratorConfig> {
    private final double[] noiseFalloff = this.buildNoiseFalloff();
    private final CavesChunkGeneratorConfig chunkGeneratorConfig;

    public HighCavesChunkGenerator(long seed, BiomeSource biomeSource, CavesChunkGeneratorConfig config) {
        super(biomeSource, seed, config,2, 16, 256,  true);
        this.chunkGeneratorConfig = config;
    }

    @Override
    public ChunkGenerator create(long seed) {
        return new HighCavesChunkGenerator(seed, this.biomeSource.create(seed), this.chunkGeneratorConfig);
    }

    protected void sampleNoiseColumn(double[] doubles_1, int int_1, int int_2) {
        this.sampleNoiseColumn(doubles_1, int_1, int_2, 684.412D, 2053.236D, 8.555150000000001D, 34.2206D, 3, -10);
    }

    protected double[] computeNoiseRange(int int_1, int int_2) {
        return new double[]{0.0D, 0.0D};
    }

    protected double computeNoiseFalloff(double double_1, double double_2, int int_1) {
        return this.noiseFalloff[int_1];
    }

    private double[] buildNoiseFalloff() {
        double[] doubles = new double[this.getNoiseSizeY()];

        for (int i = 0; i < this.getNoiseSizeY(); ++i) {
            doubles[i] = Math.cos((double) i * 3.141592653589793D * 6.0D / (double) this.getNoiseSizeY()) * 2.0D;
            double v = i;
            if (i > this.getNoiseSizeY() / 2) {
                v = this.getNoiseSizeY() - 1 - i;
            }

            if (v < 4.0D) {
                v = 4.0D - v;
                doubles[i] -= v * v * v * 10.0D;
            }
        }

        return doubles;
    }

    public int getSpawnHeight() {
        return getSeaLevel() + 1;
    }

    public int getMaxY() {
        return 128;
    }

    public int getSeaLevel() {
        return 32;
    }
}
