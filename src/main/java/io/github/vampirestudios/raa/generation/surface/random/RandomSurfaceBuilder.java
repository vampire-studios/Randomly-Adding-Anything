package io.github.vampirestudios.raa.generation.surface.random;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import java.util.List;
import java.util.Random;

public class RandomSurfaceBuilder extends SurfaceBuilder<TernarySurfaceConfig> {
    private final List<SurfaceElement> elements;

    public RandomSurfaceBuilder(List<SurfaceElement> elements) {
        super(TernarySurfaceConfig::deserialize, TernarySurfaceConfig::method_26680);
        this.elements = elements;
    }

    @Override
    public void generate(Random random, Chunk chunk, Biome biome, int x, int z, int height, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, TernarySurfaceConfig surfaceBlocks) {
        for (SurfaceElement element : elements) {
            element.generate(random, chunk, biome, x, z, height, noise, defaultBlock, defaultFluid, seaLevel, seed, surfaceBlocks);
        }
    }
}
