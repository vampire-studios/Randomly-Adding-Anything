package io.github.vampirestudios.raa.generation.carvers;

import com.google.common.collect.ImmutableSet;
import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.dimension.DimensionChunkGenerators;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;
import net.minecraft.world.gen.carver.Carver;

import java.util.BitSet;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

public class RavineCarver extends RAACarver<ProbabilityConfig> {
    private final float[] heightToHorizontalStretchFactor = new float[1024];
    private DimensionData data;

    public RavineCarver(DimensionData dimensionData) {
        super(ProbabilityConfig::deserialize, dimensionData);
        this.data = dimensionData;
        this.alwaysCarvableBlocks = ImmutableSet.of(Registry.BLOCK.get(Utils.addSuffixToPath(data.getId(),"_stone")),
                Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL,
                Blocks.GRASS_BLOCK, Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA,
                Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA,
                Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA,
                Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.MYCELIUM,
                Blocks.SNOW, Blocks.PACKED_ICE);
    }

    public boolean shouldCarve(Random random_1, int int_1, int int_2, ProbabilityConfig probabilityConfig_1) {
        return random_1.nextFloat() <= probabilityConfig_1.probability;
    }

    public boolean carve(Chunk chunk, Function<BlockPos, Biome> posBiomeFunction, Random random, int chunkX, int chunkZ, int mainChunkX, int mainChunkZ, int i, BitSet bitSet, ProbabilityConfig carverConfig) {
        int branchFactor = (this.getBranchFactor() * 2 - 1) * 16;
        double v = chunkZ * 16 + random.nextInt(16);
        double double_2 = random.nextInt(random.nextInt(40) + 8) + 20;
        double double_3 = mainChunkX * 16 + random.nextInt(16);
        float float_1 = random.nextFloat() * 6.2831855F;
        float float_2 = (random.nextFloat() - 0.5F) * 2.0F / 8.0F;
        float float_3 = (random.nextFloat() * 2.0F + random.nextFloat()) * 2.0F;
        int int_7 = branchFactor - random.nextInt(branchFactor / 4);
        this.carveRavine(chunk, posBiomeFunction, random.nextLong(), chunkX, mainChunkZ, i, v, double_2, double_3, float_3, float_1, float_2, int_7, bitSet);
        return true;
    }

    private void carveRavine(Chunk chunk, Function<BlockPos, Biome> posBiomeFunction, long l, int chunkX, int chunkZ, int mainChunkZ, double v, double v1, double v2, float float_1, float float_2, float float_3, int int_5, BitSet bitSet_1) {
        Random random_1 = new Random(l);
        float float_4 = 1.0F;

        for (int int_6 = 0; int_6 < 256; ++int_6) {
            if (int_6 == 0 || random_1.nextInt(3) == 0) {
                float_4 = 1.0F + random_1.nextFloat() * random_1.nextFloat();
            }

            this.heightToHorizontalStretchFactor[int_6] = float_4 * float_4;
        }

        float float_5 = 0.0F;
        float float_6 = 0.0F;

        for (int int_7 = 0; int_7 < int_5; ++int_7) {
            double double_5 = 1.5D + (double) (MathHelper.sin((float) int_7 * 3.1415927F / (float) int_5) * float_1);
            double double_6 = double_5 * 3.0;
            double_5 *= (double) random_1.nextFloat() * 0.25D + 0.75D;
            double_6 *= (double) random_1.nextFloat() * 0.25D + 0.75D;
            float float_7 = MathHelper.cos(float_3);
            float float_8 = MathHelper.sin(float_3);
            v += MathHelper.cos(float_2) * float_7;
            v1 += float_8;
            v2 += MathHelper.sin(float_2) * float_7;
            float_3 *= 0.7F;
            float_3 += float_6 * 0.05F;
            float_2 += float_5 * 0.05F;
            float_6 *= 0.8F;
            float_5 *= 0.5F;
            float_6 += (random_1.nextFloat() - random_1.nextFloat()) * random_1.nextFloat() * 2.0F;
            float_5 += (random_1.nextFloat() - random_1.nextFloat()) * random_1.nextFloat() * 4.0F;
            if (random_1.nextInt(4) != 0) {
                if (!this.canCarveBranch(chunkZ, mainChunkZ, v, v2, int_7, int_5, float_1)) {
                    return;
                }

                this.carveRegion(chunk, posBiomeFunction, l, chunkX, chunkZ, mainChunkZ, v, v1, v2, double_5, double_6, bitSet_1);
            }
        }

    }

    protected boolean isPositionExcluded(double double_1, double double_2, double double_3, int int_1) {
        return (double_1 * double_1 + double_3 * double_3) * (double) this.heightToHorizontalStretchFactor[int_1 - 1] + double_2 * double_2 / 6.0D >= 1.0D;
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
            BlockState blockState_2 = chunk_1.getBlockState(blockPos$Mutable_2.set(blockPos$Mutable_1).offset(Direction.UP));
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
                        blockPos$Mutable_3.set(blockPos$Mutable_1).offset(Direction.DOWN);
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
