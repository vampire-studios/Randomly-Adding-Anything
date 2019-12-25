package io.github.vampirestudios.raa.generation.surface;

import com.mojang.datafixers.Dynamic;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;
import java.util.function.Function;

public class HyperflatSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    public static OctaveSimplexNoiseSampler HEIGHT = new OctaveSimplexNoiseSampler(new ChunkRandom(79), 4, 0);
    public static OctaveSimplexNoiseSampler WATER_NOISE = new OctaveSimplexNoiseSampler(new ChunkRandom(7979), 4, 0);

    public HyperflatSurfaceBuilder(Function<Dynamic<?>, ? extends TernarySurfaceConfig> function_1) {
        super(function_1);
    }

    @Override
    public void generate(Random rand, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState stone, BlockState water, int var11, long seed, TernarySurfaceConfig config) {
        BlockPos.Mutable delPos = new BlockPos.Mutable(x, height, z);
        for (int i = 0; i < height; i++) {
            chunk.setBlockState(delPos, AIR, false);
            delPos.setOffset(Direction.DOWN);
        }

        double noiseHeight = HEIGHT.sample(x * 0.05, z * 0.05, false);
        BlockPos.Mutable pos = new BlockPos.Mutable(x, 0, z);
        for (int i = 0; i < 80 + (noiseHeight * 8); i++) {
            chunk.setBlockState(pos, stone, false);
            pos.setOffset(Direction.UP);
        }
        for (int i = 0; i < 3; i++) {
            chunk.setBlockState(pos, DIRT, false);
            pos.setOffset(Direction.UP);
        }
        if (noiseHeight > 0) {
            chunk.setBlockState(pos.add(0, -1, 0), config.getTopMaterial(), false);
            if (pos.getY() == 84) chunk.setBlockState(pos.add(0, -1, 0), SAND, false);
        } else {
            chunk.setBlockState(pos.add(0, -1, 0), WATER_NOISE.sample(x * 0.05, z * 0.05, false) > 0.2 ? GRAVEL : SAND, false);
            while (pos.getY() < 84) {
                chunk.setBlockState(pos, water, false);
                pos.setOffset(Direction.UP);
            }
        }
    }
}
