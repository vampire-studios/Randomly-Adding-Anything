package io.github.vampirestudios.raa.registries;

import io.github.vampirestudios.raa.generation.surface.*;
import io.github.vampirestudios.raa.generation.surface.vanilla_variants.DarkBadlandsSurfaceBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig;

import static io.github.vampirestudios.raa.RandomlyAddingAnything.MOD_ID;

public class SurfaceBuilders {
    public static DarkBadlandsSurfaceBuilder DARK_BADLANDS;

    public static PatchyDesertSurfaceBuilder PATCHY_DESERT;
    public static PatchyBadlandsSurfaceBuilder PATCHY_BADLANDS;
    public static PatchyDarkBadlandsSurfaceBuilder DARK_PATCHY_BADLANDS;
    public static ClassicCliffsSurfaceBuilder CLASSIC_CLIFFS;
    public static StratifiedSurfaceBuilder STRATIFIED_CLIFFS;
    public static FloatingIslandSurfaceBuilder FLOATING_ISLANDS;
    public static DuneSurfaceBuilder DUNES;
    public static SandyDunesSurfaceBuilder SANDY_DUNES;
    public static LazyNoiseSurfaceBuilder LAZY_NOISE;
    public static HyperflatSurfaceBuilder HYPER_FLAT;

    public static void init() {
        DARK_BADLANDS = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "dark_badlands"),
                new DarkBadlandsSurfaceBuilder(TernarySurfaceConfig::deserialize, TernarySurfaceConfig::method_26680));

        PATCHY_DESERT = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "patchy_desert"),
                new PatchyDesertSurfaceBuilder(TernarySurfaceConfig::deserialize, TernarySurfaceConfig::method_26680));
        PATCHY_BADLANDS = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "patchy_badlands"),
                new PatchyBadlandsSurfaceBuilder(TernarySurfaceConfig::deserialize, TernarySurfaceConfig::method_26680));
        DARK_PATCHY_BADLANDS = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "dark_patchy_badlands"),
                new PatchyDarkBadlandsSurfaceBuilder(TernarySurfaceConfig::deserialize, TernarySurfaceConfig::method_26680));
        CLASSIC_CLIFFS = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "classic_cliffs"),
                new ClassicCliffsSurfaceBuilder(TernarySurfaceConfig::deserialize, TernarySurfaceConfig::method_26680));
        STRATIFIED_CLIFFS = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "stratified_cliffs"),
                new StratifiedSurfaceBuilder(TernarySurfaceConfig::deserialize, TernarySurfaceConfig::method_26680));
        FLOATING_ISLANDS = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "floating_islands"),
                new FloatingIslandSurfaceBuilder(TernarySurfaceConfig::deserialize, TernarySurfaceConfig::method_26680));
        DUNES = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "dunes"),
                new DuneSurfaceBuilder(TernarySurfaceConfig::deserialize, TernarySurfaceConfig::method_26680));
        SANDY_DUNES = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "sandy_dunes"),
                new SandyDunesSurfaceBuilder(TernarySurfaceConfig::deserialize, TernarySurfaceConfig::method_26680));
        LAZY_NOISE = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "lazy_noise"),
                new LazyNoiseSurfaceBuilder(TernarySurfaceConfig::deserialize, TernarySurfaceConfig::method_26680));
        HYPER_FLAT = Registry.register(Registry.SURFACE_BUILDER, new Identifier(MOD_ID, "hyper_flat"),
                new HyperflatSurfaceBuilder(TernarySurfaceConfig::deserialize, TernarySurfaceConfig::method_26680));
    }
}
