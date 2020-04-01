package io.github.vampirestudios.raa.generation.chunkgenerator.floating;

import io.github.vampirestudios.raa.registries.ChunkGenerators;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.FloatingIslandsChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

public class FloatingIslandsChunkGenerator extends SurfaceChunkGenerator<FloatingIslandsChunkGeneratorConfig> {

    public FloatingIslandsChunkGenerator(IWorld iWorld, BiomeSource biomeSource, FloatingIslandsChunkGeneratorConfig floatingIslandsChunkGeneratorConfig) {
        super(iWorld, biomeSource, 8, 4, 128, floatingIslandsChunkGeneratorConfig, true);
    }

    protected void sampleNoiseColumn(double[] buffer, int x, int z) {
        this.sampleNoiseColumn(buffer, x, z, 1368.824D, 684.412D, 17.110300000000002D, 4.277575000000001D, 64, -3000);
    }

    protected double[] computeNoiseRange(int x, int z) {
        return new double[]{(double) this.biomeSource.getNoiseRange(x, z), 0.0D};
    }

    protected double computeNoiseFalloff(double depth, double scale, int y) {
        return 8.0D - depth;
    }

    protected double method_16409() {
        return super.method_16409() / 2;
    }

    protected double method_16410() {
        return 8.0D;
    }

    public int getSpawnHeight() {
        return 50;
    }

    public int getSeaLevel() {
        return 0;
    }

    @Override
    public ChunkGeneratorType<?, ?> method_26490() {
        return ChunkGenerators.FLOATING_ISLANDS;
    }

}