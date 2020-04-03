package io.github.vampirestudios.raa.generation.chunkgenerator;

import io.github.vampirestudios.raa.registries.ChunkGenerators;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.*;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;

import java.util.stream.IntStream;

public class RetroChunkGenerator extends ChunkGenerator<NoneGeneratorSettings> {
      private final OctaveSimplexNoiseSampler noise = new OctaveSimplexNoiseSampler(new ChunkRandom(1234L), IntStream.rangeClosed(-5, 0));

      public RetroChunkGenerator(IWorld iWorld, BiomeSource biomeSource, NoneGeneratorSettings arg) {
         super(iWorld, biomeSource, arg);
      }

      public void buildSurface(ChunkRegion region, Chunk chunk) {
      }

      public void generateFeatures(ChunkRegion region) {
      }

      public void carve(BiomeAccess biomeAccess, Chunk chunk, GenerationStep.Carver carver) {
      }

      public int getSpawnHeight() {
         return 100;
      }

      public void populateNoise(IWorld world, Chunk chunk) {
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

      public ChunkGeneratorType<?, ?> method_26490() {
         return ChunkGenerators.RETRO;
      }

}