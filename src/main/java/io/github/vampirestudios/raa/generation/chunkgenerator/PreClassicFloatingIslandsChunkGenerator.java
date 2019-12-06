package io.github.vampirestudios.raa.generation.chunkgenerator;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.FloatingIslandsChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

public class PreClassicFloatingIslandsChunkGenerator extends SurfaceChunkGenerator<FloatingIslandsChunkGeneratorConfig> {
    private final BlockPos center;

    public PreClassicFloatingIslandsChunkGenerator(IWorld iWorld_1, BiomeSource biomeSource_1, FloatingIslandsChunkGeneratorConfig floatingIslandsChunkGeneratorConfig_1) {
        super(iWorld_1, biomeSource_1, 4, 4, 256, floatingIslandsChunkGeneratorConfig_1, true);
        this.center = floatingIslandsChunkGeneratorConfig_1.getCenter();
    }

    protected void sampleNoiseColumn(double[] doubles_1, int int_1, int int_2) {
        this.sampleNoiseColumn(doubles_1, int_1, int_2, 1368.824D, 684.412D, 17.110300000000002D, 4.277575000000001D, 256, -3000);
    }

    protected double[] computeNoiseRange(int int_1, int int_2) {
        return new double[]{(double) this.biomeSource.getNoiseRange(int_1, int_2), 0.0D};
    }

    protected double computeNoiseFalloff(double double_1, double double_2, int int_1) {
        return 8.0D - double_1;
    }

    protected double method_16409() {
        return (int) super.method_16409() / 2;
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
}