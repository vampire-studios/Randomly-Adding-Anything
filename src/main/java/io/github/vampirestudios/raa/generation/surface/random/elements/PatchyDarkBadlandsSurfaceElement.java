package io.github.vampirestudios.raa.generation.surface.random.elements;

import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.generation.surface.random.SurfaceElement;
import io.github.vampirestudios.raa.registries.SurfaceBuilders;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.BlockState;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.noise.OctaveSimplexNoiseSampler;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.Random;
import java.util.stream.IntStream;

public class PatchyDarkBadlandsSurfaceElement extends SurfaceElement {
    private double chance;

    public PatchyDarkBadlandsSurfaceElement() {
        chance = Rands.randFloatRange(-0.25f, 2);
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        if (noise > chance) {
            SurfaceBuilders.DARK_BADLANDS.initSeed(seed);
            SurfaceBuilders.DARK_BADLANDS.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.BADLANDS_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, surfaceBlocks);
        }
    }

    @Override
    public void serialize(JsonObject obj) {
        obj.addProperty("chance", chance);
    }

    @Override
    public void deserialize(JsonObject obj) {
        chance = obj.get("chance").getAsDouble();
    }

    @Override
    public Identifier getType() {
        return new Identifier("raa", "patchy_dark_badlands");
    }

    @Override
    public int getPriority() {
        return 100;
    }

}
