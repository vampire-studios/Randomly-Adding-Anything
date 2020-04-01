package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.dimension.DimensionChunkGenerators;
import io.github.vampirestudios.raa.generation.carvers.CaveCavityCarver;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.dimension.OverworldDimension;
import net.minecraft.world.gen.ChunkRandom;
import net.minecraft.world.gen.chunk.ChunkGenerator;

import java.util.Set;

public class CustomDimension extends Dimension {

    private DimensionType dimensionType;
    private DimensionData dimensionData;
    private Set<Biome> biomeSet;
    private Block stoneBlock;

    private final boolean field_23485;
    private final float field_23486;
    private final double field_23487;
    private final boolean field_23488;
    private final boolean field_23489;
    private final float field_23490;
    private final Vector3f field_23491;
    private final float field_23492;
    private final Vector3f field_23493;
    private final Vec3d field_23494;
    private final Vec3d field_23495;
    private final boolean field_23496;
    private final int field_23499;

    public CustomDimension(World world, DimensionType dimensionType, DimensionData data, Set<Biome> biomeSet, Block stoneBlock) {
        super(world, dimensionType, 0.0F);
        ChunkRandom chunkRandom = new ChunkRandom(world.getSeed());
        this.dimensionType = dimensionType;
        this.dimensionData = data;
        this.biomeSet = biomeSet;
        this.stoneBlock = stoneBlock;
        this.field_23485 = chunkRandom.nextBoolean();
        this.field_23486 = chunkRandom.nextFloat();
        this.field_23488 = chunkRandom.nextBoolean();
        this.field_23489 = chunkRandom.nextInt(8) == 0;
        this.field_23487 = Math.max(100.0D, chunkRandom.nextGaussian() * 3.0D * 24000.0D);
        this.field_23494 = new Vec3d(chunkRandom.nextDouble(), chunkRandom.nextDouble(), chunkRandom.nextDouble());
        this.field_23495 = new Vec3d(chunkRandom.nextDouble(), chunkRandom.nextDouble(), chunkRandom.nextDouble());
        this.field_23496 = chunkRandom.nextBoolean();
        this.field_23490 = (float)Math.max(5.0D, 30.0D * (1.0D + 4.0D * chunkRandom.nextGaussian()));
        this.field_23492 = (float)Math.max(5.0D, 20.0D * (1.0D + 4.0D * chunkRandom.nextGaussian()));
        this.field_23491 = this.method_26513(chunkRandom);
        this.field_23493 = this.method_26513(chunkRandom);
        this.field_23499 = chunkRandom.nextInt(255);
    }

    private Vector3f method_26513(ChunkRandom chunkRandom) {
        return chunkRandom.nextBoolean() ? new Vector3f(chunkRandom.nextFloat(), chunkRandom.nextFloat(), chunkRandom.nextFloat()) :
                new Vector3f(1.0F, 1.0F, 1.0F);
    }

    public float getSkyAngle(long timeOfDay, float tickDelta) {
        return this.field_23485 ? this.field_23486 : OverworldDimension.method_26524(timeOfDay, this.field_23487);
    }

    public boolean hasVisibleSky() {
        return this.field_23488;
    }

    @Environment(EnvType.CLIENT)
    public Vec3d modifyFogColor(Vec3d vec3d, float tickDelta) {
        return vec3d.multiply((double)tickDelta * this.field_23494.x + this.field_23495.x, (double)tickDelta * this.field_23494.y + this.field_23495.y, (double)tickDelta * this.field_23494.z + this.field_23495.z);
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        CaveCavityCarver.setSeed(world.getSeed());
        return this.dimensionData.getDimensionChunkGenerator().getChunkGenerator(this.world, RandomlyAddingAnything.DIMENSIONAL_BIOMES.
                applyConfig(new DimensionalBiomeSourceConfig(this.world.getLevelProperties()).setBiomes(biomeSet)), this.dimensionData, this.stoneBlock);
    }

    @Override
    public BlockPos getSpawningBlockInChunk(ChunkPos pos, boolean checkMobSpawnValidity) {
        for (int startX = pos.getStartX(); startX <= pos.getEndX(); ++startX) {
            for (int startZ = pos.getStartZ(); startZ <= pos.getEndZ(); ++startZ) {
                BlockPos topSpawningBlockPosition = this.getTopSpawningBlockPosition(startX, startZ, checkMobSpawnValidity);
                if (topSpawningBlockPosition != null) {
                    return topSpawningBlockPosition;
                }
            }
        }
        return null;
    }

    @Override
    public BlockPos getTopSpawningBlockPosition(int x, int z, boolean checkMobSpawnValidity) {
        BlockPos.Mutable mutable = new BlockPos.Mutable(x, 0, z);
        Biome biome = this.world.getBiomeAccess().getBiome(mutable);
        BlockState topMaterial = biome.getSurfaceConfig().getTopMaterial();
        if (checkMobSpawnValidity && !topMaterial.getBlock().isIn(BlockTags.VALID_SPAWN)) {
            return null;
        } else {
            Chunk chunk = this.world.getChunk(x >> 4, z >> 4);
            int heightmap = chunk.sampleHeightmap(Heightmap.Type.MOTION_BLOCKING, x & 15, z & 15);
            if (heightmap < 0) {
                return null;
            } else if (chunk.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, x & 15, z & 15) > chunk.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR, x & 15, z & 15)) {
                return null;
            } else {
                for (int i = heightmap + 1; i >= 0; --i) {
                    mutable.set(x, i, z);
                    BlockState blockState = this.world.getBlockState(mutable);
                    if (!blockState.getFluidState().isEmpty()) {
                        break;
                    }

                    if (blockState.equals(topMaterial)) {
                        return mutable.up().toImmutable();
                    }
                }

                return null;
            }
        }
    }

    /*@Override
    public float getSkyAngle(long timeOfDay, float tickDelta) {
        if (!Utils.checkBitFlag(dimensionData.getFlags(), Utils.LUCID) && dimensionData.hasSky()) {
            double fractionalPart = MathHelper.fractionalPart((double) timeOfDay / 24000.0D - 0.25D);
            double v1 = 0.5D - Math.cos(fractionalPart * 3.141592653589793D) / 2.0D;
            return (float) (fractionalPart * 2.0D + v1) / 3.0F;
        } else {
            return 0.0F;
        }
    }*/

    @Environment(EnvType.CLIENT)
    public float[] getBackgroundColor(float skyAngle, float tickDelta) {
        return null;
    }

    /*@Override
    public boolean hasVisibleSky() {
        return !Utils.checkBitFlag(dimensionData.getFlags(), Utils.LUCID) && dimensionData.hasSky();
    }*/

    @Override
    public boolean hasGround() {
        return !dimensionData.getDimensionChunkGenerator().equals(DimensionChunkGenerators.FLOATING) &&
                !dimensionData.getDimensionChunkGenerator().equals(DimensionChunkGenerators.LAYERED_FLOATING) &&
                !dimensionData.getDimensionChunkGenerator().equals(DimensionChunkGenerators.PRE_CLASSIC_FLOATING);
    }

    @Override
    public float getCloudHeight() {
        return dimensionData.getCloudHeight();
    }

    /*@Override
    @Environment(EnvType.CLIENT)
    public Vec3d modifyFogColor(Vec3d fogColor, float tickDelta) {
        if (Utils.checkBitFlag(dimensionData.getFlags(), Utils.LUCID)) {
            return fogColor.multiply(0.15000000596046448D);
        }
        int fogColor2 = dimensionData.getDimensionColorPalette().getFogColor();
        int[] rgbColor = Color.intToRgb(fogColor2);
        return new Vec3d(rgbColor[0] / 255.0, rgbColor[1] / 255.0, rgbColor[2] / 255.0);
    }*/

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

    public Block getStoneBlock() {
        return stoneBlock;
    }

    public DimensionData getDimensionData() {
        return dimensionData;
    }

}