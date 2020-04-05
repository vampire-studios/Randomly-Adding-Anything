package io.github.vampirestudios.raa.generation.surface.random.elements;

import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.generation.surface.random.SurfaceElement;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;

public class FloatingIslandSurfaceElement extends SurfaceElement {
    private double threshold;
    private int baseHeight;
    private int defaultBlockHeight;
    private double defaultBlockNoiseDivisor;
    private int underBlockHeight;
    private double underBlockNoiseDivisor;

    public FloatingIslandSurfaceElement() {
        threshold = Rands.randDoubleRange(0.5, 1.5);
        baseHeight = Rands.randIntRange(25, 75);
        defaultBlockHeight = Rands.randIntRange(1, 4);
        defaultBlockNoiseDivisor = Rands.randDoubleRange(2, 6);
        underBlockHeight = Rands.randIntRange(2, 5);
        underBlockNoiseDivisor = Rands.randDoubleRange(1, 3);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        if (noise > 1) {
            BlockPos.Mutable pos = new BlockPos.Mutable(x, 50 + height + (noise), z);
            for (int i = 0; i < 2 + (noise / 4); i++) {
                chunk.setBlockState(pos, defaultBlock, false);
                pos.offset(Direction.UP);
            }
            for (int i = 0; i < 3 + (noise / 2); i++) {
                chunk.setBlockState(pos, surfaceBlocks.getUnderMaterial(), false);
                pos.offset(Direction.UP);
            }
            chunk.setBlockState(pos, surfaceBlocks.getTopMaterial(), false);
        }
        SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, surfaceBlocks);
    }

    @Override
    public void serialize(JsonObject obj) {
        obj.addProperty("threshold", threshold);
        obj.addProperty("baseHeight", baseHeight);
        obj.addProperty("defaultBlockHeight", defaultBlockHeight);
        obj.addProperty("defaultBlockNoiseDivisor", defaultBlockNoiseDivisor);
        obj.addProperty("underBlockHeight", underBlockHeight);
        obj.addProperty("underBlockNoiseDivisor", underBlockNoiseDivisor);
    }

    @Override
    public void deserialize(JsonObject obj) {
        threshold = obj.get("threshold").getAsDouble();
        baseHeight = obj.get("baseHeight").getAsInt();
        defaultBlockHeight = obj.get("defaultBlockHeight").getAsInt();
        defaultBlockNoiseDivisor = obj.get("defaultBlockNoiseDivisor").getAsDouble();
        underBlockHeight = obj.get("underBlockHeight").getAsInt();
        underBlockNoiseDivisor = obj.get("underBlockNoiseDivisor").getAsDouble();
    }

    @Override
    public Identifier getType() {
        return new Identifier("raa", "floating_island");
    }

    @Override
    public int getPriority() {
        return 100;
    }

}
