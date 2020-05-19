package io.github.vampirestudios.raa.api.accessor;

import net.minecraft.world.dimension.DimensionType;

public interface DimensionTypeAccessor {

    DimensionType setHellish(boolean hellish);

    DimensionType setDoesWaterVaporize(boolean doesWaterVaporize);
}
