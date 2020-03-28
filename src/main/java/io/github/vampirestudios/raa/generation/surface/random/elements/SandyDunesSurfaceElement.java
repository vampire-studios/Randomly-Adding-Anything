package io.github.vampirestudios.raa.generation.surface.random.elements;

import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.generation.surface.random.SurfaceElement;
import io.github.vampirestudios.raa.utils.WorleyNoise;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class SandyDunesSurfaceElement extends SurfaceElement {
    private static final WorleyNoise NOISE = new WorleyNoise(3445);

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        height = chunk.getHeightmap(Heightmap.Type.OCEAN_FLOOR_WG).get(x & 15, z & 15);
        BlockPos.Mutable pos = new BlockPos.Mutable(x, height - 8, z);

        double blend = MathHelper.clamp((height - seaLevel) * 0.125, 0, 1);

        double vHeight = (NOISE.sample(x * 0.01, z * 0.015) * 30) * blend;

        vHeight = Math.abs(vHeight);

        for (int i = 0; i < 8; i++) {
            chunk.setBlockState(pos, Blocks.CUT_SANDSTONE.getDefaultState(), false);
            pos.offset(Direction.UP);
        }

        // Cap the height based on noise

        vHeight = Math.min(vHeight, (NOISE.sample(x * 0.03 + 5, z * 0.05 + 5) * 30 + 6));

        for (int h = 0; h < vHeight; h++) {
            chunk.setBlockState(pos, Blocks.SANDSTONE.getDefaultState(), false);
            pos.offset(Direction.UP);
        }

        for (int i = 0; i < 3 + (noise / 2); i++) {
            chunk.setBlockState(pos, Blocks.SMOOTH_SANDSTONE.getDefaultState(), false);
            pos.offset(Direction.UP);
        }

        for (int i = 0; i < 3 + (noise / 2); i++) {
            chunk.setBlockState(pos, Blocks.SAND.getDefaultState(), false);
        }
    }

    @Override
    public void serialize(JsonObject obj) {}

    @Override
    public void deserialize(JsonObject obj) {}

    @Override
    public Identifier getType() {
        return new Identifier("raa", "sandy_dunes");
    }

    @Override
    public int getPriority() {
        return 100;
    }

}
