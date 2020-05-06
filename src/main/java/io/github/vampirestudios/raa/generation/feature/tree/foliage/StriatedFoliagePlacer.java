package io.github.vampirestudios.raa.generation.feature.tree.foliage;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class StriatedFoliagePlacer extends FoliagePlacer {
    private final int height;

    public StriatedFoliagePlacer(int radius, int randomRadius, int offset, int randomOffset, int height) {
        super(radius, randomRadius, offset, randomOffset, FoliagePlacerType.BLOB_FOLIAGE_PLACER);
        this.height = height;
    }

    @Override
    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int i) {
        int i2 = 0;

        for(int m = foliageHeight; m >= 0; --m) {
            i2++;
            if (i2 % 2 == 1) {
                this.generate(world, random, config, trunkHeight, treeNode, foliageHeight, radius, leaves);
            }
        }
    }

    @Override
    public int getHeight(Random random, int trunkHeight, TreeFeatureConfig config) {
        return this.height;
    }

    @Override
    public int getRadius(Random random, int baseHeight) {
        return this.radius + random.nextInt(this.randomRadius + 1);
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean bl) {
        return Math.abs(dx) == radius && Math.abs(dz) == radius && radius > 0;
    }

    /*@Override
    public int getRadiusForPlacement(int trunkHeight, int baseHeight, int radius) {
        return radius <= 1 ? 0 : 2;
    }*/

}
