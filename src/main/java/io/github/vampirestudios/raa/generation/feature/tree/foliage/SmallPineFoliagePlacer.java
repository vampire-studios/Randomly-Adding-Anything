package io.github.vampirestudios.raa.generation.feature.tree.foliage;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class SmallPineFoliagePlacer extends FoliagePlacer {
    private final int field_23755;
    private final int field_23756;

    public SmallPineFoliagePlacer(int i, int j, int k, int l, int m, int n) {
        super(i, j, k, l, FoliagePlacerType.PINE_FOLIAGE_PLACER);
        this.field_23755 = m;
        this.field_23756 = n;
    }

    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig treeFeatureConfig, int trunkHeight, TreeNode arg, int foliageHeight, int radius, Set<BlockPos> leaves, int i) {
        int i2 = 0;

        for(int j = trunkHeight; j >= 0; --j) {
            this.generate(world, random, treeFeatureConfig, trunkHeight, arg, foliageHeight, radius, leaves, i);
            if (i2 >= 1 && j == trunkHeight + 1) {
                --i2;
            } else if (i2 < radius) {
                ++i2;
            }
        }

    }

    @Override
    public int getHeight(Random random, int trunkHeight, TreeFeatureConfig treeFeatureConfig) {
        return this.field_23755 + random.nextInt(this.field_23756 + 1);
    }

    public int getRadius(Random random, int baseHeight, TreeFeatureConfig config) {
        return 1;
    }

    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean bl) {
        return Math.abs(dx) == radius && Math.abs(dz) == radius && radius > 0;
    }

}
