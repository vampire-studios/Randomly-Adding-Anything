package io.github.vampirestudios.raa.generation.chunkgenerator.overworld;

import net.minecraft.entity.EntityCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.village.ZombieSiegeManager;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.CatSpawner;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.PhantomSpawner;
import net.minecraft.world.gen.PillagerSpawner;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.SurfaceChunkGenerator;
import net.minecraft.world.gen.feature.Feature;

import java.util.List;
import java.util.stream.IntStream;

public class PillarWorldChunkGenerator extends SurfaceChunkGenerator<OverworldChunkGeneratorConfig> {
    private final PhantomSpawner phantomSpawner = new PhantomSpawner();
    private final PillagerSpawner pillagerSpawner = new PillagerSpawner();
    private final CatSpawner catSpawner = new CatSpawner();
    private final ZombieSiegeManager zombieSiegeManager = new ZombieSiegeManager();

    private final OctaveSimplexNoiseSampler simplexNoise;

    public PillarWorldChunkGenerator(IWorld iWorld_1, BiomeSource biomeSource_1, OverworldChunkGeneratorConfig overworldChunkGeneratorConfig_1) {
        super(iWorld_1, biomeSource_1, 8, 4, 256, overworldChunkGeneratorConfig_1, true);
        this.random.consume(2620);
        this.simplexNoise = new OctaveSimplexNoiseSampler(this.random, IntStream.of(4, 0));
    }

    public void populateEntities(ChunkRegion chunkRegion_1) {
        int int_1 = chunkRegion_1.getCenterChunkX();
        int int_2 = chunkRegion_1.getCenterChunkZ();
        Biome biome_1 = chunkRegion_1.getBiome((new ChunkPos(int_1, int_2)).getCenterBlockPos());
        ChunkRandom chunkRandom_1 = new ChunkRandom();
        chunkRandom_1.setSeed(chunkRegion_1.getSeed(), int_1 << 4, int_2 << 4);
        SpawnHelper.populateEntities(chunkRegion_1, biome_1, int_1, int_2, chunkRandom_1);
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
        double[] doubles_1 = new double[2];
        doubles_1[0] = simplexNoise.sample(x, z, false) * 128;
        doubles_1[1] = simplexNoise.sample(z, x, false) * 128;
        return doubles_1;
    }

    public List<Biome.SpawnEntry> getEntitySpawnList(EntityCategory entityCategory_1, BlockPos blockPos_1) {
        if (Feature.SWAMP_HUT.method_14029(this.world, blockPos_1)) {
            if (entityCategory_1 == EntityCategory.MONSTER) {
                return Feature.SWAMP_HUT.getMonsterSpawns();
            }

            if (entityCategory_1 == EntityCategory.CREATURE) {
                return Feature.SWAMP_HUT.getCreatureSpawns();
            }
        } else if (entityCategory_1 == EntityCategory.MONSTER) {
            if (Feature.PILLAGER_OUTPOST.isApproximatelyInsideStructure(this.world, blockPos_1)) {
                return Feature.PILLAGER_OUTPOST.getMonsterSpawns();
            }

            if (Feature.OCEAN_MONUMENT.isApproximatelyInsideStructure(this.world, blockPos_1)) {
                return Feature.OCEAN_MONUMENT.getMonsterSpawns();
            }
        }

        return super.getEntitySpawnList(entityCategory_1, blockPos_1);
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
}
