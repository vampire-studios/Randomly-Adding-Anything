package io.github.vampirestudios.raa.generation.surface.random;

import com.google.gson.JsonObject;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
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
}