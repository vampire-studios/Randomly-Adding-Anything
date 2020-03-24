package io.github.vampirestudios.raa.generation.surface.random.elements;

import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.generation.surface.random.SurfaceElement;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class RandomSpiresSurfaceElement extends SurfaceElement {

    public RandomSpiresSurfaceElement() {
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        BlockPos.Mutable pos = new BlockPos.Mutable(x, height, z);

        //don't place on water
        if (chunk.getBlockState(pos.toImmutable().down()) == defaultFluid) return;

        if (random.nextInt(10) == 0) {
            for (int i = 0; i < random.nextInt(10); i++) {
                pos.setY(height + i);
                chunk.setBlockState(pos, defaultBlock, false);
            }
        }
    }

    @Override
    public void serialize(JsonObject obj) {
    }

    @Override
    public void deserialize(JsonObject obj) {
    }

    @Override
    public Identifier getType() {
        return new Identifier("raa", "spires");
    }

    @Override
    public int getPriority() {
        return 90;
    }
}
