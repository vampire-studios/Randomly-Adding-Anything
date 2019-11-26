package io.github.vampirestudios.raa.world.gen.feature;

import com.mojang.datafixers.Dynamic;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.BitSet;
import java.util.Random;
import java.util.function.Function;

public class OreFeature extends Feature<OreFeatureConfig> {

    public OreFeature(Function<Dynamic<?>, ? extends OreFeatureConfig> function_1) {
        super(function_1);
    }

    @Override
    public boolean generate(IWorld iWorld, ChunkGenerator<? extends ChunkGeneratorConfig> chunkGenerator, Random random, BlockPos blockPos, OreFeatureConfig oreFeatureConfig) {
        float f = random.nextFloat() * 3.1415927F;
        float g = (float) oreFeatureConfig.size / 8.0F;
        int i = MathHelper.ceil(((float) oreFeatureConfig.size / 16.0F * 2.0F + 1.0F) / 2.0F);
        double startX = (float) blockPos.getX() + MathHelper.sin(f) * g;
        double endX = (float) blockPos.getX() - MathHelper.sin(f) * g;
        double startZ = (float) blockPos.getZ() + MathHelper.cos(f) * g;
        double endZ = (float) blockPos.getZ() - MathHelper.cos(f) * g;
        double startY = blockPos.getY() + random.nextInt(3) - 2;
        double endY = blockPos.getY() + random.nextInt(3) - 2;
        int xPos = blockPos.getX() - MathHelper.ceil(g) - i;
        int yPos = blockPos.getY() - 2 - i;
        int zPos = blockPos.getZ() - MathHelper.ceil(g) - i;
        int size = 2 * (MathHelper.ceil(g) + i);
        int iIdk = 2 * (2 + i);

        for (int x = xPos; x <= xPos + size; ++x) {
            for (int z = zPos; z <= zPos + size; ++z) {
                if (yPos <= iWorld.getTopY(Type.OCEAN_FLOOR_WG, x, z)) {
                    return this.generateVeinPart(iWorld, random, oreFeatureConfig, startX, endX, startZ, endZ, startY, endY, xPos, yPos, zPos, size, iIdk);
                }
            }
        }

        return false;
    }

    protected boolean generateVeinPart(IWorld world, Random random_1, OreFeatureConfig config, double startX, double endX, double startZ, double endZ, double startY, double endY, int x, int y, int z, int size, int i) {
        int j = 0;
        BitSet bitSet = new BitSet(size * i * size);
        Mutable mutable = new Mutable();
        double[] ds = new double[config.size * 4];

        int m;
        double o;
        double p;
        double q;
        double r;
        for (m = 0; m < config.size; ++m) {
            float float_1 = (float) m / (float) config.size;
            o = MathHelper.lerp(float_1, startX, endX);
            p = MathHelper.lerp(float_1, startY, endY);
            q = MathHelper.lerp(float_1, startZ, endZ);
            r = random_1.nextDouble() * (double) config.size / 16.0D;
            double iIdk = ((double) (MathHelper.sin(3.1415927F * float_1) + 1.0F) * r + 1.0D) / 2.0D;
            ds[m * 4] = o;
            ds[m * 4 + 1] = p;
            ds[m * 4 + 2] = q;
            ds[m * 4 + 3] = iIdk;
        }

        for (m = 0; m < config.size - 1; ++m) {
            if (ds[m * 4 + 3] > 0.0D) {
                for (int int_9 = m + 1; int_9 < config.size; ++int_9) {
                    if (ds[int_9 * 4 + 3] > 0.0D) {
                        o = ds[m * 4] - ds[int_9 * 4];
                        p = ds[m * 4 + 1] - ds[int_9 * 4 + 1];
                        q = ds[m * 4 + 2] - ds[int_9 * 4 + 2];
                        r = ds[m * 4 + 3] - ds[int_9 * 4 + 3];
                        if (r * r > o * o + p * p + q * q) {
                            if (r > 0.0D) {
                                ds[int_9 * 4 + 3] = -1.0D;
                            } else {
                                ds[m * 4 + 3] = -1.0D;
                            }
                        }
                    }
                }
            }
        }

        for (m = 0; m < config.size; ++m) {
            double t = ds[m * 4 + 3];
            if (t >= 0.0D) {
                double u = ds[m * 4];
                double v = ds[m * 4 + 1];
                double w = ds[m * 4 + 2];
                int aa = Math.max(MathHelper.floor(u - t), x);
                int ab = Math.max(MathHelper.floor(v - t), y);
                int ac = Math.max(MathHelper.floor(w - t), z);
                int ad = Math.max(MathHelper.floor(u + t), aa);
                int ae = Math.max(MathHelper.floor(v + t), ab);
                int f = Math.max(MathHelper.floor(w + t), ac);

                for (int ag = aa; ag <= ad; ++ag) {
                    double ah = ((double) ag + 0.5D - u) / t;
                    if (ah * ah < 1.0D) {
                        for (int ai = ab; ai <= ae; ++ai) {
                            double aj = ((double) ai + 0.5D - v) / t;
                            if (ah * ah + aj * aj < 1.0D) {
                                for (int ak = ac; ak <= f; ++ak) {
                                    double al = ((double) ak + 0.5D - w) / t;
                                    if (ah * ah + aj * aj + al * al < 1.0D) {
                                        int am = ag - x + (ai - y) * size + (ak - z) * size * i;
                                        if (!bitSet.get(am)) {
                                            bitSet.set(am);
                                            mutable.set(ag, ai, ak);
                                            if (config.target != null) {
                                                if (config.target.getCondition().test(world.getBlockState(mutable))) {
                                                    world.setBlockState(mutable, config.state, 2);
                                                    j++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        return j > 0;
    }
}
