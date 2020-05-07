package io.github.vampirestudios.raa.generation.feature.tree.foliage;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class EcotonesHazelFoliagePlacer extends FoliagePlacer {
    private final int height;

    public EcotonesHazelFoliagePlacer(int radius, int randomRadius, int offset, int randomOffset, int height) {
        super(radius, randomRadius, offset, randomOffset, FoliagePlacerType.BLOB_FOLIAGE_PLACER);
        this.height = height;
    }

    @Override
    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig config, int trunkHeight, TreeNode treeNode, int foliageHeight, int radius, Set<BlockPos> leaves, int i) {
        for(int j = i; j >= i - foliageHeight; --j) {
            int k = Math.max(radius + treeNode.getFoliageRadius() - 1 - j / 2, 0);
            this.generate(world, random, config, treeNode.getCenter(), k, leaves, j, treeNode.isGiantTrunk());
        }
    }

    @Override
    public int getHeight(Random random, int trunkHeight, TreeFeatureConfig treeFeatureConfig) {
        return this.height;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean bl) {
        return baseHeight == dz && dy == dz && dz > 0;
    }

    public int getHeight(Random random, int i) {
        return this.height;
    }

    protected boolean isInvalidForLeaves(Random random, int i, int j, int k, int l, int m) {
        return Math.abs(j) == m && Math.abs(l) == m;
    }

    public int getRadiusForPlacement(int trunkHeight, int baseHeight, int radius) {
        return radius <= 1 ? 0 : 2;
    }

}
