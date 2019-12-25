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

//Code kindly taken from Terraform. Thank you, coderbot, Prospector, and Valoeghese!
public class ClassicCliffsSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {

    public ClassicCliffsSurfaceBuilder(Function<Dynamic<?>, ? extends TernarySurfaceConfig> function_1) {
        super(function_1);
    }

    @Override
    public void generate(Random rand, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState stone, BlockState water, int var11, long seed, TernarySurfaceConfig config) {
        int seaLevel = 62;
        x &= 15;
        z &= 15;

        height -= 1;

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

            if (height >= seaLevel + 7) {
                basaltLayers += (seaLevel + 5 - height) / 2;
            }

            for (int i = 0; i < basaltLayers; i++) {
                chunk.setBlockState(pos, DIRT, false);
                pos.setOffset(Direction.UP);
            }

            for (int i = 0; i < 3; i++) {
                chunk.setBlockState(pos, config.getUnderMaterial(), false);
                pos.setOffset(Direction.UP);
            }
            chunk.setBlockState(pos, config.getTopMaterial(), false);
        } else {
            SurfaceBuilder.DEFAULT.generate(rand, chunk, biome, x, z, height, noise, stone, water, var11, seed, config);
        }
    }
}
