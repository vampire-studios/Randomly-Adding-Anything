package io.github.vampirestudios.raa.generation.chunkgenerator.caves;

import io.github.vampirestudios.raa.registries.ChunkGenerators;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.CavesChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.List;

public class CavesChunkGenerator extends SurfaceChunkGenerator<CavesChunkGeneratorConfig> {
    private final double[] noiseFalloff = this.buildNoiseFalloff();

    public CavesChunkGenerator(IWorld world, BiomeSource biomeSource, CavesChunkGeneratorConfig config) {
        super(world, biomeSource, 4, 8, 256, config, true);
    }

    protected void sampleNoiseColumn(double[] buffer, int x, int z) {
        this.sampleNoiseColumn(buffer, x, z, 684.412D, 2053.236D, 8.555150000000001D, 34.2206D, 3, -10);
    }

    protected double[] computeNoiseRange(int x, int z) {
        return new double[]{0.0D, 0.0D};
    }

    protected double computeNoiseFalloff(double depth, double scale, int y) {
        return this.noiseFalloff[y];
    }

    private double[] buildNoiseFalloff() {
        double[] ds = new double[this.getNoiseSizeY()];

        for (int i = 0; i < this.getNoiseSizeY(); ++i) {
            ds[i] = Math.cos((double) i * 3.141592653589793D * 6.0D / (double) this.getNoiseSizeY()) * 2.0D;
            double d = i;
            if (i > this.getNoiseSizeY() / 2) {
                d = this.getNoiseSizeY() - 1 - i;
            }

            if (d < 4.0D) {
                d = 4.0D - d;
                ds[i] -= d * d * d * 10.0D;
            }
        }

        return ds;
    }

    public List<Biome.SpawnEntry> getEntitySpawnList(EntityCategory category, BlockPos pos) {
        if (category == EntityCategory.MONSTER) {
            if (Feature.NETHER_BRIDGE.isInsideStructure(this.world, pos)) {
                return Feature.NETHER_BRIDGE.getMonsterSpawns();
            }

            if (Feature.NETHER_BRIDGE.isApproximatelyInsideStructure(this.world, pos) && this.world.getBlockState(pos.down()).getBlock() == Blocks.NETHER_BRICKS) {
                return Feature.NETHER_BRIDGE.getMonsterSpawns();
            }
        }

        return super.getEntitySpawnList(category, pos);
    }

    public int getSpawnHeight() {
        return this.world.getSeaLevel() + 1;
    }

    public int getMaxY() {
        return 256;
    }

    public int getSeaLevel() {
        return 32;
    }

    @Override
    public ChunkGeneratorType<?, ?> method_26490() {
        return ChunkGenerators.CAVES;
    }
}
