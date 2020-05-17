package io.github.vampirestudios.raa.generation.chunkgenerator.floating;

import net.minecraft.class_5284;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

public class FloatingIslandsChunkGenerator extends SurfaceChunkGenerator<class_5284> {
    private final class_5284 chunkGeneratorConfig;

    public FloatingIslandsChunkGenerator(long seed, BiomeSource biomeSource, class_5284 chunkGeneratorConfig) {
        super(biomeSource, seed, chunkGeneratorConfig, 8, 4, 128, true);
        this.chunkGeneratorConfig = chunkGeneratorConfig;
    }

    @Override
    public ChunkGenerator create(long seed) {
        return new FloatingIslandsChunkGenerator(seed, this.biomeSource.create(seed), this.chunkGeneratorConfig);
    }

    protected void sampleNoiseColumn(double[] buffer, int x, int z) {
        this.sampleNoiseColumn(buffer, x, z, 1368.824D, 684.412D, 17.110300000000002D, 4.277575000000001D, 64, -3000);
    }

    protected double[] computeNoiseRange(int x, int z) {
        return new double[]{(double) this.biomeSource.getNoiseAt(x, z), 0.0D};
    }

    protected double computeNoiseFalloff(double depth, double scale, int y) {
        return 8.0D - depth;
    }



    @Override
    protected double topInterpolationStart() {
        return super.topInterpolationStart();
    }

    @Override
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