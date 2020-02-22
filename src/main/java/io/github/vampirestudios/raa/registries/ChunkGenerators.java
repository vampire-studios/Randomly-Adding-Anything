package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.api.dimension.FabricChunkGeneratorType;
import io.github.vampirestudios.raa.generation.chunkgenerator.caves.CavesChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.caves.FlatCavesChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.caves.HighCavesChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.caves.HoleCaveChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.floating.FloatingIslandsChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.floating.LayeredFloatingIslandsChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.floating.PreClassicFloatingIslandsChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.overworld.*;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.CavesChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.FloatingIslandsChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class ChunkGenerators {
    public static ChunkGeneratorType<FloatingIslandsChunkGeneratorConfig, FloatingIslandsChunkGenerator> FLOATING_ISLANDS;
    public static ChunkGeneratorType<FloatingIslandsChunkGeneratorConfig, LayeredFloatingIslandsChunkGenerator> LAYERED_FLOATING;
    public static ChunkGeneratorType<FloatingIslandsChunkGeneratorConfig, PreClassicFloatingIslandsChunkGenerator> PRECLASSIC_FLOATING;

    public static ChunkGeneratorType<CavesChunkGeneratorConfig, CavesChunkGenerator> CAVES;
    public static ChunkGeneratorType<CavesChunkGeneratorConfig, FlatCavesChunkGenerator> FLAT_CAVES;
    public static ChunkGeneratorType<CavesChunkGeneratorConfig, HighCavesChunkGenerator> HIGH_CAVES;

    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, OverworldChunkGenerator> SURFACE;
    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, CustomOverworldChunkGenerator> CUSTOM_SURFACE;
    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, QuadrupleAmplifiedChunkGenerator> QUADRUPLE_AMPLIFIED;
    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, PillarWorldChunkGenerator> PILLAR_WORLD;
    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, SmoothOverworldChunkGenerator> SMOOTH;
    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, HoleCaveChunkGenerator> TOTALLY_CUSTOM;

    public static void init() {
        //End-like chunk generators
        FLOATING_ISLANDS = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "floating_islands"), FloatingIslandsChunkGenerator::new, FloatingIslandsChunkGeneratorConfig::new, false);
        LAYERED_FLOATING = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "layered_floating"), LayeredFloatingIslandsChunkGenerator::new, FloatingIslandsChunkGeneratorConfig::new, false);
        PRECLASSIC_FLOATING = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "preclassic_floating"), PreClassicFloatingIslandsChunkGenerator::new, FloatingIslandsChunkGeneratorConfig::new, false);

        //Nether-like chunk generators
        CAVES = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "caves"), CavesChunkGenerator::new, CavesChunkGeneratorConfig::new, false);
        FLAT_CAVES = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "flat_caves"), FlatCavesChunkGenerator::new, CavesChunkGeneratorConfig::new, false);
        HIGH_CAVES = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "high_caves"), HighCavesChunkGenerator::new, CavesChunkGeneratorConfig::new, false);

        //Overworld-like chunk generators
        SURFACE = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "surface"), OverworldChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);
        QUADRUPLE_AMPLIFIED = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "quadruple_amplified"), QuadrupleAmplifiedChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);
        PILLAR_WORLD = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "pillar_world"), PillarWorldChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);
        SMOOTH = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "smooth_overworld"), SmoothOverworldChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);
        TOTALLY_CUSTOM = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "totally_custom"), HoleCaveChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);

        if (FabricLoader.getInstance().isModLoaded("simplexterrain"))
            CUSTOM_SURFACE = FabricChunkGeneratorType.register(new Identifier(MOD_ID, "custom_surface"), CustomOverworldChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);
    }
}
