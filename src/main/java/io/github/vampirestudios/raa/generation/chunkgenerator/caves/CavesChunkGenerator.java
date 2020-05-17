package io.github.vampirestudios.raa.generation.chunkgenerator.caves;

import net.minecraft.block.Blocks;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.List;

public class CavesChunkGenerator extends SurfaceChunkGenerator<CavesChunkGeneratorConfig> {
    private final double[] noiseFalloff = this.buildNoiseFalloff();
    private final CavesChunkGeneratorConfig chunkGeneratorConfig;

    public CavesChunkGenerator(long seed, BiomeSource biomeSource, CavesChunkGeneratorConfig config) {
        super(biomeSource, seed, config,4, 8, 256,  true);
        this.chunkGeneratorConfig = config;
    }

    @Override
    public ChunkGenerator create(long seed) {
        return new CavesChunkGenerator(seed, this.biomeSource.create(seed), this.chunkGeneratorConfig);
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

    @Override
    public List<Biome.SpawnEntry> getEntitySpawnList(Biome biome, StructureAccessor accessor, SpawnGroup group, BlockPos pos) {
        return group == SpawnGroup.MONSTER && Feature.NETHER_BRIDGE.isInsideStructure(accessor, pos) ?
                Feature.NETHER_BRIDGE.getMonsterSpawns() : super.getEntitySpawnList(biome, accessor, group, pos);
    }

    public int getSpawnHeight() {
        return getSeaLevel() + 1;
    }

    public int getMaxY() {
        return 256;
    }

    public int getSeaLevel() {
        return 32;
    }
}
