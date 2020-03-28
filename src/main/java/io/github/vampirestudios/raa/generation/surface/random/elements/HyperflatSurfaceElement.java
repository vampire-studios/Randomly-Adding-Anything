package io.github.vampirestudios.raa.generation.surface.random.elements;

import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.generation.surface.random.SurfaceElement;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;
import java.util.stream.IntStream;

public class HyperflatSurfaceElement extends SurfaceElement {
    public static OctaveSimplexNoiseSampler HEIGHT = new OctaveSimplexNoiseSampler(new ChunkRandom(79), IntStream.of(4, 0));
    public static OctaveSimplexNoiseSampler WATER_NOISE = new OctaveSimplexNoiseSampler(new ChunkRandom(7979), IntStream.of(4, 0));

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        BlockPos.Mutable delPos = new BlockPos.Mutable(x, height, z);
        for (int i = 0; i < height; i++) {
            chunk.setBlockState(delPos, Blocks.AIR.getDefaultState(), false);
            delPos.offset(Direction.DOWN);
        }

        double noiseHeight = HEIGHT.sample(x * 0.05, z * 0.05, false);
        BlockPos.Mutable pos = new BlockPos.Mutable(x, 0, z);
        for (int i = 0; i < 80 + (noiseHeight * 8); i++) {
            chunk.setBlockState(pos, defaultBlock, false);
            pos.offset(Direction.UP);
        }
        for (int i = 0; i < 3; i++) {
            chunk.setBlockState(pos, surfaceBlocks.getUnderMaterial(), false);
            pos.offset(Direction.UP);
        }
        if (noiseHeight > 0) {
            chunk.setBlockState(pos.add(0, -1, 0), surfaceBlocks.getTopMaterial(), false);
            if (pos.getY() == 84) chunk.setBlockState(pos.add(0, -1, 0), Blocks.SAND.getDefaultState(), false);
        } else {
            chunk.setBlockState(pos.add(0, -1, 0), WATER_NOISE.sample(x * 0.05, z * 0.05, false) > 0.2 ? Blocks.GRAVEL.getDefaultState() : Blocks.SAND.getDefaultState(), false);
            while (pos.getY() < 84) {
                chunk.setBlockState(pos, defaultFluid, false);
                pos.offset(Direction.UP);
            }
        }
    }

    @Override
    public void serialize(JsonObject obj) { }

    @Override
    public void deserialize(JsonObject obj) {}

    @Override
    public Identifier getType() {
        return new Identifier("raa", "floating_island");
    }

    @Override
    public int getPriority() {
        return 100;
    }

}
