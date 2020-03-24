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

public class ClassicCliffsSurfaceElement extends SurfaceElement {
    private int maxSeaLevel;
    private int minBasaltLayers;
    private int maxSeaLevelAddition;
    private double minNoiseThreshold;
    private double add1NoiseThreshold;
    private double add2NoiseThreshold;

    public ClassicCliffsSurfaceElement() {
        maxSeaLevel = Rands.randIntRange(8, 16);
        minBasaltLayers = Rands.randIntRange(2, 5);
        maxSeaLevelAddition = Rands.randIntRange(2, 8);
        minNoiseThreshold = Rands.randFloatRange(0.25f, 1f);
        add1NoiseThreshold = Rands.randFloatRange(0.75f, 1.45f);
        add2NoiseThreshold = Rands.randFloatRange(1f, 2f);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        x &= 15;
        z &= 15;
        height -= 1;
        if (noise > minNoiseThreshold && height > seaLevel + 1 && height < seaLevel + maxSeaLevel) {
            if (height > seaLevel + maxSeaLevelAddition) {
                height = seaLevel + maxSeaLevelAddition;
            }
            BlockPos.Mutable pos = new BlockPos.Mutable(x, height, z);
            int basaltLayers = minBasaltLayers;
            if (noise > add1NoiseThreshold) {
                basaltLayers += 1;
            }
            if (noise > add2NoiseThreshold) {
                basaltLayers += 1;
            }
            for (int i = 0; i < basaltLayers; i++) {
                chunk.setBlockState(pos, SurfaceBuilder.DIRT, false);
                pos.offset(Direction.UP);
            }
            for (int i = 0; i < 3; i++) {
                chunk.setBlockState(pos, surfaceBlocks.getUnderMaterial(), false);
                pos.offset(Direction.UP);
            }
            chunk.setBlockState(pos, surfaceBlocks.getTopMaterial(), false);
        }
    }

    @Override
    public void serialize(JsonObject obj) {
        obj.addProperty("maxSeaLevel", maxSeaLevel);
        obj.addProperty("minBasaltLayers", minBasaltLayers);
        obj.addProperty("maxSeaLevelAddition", maxSeaLevelAddition);
        obj.addProperty("minNoiseThreshold", minNoiseThreshold);
        obj.addProperty("add1NoiseThreshold", add1NoiseThreshold);
        obj.addProperty("add2NoiseThreshold", add2NoiseThreshold);
    }

    @Override
    public void deserialize(JsonObject obj) {
        maxSeaLevel = obj.get("maxSeaLevel").getAsInt();
        minBasaltLayers = obj.get("minBasaltLayers").getAsInt();
        maxSeaLevelAddition = obj.get("maxSeaLevelAddition").getAsInt();
        minNoiseThreshold = obj.get("minNoiseThreshold").getAsDouble();
        add1NoiseThreshold = obj.get("add1NoiseThreshold").getAsDouble();
        add2NoiseThreshold = obj.get("add2NoiseThreshold").getAsDouble();
    }

    @Override
    public Identifier getType() {
        return new Identifier("raa", "classic_cliffs");
    }

    @Override
    public int getPriority() {
        return 95;
    }
}
