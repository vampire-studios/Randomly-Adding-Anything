package io.github.vampirestudios.raa.api.dimension;

import io.github.vampirestudios.raa.registries.SurfaceBuilders;
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder;

public enum DimensionSurfaceBuilders {
    DARK_BADLANDS,
    PATCHY_DESERT,
    PATCHY_BADLANDS,
    DARK_PATCHY_BADLANDS,
    CLASSIC_CLIFFS,
    STRATIFIED_CLIFFS,
    FLOATING_ISLANDS,
    DUNES,
    SANDY_DUNES,
    LAZY_NOISE,
    HYPER_FLAT;

    public SurfaceBuilder<?> getSurfaceBuilder() {
        if (this == DARK_BADLANDS) return SurfaceBuilders.DARK_BADLANDS;
        if (this == PATCHY_DESERT) return SurfaceBuilders.PATCHY_DESERT;
        if (this == PATCHY_BADLANDS) return SurfaceBuilders.PATCHY_BADLANDS;
        if (this == DARK_PATCHY_BADLANDS) return SurfaceBuilders.DARK_PATCHY_BADLANDS;
        if (this == CLASSIC_CLIFFS) return SurfaceBuilders.CLASSIC_CLIFFS;
        if (this == STRATIFIED_CLIFFS) return SurfaceBuilders.STRATIFIED_CLIFFS;
        if (this == FLOATING_ISLANDS) return SurfaceBuilders.FLOATING_ISLANDS;
        if (this == DUNES) return SurfaceBuilders.DUNES;
        if (this == SANDY_DUNES) return SurfaceBuilders.SANDY_DUNES;
        if (this == LAZY_NOISE) return SurfaceBuilders.LAZY_NOISE;
        if (this == HYPER_FLAT) return SurfaceBuilders.HYPER_FLAT;
        return SurfaceBuilder.DEFAULT;
    }

}
