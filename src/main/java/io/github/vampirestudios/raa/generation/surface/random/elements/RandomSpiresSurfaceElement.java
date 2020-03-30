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
    private int spireChance;
    private int spireHeight;

    public RandomSpiresSurfaceElement() {
        spireChance = Rands.randIntRange(32, 256);
        spireHeight = Rands.randIntRange(3, 16);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        BlockPos.Mutable pos = new BlockPos.Mutable(x, height, z);

        //don't place on water
        if (chunk.getBlockState(pos.toImmutable().down()) == defaultFluid) return;

        //don't place on air/void
        if (chunk.getBlockState(pos.toImmutable().down()).isAir()) return;

        if (random.nextInt(spireChance) == 0) {
            int spireHeightRandom = random.nextInt(spireHeight);
            for (int i = 0; i < spireHeightRandom; i++) {
                pos.setY(height + i);
                chunk.setBlockState(pos, defaultBlock, false);
            }

            //generate the top layer of the spire
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height + spireHeightRandom + 1, noise, defaultBlock, defaultFluid, seaLevel, seed, surfaceBlocks);
        }
    }

    @Override
    public void serialize(JsonObject obj) {
        obj.addProperty("spireChance", spireChance);
        obj.addProperty("spireHeight", spireHeight);
    }

    @Override
    public void deserialize(JsonObject obj) {
        spireChance = obj.get("spireChance").getAsInt();
        spireHeight = obj.get("spireHeight").getAsInt();
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
