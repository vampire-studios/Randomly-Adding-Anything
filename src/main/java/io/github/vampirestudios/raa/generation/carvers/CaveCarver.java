package io.github.vampirestudios.raa.generation.carvers;

import com.google.common.collect.ImmutableSet;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.ProbabilityConfig;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class CaveCarver extends RAACarver<ProbabilityConfig> {

    public CaveCarver(DimensionData dimensionData) {
        super(ProbabilityConfig::deserialize, dimensionData);
        this.alwaysCarvableBlocks = ImmutableSet.of(Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_stone")),
                Blocks.STONE, Blocks.GRANITE, Blocks.DIORITE, Blocks.ANDESITE, Blocks.DIRT, Blocks.COARSE_DIRT, Blocks.PODZOL,
                Blocks.GRASS_BLOCK, Blocks.TERRACOTTA, Blocks.WHITE_TERRACOTTA, Blocks.ORANGE_TERRACOTTA, Blocks.MAGENTA_TERRACOTTA,
                Blocks.LIGHT_BLUE_TERRACOTTA, Blocks.YELLOW_TERRACOTTA, Blocks.LIME_TERRACOTTA, Blocks.PINK_TERRACOTTA, Blocks.GRAY_TERRACOTTA,
                Blocks.LIGHT_GRAY_TERRACOTTA, Blocks.CYAN_TERRACOTTA, Blocks.PURPLE_TERRACOTTA, Blocks.BLUE_TERRACOTTA, Blocks.BROWN_TERRACOTTA,
                Blocks.GREEN_TERRACOTTA, Blocks.RED_TERRACOTTA, Blocks.BLACK_TERRACOTTA, Blocks.SANDSTONE, Blocks.RED_SANDSTONE, Blocks.MYCELIUM,
                Blocks.SNOW, Blocks.PACKED_ICE);
    }

    public boolean shouldCarve(Random random, int chunkX, int chunkZ, ProbabilityConfig config) {
        return random.nextFloat() <= config.probability;
    }

    public boolean carve(Chunk chunk, Function<BlockPos, Biome> posBiomeFunction, Random random, int chunkX, int chunkZ, int mainChunkX, int mainChunkZ, int i, BitSet bitSet, ProbabilityConfig config) {
        int i5 = (this.getBranchFactor() * 2 - 1) * 16;
        int int_7 = random.nextInt(random.nextInt(random.nextInt(this.getMaxCaveCount()) + 1) + 1);

        for (int i1 = 0; i1 < int_7; ++i1) {
            double z = chunkZ * 16 + random.nextInt(16);
            double y = this.getCaveY(random);
            double x = mainChunkX * 16 + random.nextInt(16);
            int i2 = 1;
            float v;
            if (random.nextInt(4) == 0) {
                v = 1.0F + random.nextFloat() * 6.0F;
                this.carveCave(chunk, posBiomeFunction, random.nextLong(), chunkX, mainChunkZ, i, z, y, x, v, bitSet);
                i2 += random.nextInt(4);
            }

            for (int i4 = 0; i4 < i2; ++i4) {
                float v1 = random.nextFloat() * 6.2831855F;
                v = (random.nextFloat() - 0.5F) / 4.0F;
                float tunnelSystemWidth = this.getTunnelSystemWidth(random);
                int i3 = i5 - random.nextInt(i5 / 4);
                this.carveTunnels(chunk, posBiomeFunction, random.nextLong(), chunkX, mainChunkZ, i, z, y, x, tunnelSystemWidth, v1, v, 0, i3, this.getTunnelSystemHeightWidthRatio(), bitSet);
            }
        }

        return true;
    }

    protected int getMaxCaveCount() {
        return 15;
    }

    protected float getTunnelSystemWidth(Random random) {
        float v = random.nextFloat() * 2.0F + random.nextFloat();
        if (random.nextInt(10) == 0) {
            v *= random.nextFloat() * random.nextFloat() * 3.0F + 1.0F;
        }

        return v;
    }

    protected double getTunnelSystemHeightWidthRatio() {
        return 1.0D;
    }

    protected int getCaveY(Random random) {
        return random.nextInt(random.nextInt(120) + 8);
    }

    protected void carveCave(Chunk chunk, Function<BlockPos, Biome> posBiomeFunction, long long1, int i, int i1, int i2, double v, double v1, double v2, float f, BitSet bitSet) {
        double v3 = 1.5D + (double) (MathHelper.sin(1.5707964F) * f);
        double v4 = v3 * 0.5;
        this.carveRegion(chunk, posBiomeFunction, long1, i, i1, i2, v + 1.0D, v1, v2, v3, v4, bitSet);
    }

    protected void carveTunnels(Chunk chunk, Function<BlockPos, Biome> posBiomeFunction, long seed, int i, int i1, int i2, double v, double v1, double v2, float f, float f1, float f2, int i3, int i4, double v3, BitSet bitSet) {
        Random random = new Random(seed);
        int i5 = random.nextInt(i4 / 2) + i4 / 4;
        boolean isZero = random.nextInt(6) == 0;
        float v4 = 0.0F;
        float v5 = 0.0F;

        for (int i6 = i3; i6 < i4; ++i6) {
            double v6 = 1.5D + (double) (MathHelper.sin(3.1415927F * (float) i6 / (float) i4) * f);
            double v7 = v6 * v3;
            float cos = MathHelper.cos(f2);
            v += MathHelper.cos(f1) * cos;
            v1 += MathHelper.sin(f2);
            v2 += MathHelper.sin(f1) * cos;
            f2 *= isZero ? 0.92F : 0.7F;
            f2 += v5 * 0.1F;
            f1 += v4 * 0.1F;
            v5 *= 0.9F;
            v4 *= 0.75F;
            v5 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 2.0F;
            v4 += (random.nextFloat() - random.nextFloat()) * random.nextFloat() * 4.0F;
            if (i6 == i5 && f > 1.0F) {
                this.carveTunnels(chunk, posBiomeFunction, random.nextLong(), i, i1, i2, v, v1, v2, random.nextFloat() * 0.5F + 0.5F, f1 - 1.5707964F, f2 / 3.0F, i6, i4, 1.0D, bitSet);
                this.carveTunnels(chunk, posBiomeFunction, random.nextLong(), i, i1, i2, v, v1, v2, random.nextFloat() * 0.5F + 0.5F, f1 + 1.5707964F, f2 / 3.0F, i6, i4, 1.0D, bitSet);
                return;
            }

            if (random.nextInt(4) != 0) {
                if (!this.canCarveBranch(i1, i2, v, v2, i6, i4, f)) {
                    return;
                }

                this.carveRegion(chunk, posBiomeFunction, seed, i, i1, i2, v, v1, v2, v6, v7, bitSet);
            }
        }

    }

    protected boolean isPositionExcluded(double scaledRelativeX, double scaledRelativeY, double scaledRelativeZ, int y) {
        return scaledRelativeY <= -0.7D || scaledRelativeX * scaledRelativeX + scaledRelativeY * scaledRelativeY + scaledRelativeZ * scaledRelativeZ >= 1.0D;
    }
}
