package io.github.vampirestudios.raa.generation.surface;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;
import java.util.function.Function;

public class StratifiedSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    public StratifiedSurfaceBuilder(Function<Dynamic<?>, ? extends TernarySurfaceConfig> function_1) {
        super(function_1);
    }

    @Override
    public void generate(Random rand, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState stone, BlockState water, int var11, long seed, TernarySurfaceConfig config) {
        if (noise > 1.5) {
            BlockPos.Mutable pos = new BlockPos.Mutable(x, height, z);
            BlockPos.Mutable posTilHeight = new BlockPos.Mutable(x, 0, z);
            for (int i = 0; i <= height; i++) {
                chunk.setBlockState(posTilHeight, stone, false);
                posTilHeight.setOffset(Direction.UP);
            }
            for (int i = 0; i < (noise > 2.5 ? 20 : 12); i++) {
                chunk.setBlockState(pos, stone, false);
                pos.setOffset(Direction.UP);
            }
            int dirtHeight = (int) noise * 2;
            for (int i = 0; i < dirtHeight; i++) {
                chunk.setBlockState(pos, DIRT, false);
                pos.setOffset(Direction.UP);
            }
            chunk.setBlockState(pos, config.getTopMaterial(), false);
        } else {
            SurfaceBuilder.DEFAULT.generate(rand, chunk, biome, x, z, height, noise, stone, water, var11, seed, config);
        }
    }
}
