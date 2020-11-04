package io.github.vampirestudios.raa.generation.chunkgenerator;

import io.github.vampirestudios.raa.api.TerrainPostProcessor;
import io.github.vampirestudios.raa.api.noise.NoiseModifier;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.*;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.stream.IntStream;

public class RetroChunkGenerator extends ChunkGenerator<NoneGeneratorSettings> implements io.github.vampirestudios.raa.api.Heightmap {
    private static final ChunkRandom reuseableRandom = new ChunkRandom();
    private final OctaveSimplexNoiseSampler noise = new OctaveSimplexNoiseSampler(new ChunkRandom(1234L), IntStream.rangeClosed(-5, 0));
    private final OctavePerlinNoiseSampler noiseSampler;
    protected final ChunkRandom random;

    private static final Collection<TerrainPostProcessor> postProcessors = new ArrayList<>();

    public static void addTerrainPostProcessor(TerrainPostProcessor postProcessor) {
        postProcessors.add(postProcessor);
    }

    private static final Collection<NoiseModifier> noiseModifiers = new ArrayList<>();

    public static void addNoiseModifier(NoiseModifier noiseModifier) {
        noiseModifiers.add(noiseModifier);
    }

    private HashMap<Long, int[]> noiseCache = new HashMap<>();

    public RetroChunkGenerator(IWorld iWorld, BiomeSource biomeSource, NoneGeneratorSettings arg) {
        super(iWorld, biomeSource, arg);
        this.random = new ChunkRandom(this.seed);
        this.random.consume(Rands.randInt(100000));
        this.noiseSampler = new OctavePerlinNoiseSampler(this.random, IntStream.of(15, 0));
        postProcessors.forEach(postProcessor -> postProcessor.init(this.seed));
        noiseModifiers.forEach(noiseModifier -> noiseModifier.init(this.seed, reuseableRandom));
    }

    public void buildSurface(ChunkRegion region, Chunk chunk) {
    }

    public void carve(BiomeAccess biomeAccess, Chunk chunk, GenerationStep.Carver carver) {
    }

    public int getSpawnHeight() {
     return 100;
    }

    public void populateNoise(IWorld world, StructureAccessor StructureAccessor, Chunk chunk) {
     BlockState blockState = Blocks.BLACK_CONCRETE.getDefaultState();
     BlockState blockState2 = Blocks.LIME_CONCRETE.getDefaultState();
     ChunkPos chunkPos = chunk.getPos();
     BlockPos.Mutable mutable = new BlockPos.Mutable();

     for(int i = 0; i < 16; ++i) {
        for(int j = 0; j < 16; ++j) {
           double d = 64.0D + (this.noise.sample((float)chunkPos.x + (float)i / 16.0F, (float)chunkPos.z + (float)j / 16.0F, false) + 1.0D) * 20.0D;

           for(int k = 0; (double)k < d; ++k) {
              chunk.setBlockState(mutable.set(i, k, j), i != 0 && k % 16 != 0 && j != 0 ? blockState : blockState2, false);
           }
        }
     }

    }

    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
     return 100;
    }

    public BlockView getColumnSample(int x, int z) {
     return EmptyBlockView.INSTANCE;
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