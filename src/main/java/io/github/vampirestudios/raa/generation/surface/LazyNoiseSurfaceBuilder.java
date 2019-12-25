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

public class LazyNoiseSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    public LazyNoiseSurfaceBuilder(Function<Dynamic<?>, ? extends TernarySurfaceConfig> function_1) {
        super(function_1);
    }

    @Override
    public void generate(Random rand, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState stone, BlockState water, int var11, long seed, TernarySurfaceConfig config) {
        BlockPos.Mutable delPos = new BlockPos.Mutable(x, height, z);
        for (int i = 0; i < height; i++) {
            chunk.setBlockState(delPos, AIR, false);
            delPos.setOffset(Direction.DOWN);
        }

        BlockPos.Mutable pos = new BlockPos.Mutable(x, 1, z);
        for (int i = 0; i < 80 + (Math.abs(noise) * 8); i++) {
            chunk.setBlockState(pos, stone, false);
            pos.setOffset(Direction.UP);
        }
        for (int i = 0; i < 3; i++) {
            chunk.setBlockState(pos, DIRT, false);
            pos.setOffset(Direction.UP);
        }
        chunk.setBlockState(pos, config.getTopMaterial(), false);
    }
}
