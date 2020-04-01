package io.github.vampirestudios.raa.generation.chunkgenerator.overworld;

import io.github.vampirestudios.raa.registries.ChunkGenerators;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.Util;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;
import net.minecraft.world.level.LevelGeneratorType;

import java.util.stream.IntStream;

public class SmoothOverworldChunkGenerator extends SurfaceChunkGenerator<OverworldChunkGeneratorConfig> {
    private static final float[] BIOME_WEIGHT_TABLE = Util.make(new float[25], (floats_1) -> {
        for (int int_1 = -2; int_1 <= 2; ++int_1) {
            for (int int_2 = -2; int_2 <= 2; ++int_2) {
                float float_1 = 10.0F / MathHelper.sqrt((float) (int_1 * int_1 + int_2 * int_2) + 0.2F);
                floats_1[int_1 + 2 + (int_2 + 2) * 5] = float_1;
            }
        }

    });
    private final OctavePerlinNoiseSampler noiseSampler;
    private final boolean amplified;

    public SmoothOverworldChunkGenerator(IWorld iWorld_1, BiomeSource biomeSource_1, OverworldChunkGeneratorConfig overworldChunkGeneratorConfig_1) {
        super(iWorld_1, biomeSource_1, 16, 16, 256, overworldChunkGeneratorConfig_1, true);
        this.random.consume(Rands.randInt(100000));
        this.noiseSampler = new OctavePerlinNoiseSampler(this.random, IntStream.of(15, 0));
        this.amplified = iWorld_1.getLevelProperties().getGeneratorType() == LevelGeneratorType.AMPLIFIED;
    }

    public void populateEntities(ChunkRegion chunkRegion_1) {
        int int_1 = chunkRegion_1.getCenterChunkX();
        int int_2 = chunkRegion_1.getCenterChunkZ();
        Biome biome_1 = chunkRegion_1.getBiome((new ChunkPos(int_1, int_2)).getCenterBlockPos());
        ChunkRandom chunkRandom_1 = new ChunkRandom();
        chunkRandom_1.setPopulationSeed(chunkRegion_1.getSeed(), int_1 << 4, int_2 << 4);
        SpawnHelper.populateEntities(chunkRegion_1, biome_1, int_1, int_2, chunkRandom_1);
    }

    @Override
    public int getSpawnHeight() {
        return 64;
    }

    @Override
    protected double[] computeNoiseRange(int x, int z) {
        double[] ds = new double[2];
        float f = 0.0F;
        float g = 0.0F;
        float h = 0.0F;
        int j = this.getSeaLevel();
        float k = this.biomeSource.getBiomeForNoiseGen(x, j, z).getDepth() + 0.1f;

        for(int l = -2; l <= 2; ++l) {
            for(int m = -2; m <= 2; ++m) {
                Biome biome = this.biomeSource.getBiomeForNoiseGen(x + l, j, z + m);
                float n = biome.getDepth();
                float o = biome.getScale()+0.4f;
                if (this.amplified && n > 0.0F) {
                    n = 1.0F + n * 2.0F;
                    o = 1.0F + o * 4.0F;
                }

                float p = BIOME_WEIGHT_TABLE[l + 2 + (m + 2) * 5] / (n + 3.0F);
                if (biome.getDepth() > k) {
                    p /= 2.0F;
                }

                f += o * p;
                g += n * p;
                h += p;
            }
        }

        f /= h;
        g /= h;
        f = f * 0.8F + 0.1F;
        g = (g * 4.0F - 1.0F) / 12.0F;
        ds[0] = (double)g + this.sampleNoise(x, z);
        ds[1] = f;
        return ds;
    }

    @Override
    protected double computeNoiseFalloff(double depth, double scale, int y) {
        double e = ((double)y - (8.5D + depth*1.6 * 8.5D / 8.0D * 4.0D)) * 14.0D * 128.0D / 256.0D / scale*1.3;
        if (e < 0.0D) {
            e *= 5.0D;
        }

        return e;
    }

    @Override
    protected void sampleNoiseColumn(double[] buffer, int x, int z) {
        this.sampleNoiseColumn(buffer, x, z, 1024, 1024, 16, 4, 8, -32);
    }

    private double sampleNoise(int x, int y) {
        double d = this.noiseSampler.sample(x * 200, 10.0D, y * 200, 1.0D, 0.0D, true) * 65535.0D / 8000.0D;
        if (d < 0.0D) {
            d = -d * 0.3D;
        }

        d = d * 3.0D - 2.0D;
        if (d < 0.0D) {
            d /= 28.0D;
        } else {
            if (d > 1.0D) {
                d = 1.0D;
            }

            d /= 40.0D;
        }

        return d;
    }

    @Override
    public ChunkGeneratorType<?, ?> method_26490() {
        return ChunkGenerators.SMOOTH;
    }
}