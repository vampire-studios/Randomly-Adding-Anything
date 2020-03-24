package io.github.vampirestudios.raa.generation.surface.random;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public abstract class SurfaceElement {
    public abstract void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks);

    public abstract void serialize(JsonObject obj);
    public abstract void deserialize(JsonObject obj);

    public abstract Identifier getType();

    public abstract int getPriority();

    //Utilities

    //sets the top layers to stone so the element can have an effect
    //TODO: fix the weird bug with this method
    protected void resetTop(Chunk chunk, int x, int z, int height, BlockState defaultBlock, BlockState defaultFluid) {
        BlockPos.Mutable pos = new BlockPos.Mutable(x, height, z);

        //exit if the bottom block is water/lava
        if (chunk.getBlockState(pos.toImmutable().down()) == defaultFluid) return;

        while (chunk.getBlockState(pos) != defaultBlock) { //make sure the position is for replacement
            pos.setY(pos.getY() - 1);

            if (chunk.getBlockState(pos) == defaultFluid) return; //exit

            chunk.setBlockState(pos, defaultBlock, false);
        }
    }
}