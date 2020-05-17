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
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class ChunkGenerators {
    public static ChunkGenerator FLOATING_ISLANDS, LAYERED_FLOATING, PRE_CLASSIC_FLOATING;

    public static ChunkGenerator CAVES, FLAT_CAVES, HIGH_CAVES;

    public static ChunkGenerator SURFACE, QUADRUPLE_AMPLIFIED, PILLAR_WORLD, SMOOTH, TOTALLY_CUSTOM, LAYERED_OVERWORLD, CHAOS, ROLLING_HILLS;

    public static ChunkGenerator RETRO, CHECKERBOARD;

    //this is only for testing use!
    public static ChunkGenerator TEST;

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