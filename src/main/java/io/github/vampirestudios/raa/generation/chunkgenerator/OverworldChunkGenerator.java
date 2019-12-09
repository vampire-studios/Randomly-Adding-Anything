package io.github.vampirestudios.raa.generation.chunkgenerator;

import io.github.vampirestudios.raa.api.Heightmap;
import io.github.vampirestudios.raa.generation.TerrainPostProcessor;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityCategory;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.NoiseSampler;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.ZombieSiegeManager;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.*;
import java.util.function.LongFunction;

public class OverworldChunkGenerator extends ChunkGenerator<OverworldChunkGeneratorConfig> implements Heightmap {
   private final NoiseSampler surfaceDepthNoise;
   private final PhantomSpawner phantomSpawner = new PhantomSpawner();
   private final PillagerSpawner pillagerSpawner = new PillagerSpawner();
   private final CatSpawner catSpawner = new CatSpawner();
   private final ZombieSiegeManager zombieSiegeManager = new ZombieSiegeManager();

   private final Iterable<TerrainPostProcessor> terrainPostProcessors;

   public OverworldChunkGenerator(IWorld world, BiomeSource biomeSource, OverworldChunkGeneratorConfig config) {
      super(world, biomeSource, config);
      ChunkRandom random = new ChunkRandom(world.getSeed());
      this.surfaceDepthNoise = new OctavePerlinNoiseSampler(random, 4, 0);

      List<TerrainPostProcessor> postProcessors = new ArrayList<>();
      postProcessorFactories.forEach(factory -> postProcessors.add(factory.apply(this.seed)));
      terrainPostProcessors = postProcessors;
   }

   private static final Collection<LongFunction<TerrainPostProcessor>> postProcessorFactories = new ArrayList<>();

   public static void addTerrainPostProcessor(LongFunction<TerrainPostProcessor> factory) {
      postProcessorFactories.add(factory);
   }

   @Override
   public void generateFeatures(ChunkRegion region) {
      int chunkX = region.getCenterChunkX();
      int chunkZ = region.getCenterChunkZ();
      ChunkRandom rand = new ChunkRandom();
      rand.setSeed(chunkX, chunkZ);
      this.terrainPostProcessors.forEach(postProcessor -> postProcessor.postProcess(region, rand, chunkX, chunkZ, this));

      int i = region.getCenterChunkX();
      int j = region.getCenterChunkZ();
      int k = i * 16;
      int l = j * 16;
      BlockPos blockPos = new BlockPos(k, 0, l);
      Biome biome = this.getDecorationBiome(region.getBiomeAccess(), blockPos.add(8, 8, 8));
      ChunkRandom chunkRandom = new ChunkRandom();
      long seed = chunkRandom.setSeed(region.getSeed(), k, l);

      for (GenerationStep.Feature feature : GenerationStep.Feature.values()) {
         try {
            biome.generateFeatureStep(feature, this, region, seed, chunkRandom, blockPos);
         } catch (Exception exception) {
            CrashReport crashReport = CrashReport.create(exception, "Biome decoration");
            crashReport.addElement("Generation").add("CenterX", i).add("CenterZ", j).add("Step", feature).add("Seed", seed).add("Biome", Registry.BIOME.getId(biome));
            throw new CrashException(crashReport);
         }
      }
   }

   @Override
   public void buildSurface(ChunkRegion chunkRegion, Chunk chunk) {
      ChunkPos chunkPos = chunk.getPos();
      int i = chunkPos.x;
      int j = chunkPos.z;
      ChunkRandom chunkRandom = new ChunkRandom();
      chunkRandom.setSeed(i, j);
      ChunkPos chunkPos2 = chunk.getPos();
      int startX = chunkPos2.getStartX();
      int startZ = chunkPos2.getStartZ();
      BlockPos.Mutable mutable = new BlockPos.Mutable();

      for(int localX = 0; localX < 16; ++localX) {
         for(int localZ = 0; localZ < 16; ++localZ) {
            int x = startX + localX;
            int z = startZ + localZ;
            int height = chunk.sampleHeightmap(net.minecraft.world.Heightmap.Type.WORLD_SURFACE_WG, localX, localZ) + 1;
            double noise = this.surfaceDepthNoise.sample((double)x * 0.0625D, (double)z * 0.0625D, 0.0625D, (double)localX * 0.0625D);
            chunkRegion.getBiome(mutable.set(startX + localX, height, startZ + localZ)).buildSurface(chunkRandom, chunk, x, z, height, noise, this.getConfig().getDefaultBlock(), this.getConfig().getDefaultFluid(), this.getSeaLevel(), this.world.getSeed());
         }
      }

      this.buildBedrock(chunk, chunkRandom);
   }

   private void buildBedrock(Chunk chunk, Random random) {
      BlockPos.Mutable mutable = new BlockPos.Mutable();
      int i = chunk.getPos().getStartX();
      int j = chunk.getPos().getStartZ();
      OverworldChunkGeneratorConfig chunkGeneratorConfig = this.getConfig();
      int k = chunkGeneratorConfig.getMinY();
      int l = chunkGeneratorConfig.getMaxY();
      Iterator<BlockPos> var9 = BlockPos.iterate(i, 0, j, i + 15, 0, j + 15).iterator();

      while(true) {
         BlockPos blockPos;
         int n;
         do {
            if (!var9.hasNext()) {
               return;
            }

            blockPos = var9.next();
            if (l > 0) {
               for(n = l; n >= l - 4; --n) {
                  if (n >= l - random.nextInt(5)) {
                     chunk.setBlockState(mutable.set(blockPos.getX(), n, blockPos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
                  }
               }
            }
         } while(k >= 256);

         for(n = k + 4; n >= k; --n) {
            if (n <= k + random.nextInt(5)) {
               chunk.setBlockState(mutable.set(blockPos.getX(), n, blockPos.getZ()), Blocks.BEDROCK.getDefaultState(), false);
            }
         }
      }
   }

   public void populateEntities(ChunkRegion region) {
      int i = region.getCenterChunkX();
      int j = region.getCenterChunkZ();
      Biome biome = region.getBiome((new ChunkPos(i, j)).getCenterBlockPos());
      ChunkRandom chunkRandom = new ChunkRandom();
      chunkRandom.setSeed(region.getSeed(), i << 4, j << 4);
      SpawnHelper.populateEntities(region, biome, i, j, chunkRandom);
   }

   @Override
   public void populateNoise(IWorld world, Chunk chunk) {
      BlockPos.Mutable posMutable = new BlockPos.Mutable();

      int chunkX = chunk.getPos().x;
      int chunkZ = chunk.getPos().z;

      for (int x = 0; x < 16; ++x) {
         posMutable.setX(x);

         for (int z = 0; z < 16; ++z) {
            posMutable.setZ(z);
            int height = getHeight((chunkX * 16) + x, (chunkZ * 16) + z);

            for (int y = 0; y < 256; ++y) {
               posMutable.setY(y);
               if (height >= y) {
                  chunk.setBlockState(posMutable, config.getDefaultBlock(), false);
               } else if (y < 63) {
                  chunk.setBlockState(posMutable, Blocks.WATER.getDefaultState(), false);
               }
               //TODO: see if this actually improves performance
               if (y > height && y > 63) break;
            }
         }
      }
   }

   private double sampleNoise(int x, int y) {
      double d = this.surfaceDepthNoise.sample(x * 200, 10.0D, y * 200, 1.0D) * 65535.0D / 8000.0D;
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

   public List<Biome.SpawnEntry> getEntitySpawnList(EntityCategory category, BlockPos pos) {
      if (Feature.SWAMP_HUT.method_14029(this.world, pos)) {
         if (category == EntityCategory.MONSTER) {
            return Feature.SWAMP_HUT.getMonsterSpawns();
         }

         if (category == EntityCategory.CREATURE) {
            return Feature.SWAMP_HUT.getCreatureSpawns();
         }
      } else if (category == EntityCategory.MONSTER) {
         if (Feature.PILLAGER_OUTPOST.isApproximatelyInsideStructure(this.world, pos)) {
            return Feature.PILLAGER_OUTPOST.getMonsterSpawns();
         }

         if (Feature.OCEAN_MONUMENT.isApproximatelyInsideStructure(this.world, pos)) {
            return Feature.OCEAN_MONUMENT.getMonsterSpawns();
         }
      }

      return super.getEntitySpawnList(category, pos);
   }

   public void spawnEntities(ServerWorld serverWorld, boolean spawnMonsters, boolean spawnAnimals) {
      this.phantomSpawner.spawn(serverWorld, spawnMonsters, spawnAnimals);
      this.pillagerSpawner.spawn(serverWorld, spawnMonsters, spawnAnimals);
      this.catSpawner.spawn(serverWorld, spawnMonsters, spawnAnimals);
      this.zombieSiegeManager.tick(serverWorld, spawnMonsters, spawnAnimals);
   }

   public int getSpawnHeight() {
      return this.world.getSeaLevel() + 1;
   }

   public int getSeaLevel() {
      return 63;
   }

   @Override
   public int getHeightOnGround(int x, int z, net.minecraft.world.Heightmap.Type heightmapType) {
      return getHeight(x, z);
   }

   @Override
   public int getHeight(int x, int z) {
      int subX = ((x >> 2) << 2);
      int subZ = ((z >> 2) << 2);
      int subXUpper = subX + 4;
      int subZUpper = subZ + 4;

      double xProgress = (double) (x - subX) / 4.0;
      double zProgress = (double) (z - subZ) / 4.0;

      double sampleNW = sampleNoise(subX, subZ);
      double sampleNE = sampleNoise(subXUpper, subZ);
      double sampleSW = sampleNoise(subX, subZUpper);
      double sampleSE = sampleNoise(subXUpper, subZUpper);

      double sample = MathHelper.lerp(zProgress,
              MathHelper.lerp(xProgress, sampleNW, sampleNE),
              MathHelper.lerp(xProgress, sampleSW, sampleSE));

      double detail = 0;
      /*if (SimplexTerrain.CONFIG.addDetailNoise) {
         detail = sampleDetail(x, z);
      }*/
      return (int) (sample + detail);
   }
}