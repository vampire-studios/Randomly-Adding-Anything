package io.github.vampirestudios.raa.generation.feature.tree.foliage;

import com.google.common.collect.ImmutableMap;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class SmallPineFoliagePlacer extends FoliagePlacer {
    private final int height;
    private final int randomHeight;

    public SmallPineFoliagePlacer(int radius, int randomRadius, int offset, int randomOffset, int height, int randomHeight) {
        super(radius, randomRadius, offset, randomOffset, FoliagePlacerType.PINE_FOLIAGE_PLACER);
        this.height = height;
        this.randomHeight = randomHeight;
    }

    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig treeFeatureConfig, int trunkHeight, TreeNode node, int foliageHeight, int radius, Set<BlockPos> leaves, int i) {
        int j = 0;

        for(int k = i; k >= i - foliageHeight; --k) {
            if (k == i) {
                this.generate(world, random, treeFeatureConfig, node.getCenter(), 0, leaves, k + 1, node.isGiantTrunk());
            }
            this.generate(world, random, treeFeatureConfig, node.getCenter(), 1, leaves, k, node.isGiantTrunk());
            if (j >= 1 && k == i - foliageHeight + 1) {
                --j;
            } else if (j < radius + node.getFoliageRadius()) {
                ++j;
            }
        }

    }

    public int getRadius(Random random, int baseHeight) {
        return super.getRadius(random, baseHeight) + random.nextInt(baseHeight + 1);
    }

    public int getHeight(Random random, int trunkHeight, TreeFeatureConfig treeFeatureConfig) {
        return this.height + random.nextInt(this.randomHeight + 1);
    }

    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean bl) {
        return baseHeight == dz && dy == dz && dz > 0;
    }

    public <T> T serialize(DynamicOps<T> ops) {
        ImmutableMap.Builder<T, T> builder = ImmutableMap.builder();
        builder.put(ops.createString("height"), ops.createInt(this.height)).put(ops.createString("height_random"), ops.createInt(this.randomHeight));
        return ops.merge(super.serialize(ops), ops.createMap(builder.build()));
    }

}
