package io.github.vampirestudios.raa.generation.chunkgenerator.overworld;

import io.github.vampirestudios.raa.api.Heightmap;
import io.github.vampirestudios.raa.generation.chunkgenerator.BaseChunkGenerator;
import io.github.vampirestudios.raa.utils.noise.old.OctaveOpenSimplexNoise;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.ZombieSiegeManager;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.List;
import java.util.stream.IntStream;

public class PillarWorldChunkGenerator extends BaseChunkGenerator<OverworldChunkGeneratorConfig> implements Heightmap {
    private static final ChunkRandom reuseableRandom = new ChunkRandom();
    private final PhantomSpawner phantomSpawner = new PhantomSpawner();
    private final PillagerSpawner pillagerSpawner = new PillagerSpawner();
    private final CatSpawner catSpawner = new CatSpawner();
    private final ZombieSiegeManager zombieSiegeManager = new ZombieSiegeManager();

    private final OctaveOpenSimplexNoise simplexNoise;
    private final OctavePerlinNoiseSampler noiseSampler;

    public PillarWorldChunkGenerator(IWorld iWorld, BiomeSource biomeSource, OverworldChunkGeneratorConfig config) {
        super(iWorld, biomeSource, 8, 4, 256, config, false, iWorld.getSeed());
        this.random.consume(2620);
        this.noiseSampler = new OctavePerlinNoiseSampler(this.random, IntStream.of(15, 0));
        this.simplexNoise = new OctaveOpenSimplexNoise(this.random, 4, 1024.0D, 384.0D, -128.0D);
    }

    public void populateEntities(ChunkRegion region) {
        int centerChunkX = region.getCenterChunkX();
        int centerChunkZ = region.getCenterChunkZ();
        Biome biome = region.getBiome((new ChunkPos(centerChunkX, centerChunkZ)).getCenterBlockPos());
        ChunkRandom chunkRandom = new ChunkRandom();
        chunkRandom.setPopulationSeed(region.getSeed(), centerChunkX << 4, centerChunkZ << 4);
        SpawnHelper.populateEntities(region, biome, centerChunkX, centerChunkZ, chunkRandom);
    }

    protected void sampleNoiseColumn(double[] doubles_1, int int_1, int int_2) {
        this.sampleNoiseColumn(doubles_1, int_1, int_2, 684.4119873046875D, 684.4119873046875D, 8.555149841308594D, 4.277574920654297D, 1, -1);
    }

    protected double computeNoiseFalloff(double double_1, double double_2, int int_1) {
        double double_4 = ((double) int_1 - (8.5D + double_1 * 8.5D / 16.0D * 2.0D)) * 12.0D * 64 / 512 / double_2;
        if (double_4 < 0.0D) {
            double_4 *= 2.0D;
        }

        return double_4;
    }

    protected double[] computeNoiseRange(int x, int z) {
        double[] doubles = new double[2];
        doubles[0] = simplexNoise.sample(x, z) * 128;
        doubles[1] = simplexNoise.sample(z, x) * 128;
        return doubles;
    }

    public List<Biome.SpawnEntry> getEntitySpawnList(StructureAccessor StructureAccessor, SpawnGroup entityCategory_1, BlockPos blockPos_1) {
        if (Feature.SWAMP_HUT.method_14029(this.world, StructureAccessor, blockPos_1)) {
            if (entityCategory_1 == SpawnGroup.MONSTER) {
                return Feature.SWAMP_HUT.getMonsterSpawns();
            }

            if (entityCategory_1 == SpawnGroup.CREATURE) {
                return Feature.SWAMP_HUT.getCreatureSpawns();
            }
        } else if (entityCategory_1 == SpawnGroup.MONSTER) {
            if (Feature.PILLAGER_OUTPOST.isApproximatelyInsideStructure(this.world, StructureAccessor, blockPos_1)) {
                return Feature.PILLAGER_OUTPOST.getMonsterSpawns();
            }

            if (Feature.OCEAN_MONUMENT.isApproximatelyInsideStructure(this.world, StructureAccessor, blockPos_1)) {
                return Feature.OCEAN_MONUMENT.getMonsterSpawns();
            }
        }

        return super.getEntitySpawnList(StructureAccessor, entityCategory_1, blockPos_1);
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

        return -d;
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
