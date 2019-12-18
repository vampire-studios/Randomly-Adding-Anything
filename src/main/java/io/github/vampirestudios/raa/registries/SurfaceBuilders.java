package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.surface.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.BadlandsSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class SurfaceBuilders {
    public static BadlandsSurfaceBuilder DARK_BADLANDS;

    public static PatchyDesertSurfaceBuilder PATCHY_DESERT;
    public static PatchyBadlandsSurfaceBuilder PATCHY_BADLANDS;
    public static PatchyDarkBadlandsSurfaceBuilder DARK_PATCHY_BADLANDS;
    public static ClassicCliffsSurfaceBuilder CLASSIC_CLIFFS;
    public static StratifiedSurfaceBuilder STRATIFIED_CLIFFS;
    public static FloatingIslandSurfaceBuilder FLOATING_ISLANDS;
    public static DuneSurfaceBuilder DUNES;
    public static LazyNoiseSurfaceBuilder LAZY_NOISE;
    public static HyperflatSurfaceBuilder HYPER_FLAT;

    public static void init() {
        DARK_BADLANDS = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "dark_badlands"),
                new BadlandsSurfaceBuilder(TernarySurfaceConfig::deserialize));

        PATCHY_DESERT = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "patchy_desert"),
                new PatchyDesertSurfaceBuilder(TernarySurfaceConfig::deserialize));
        PATCHY_BADLANDS = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "patchy_badlands"),
                new PatchyBadlandsSurfaceBuilder(TernarySurfaceConfig::deserialize));
        DARK_PATCHY_BADLANDS = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "dark_patchy_badlands"),
                new PatchyDarkBadlandsSurfaceBuilder(TernarySurfaceConfig::deserialize));
        CLASSIC_CLIFFS = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "classic_cliffs"),
                new ClassicCliffsSurfaceBuilder(TernarySurfaceConfig::deserialize));
        STRATIFIED_CLIFFS = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "stratified_cliffs"),
                new StratifiedSurfaceBuilder(TernarySurfaceConfig::deserialize));
        FLOATING_ISLANDS = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "floating_islands"),
                new FloatingIslandSurfaceBuilder(TernarySurfaceConfig::deserialize));
        DUNES = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "dunes"),
                new DuneSurfaceBuilder(TernarySurfaceConfig::deserialize));
        LAZY_NOISE = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "lazy_noise"),
                new LazyNoiseSurfaceBuilder(TernarySurfaceConfig::deserialize));
        HYPER_FLAT = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "hyper_flat"),
                new HyperflatSurfaceBuilder(TernarySurfaceConfig::deserialize));
    }
}
