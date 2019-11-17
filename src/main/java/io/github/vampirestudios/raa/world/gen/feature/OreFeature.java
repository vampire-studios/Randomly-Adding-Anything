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
        double d = (float) blockPos.getX() + MathHelper.sin(f) * g;
        double e = (float) blockPos.getX() - MathHelper.sin(f) * g;
        double h = (float) blockPos.getZ() + MathHelper.cos(f) * g;
        double j = (float) blockPos.getZ() - MathHelper.cos(f) * g;
        double l = blockPos.getY() + random.nextInt(3) - 2;
        double m = blockPos.getY() + random.nextInt(3) - 2;
        int n = blockPos.getX() - MathHelper.ceil(g) - i;
        int o = blockPos.getY() - 2 - i;
        int p = blockPos.getZ() - MathHelper.ceil(g) - i;
        int q = 2 * (MathHelper.ceil(g) + i);
        int r = 2 * (2 + i);

        for (int s = n; s <= n + q; ++s) {
            for (int t = p; t <= p + q; ++t) {
                if (o <= iWorld.getTopY(Type.OCEAN_FLOOR_WG, s, t)) {
                    return this.generateVeinPart(iWorld, random, oreFeatureConfig, d, e, h, j, l, m, n, o, p, q, r);
                }
            }
        }

        return false;
    }

    protected boolean generateVeinPart(IWorld world, Random random_1, OreFeatureConfig config, double double_1, double double_2, double double_3, double double_4, double double_5, double double_6, int int_1, int int_2, int int_3, int int_4, int int_5) {
        int j = 0;
        BitSet bitSet = new BitSet(int_4 * int_5 * int_4);
        Mutable mutable = new Mutable();
        double[] ds = new double[config.size * 4];

        int m;
        double o;
        double p;
        double q;
        double r;
        for (m = 0; m < config.size; ++m) {
            float float_1 = (float) m / (float) config.size;
            o = MathHelper.lerp(float_1, double_1, double_2);
            p = MathHelper.lerp(float_1, double_5, double_6);
            q = MathHelper.lerp(float_1, double_3, double_4);
            r = random_1.nextDouble() * (double) config.size / 16.0D;
            double i = ((double) (MathHelper.sin(3.1415927F * float_1) + 1.0F) * r + 1.0D) / 2.0D;
            ds[m * 4] = o;
            ds[m * 4 + 1] = p;
            ds[m * 4 + 2] = q;
            ds[m * 4 + 3] = i;
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
                int aa = Math.max(MathHelper.floor(u - t), int_1);
                int ab = Math.max(MathHelper.floor(v - t), int_2);
                int ac = Math.max(MathHelper.floor(w - t), int_3);
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
                                        int am = ag - int_1 + (ai - int_2) * int_4 + (ak - int_3) * int_4 * int_5;
                                        if (!bitSet.get(am)) {
                                            bitSet.set(am);
                                            mutable.set(ag, ai, ak);
                                            if (config.target.getCondition().test(world.getBlockState(mutable))) {
                                                world.setBlockState(mutable, config.state, 2);
                                                ++j;
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
