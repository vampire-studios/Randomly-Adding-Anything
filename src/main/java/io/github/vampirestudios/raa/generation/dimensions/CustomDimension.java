package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.vampirelib.utils.Color;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Set;

public class CustomDimension extends Dimension {

    private DimensionType dimensionType;
    private DimensionData dimensionData;
    private Set<Biome> biomeSet;
    private Block stoneBlock;

    public CustomDimension(World world, DimensionType dimensionType, DimensionData data, Set<Biome> biomeSet, Block stoneBlock) {
        super(world, dimensionType, 0.0F);
        this.dimensionType = dimensionType;
        this.dimensionData = data;
        this.biomeSet = biomeSet;
        this.stoneBlock = stoneBlock;
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        return this.dimensionData.getDimensionChunkGenerator().getChunkGenerator(this.world, RandomlyAddingAnything.DIMENSIONAL_BIOMES.
                applyConfig(new DimensionalBiomeSourceConfig(this.world.getLevelProperties()).setBiomes(biomeSet)), this.dimensionData, this.stoneBlock);
    }

    @Override
    public BlockPos getSpawningBlockInChunk(ChunkPos pos, boolean checkMobSpawnValidity) {
        for (int int_1 = pos.getStartX(); int_1 <= pos.getEndX(); ++int_1) {
            for (int int_2 = pos.getStartZ(); int_2 <= pos.getEndZ(); ++int_2) {
                BlockPos blockPos_1 = this.getTopSpawningBlockPosition(int_1, int_2, checkMobSpawnValidity);
                if (blockPos_1 != null) {
                    return blockPos_1;
                }
            }
        }
        return null;
    }

    @Override
    public BlockPos getTopSpawningBlockPosition(int x, int z, boolean checkMobSpawnValidity) {
        BlockPos.Mutable blockPos$Mutable_1 = new BlockPos.Mutable(x, 0, z);
        Biome biome_1 = this.world.getBiomeAccess().getBiome(blockPos$Mutable_1);
        BlockState blockState_1 = biome_1.getSurfaceConfig().getTopMaterial();
        if (checkMobSpawnValidity && !blockState_1.getBlock().isIn(BlockTags.VALID_SPAWN)) {
            return null;
        } else {
            Chunk worldChunk_1 = this.world.getChunk(x >> 4, z >> 4);
            int int_3 = worldChunk_1.sampleHeightmap(Heightmap.Type.MOTION_BLOCKING, x & 15, z & 15);
            if (int_3 < 0) {
                return null;
            } else if (worldChunk_1.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, x & 15, z & 15) > worldChunk_1.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR, x & 15, z & 15)) {
                return null;
            } else {
                for (int int_4 = int_3 + 1; int_4 >= 0; --int_4) {
                    blockPos$Mutable_1.set(x, int_4, z);
                    BlockState blockState_2 = this.world.getBlockState(blockPos$Mutable_1);
                    if (!blockState_2.getFluidState().isEmpty()) {
                        break;
                    }

                    if (blockState_2.equals(blockState_1)) {
                        return blockPos$Mutable_1.up().toImmutable();
                    }
                }

                return null;
            }
        }
    }

    @Override
    public float getSkyAngle(long timeOfDay, float tickDelta) {
        if (dimensionData.hasSky()) {
            double fractionalPart = MathHelper.fractionalPart((double) timeOfDay / 24000.0D - 0.25D);
            double v1 = 0.5D - Math.cos(fractionalPart * 3.141592653589793D) / 2.0D;
            return (float) (fractionalPart * 2.0D + v1) / 3.0F;
        } else {
            return 0.0F;
        }
    }

    @Environment(EnvType.CLIENT)
    public float[] getBackgroundColor(float skyAngle, float tickDelta) {
        return null;
    }

    @Override
    public boolean hasVisibleSky() {
        return dimensionData.hasSky();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public Vec3d modifyFogColor(Vec3d fogColor, float tickDelta) {
        int fogColor2 = dimensionData.getDimensionColorPalette().getFogColor();
        int[] rgbColor = Color.intToRgb(fogColor2);
        return new Vec3d(rgbColor[0] / 255.0, rgbColor[1] / 255.0, rgbColor[2] / 255.0);
    }

    @Override
    public boolean canPlayersSleep() {
        return dimensionData.canSleep();
    }

    @Override
    public boolean doesWaterVaporize() {
        return dimensionData.doesWaterVaporize();
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean isFogThick(int x, int z) {
        return dimensionData.hasThickFog();
    }

    @Override
    public DimensionType getType() {
        return dimensionType;
    }
}