package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.surface.*;
import io.github.vampirestudios.raa.generation.surface.vanilla_variants.BadlandsSurfaceBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class SurfaceBuilders {
    public static BadlandsSurfaceBuilder DARK_BADLANDS;

    public static PatchyDesertSurfaceBuilder PATCHY_DESERT;
    public static PatchyMesaSurfaceBuilder PATCHY_MESA;
    public static PatchyDarkMesaSurfaceBuilder PATCHY_DARK_MESA;
    public static ClassicCliffsSurfaceBuilder CLASSIC_CLIFFS;
    public static StratifiedSurfaceBuilder STRATIFIED_CLIFFS;
    public static FloatingIslandSurfaceBuilder FLOATING_ISLANDS;
    public static DuneSurfaceBuilder DUNES;
    public static LazyNoiseSurfaceBuilder LAZY_NOISE;
    public static HyperflatSurfaceBuilder HYPERFLAT;

    public static void init() {
        DARK_BADLANDS = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "dark_badlands"),
                new BadlandsSurfaceBuilder(TernarySurfaceConfig::deserialize));

        PATCHY_DESERT = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "patchy_desert"),
                new PatchyDesertSurfaceBuilder(TernarySurfaceConfig::deserialize));
        PATCHY_MESA = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "patchy_mesa"),
                new PatchyMesaSurfaceBuilder(TernarySurfaceConfig::deserialize));
        PATCHY_DARK_MESA = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "patchy_dark_mesa"),
                new PatchyDarkMesaSurfaceBuilder(TernarySurfaceConfig::deserialize));
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
        HYPERFLAT = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "hyperflat"),
                new HyperflatSurfaceBuilder(TernarySurfaceConfig::deserialize));
    }
}
