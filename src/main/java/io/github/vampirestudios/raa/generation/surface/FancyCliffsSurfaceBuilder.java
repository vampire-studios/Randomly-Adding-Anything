package io.github.vampirestudios.raa.generation.surface;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.surface.config.CustomTernarySurfaceConfig;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;
import java.util.function.Function;

public class FancyCliffsSurfaceBuilder extends SurfaceBuilder<CustomTernarySurfaceConfig> {

    public FancyCliffsSurfaceBuilder(Function<Dynamic<?>, ? extends CustomTernarySurfaceConfig> function_1) {
        super(function_1);
    }

    @Override
    public void generate(Random rand, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState stone, BlockState water, int var11, long seed, CustomTernarySurfaceConfig config) {
        int seaLevel = 62;
        x &= 15;
        z &= 15;

        height -= 1;

        if (rand.nextBoolean())

        if (noise > 0.5D && height > seaLevel + 1 && height < seaLevel + 12) {
            if (height > seaLevel + 5) {
                height = seaLevel + 5;
            }

            BlockPos.Mutable pos = new BlockPos.Mutable(x, height, z);

            int basaltLayers = 3;

            if (noise > 1.0D) {
                basaltLayers += 1;
            }

            if (noise > 1.5D) {
                basaltLayers += 1;
            }

            for (int i = 0; i < basaltLayers; i++) {
                placeBlock(config, chunk, pos, stone, rand);
                pos.setOffset(Direction.UP);
            }

            for (int i = 0; i < 3; i++) {
                placeBlock(config, chunk, pos, stone, rand);
                pos.setOffset(Direction.UP);
            }
            placeBlock(config, chunk, pos, stone, rand);
        } else {
            TernarySurfaceConfig config1 = new TernarySurfaceConfig(config.getTopMaterial(), config.getUnderMaterial(), config.getUnderwaterMaterial());
            SurfaceBuilder.DEFAULT.generate(rand, chunk, biome, x, z, height, noise, stone, water, var11, seed, config1);
        }
    }

    private void placeBlock(CustomTernarySurfaceConfig config, Chunk chunk,  BlockPos pos, BlockState state, Random random) {
        if (random.nextBoolean()) {
            if (Rands.chance(10)) {
                chunk.setBlockState(pos, state, false);
            } else {
                chunk.setBlockState(pos, config.getMiddleMaterial(), false);
            }
        } else {
            if (Rands.chance(10)) {
                chunk.setBlockState(pos, config.getTopMaterial(), false);
            } else {
                chunk.setBlockState(pos, config.getUnderMaterial(), false);
            }
        }
    }

}
