package io.github.vampirestudios.raa.generation.feature.tree.foliage;


import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;
import com.mojang.datafixers.Dynamic;
import com.mojang.datafixers.types.DynamicOps;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class VanillaPineFoliagePlacer extends FoliagePlacer {
    private final int height;
    private final int randomHeight;

    public VanillaPineFoliagePlacer(int i, int j, int k, int l, int m, int n) {
        super(i, j, k, l, FoliagePlacerType.PINE_FOLIAGE_PLACER);
        this.height = m;
        this.randomHeight = n;
    }

    public <T> VanillaPineFoliagePlacer(Dynamic<T> data) {
        this(data.get("radius").asInt(0), data.get("radius_random").asInt(0), data.get("offset").asInt(0), data.get("offset_random").asInt(0), data.get("height").asInt(0), data.get("height_random").asInt(0));
    }

    public void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig treeFeatureConfig, int trunkHeight, TreeNode arg, int foliageHeight, int radius, Set<BlockPos> leaves, int i) {
        int offset = this.offset + random.nextInt(this.randomOffset + 1);
        int j = 0;

        for(int k = foliageHeight + offset; k >= offset; --k) {
            this.generate(world, random, treeFeatureConfig, trunkHeight, arg, k, j, leaves, i);
            if (j >= 1 && k == offset + 1) {
                --j;
            } else if (j < radius) {
                ++j;
            }
        }

    }

    public int getRadius(Random random, int baseHeight, TreeFeatureConfig config) {
        if (baseHeight < 0) {
            baseHeight *= -1; //make the height positive if it's negative
        }

        return this.radius + random.nextInt(this.randomRadius + 1) + random.nextInt(baseHeight + 1);
    }

    public int getHeight(Random random, int trunkHeight, TreeFeatureConfig treeFeatureConfig) {
        return this.height + random.nextInt(this.randomHeight + 1);
    }

    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean bl) {
        return Math.abs(dx) == radius && Math.abs(dz) == radius && radius > 0;
    }

    public <T> T serialize(DynamicOps<T> ops) {
        Builder<T, T> builder = ImmutableMap.builder();
        builder.put(ops.createString("height"), ops.createInt(this.height)).put(ops.createString("height_random"), ops.createInt(this.randomHeight));
        return ops.merge(super.serialize(ops), ops.createMap(builder.build()));
    }
}
