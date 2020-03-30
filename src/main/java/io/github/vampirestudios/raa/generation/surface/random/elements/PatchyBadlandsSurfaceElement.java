package io.github.vampirestudios.raa.generation.surface.random.elements;

import com.google.gson.JsonObject;
import io.github.vampirestudios.raa.generation.surface.random.SurfaceElement;
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

public class PatchyBadlandsSurfaceElement extends SurfaceElement {
    public static final OctaveSimplexNoiseSampler MESA_NOISE = new OctaveSimplexNoiseSampler(new ChunkRandom(79L), IntStream.of(6, 0));

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        double mesaNoise = MESA_NOISE.sample(x * 0.049765625D, z * 0.049765625D, false);
        if (mesaNoise > 0.0D) {
            SurfaceBuilder.BADLANDS.initSeed(seed);
            SurfaceBuilder.BADLANDS.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.BADLANDS_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, surfaceBlocks);
        }
    }

    @Override
    public void serialize(JsonObject obj) {}

    @Override
    public void deserialize(JsonObject obj) {}

    @Override
    public Identifier getType() {
        return new Identifier("raa", "patchy_badlands");
    }

    @Override
    public int getPriority() {
        return 100;
    }

}
