package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.chunkgenerator.*;
import io.github.vampirestudios.raa.generation.chunkgenerator.caves.CavesChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.caves.FlatCavesChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.caves.HighCavesChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.floating.FloatingIslandsChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.floating.LayeredFloatingIslandsChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.floating.PreClassicFloatingIslandsChunkGenerator;
import io.github.vampirestudios.raa.generation.chunkgenerator.overworld.*;
import io.github.vampirestudios.raa.utils.RegistryUtils;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.chunk.CavesChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.ChunkGeneratorType;
import net.minecraft.world.gen.chunk.FloatingIslandsChunkGeneratorConfig;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class ChunkGenerators {
    public static ChunkGeneratorType<FloatingIslandsChunkGeneratorConfig, FloatingIslandsChunkGenerator> FLOATING_ISLANDS;
    public static ChunkGeneratorType<FloatingIslandsChunkGeneratorConfig, LayeredFloatingIslandsChunkGenerator> LAYERED_FLOATING;
    public static ChunkGeneratorType<FloatingIslandsChunkGeneratorConfig, PreClassicFloatingIslandsChunkGenerator> PRE_CLASSIC_FLOATING;

    public static ChunkGeneratorType<CavesChunkGeneratorConfig, CavesChunkGenerator> CAVES;
    public static ChunkGeneratorType<CavesChunkGeneratorConfig, FlatCavesChunkGenerator> FLAT_CAVES;
    public static ChunkGeneratorType<CavesChunkGeneratorConfig, HighCavesChunkGenerator> HIGH_CAVES;

    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, OverworldChunkGenerator> SURFACE;
    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, QuadrupleAmplifiedChunkGenerator> QUADRUPLE_AMPLIFIED;
    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, PillarWorldChunkGenerator> PILLAR_WORLD;
    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, SmoothOverworldChunkGenerator> SMOOTH;
    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, TotallyCustomChunkGenerator> TOTALLY_CUSTOM;
    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, LayeredChunkGenerator> LAYERED_OVERWORLD;
    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, ChaosChunkGenerator> CHAOS;
    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, RollingHillsChunkGenerator> ROLLING_HILLS;

    public static ChunkGeneratorType<NoneGeneratorSettings, RetroChunkGenerator> RETRO;
    public static ChunkGeneratorType<NoneGeneratorSettings, CheckerboardChunkGenerator> CHECKERBOARD;

    //this is only for testing use!
    public static ChunkGeneratorType<OverworldChunkGeneratorConfig, TestChunkGenerator> TEST;

    public static void init() {
        //End-like chunk generators
        FLOATING_ISLANDS = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "floating_islands"), FloatingIslandsChunkGenerator::new, FloatingIslandsChunkGeneratorConfig::new, false);
        LAYERED_FLOATING = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "layered_floating"), LayeredFloatingIslandsChunkGenerator::new, FloatingIslandsChunkGeneratorConfig::new, false);
        PRE_CLASSIC_FLOATING = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "pre_classic_floating"), PreClassicFloatingIslandsChunkGenerator::new, FloatingIslandsChunkGeneratorConfig::new, false);

        //Nether-like chunk generators
        CAVES = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "caves"), CavesChunkGenerator::new, CavesChunkGeneratorConfig::new, false);
        FLAT_CAVES = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "flat_caves"), FlatCavesChunkGenerator::new, CavesChunkGeneratorConfig::new, false);
        HIGH_CAVES = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "high_caves"), HighCavesChunkGenerator::new, CavesChunkGeneratorConfig::new, false);

        //Overworld-like chunk generators
        SURFACE = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "surface"), OverworldChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);
        QUADRUPLE_AMPLIFIED = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "quadruple_amplified"), QuadrupleAmplifiedChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);
        PILLAR_WORLD = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "pillar_world"), PillarWorldChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);
        SMOOTH = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "smooth_overworld"), SmoothOverworldChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);
        TOTALLY_CUSTOM = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "totally_custom"), TotallyCustomChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);
        LAYERED_OVERWORLD = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "layered_overworld"), LayeredChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);
        CHAOS = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "chaos"), ChaosChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);
        ROLLING_HILLS = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "rolling_hills"), RollingHillsChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);

        RETRO = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "retro"), RetroChunkGenerator::new, NoneGeneratorSettings::new, false);
        CHECKERBOARD = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "checkerboard"), CheckerboardChunkGenerator::new, NoneGeneratorSettings::new, false);

        TEST = RegistryUtils.registerChunkGenerator(new Identifier(MOD_ID, "test"), TestChunkGenerator::new, OverworldChunkGeneratorConfig::new, false);
    }

}