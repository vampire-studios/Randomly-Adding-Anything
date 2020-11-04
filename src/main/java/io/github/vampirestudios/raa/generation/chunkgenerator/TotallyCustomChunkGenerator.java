package io.github.vampirestudios.raa.generation.chunkgenerator;

import io.github.vampirestudios.raa.api.Heightmap;
import io.github.vampirestudios.raa.api.TerrainPostProcessor;
import io.github.vampirestudios.raa.api.noise.NoiseModifier;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.IntStream;

public class TotallyCustomChunkGenerator extends SurfaceChunkGenerator<OverworldChunkGeneratorConfig> implements Heightmap {
    private static final ChunkRandom reuseableRandom = new ChunkRandom();
    private static final float[] BIOME_WEIGHT_TABLE = Util.make(new float[25], (floats) -> {
        for (int i = -2; i <= 2; ++i) {
            for (int i1 = -2; i1 <= 2; ++i1) {
                float v = 10.0F / MathHelper.sqrt((float) (i * i + i1 * i1) + 0.2F);
                floats[i + 2 + (i1 + 2) * 5] = v;
            }
        }

    });
    private final OctavePerlinNoiseSampler noiseSampler;

    private final float b1 = Rands.randFloatRange(256, 4096);
    private final float b2 = Rands.randFloatRange(256, 4096);
    private final float c1 = Rands.randFloatRange(2, 24);
    private final float c2 = Rands.randFloatRange(2, 24);
    private final int div = Rands.randIntRange(3, 64);
    private final int mod = Rands.randIntRange(-4096, 4096);

    private static final Collection<TerrainPostProcessor> postProcessors = new ArrayList<>();

    public static void addTerrainPostProcessor(TerrainPostProcessor postProcessor) {
        postProcessors.add(postProcessor);
    }

    private static final Collection<NoiseModifier> noiseModifiers = new ArrayList<>();

    public static void addNoiseModifier(NoiseModifier noiseModifier) {
        noiseModifiers.add(noiseModifier);
    }

    private HashMap<Long, int[]> noiseCache = new HashMap<>();

    public TotallyCustomChunkGenerator(IWorld iWorld_1, BiomeSource biomeSource_1, OverworldChunkGeneratorConfig overworldChunkGeneratorConfig_1) {
        super(iWorld_1, biomeSource_1, (int) Math.pow(2, Rands.randIntRange(3, 3)), (int) Math.pow(2, Rands.randIntRange(3, 3)), 256, overworldChunkGeneratorConfig_1, true);
        this.random.consume(Rands.randInt(100000));
        this.noiseSampler = new OctavePerlinNoiseSampler(this.random, IntStream.of(15, 0));

        postProcessors.forEach(postProcessor -> postProcessor.init(this.seed));
        noiseModifiers.forEach(noiseModifier -> noiseModifier.init(this.seed, reuseableRandom));
    }

    public void populateEntities(ChunkRegion chunkRegion) {
        int centerChunkX = chunkRegion.getCenterChunkX();
        int centerChunkZ = chunkRegion.getCenterChunkZ();
        Biome chunkRegionBiome = chunkRegion.getBiome((new ChunkPos(centerChunkX, centerChunkZ)).getCenterBlockPos());
        ChunkRandom chunkRandom = new ChunkRandom();
        chunkRandom.setPopulationSeed(chunkRegion.getSeed(), centerChunkX << 4, centerChunkZ << 4);
        SpawnHelper.populateEntities(chunkRegion, chunkRegionBiome, centerChunkX, centerChunkZ, chunkRandom);
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
        int seaLevel = this.getSeaLevel();
        float v = this.biomeSource.getBiomeForNoiseGen(x, seaLevel, z).getDepth() + 0.1f;

        for(int l = -2; l <= 2; ++l) {
            for(int m = -2; m <= 2; ++m) {
                Biome biome = this.biomeSource.getBiomeForNoiseGen(x + l, seaLevel, z + m);
                float n = biome.getDepth();
                float o = biome.getScale()+0.4f;

                float p = BIOME_WEIGHT_TABLE[l + 2 + (m + 2) * 5] / (n + 3.0F);
                if (biome.getDepth() > v) {
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