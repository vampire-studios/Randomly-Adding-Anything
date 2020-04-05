package io.github.vampirestudios.raa.api.dimension;

import com.google.common.collect.ImmutableList;
import io.github.vampirestudios.raa.utils.Rands;

public enum DimensionPortalType {
    OBSIDIAN,
    STORAGE_BLOCK,
    SINGLE;

    public static DimensionPortalType getRandom() {
        return Rands.list(ImmutableList.of(OBSIDIAN, STORAGE_BLOCK, SINGLE));
    }
}
