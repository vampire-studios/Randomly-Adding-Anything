package io.github.vampirestudios.raa.generation.chunkgenerator.overworld;

import net.minecraft.world.IWorld;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;
import supercoder79.simplexterrain.world.gen.SimplexChunkGenerator;

public class CustomOverworldChunkGenerator extends SimplexChunkGenerator {

    public CustomOverworldChunkGenerator(IWorld world, BiomeSource biomeSource, OverworldChunkGeneratorConfig config) {
        super(world, biomeSource, config);
    }

}
