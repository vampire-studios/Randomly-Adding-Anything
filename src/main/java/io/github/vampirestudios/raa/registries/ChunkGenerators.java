package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.chunkgenerator.HighCavesChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.LayeredFloatingIslandsChunkGenerator;
import io.github.vampirestudios.raa.api.dimension.FabricChunkGeneratorType;
import io.github.vampirestudios.raa.generation.chunkgenerator.FlatCavesChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.PreClassicFloatingIslandsChunkGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.CavesChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.FloatingIslandsChunkGeneratorConfig;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class ChunkGenerators {
    public static ChunkGeneratorType<FloatingIslandsChunkGeneratorConfig, LayeredFloatingIslandsChunkGenerator> LAYERED_FLOATING;
    public static ChunkGeneratorType<FloatingIslandsChunkGeneratorConfig, PreClassicFloatingIslandsChunkGenerator> PRECLASSIC_FLOATING;

    public static ChunkGeneratorType<CavesChunkGeneratorConfig, FlatCavesChunkGenerator> FLAT_CAVES;
    public static ChunkGeneratorType<CavesChunkGeneratorConfig, HighCavesChunkGenerator> HIGH_CAVES;

    public static void init() {
        LAYERED_FLOATING = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "layered_floating"), LayeredFloatingIslandsChunkGenerator::new, FloatingIslandsChunkGeneratorConfig::new, false);
        PRECLASSIC_FLOATING = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "preclassic_floating"), PreClassicFloatingIslandsChunkGenerator::new, FloatingIslandsChunkGeneratorConfig::new, false);

        FLAT_CAVES = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "flat_cave"), FlatCavesChunkGenerator::new, CavesChunkGeneratorConfig::new, false);
        HIGH_CAVES = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "nhigh_cave"), HighCavesChunkGenerator::new, CavesChunkGeneratorConfig::new, false);
    }
}
