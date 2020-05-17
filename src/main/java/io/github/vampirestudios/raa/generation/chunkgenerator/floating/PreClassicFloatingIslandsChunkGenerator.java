package io.github.vampirestudios.raa.generation.chunkgenerator.floating;

import net.minecraft.class_5284;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

public class PreClassicFloatingIslandsChunkGenerator extends SurfaceChunkGenerator<class_5284> {
    private final class_5284 chunkGeneratorConfig;

    public PreClassicFloatingIslandsChunkGenerator(long seed, BiomeSource biomeSource, class_5284 chunkGeneratorConfig) {
        super(biomeSource, seed, chunkGeneratorConfig, 4, 4, 256, true);
        this.chunkGeneratorConfig = chunkGeneratorConfig;
    }

    @Override
    public ChunkGenerator create(long seed) {
        return new PreClassicFloatingIslandsChunkGenerator(seed, this.biomeSource.create(seed), this.chunkGeneratorConfig);
    }

    protected void sampleNoiseColumn(double[] doubles_1, int int_1, int int_2) {
        this.sampleNoiseColumn(doubles_1, int_1, int_2, 1368.824D, 684.412D, 17.110300000000002D, 4.277575000000001D, 256, -3000);
    }

    protected double[] computeNoiseRange(int int_1, int int_2) {
        return new double[]{(double) this.biomeSource.getNoiseAt(int_1, int_2), 0.0D};
    }

    protected double computeNoiseFalloff(double double_1, double double_2, int int_1) {
        return 8.0D - double_1;
    }

    protected double topInterpolationStart() {
        return super.topInterpolationStart() / 2;
    }

    protected double bottomInterpolationStart() {
        return 8.0D;
    }

    public int getSpawnHeight() {
        return 50;
    }

    public int getSeaLevel() {
        return 0;
    }

}