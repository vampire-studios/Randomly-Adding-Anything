package io.github.vampirestudios.raa.generation.chunkgenerator;

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
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

import java.util.stream.IntStream;

public class TotallyCustomChunkGenerator extends SurfaceChunkGenerator<OverworldChunkGeneratorConfig> {
    private static final float[] BIOME_WEIGHT_TABLE = Util.make(new float[25], (floats) -> {
        for (int i = -2; i <= 2; ++i) {
            for (int i1 = -2; i1 <= 2; ++i1) {
                float v = 10.0F / MathHelper.sqrt((float) (i * i + i1 * i1) + 0.2F);
                floats[i + 2 + (i1 + 2) * 5] = v;
            }
        }

    });
    private final OctavePerlinNoiseSampler noiseSampler;

    private float b1 = Rands.randFloatRange(256, 4096);
    private float b2 = Rands.randFloatRange(256, 4096);
    private float c1 = Rands.randFloatRange(2, 24);
    private float c2 = Rands.randFloatRange(2, 24);
    private int div = Rands.randIntRange(3, 64);
    private int mod = Rands.randIntRange(-4096, 4096);

    public TotallyCustomChunkGenerator(IWorld iWorld_1, BiomeSource biomeSource_1, OverworldChunkGeneratorConfig overworldChunkGeneratorConfig_1) {
        super(iWorld_1, biomeSource_1, (int) Math.pow(2, Rands.randIntRange(3, 3)), (int) Math.pow(2, Rands.randIntRange(3, 3)), 256, overworldChunkGeneratorConfig_1, true);
        this.random.consume(Rands.randInt(100000));
        this.noiseSampler = new OctavePerlinNoiseSampler(this.random, IntStream.of(15, 0));

    }

    public void populateEntities(ChunkRegion chunkRegion_1) {
        int int_1 = chunkRegion_1.getCenterChunkX();
        int int_2 = chunkRegion_1.getCenterChunkZ();
        Biome biome_1 = chunkRegion_1.getBiome((new ChunkPos(int_1, int_2)).getCenterBlockPos());
        ChunkRandom chunkRandom_1 = new ChunkRandom();
        chunkRandom_1.setSeed(chunkRegion_1.getSeed(), int_1 << 4, int_2 << 4);
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

                float p = BIOME_WEIGHT_TABLE[l + 2 + (m + 2) * 5] / (n + 3.0F);
                if (biome.getDepth() > k) {
                    p /= 2;
                }

                f += o * p;
                g += n * p;
                h += p;
            }
        }

        f /= h;
        g /= h;
        f = f * 0.8F + 0.1F;
        g = (g * 4.0F - 1.0F) / 8.f;
        ds[0] = (double)g + this.sampleNoise(x, z);
        ds[1] = f;
        return ds;
    }

    @Override
    protected double computeNoiseFalloff(double depth, double scale, int y) {
        double e = ((double)y - (8.5D + depth * 8.5D / 8.0D * 4.0D)) * 14.0D * 128.0D / 256.0D / scale*1.6;
        if (e < 0.0D) {
            e *= 5;
        }

        return e;
    }

    @Override
    protected void sampleNoiseColumn(double[] buffer, int x, int z) {
        this.sampleNoiseColumn(buffer, x, z, b1, b2, c1, c2, div, mod);
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
}