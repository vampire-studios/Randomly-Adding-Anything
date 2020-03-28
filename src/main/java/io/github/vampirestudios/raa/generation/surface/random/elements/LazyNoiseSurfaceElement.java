package io.github.vampirestudios.raa.generation.surface.random.elements;

import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.generation.surface.random.SurfaceElement;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class LazyNoiseSurfaceElement extends SurfaceElement {

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        BlockPos.Mutable delPos = new BlockPos.Mutable(x, height, z);
        for (int i = 0; i < height; i++) {
            chunk.setBlockState(delPos, Blocks.AIR.getDefaultState(), false);
            delPos.offset(Direction.DOWN);
        }

        BlockPos.Mutable pos = new BlockPos.Mutable(x, 1, z);
        for (int i = 0; i < 80 + (Math.abs(noise) * 8); i++) {
            chunk.setBlockState(pos, defaultBlock, false);
            pos.offset(Direction.UP);
        }
        for (int i = 0; i < 3; i++) {
            chunk.setBlockState(pos, surfaceBlocks.getUnderMaterial(), false);
            pos.offset(Direction.UP);
        }
        chunk.setBlockState(pos, surfaceBlocks.getTopMaterial(), false);
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
