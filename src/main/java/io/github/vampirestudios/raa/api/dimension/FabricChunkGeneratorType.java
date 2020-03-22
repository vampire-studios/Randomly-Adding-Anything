package io.github.vampirestudios.raa.api.dimension;


import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.source.BiomeSource;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;

import java.util.function.Supplier;

/**
 * Fabric version of ChunkGeneratorType which utilizes the FabricChunkGeneratorFactory.
 * ChunkGeneratorType is a registry wrapper for ChunkGenerator, similar to BlockEntityType or EntityType.
 *
 * @param <C> ChunkGenerator config
 * @param <T> ChunkGenerator
 */
public final class FabricChunkGeneratorType<C extends ChunkGeneratorConfig, T extends ChunkGenerator<C>> extends ChunkGeneratorType<C, T> {
    private final FabricChunkGeneratorFactory<C, T> factory;

    private FabricChunkGeneratorType(FabricChunkGeneratorFactory<C, T> factory, boolean buffetScreenOption, Supplier<C> settingsSupplier) {
        super(null, buffetScreenOption, settingsSupplier);
        this.factory = factory;
    }

    /**
     * Called to register and create new instance of the ChunkGeneratorType.
     *
     * @param id                 registry ID of the ChunkGeneratorType
     * @param factory            factory instance to provide a ChunkGenerator
     * @param settingsSupplier   config supplier
     * @param buffetScreenOption whether or not the ChunkGeneratorType should appear in the buffet screen options page
     */
    public static <C extends ChunkGeneratorConfig, T extends ChunkGenerator<C>> FabricChunkGeneratorType<C, T> register(Identifier id, FabricChunkGeneratorFactory<C, T> factory, Supplier<C> settingsSupplier, boolean buffetScreenOption) {
        return Registry.register(Registry.CHUNK_GENERATOR_TYPE, id, new FabricChunkGeneratorType<>(factory, buffetScreenOption, settingsSupplier));
    }

    /**
     * Called to get an instance of the ChunkGeneratorType's ChunkGenerator.
     *
     * @param iWorld       DimensionType's world instance
     * @param biomeSource BiomeSource to use while generating the world
     * @param chunkGeneratorConfig      ChunkGenerator config instance
     */
    @Override
    public T create(IWorld iWorld, BiomeSource biomeSource, C chunkGeneratorConfig) {
        return factory.create(iWorld, biomeSource, chunkGeneratorConfig);
    }

    /**
     * Responsible for creating the FabricChunkGeneratorType's ChunkGenerator instance.
     * Called when a new instance of a ChunkGenerator is requested in the ChunkGeneratorType.
     *
     * @param <C> ChunkGeneratorConfig
     * @param <T> ChunkGenerator
     */
    @FunctionalInterface
    public interface FabricChunkGeneratorFactory<C extends ChunkGeneratorConfig, T extends ChunkGenerator<C>> {
        T create(IWorld world, BiomeSource source, C config);
    }
}