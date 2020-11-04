package io.github.vampirestudios.raa.generation.chunkgenerator.overworld;

import io.github.vampirestudios.raa.api.Heightmap;
import io.github.vampirestudios.raa.generation.chunkgenerator.BaseChunkGenerator;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.math.noise.PerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.ZombieSiegeManager;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;

import java.util.stream.IntStream;

public class QuadrupleAmplifiedChunkGenerator extends BaseChunkGenerator<OverworldChunkGeneratorConfig> implements Heightmap {
    private static final float[] BIOME_WEIGHT_TABLE = Util.make(new float[25], (floats_1) -> {
        for (int int_1 = -2; int_1 <= 2; ++int_1) {
            for (int int_2 = -2; int_2 <= 2; ++int_2) {
                float float_1 = 10.0F / MathHelper.sqrt((float) (int_1 * int_1 + int_2 * int_2) + 0.2F);
                floats_1[int_1 + 2 + (int_2 + 2) * 5] = float_1;
            }
        }

    });
    private final OctavePerlinNoiseSampler noiseSampler;
    private final PhantomSpawner phantomSpawner = new PhantomSpawner();
    private final PillagerSpawner pillagerSpawner = new PillagerSpawner();
    private final CatSpawner catSpawner = new CatSpawner();
    private final ZombieSiegeManager zombieSiegeManager = new ZombieSiegeManager();

    private final OctavePerlinNoiseSampler field_16574;
    private final OctavePerlinNoiseSampler field_16581;
    private final OctavePerlinNoiseSampler field_16575;

    public QuadrupleAmplifiedChunkGenerator(IWorld iWorld_1, BiomeSource biomeSource_1, OverworldChunkGeneratorConfig overworldChunkGeneratorConfig_1) {
        super(iWorld_1, biomeSource_1, 4, 4, 256, overworldChunkGeneratorConfig_1, true, iWorld_1.getSeed());
        this.random.consume(Rands.randInt(100000));
        this.noiseSampler = new OctavePerlinNoiseSampler(this.random, IntStream.of(15, 0));

        this.field_16574 = new OctavePerlinNoiseSampler(this.random, IntStream.of(15, 0));
        this.field_16581 = new OctavePerlinNoiseSampler(this.random, IntStream.of(15, 0));
        this.field_16575 = new OctavePerlinNoiseSampler(this.random, IntStream.of(7, 0));
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
    protected void sampleNoiseColumn(double[] doubles_1, int int_1, int int_2, double double_1, double double_2, double double_3, double double_4, int int_3, int int_4) {
        double[] doubles_2 = this.computeNoiseRange(int_1, int_2);
        double double_5 = doubles_2[0];
        double double_6 = doubles_2[1];
        double double_7 = this.method_16409();
        double double_8 = this.method_16410();

        for (int int_5 = 0; int_5 < this.getNoiseSizeY(); ++int_5) {
            double double_9 = this.sampleNoise(int_1, int_5, int_2, double_1, double_2, double_3, double_4);
            double_9 -= this.computeNoiseFalloff(double_5, double_6, int_5);
            if ((double) int_5 > double_7) {
                double_9 = MathHelper.clampedLerp(double_9, int_4, ((double) int_5 - double_7) / (double) int_3);
            } else if ((double) int_5 < double_8) {
                double_9 = MathHelper.clampedLerp(double_9, -30.0D, (double_8 - (double) int_5) / (double_8 - 1.0D));
            }

            doubles_1[int_5] = double_9 + MathHelper.sin((float) double_9);
        }

    }

    public double sampleNoise(int int_1, int int_2, int int_3, double double_1, double double_2, double double_3, double double_4) {
        double double_5 = 0.0D;
        double double_6 = 0.0D;
        double double_7 = 0.0D;
        double double_8 = 0.5D;

        for (int int_4 = 0; int_4 < 16; ++int_4) {
            double double_9 = OctavePerlinNoiseSampler.maintainPrecision((double) int_1 * double_1 * double_8) + MathHelper.sin((float) (int_1 * double_1 * double_8));
            double double_10 = OctavePerlinNoiseSampler.maintainPrecision((double) int_2 * double_2 * double_8) + MathHelper.sin((float) (int_2 * double_2 * double_8));
            double double_11 = OctavePerlinNoiseSampler.maintainPrecision((double) int_3 * double_1 * double_8) + MathHelper.sin((float) (int_3 * double_1 * double_8));
            double double_12 = double_2 * double_8;
            PerlinNoiseSampler perlinNoiseSampler_1 = this.field_16574.getOctave(int_4);
            if (perlinNoiseSampler_1 != null) {
                double_5 += perlinNoiseSampler_1.sample(double_9, double_10, double_11, double_12, (double) int_2 * double_12) / double_8;
                double_5 += MathHelper.sin((float) double_5) * 0.1;
            }

            PerlinNoiseSampler perlinNoiseSampler_2 = this.field_16581.getOctave(int_4);
            if (perlinNoiseSampler_2 != null) {
                double_6 += perlinNoiseSampler_2.sample(double_9, double_10, double_11, double_12, (double) int_2 * double_12) / double_8;
                double_6 += MathHelper.sin((float) double_6) * 0.1;
            }

            if (int_4 < 8) {
                PerlinNoiseSampler perlinNoiseSampler_3 = this.field_16575.getOctave(int_4);
                if (perlinNoiseSampler_3 != null) {
                    double_7 += perlinNoiseSampler_3.sample(OctavePerlinNoiseSampler.maintainPrecision((double) int_1 * double_3 * double_8), OctavePerlinNoiseSampler.maintainPrecision((double) int_2 * double_4 * double_8), OctavePerlinNoiseSampler.maintainPrecision((double) int_3 * double_3 * double_8), double_4 * double_8, (double) int_2 * double_4 * double_8) / double_8;
                    double_7 += MathHelper.sin((float) double_7) * 0.1;
                }
            }

            double_8 /= 2.0D;
        }

        return MathHelper.clampedLerp(double_5 / 512.0D, double_6 / 512.0D, (double_7 / 10.0D + 1.0D) / 2.0D) + MathHelper.sin((float) double_8);
    }

    protected void sampleNoiseColumn(double[] doubles_1, int int_1, int int_2) {
        this.sampleNoiseColumn(doubles_1, int_1, int_2, 684.4119873046875D, 684.4119873046875D, 8.555149841308594D, 4.277574920654297D, 32, -100);
    }

    protected double computeNoiseFalloff(double double_1, double double_2, int int_1) {
        double double_4 = (((double) int_1 - MathHelper.sin((float) (8.5D + double_1 * 8.5D / 8.0D * 4.0D))) * MathHelper.sin((float) (12.0D * 128.0D / 256.0D / double_2))) + MathHelper.sin((float) (double_1 * double_2));
        if (double_4 < 0.0D) {
            double_4 *= 4.0D;
        }

        return double_4;
    }

    protected double[] computeNoiseRange(int int_1, int int_2) {
        double[] doubles_1 = new double[2];
        float float_1 = 0.0F;
        float float_2 = 0.0F;
        float float_3 = 0.0F;
        int int_4 = this.getSeaLevel();
        float float_4 = this.biomeSource.getBiomeForNoiseGen(int_1, int_4, int_2).getDepth();
        float_4 += MathHelper.sin(float_4);

        for (int int_5 = -2; int_5 <= 2; ++int_5) {
            for (int int_6 = -2; int_6 <= 2; ++int_6) {
                Biome biome_1 = this.biomeSource.getBiomeForNoiseGen(int_1 + int_5, int_4, int_2 + int_6);
                float float_5 = biome_1.getDepth();
                float float_6 = biome_1.getScale();
                float_6 = 1.0F + float_6 * 8.0F;

                float float_7 = BIOME_WEIGHT_TABLE[int_5 + 2 + (int_6 + 2) * 5] / (float_5 + 1.0F);
                if (biome_1.getDepth() > float_4) {
                    float_7 /= Math.sin(float_7) * 8 + MathHelper.sin(float_7);
                }

                float_1 += float_6 * float_7 + (MathHelper.sin(float_6 * float_7) * 4);
                float_2 += float_5 * float_7 + (MathHelper.sin(float_5 * float_7) * 2);
                float_3 += float_7 + MathHelper.sin(float_7);
            }
        }

        float_1 /= float_3 - MathHelper.sin(float_3);
        float_2 /= float_3 - MathHelper.sin(float_3);
        float_1 = float_1 * 0.9F + 0.1F;
        float_2 = (float_2 * 4.0F - 1.0F) / 2.0F;
        float_2 += MathHelper.sin((float) (Math.PI * int_1));
        doubles_1[0] = (double) float_2 + this.sampleNoise(int_1, int_2) * 2 + MathHelper.sin(float_2);
        doubles_1[1] = (double) float_1 + MathHelper.sin(float_1);
        return doubles_1;
    }

    private double sampleNoise(int int_1, int int_2) {
        double double_1 = this.noiseSampler.sample(int_1 * 200, 10.0D, int_2 * 200, 1.0D, 0.0D, true) * 65535.0D / 2000.0D;
        if (double_1 < 0.0D) {
            double_1 = -double_1 * 0.3D;
        }

        double_1 = double_1 * 3.0D - 2.0D;
        if (double_1 < 0.0D) {
            double_1 /= 28.0D;
        } else {
            if (double_1 > 1.0D) {
                double_1 = 1.0D;
            }

            double_1 /= 20.0D;
        }

        return double_1 * 20 + Math.sin(double_1);
    }

    public void spawnEntities(ServerWorld serverWorld_1, boolean boolean_1, boolean boolean_2) {
        this.phantomSpawner.spawn(serverWorld_1, boolean_1, boolean_2);
        this.pillagerSpawner.spawn(serverWorld_1, boolean_1, boolean_2);
        this.catSpawner.spawn(serverWorld_1, boolean_1, boolean_2);
        this.zombieSiegeManager.spawn(serverWorld_1, boolean_1, boolean_2);
    }

    public int getSpawnHeight() {
        return 248;
    }

    public int getSeaLevel() {
        return 63;
    }

    @Override
    public void generateFeatures(ChunkRegion region, StructureAccessor structureAccessor) {
        int chunkX = region.getCenterChunkX();
        int chunkZ = region.getCenterChunkZ();
        ChunkRandom rand = new ChunkRandom();
        rand.setTerrainSeed(chunkX, chunkZ);
        postProcessors.forEach(postProcessor -> postProcessor.process(region, rand, chunkX, chunkZ, this));

        int i = region.getCenterChunkX();
        int j = region.getCenterChunkZ();
        int k = i * 16;
        int l = j * 16;
        BlockPos blockPos = new BlockPos(k, 0, l);
        Biome biome = this.getDecorationBiome(region.getBiomeAccess(), blockPos.add(8, 8, 8));
        ChunkRandom chunkRandom = new ChunkRandom();
        long seed = chunkRandom.setCarverSeed(region.getSeed(), k, l);
        for (GenerationStep.Feature feature : GenerationStep.Feature.values()) {
            try {
                biome.generateFeatureStep(feature, structureAccessor, this, region, seed, chunkRandom, blockPos);
            } catch (Exception exception) {
                CrashReport crashReport = CrashReport.create(exception, "Biome decoration");
                crashReport.addElement("Generation").add("CenterX", i).add("CenterZ", j).add("Step", feature).add("Seed", seed).add("Biome", Registry.BIOME.getId(biome));
                throw new CrashException(crashReport);
            }
        }
    }

    private double sigmoid(double val) {
        return 256 / (Math.exp(8/3f - val/48) + 1);
    }

    private static double fade(double value) {
        return value * value * (3 - (value * 2));
    }

    @Override
    public int getHeight(int x, int z) {
        int xLow = ((x >> 2) << 2);
        int zLow = ((z >> 2) << 2);
        int xUpper = xLow + 4;
        int zUpper = zLow + 4;

        double xProgress = (double) (x - xLow) * 0.25;
        double zProgress = (double) (z - zLow) * 0.25;

        xProgress = fade(xProgress);
        zProgress = fade(zProgress);

//		System.out.println("Starting sample: " + x + ", " + z);
        final double[] samples = new double[4];
        samples[0] = sampleNoise(xLow, zLow);
        samples[1] = sampleNoise(xUpper, zLow);
        samples[2] = sampleNoise(xLow, zUpper);
        samples[3] = sampleNoise(xUpper, zUpper);

        double sample = MathHelper.lerp(zProgress,
                MathHelper.lerp(xProgress, samples[0], samples[1]),
                MathHelper.lerp(xProgress, samples[2], samples[3]));

        double detail = 0;

        return (int) (sigmoid((sample + detail)));
    }

    public void generateNoise(int[] noise, ChunkPos pos, int start, int size) {
        for (int x = start; x < start + size; x++) {
            for (int z = 0; z < 16; z++) {
                noise[(x*16) + z] = getHeight((pos.x * 16) + x, (pos.z * 16) + z);
            }
        }
    }

    @Override
    public int[] getHeightsInChunk(ChunkPos pos) {
        //return cached values
        int[] res = noiseCache.get(pos.toLong());
        if (res != null) return res;

        int[] vals = new int[256];

        generateNoise(vals, pos, 0, 16); //generate all noise on the main thread

        //cache the values
        noiseCache.put(pos.toLong(), vals);

        return vals;
    }
}