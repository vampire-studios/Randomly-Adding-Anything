package io.github.vampirestudios.raa.generation.chunkgenerator;

import io.github.vampirestudios.raa.generation.chunkgenerator.config.CustomOverworldChunkGeneratorConfig;
import net.minecraft.block.Blocks;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.noise.NoiseSampler;
import net.minecraft.util.math.noise.OctavePerlinNoiseSampler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ChunkRegion;
import net.minecraft.world.IWorld;
import net.minecraft.world.SpawnHelper;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import supercoder79.simplexterrain.api.Heightmap;
import supercoder79.simplexterrain.api.noise.Noise;
import supercoder79.simplexterrain.api.noise.NoiseType;
import supercoder79.simplexterrain.api.noise.OctaveNoiseSampler;
import supercoder79.simplexterrain.api.postprocess.TerrainPostProcessor;

import java.util.*;
import java.util.function.LongFunction;

public class CustomOverworldChunkGenerator extends ChunkGenerator<CustomOverworldChunkGeneratorConfig> implements Heightmap {
   private final NoiseSampler surfaceDepthNoise;

   private final OctaveNoiseSampler heightNoise;
   private final OctaveNoiseSampler detailNoise;
   private final OctaveNoiseSampler scaleNoise;
   private final OctaveNoiseSampler peaksNoise;

   private final Iterable<TerrainPostProcessor> terrainPostProcessors;

   public CustomOverworldChunkGenerator(IWorld world, BiomeSource biomeSource, CustomOverworldChunkGeneratorConfig config) {
      super(world, biomeSource, config);
      ChunkRandom random = new ChunkRandom(world.getSeed());

      double amplitude = Math.pow(2, this.getConfig().getBaseOctaveAmount());

      Class<? extends Noise> noiseClass = NoiseType.PERLIN.noiseClass;
      heightNoise = new OctaveNoiseSampler<>(noiseClass, random, this.getConfig().getBaseOctaveAmount(), this.getConfig().getBaseNoiseFrequencyCoefficient()  * amplitude, amplitude, amplitude);
      detailNoise = new OctaveNoiseSampler<>(noiseClass, random, this.getConfig().getDetailOctaveAmount(), config.getDetailFrequency(), this.getConfig().getDetailAmplitudeHigh(), this.getConfig().getDetailAmplitudeLow());
      scaleNoise = new OctaveNoiseSampler<>(noiseClass, random, this.getConfig().getScaleOctaveAmount(), Math.pow(2, this.getConfig().getScaleFrequencyExponent()), this.getConfig().getScaleAmplitudeHigh(),
              this.getConfig().getScaleAmplitudeLow());
      peaksNoise = new OctaveNoiseSampler<>(noiseClass, random, this.getConfig().getPeaksOctaveAmount(), this.getConfig().getPeaksFrequency(), 1.0, 1.0);

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
      this.terrainPostProcessors.forEach(postProcessor -> postProcessor.process(region, rand, chunkX, chunkZ, this));

      int i = region.getCenterChunkX();
      int j = region.getCenterChunkZ();
      int k = i * 16;
      int l = j * 16;
      BlockPos blockPos = new BlockPos(k, 0, l);
      Biome biome = this.getDecorationBiome(region.getBiomeAccess(), blockPos.add(8, 8, 8));
      ChunkRandom chunkRandom = new ChunkRandom();
      long seed = chunkRandom.setSeed(region.getSeed(), k, l);
      GenerationStep.Feature[] features = GenerationStep.Feature.values();

      for (GenerationStep.Feature feature : features) {
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
            chunkRegion.getBiome(mutable.set(startX + localX, height, startZ + localZ)).buildSurface(chunkRandom, chunk, x, z, height, noise, this.getConfig().getDefaultBlock(), this.getConfig().getDefaultFluid(),
                    this.getSeaLevel(), this.world.getSeed());
         }
      }

      this.buildBedrock(chunk, chunkRandom);
   }

   private void buildBedrock(Chunk chunk, Random random) {
      BlockPos.Mutable mutable = new BlockPos.Mutable();
      int i = chunk.getPos().getStartX();
      int j = chunk.getPos().getStartZ();
      CustomOverworldChunkGeneratorConfig chunkGeneratorConfig = this.getConfig();
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

   public int getSpawnHeight() {
      return this.getConfig().getSeaLevel();
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

      double detail = sampleDetail(x, z);
      return (int) (sample + detail);
   }

   private double sampleNoise(int x, int z) {
      double amplitudeSample = this.scaleNoise.sample(x, z) + this.config.getScaleAmplitudeLow(); // change range to have a minimum value of 0.0
      return this.heightNoise.sampleCustom(x, z, this.config.getBaseNoiseSamplingFrequency(), amplitudeSample, amplitudeSample, this.config.getBaseOctaveAmount())
              + modifyPeaksNoise(this.peaksNoise.sample(x, z))
              + this.config.getBaseHeight();
   }

   private static double modifyPeaksNoise(double sample) {
      sample += -0.33;
      if (sample < 0) {
         return 0;
      } else {
         return sample * 280;
      }
   }

   private double sampleDetail(int x, int z) {
      double sample = detailNoise.sample(x, z);
      if (sample < this.config.getDetailNoiseThreshold()) {
         if (scaleNoise.sample(x, z) < this.config.getScaleNoiseThreshold()) {
            sample = 0;
         }
      }
      return sample;
   }

}