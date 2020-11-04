package io.github.vampirestudios.raa.generation.chunkgenerator.overworld;

import io.github.vampirestudios.raa.api.Heightmap;
import io.github.vampirestudios.raa.generation.chunkgenerator.BaseChunkGenerator;
import net.minecraft.entity.SpawnGroup;
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
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.level.LevelGeneratorType;

import java.util.List;
import java.util.stream.IntStream;

public class RollingHillsChunkGenerator extends BaseChunkGenerator<OverworldChunkGeneratorConfig> implements Heightmap {
    private static final ChunkRandom reuseableRandom = new ChunkRandom();
    private static final float[] BIOME_WEIGHT_TABLE = Util.make(new float[25], (fs) -> {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                float f = 10.0F / MathHelper.sqrt((float) (i * i + j * j) + 0.2F);
                fs[i + 2 + (j + 2) * 5] = f;
            }
        }

    });
    private final OctavePerlinNoiseSampler noiseSampler;
    private final boolean amplified;
    private final PhantomSpawner phantomSpawner = new PhantomSpawner();
    private final PillagerSpawner pillagerSpawner = new PillagerSpawner();
    private final CatSpawner catSpawner = new CatSpawner();
    private final ZombieSiegeManager zombieSiegeManager = new ZombieSiegeManager();

    //from surface chunk generator
    private final OctavePerlinNoiseSampler field_16574;
    private final OctavePerlinNoiseSampler field_16581;
    private final OctavePerlinNoiseSampler field_16575;

    public RollingHillsChunkGenerator(IWorld world, BiomeSource biomeSource, OverworldChunkGeneratorConfig config) {
        super(world, biomeSource, 4, 8, 256, config, false, world.getSeed());
        this.random.consume(2620);
        this.noiseSampler = new OctavePerlinNoiseSampler(this.random, IntStream.of(15, 0));
        this.amplified = world.getLevelProperties().getGeneratorType() == LevelGeneratorType.AMPLIFIED;

        this.field_16574 = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
        this.field_16581 = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-15, 0));
        this.field_16575 = new OctavePerlinNoiseSampler(this.random, IntStream.rangeClosed(-7, 0));
    }

    public void populateEntities(ChunkRegion region) {
        int i = region.getCenterChunkX();
        int j = region.getCenterChunkZ();
        Biome biome = region.getBiome((new ChunkPos(i, j)).getCenterBlockPos());
        ChunkRandom chunkRandom = new ChunkRandom();
        chunkRandom.setPopulationSeed(region.getSeed(), i << 4, j << 4);
        SpawnHelper.populateEntities(region, biome, i, j, chunkRandom);
    }

    protected void sampleNoiseColumn(double[] buffer, int x, int z) {
        this.sampleNoiseColumn(buffer, x, z, 684.4119873046875D, 684.4119873046875D, 8.555149841308594D, 4.277574920654297D, 3, -10);
    }

    @Override
    protected void sampleNoiseColumn(double[] buffer, int x, int z, double d, double e, double f, double g, int i, int j) {
        double[] ds = this.computeNoiseRange(x, z);
        double h = ds[0];
        double k = ds[1];
        double l = this.method_16409();
        double m = this.method_16410();

        for(int n = 0; n < this.getNoiseSizeY(); ++n) {
            double o = this.sampleNoise(x, n, z, d, e, f, g);
            o -= this.computeNoiseFalloff(h, k, n);
            if ((double)n > l) {
                o = MathHelper.clampedLerp(o, (double)j, ((double)n - l) / (double)i);
            } else if ((double)n < m) {
                o = MathHelper.clampedLerp(o, -30.f, (m - (double)n) / (m - 1.0D));
            }

            buffer[n] = o;
        }

    }

    public double sampleNoise(int x, int y, int z, double d, double e, double f, double g) {
        double h = 0.0D;
        double i = 0.0D;
        double j = 0.0D;
        double k = 1.0D;

        for(int l = 0; l < 16; ++l) {
            double m = OctavePerlinNoiseSampler.maintainPrecision((double)x * d * k);
            double n = OctavePerlinNoiseSampler.maintainPrecision((double)y * e * k);
            double o = OctavePerlinNoiseSampler.maintainPrecision((double)z * d * k);
            double p = e * k;
            PerlinNoiseSampler perlinNoiseSampler = this.field_16574.getOctave(l);
            if (perlinNoiseSampler != null) {
                h += perlinNoiseSampler.sample(m, n, o, p, (double)y * p) / k;
            }

            PerlinNoiseSampler perlinNoiseSampler2 = this.field_16581.getOctave(l);
            if (perlinNoiseSampler2 != null) {
                i += perlinNoiseSampler2.sample(m, n, o, p, (double)y * p) / k;
            }

            if (l < 8) {
                PerlinNoiseSampler perlinNoiseSampler3 = this.field_16575.getOctave(l);
                if (perlinNoiseSampler3 != null) {
                    j += perlinNoiseSampler3.sample(OctavePerlinNoiseSampler.maintainPrecision((double)x * f * k), OctavePerlinNoiseSampler.maintainPrecision((double)y * g * k), OctavePerlinNoiseSampler.maintainPrecision((double)z * f * k), g * k, (double)y * g * k) / k;
                }
            }

            k /= 2.0D;
        }

        return MathHelper.clampedLerp(h / 512.0D, i / 512.0D, (j / 10.0D + 1.0D) / 2.0D);
    }

    protected double computeNoiseFalloff(double depth, double scale, int y) {
        double d = 9.5D;
        double e = ((double) y - (d + depth * d / 8.0D * 4.0D)) * 12.0D * 128.0D / 256.0D / scale;
        if (e < 0.0D) {
            e *= 4.0D;
        }

//        e += (Math.sin(e) * ((y + 8) / 4f));

        return e;
    }

    protected double[] computeNoiseRange(int x, int z) {
        double[] ds = new double[2];
        float f = 0.0F;
        float g = 0.0F;
        float h = 0.0F;
        int j = this.getSeaLevel();
        float k = this.biomeSource.getBiomeForNoiseGen(x, j, z).getDepth();

        for (int l = -2; l <= 2; ++l) {
            for (int m = -2; m <= 2; ++m) {
                Biome biome = this.biomeSource.getBiomeForNoiseGen(x + l, j, z + m);
                float n = biome.getDepth();
                float o = biome.getScale();
                if (this.amplified && n > 0.0F) {
                    n = 1.0F + n * 2.0F;
                    o = 1.0F + o * 4.0F;
                }

                float p = BIOME_WEIGHT_TABLE[l + 2 + (m + 2) * 5] / (n + 2.0F);
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
        f = f * 0.9F + 0.1F;
        g = (g * 4.0F - 1.0F) / 8.0F;
        ds[0] = (double) g + (this.sampleNoise(x, z));
        ds[1] = 0.1;
        return ds;
    }

    private double sampleNoise(int x, int y) {
        double d = this.noiseSampler.sample(x / 50f, 10.0D, y / 50f, 1.0D, 0.0D, true) * 65535.0D / 8000.0D;
        if (d < 0.0D) {
            d = -d * 0.7D;
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

        return d * 5f;
    }

    public List<Biome.SpawnEntry> getEntitySpawnList(SpawnGroup category, StructureAccessor StructureAccessor, BlockPos pos) {
        if (Feature.SWAMP_HUT.method_14029(this.world, StructureAccessor, pos)) {
            if (category == SpawnGroup.MONSTER) {
                return Feature.SWAMP_HUT.getMonsterSpawns();
            }

            if (category == SpawnGroup.CREATURE) {
                return Feature.SWAMP_HUT.getCreatureSpawns();
            }
        } else if (category == SpawnGroup.MONSTER) {
            if (Feature.PILLAGER_OUTPOST.isApproximatelyInsideStructure(this.world, StructureAccessor, pos)) {
                return Feature.PILLAGER_OUTPOST.getMonsterSpawns();
            }

            if (Feature.OCEAN_MONUMENT.isApproximatelyInsideStructure(this.world, StructureAccessor, pos)) {
                return Feature.OCEAN_MONUMENT.getMonsterSpawns();
            }
        }

        return super.getEntitySpawnList(StructureAccessor, category, pos);
    }

    public void spawnEntities(ServerWorld serverWorld, boolean spawnMonsters, boolean spawnAnimals) {
        this.phantomSpawner.spawn(serverWorld, spawnMonsters, spawnAnimals);
        this.pillagerSpawner.spawn(serverWorld, spawnMonsters, spawnAnimals);
        this.catSpawner.spawn(serverWorld, spawnMonsters, spawnAnimals);
        this.zombieSiegeManager.spawn(serverWorld, spawnMonsters, spawnAnimals);
    }

    public int getSpawnHeight() {
        return this.world.getSeaLevel() + 1;
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