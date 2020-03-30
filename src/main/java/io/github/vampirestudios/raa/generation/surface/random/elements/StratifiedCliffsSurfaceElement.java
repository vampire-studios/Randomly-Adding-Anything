package io.github.vampirestudios.raa.generation.surface.random.elements;

import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.generation.surface.random.SurfaceElement;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class StratifiedCliffsSurfaceElement extends SurfaceElement {

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        if (noise > 1.5) {
            BlockPos.Mutable pos = new BlockPos.Mutable(x, height, z);
            BlockPos.Mutable posTilHeight = new BlockPos.Mutable(x, 0, z);
            for (int i = 0; i <= height; i++) {
                chunk.setBlockState(posTilHeight, defaultBlock, false);
                posTilHeight.offset(Direction.UP);
            }
            for (int i = 0; i < (noise > 2.5 ? 20 : 12); i++) {
                chunk.setBlockState(pos, defaultBlock, false);
                pos.offset(Direction.UP);
            }
            int dirtHeight = (int) noise * 2;
            for (int i = 0; i < dirtHeight; i++) {
                chunk.setBlockState(pos, surfaceBlocks.getUnderMaterial(), false);
                pos.offset(Direction.UP);
            }
            chunk.setBlockState(pos, surfaceBlocks.getTopMaterial(), false);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, surfaceBlocks);
        }
    }

    @Override
    public void serialize(JsonObject obj) { }

    @Override
    public void deserialize(JsonObject obj) { }

    @Override
    public Identifier getType() {
        return new Identifier("raa", "stratified_cliffs");
    }

    @Override
    public int getPriority() {
        return 95;
    }

}
