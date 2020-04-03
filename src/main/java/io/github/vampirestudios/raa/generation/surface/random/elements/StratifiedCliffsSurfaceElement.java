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

public class StratifiedCliffsSurfaceElement extends SurfaceElement {
    private double threshold;
    private double bigThreshold;
    private int normalHeight;
    private int bigHeight;
    private double dirtCoefficient;

    public StratifiedCliffsSurfaceElement() {
        this.threshold = Rands.randDoubleRange(0.5, 1.95);
        this.bigThreshold = Rands.randDoubleRange(2, 3);
        this.normalHeight = Rands.randIntRange(6, 12);
        this.bigHeight = Rands.randIntRange(8, 30);
        this.dirtCoefficient = Rands.randDoubleRange(1.25, 2.5);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        if (noise > threshold) {
            BlockPos.Mutable pos = new BlockPos.Mutable(x, height, z);
            BlockPos.Mutable posTilHeight = new BlockPos.Mutable(x, 0, z);
            for (int i = 0; i <= height; i++) {
                chunk.setBlockState(posTilHeight, defaultBlock, false);
                posTilHeight.offset(Direction.UP);
            }
            for (int i = 0; i < (noise > bigThreshold ? bigHeight : normalHeight); i++) {
                chunk.setBlockState(pos, defaultBlock, false);
                pos.offset(Direction.UP);
            }
            int dirtHeight = (int) (noise * dirtCoefficient);
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
    public void serialize(JsonObject obj) {
        obj.addProperty("threshold", threshold);
        obj.addProperty("bigThreshold", bigThreshold);
        obj.addProperty("normalHeight", normalHeight);
        obj.addProperty("bigHeight", bigHeight);
        obj.addProperty("dirtCoefficient", dirtCoefficient);
    }

    @Override
    public void deserialize(JsonObject obj) {
        threshold = obj.get("threshold").getAsDouble();
        bigThreshold = obj.get("bigThreshold").getAsDouble();
        normalHeight = obj.get("normalHeight").getAsInt();
        bigHeight = obj.get("bigHeight").getAsInt();
        dirtCoefficient = obj.get("dirtCoefficient").getAsDouble();
    }

    @Override
    public Identifier getType() {
        return new Identifier("raa", "stratified_cliffs");
    }

    @Override
    public int getPriority() {
        return 95;
    }

}
