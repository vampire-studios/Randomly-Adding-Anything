package io.github.vampirestudios.raa.api.enums;

import io.github.vampirestudios.raa.api.interfaces.DimensionChunkGenerator;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.*;

import java.lang.reflect.InvocationTargetException;

public enum DimensionChunkGenerators implements DimensionChunkGenerator {
    CAVE,
    FLOATING,
    OVERWORLD;

    @Override
    public ChunkGenerator<?> getChunkGenerator(World world, BiomeSource biomeSource) {
        if (this == CAVE) return ChunkGeneratorType.CAVES.create(world, biomeSource, new CavesChunkGeneratorConfig());
        if (this == FLOATING) return ChunkGeneratorType.FLOATING_ISLANDS.create(world, biomeSource, new FloatingIslandsChunkGeneratorConfig());
        return ChunkGeneratorType.SURFACE.create(world, biomeSource, new OverworldChunkGeneratorConfig());
    }
}
