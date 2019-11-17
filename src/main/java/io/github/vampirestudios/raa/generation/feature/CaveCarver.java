package io.github.vampirestudios.raa.generation.feature;

import com.google.common.collect.ImmutableSet;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.dimension.DimensionChunkGenerators;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class CaveCarver extends Carver<ProbabilityConfig> {
    private DimensionData data;

    public CaveCarver(DimensionData dimensionData) {
        super(ProbabilityConfig::deserialize, 256);
        this.alwaysCarvableBlocks = ImmutableSet.of(Registry.BLOCK.get(new Identifier(RandomlyAddingAnything.MOD_ID, dimensionData.getName().toLowerCase() + "_stone")),
                Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL,
                Blocks.GRASS_BLOCK, Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA,
                Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA,
                Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA,
                Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.MYCELIUM,
                Blocks.SNOW, Blocks.PACKED_ICE);
        this.data = dimensionData;
    }

    public boolean shouldCarve(Random random_1, int int_1, int int_2, ProbabilityConfig probabilityConfig_1) {
        return random_1.nextFloat() <= probabilityConfig_1.probability;
    }

    public boolean carve(Chunk chunk_1, Function<BlockPos, Biome> function_1, Random random_1, int int_1, int int_2, int int_3, int int_4, int int_5, BitSet bitSet_1, ProbabilityConfig probabilityConfig_1) {
        int int_6 = (this.getBranchFactor() * 2 - 1) * 16;
        int int_7 = random_1.nextInt(random_1.nextInt(random_1.nextInt(this.getMaxCaveCount()) + 1) + 1);

        for (int int_8 = 0; int_8 < int_7; ++int_8) {
            double double_1 = int_2 * 16 + random_1.nextInt(16);
            double double_2 = this.getCaveY(random_1);
            double double_3 = int_3 * 16 + random_1.nextInt(16);
            int int_9 = 1;
            float float_3;
            if (random_1.nextInt(4) == 0) {
                float_3 = 1.0F + random_1.nextFloat() * 6.0F;
                this.carveCave(chunk_1, function_1, random_1.nextLong(), int_1, int_4, int_5, double_1, double_2, double_3, float_3, bitSet_1);
                int_9 += random_1.nextInt(4);
            }

            for (int int_10 = 0; int_10 < int_9; ++int_10) {
                float float_2 = random_1.nextFloat() * 6.2831855F;
                float_3 = (random_1.nextFloat() - 0.5F) / 4.0F;
                float float_4 = this.getTunnelSystemWidth(random_1);
                int int_11 = int_6 - random_1.nextInt(int_6 / 4);
                this.carveTunnels(chunk_1, function_1, random_1.nextLong(), int_1, int_4, int_5, double_1, double_2, double_3, float_4, float_2, float_3, 0, int_11, this.getTunnelSystemHeightWidthRatio(), bitSet_1);
            }
        }

        return true;
    }

    protected int getMaxCaveCount() {
        return 15;
    }

    protected float getTunnelSystemWidth(Random random_1) {
        float float_1 = random_1.nextFloat() * 2.0F + random_1.nextFloat();
        if (random_1.nextInt(10) == 0) {
            float_1 *= random_1.nextFloat() * random_1.nextFloat() * 3.0F + 1.0F;
        }

        return float_1;
    }

    protected double getTunnelSystemHeightWidthRatio() {
        return 1.0D;
    }

    protected int getCaveY(Random random_1) {
        return random_1.nextInt(random_1.nextInt(120) + 8);
    }

    protected void carveCave(Chunk chunk_1, Function<BlockPos, Biome> function_1, long long_1, int int_1, int int_2, int int_3, double double_1, double double_2, double double_3, float float_1, BitSet bitSet_1) {
        double double_5 = 1.5D + (double) (MathHelper.sin(1.5707964F) * float_1);
        double double_6 = double_5 * 0.5;
        this.carveRegion(chunk_1, function_1, long_1, int_1, int_2, int_3, double_1 + 1.0D, double_2, double_3, double_5, double_6, bitSet_1);
    }

    protected void carveTunnels(Chunk chunk_1, Function<BlockPos, Biome> function_1, long long_1, int int_1, int int_2, int int_3, double double_1, double double_2, double double_3, float float_1, float float_2, float float_3, int int_4, int int_5, double double_4, BitSet bitSet_1) {
        Random random_1 = new Random(long_1);
        int int_6 = random_1.nextInt(int_5 / 2) + int_5 / 4;
        boolean boolean_1 = random_1.nextInt(6) == 0;
        float float_4 = 0.0F;
        float float_5 = 0.0F;

        for (int int_7 = int_4; int_7 < int_5; ++int_7) {
            double double_5 = 1.5D + (double) (MathHelper.sin(3.1415927F * (float) int_7 / (float) int_5) * float_1);
            double double_6 = double_5 * double_4;
            float float_6 = MathHelper.cos(float_3);
            double_1 += MathHelper.cos(float_2) * float_6;
            double_2 += MathHelper.sin(float_3);
            double_3 += MathHelper.sin(float_2) * float_6;
            float_3 *= boolean_1 ? 0.92F : 0.7F;
            float_3 += float_5 * 0.1F;
            float_2 += float_4 * 0.1F;
            float_5 *= 0.9F;
            float_4 *= 0.75F;
            float_5 += (random_1.nextFloat() - random_1.nextFloat()) * random_1.nextFloat() * 2.0F;
            float_4 += (random_1.nextFloat() - random_1.nextFloat()) * random_1.nextFloat() * 4.0F;
            if (int_7 == int_6 && float_1 > 1.0F) {
                this.carveTunnels(chunk_1, function_1, random_1.nextLong(), int_1, int_2, int_3, double_1, double_2, double_3, random_1.nextFloat() * 0.5F + 0.5F, float_2 - 1.5707964F, float_3 / 3.0F, int_7, int_5, 1.0D, bitSet_1);
                this.carveTunnels(chunk_1, function_1, random_1.nextLong(), int_1, int_2, int_3, double_1, double_2, double_3, random_1.nextFloat() * 0.5F + 0.5F, float_2 + 1.5707964F, float_3 / 3.0F, int_7, int_5, 1.0D, bitSet_1);
                return;
            }

            if (random_1.nextInt(4) != 0) {
                if (!this.canCarveBranch(int_2, int_3, double_1, double_3, int_7, int_5, float_1)) {
                    return;
                }

                this.carveRegion(chunk_1, function_1, long_1, int_1, int_2, int_3, double_1, double_2, double_3, double_5, double_6, bitSet_1);
            }
        }

    }

    protected boolean isPositionExcluded(double double_1, double double_2, double double_3, int int_1) {
        return double_2 <= -0.7D || double_1 * double_1 + double_2 * double_2 + double_3 * double_3 >= 1.0D;
    }

    @Override
    protected boolean carveAtPoint(Chunk chunk_1, Function<BlockPos, Biome> function_1, BitSet bitSet_1, Random random_1, BlockPos.Mutable blockPos$Mutable_1, BlockPos.Mutable blockPos$Mutable_2, BlockPos.Mutable blockPos$Mutable_3, int int_1, int int_2, int int_3, int int_4, int int_5, int int_6, int int_7, int int_8, AtomicBoolean atomicBoolean_1) {
        int int_9 = int_6 | int_8 << 4 | int_7 << 8;
        if (bitSet_1.get(int_9)) {
            return false;
        } else {
            bitSet_1.set(int_9);
            blockPos$Mutable_1.set(int_4, int_7, int_5);
            BlockState blockState_1 = chunk_1.getBlockState(blockPos$Mutable_1);
            BlockState blockState_2 = chunk_1.getBlockState(blockPos$Mutable_2.set(blockPos$Mutable_1).setOffset(Direction.UP));
            if (blockState_1.getBlock() == Blocks.GRASS_BLOCK || blockState_1.getBlock() == Blocks.MYCELIUM) {
                atomicBoolean_1.set(true);
            }

            if (!this.canCarveBlock(blockState_1, blockState_2)) {
                return false;
            } else {
                if (int_7 < 11) {
                    DimensionChunkGenerators generator = data.getDimensionChunkGenerator();
                    if (generator == DimensionChunkGenerators.FLOATING || generator == DimensionChunkGenerators.PRE_CLASSIC_FLOATING || generator == DimensionChunkGenerators.LAYERED_FLOATING)
                        return true;
                    chunk_1.setBlockState(blockPos$Mutable_1, LAVA.getBlockState(), false);
                } else {
                    chunk_1.setBlockState(blockPos$Mutable_1, CAVE_AIR, false);
                    if (atomicBoolean_1.get()) {
                        blockPos$Mutable_3.set(blockPos$Mutable_1).setOffset(Direction.DOWN);
                        if (chunk_1.getBlockState(blockPos$Mutable_3).getBlock() == Blocks.DIRT) {
                            chunk_1.setBlockState(blockPos$Mutable_3, function_1.apply(blockPos$Mutable_1).getSurfaceConfig().getTopMaterial(), false);
                        }
                    }
                }

                return true;
            }
        }
    }
}
