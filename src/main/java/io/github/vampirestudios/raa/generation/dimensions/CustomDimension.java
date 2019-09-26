package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.client.Color;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;

public class CustomDimension extends Dimension {

    private DimensionType dimensionType;
    private io.github.vampirestudios.raa.generation.dimensions.Dimension dimension;

    public CustomDimension(World world_1, DimensionType dimensionType_1) {
        super(world_1, dimensionType_1);
        this.dimensionType = dimensionType_1;
    }

    public CustomDimension(World world_1, DimensionType dimensionType_1, io.github.vampirestudios.raa.generation.dimensions.Dimension dimension) {
        super(world_1, dimensionType_1);
        this.dimensionType = dimensionType_1;
        this.dimension = dimension;
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        return null;
    }

    @Override
    public BlockPos getSpawningBlockInChunk(ChunkPos chunkPos, boolean b) {
        return null;
    }

    @Override
    public BlockPos getTopSpawningBlockPosition(int i, int i1, boolean b) {
        return null;
    }

    @Override
    public float getSkyAngle(long l, float v) {
        return 0;
    }

    @Override
    public boolean hasVisibleSky() {
        return false;
    }

    @Override
    public Vec3d getFogColor(float v, float v1) {
        int fogColor = dimension.getFogColor();
        int[] rgbColor = Color.intToRgb(fogColor);
        return new Vec3d(rgbColor[0], rgbColor[1], rgbColor[2]);
    }

    @Override
    public boolean canPlayersSleep() {
        return false;
    }

    @Override
    public boolean shouldRenderFog(int i, int i1) {
        return false;
    }

    @Override
    public DimensionType getType() {
        return dimensionType;
    }

}