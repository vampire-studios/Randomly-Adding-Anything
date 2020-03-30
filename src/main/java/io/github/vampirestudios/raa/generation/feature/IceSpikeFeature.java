package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class IceSpikeFeature extends Feature<DefaultFeatureConfig> {

   private Block blockToUse;

   public IceSpikeFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
      super(function);
   }

   public boolean generate(IWorld iWorld, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random random, BlockPos blockPos, DefaultFeatureConfig defaultFeatureConfig) {
      while(iWorld.isAir(blockPos) && blockPos.getY() > 2) {
         blockPos = blockPos.down();
      }

      blockPos = blockPos.up(random.nextInt(4));
      int i = random.nextInt(4) + 7;
      int j = i / 4 + random.nextInt(2);
      if (j > 1 && random.nextInt(60) == 0) {
         blockPos = blockPos.up(10 + random.nextInt(30));
      }

      int k;
      int l;
      for(k = 0; k < i; ++k) {
         float f = (1.0F - (float)k / (float)i) * (float)j;
         l = MathHelper.ceil(f);

         for(int m = -l; m <= l; ++m) {
            float g = (float)MathHelper.abs(m) - 0.25F;

            for(int n = -l; n <= l; ++n) {
               float h = (float)MathHelper.abs(n) - 0.25F;
               if ((m == 0 && n == 0 || g * g + h * h <= f * f) && (m != -l && m != l && n != -l && n != l || random.nextFloat() <= 0.75F)) {
                  BlockState blockState = iWorld.getBlockState(blockPos.add(m, k, n));
                  Block block = blockState.getBlock();
                  if (blockState.isAir() || isDirt(block)) {
                     this.setBlockState(iWorld, blockPos.add(m, k, n), Blocks.PACKED_ICE.getDefaultState());
                  }

                  if (k != 0 && l > 1) {
                     blockState = iWorld.getBlockState(blockPos.add(m, -k, n));
                     block = blockState.getBlock();
                     if (blockState.isAir() || isDirt(block)) {
                        this.setBlockState(iWorld, blockPos.add(m, -k, n), Blocks.PACKED_ICE.getDefaultState());
                     }
                  }
               }
            }
         }
      }

      k = j - 1;
      if (k < 0) {
         k = 0;
      } else if (k > 1) {
         k = 1;
      }

      for(int p = -k; p <= k; ++p) {
         for(l = -k; l <= k; ++l) {
            BlockPos blockPos2 = blockPos.add(p, -1, l);
            int r = 50;
            if (Math.abs(p) == 1 && Math.abs(l) == 1) {
               r = random.nextInt(5);
            }

            while(blockPos2.getY() > 50) {
               this.setBlockState(iWorld, blockPos2, Blocks.PACKED_ICE.getDefaultState());
               blockPos2 = blockPos2.down();
               --r;
               if (r <= 0) {
                  blockPos2 = blockPos2.down(random.nextInt(5) + 1);
                  r = random.nextInt(5);
               }
            }
         }
      }

      return true;
   }
}
