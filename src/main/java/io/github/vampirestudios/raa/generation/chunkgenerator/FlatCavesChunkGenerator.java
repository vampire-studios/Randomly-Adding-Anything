package io.github.vampirestudios.raa.generation.chunkgenerator;

import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.CavesChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

public class FlatCavesChunkGenerator extends SurfaceChunkGenerator<CavesChunkGeneratorConfig> {
    private final double[] noiseFalloff = this.buildNoiseFalloff();

    public FlatCavesChunkGenerator(World world_1, BiomeSource biomeSource_1, CavesChunkGeneratorConfig cavesChunkGeneratorConfig_1) {
        super(world_1, biomeSource_1, 4, 4, 128, cavesChunkGeneratorConfig_1, false);
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
        double[] doubles_1 = new double[this.getNoiseSizeY()];

        for (int int_1 = 0; int_1 < this.getNoiseSizeY(); ++int_1) {
            doubles_1[int_1] = Math.cos((double) int_1 * 3.141592653589793D * 6.0D / (double) this.getNoiseSizeY()) * 1.1;
            double double_1 = int_1;
            if (int_1 > this.getNoiseSizeY() / 2) {
                double_1 = this.getNoiseSizeY() - int_1;
            }

            if (double_1 < 4.0D) {
                double_1 = 4.0D - double_1;
                doubles_1[int_1] -= double_1 * double_1 * double_1 * 10.0D;
            }
        }

        return doubles_1;
    }

    public int getSpawnHeight() {
        return this.world.getSeaLevel() + 1;
    }

    public int getMaxY() {
        return 128;
    }

    public int getSeaLevel() {
        return 32;
    }
}
