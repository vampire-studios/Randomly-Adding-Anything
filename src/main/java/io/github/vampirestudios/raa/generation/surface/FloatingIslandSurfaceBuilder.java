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

public class FloatingIslandSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    public FloatingIslandSurfaceBuilder(Function<Dynamic<?>, ? extends TernarySurfaceConfig> configDeserializer, Function<Random, ? extends TernarySurfaceConfig> function) {
        super(configDeserializer, function);
    }

    @Override
    public void generate(Random rand, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        if (noise > 1) {
            BlockPos.Mutable pos = new BlockPos.Mutable(x, 50 + height + (noise), z);
            for (int i = 0; i < 2 + (noise / 4); i++) {
                chunk.setBlockState(pos, defaultBlock, false);
                pos.offset(Direction.UP);
            }
            for (int i = 0; i < 3 + (noise / 2); i++) {
                chunk.setBlockState(pos, DIRT, false);
                pos.offset(Direction.UP);
            }
            chunk.setBlockState(pos, surfaceBlocks.getTopMaterial(), false);
        }
        SurfaceBuilder.DEFAULT.generate(rand, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, surfaceBlocks);
    }
}
