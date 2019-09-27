package io.github.vampirestudios.raa.generation.dimensions;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.client.Color;
import io.github.vampirestudios.raa.init.CustomDimensionBiome;
import io.github.vampirestudios.raa.utils.RegistryUtils;
import net.minecraft.block.BlockState;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.source.FixedBiomeSource;
import net.minecraft.world.biome.source.FixedBiomeSourceConfig;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.OverworldChunkGenerator;
import net.minecraft.world.gen.chunk.OverworldChunkGeneratorConfig;

public class CustomDimension extends Dimension {

    private DimensionType dimensionType;
    private DimensionData dimension;

    public CustomDimension(World world_1, DimensionType dimensionType_1, DimensionData dimension) {
        super(world_1, dimensionType_1);
        this.dimensionType = dimensionType_1;
        this.dimension = dimension;
    }

    @Override
    public ChunkGenerator<?> createChunkGenerator() {
        //Biome biome = Registry.BIOME.get(new Identifier("randombiomes", Integer.toString(dimension.rID)));
        CustomDimensionBiome biome = new CustomDimensionBiome(dimension);
        RegistryUtils.register(new Identifier(RandomlyAddingAnything.MOD_ID, dimension.getName().toLowerCase() + "_biome"), biome);
        return new OverworldChunkGenerator(world, new FixedBiomeSource(new FixedBiomeSourceConfig(world.getLevelProperties()).setBiome(biome)), new OverworldChunkGeneratorConfig());
    }

    @Override
    public BlockPos getSpawningBlockInChunk(ChunkPos chunkPos, boolean b) {
        for(int int_1 = chunkPos.getStartX(); int_1 <= chunkPos.getEndX(); ++int_1) {
            for(int int_2 = chunkPos.getStartZ(); int_2 <= chunkPos.getEndZ(); ++int_2) {
                BlockPos blockPos_1 = this.getTopSpawningBlockPosition(int_1, int_2, b);
                if (blockPos_1 != null) {
                    return blockPos_1;
                }
            }
        }

        return null;
    }

    @Override
    public BlockPos getTopSpawningBlockPosition(int int_1, int int_2, boolean boolean_1) {
        BlockPos.Mutable blockPos$Mutable_1 = new BlockPos.Mutable(int_1, 0, int_2);
        Biome biome_1 = this.world.getBiome(blockPos$Mutable_1);
        BlockState blockState_1 = biome_1.getSurfaceConfig().getTopMaterial();
        if (boolean_1 && !blockState_1.getBlock().matches(BlockTags.VALID_SPAWN)) {
            return null;
        } else {
            WorldChunk worldChunk_1 = this.world.method_8497(int_1 >> 4, int_2 >> 4);
            int int_3 = worldChunk_1.sampleHeightmap(Heightmap.Type.MOTION_BLOCKING, int_1 & 15, int_2 & 15);
            if (int_3 < 0) {
                return null;
            } else if (worldChunk_1.sampleHeightmap(Heightmap.Type.WORLD_SURFACE, int_1 & 15, int_2 & 15) > worldChunk_1.sampleHeightmap(Heightmap.Type.OCEAN_FLOOR, int_1 & 15, int_2 & 15)) {
                return null;
            } else {
                for(int int_4 = int_3 + 1; int_4 >= 0; --int_4) {
                    blockPos$Mutable_1.set(int_1, int_4, int_2);
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
    public float getSkyAngle(long l, float v) {
        double double_1 = MathHelper.fractionalPart((double)l / 24000.0D - 0.25D);
        double double_2 = 0.5D - Math.cos(double_1 * 3.141592653589793D) / 2.0D;
        return (float)(double_1 * 2.0D + double_2) / 3.0F;
    }

    @Override
    public boolean hasVisibleSky() {
        return dimension.hasSky();
    }

    @Override
    public Vec3d getFogColor(float v, float v1) {
        int fogColor = dimension.getFogColor();
        int[] rgbColor = Color.intToRgb(fogColor);
        return new Vec3d(rgbColor[0], rgbColor[1], rgbColor[2]);
    }

    @Override
    public boolean canPlayersSleep() {
        return dimension.canSleep();
    }

    @Override
    public boolean shouldRenderFog(int i, int i1) {
        return dimension.shouldRenderFog();
    }

    @Override
    public DimensionType getType() {
        return dimensionType;
    }

}