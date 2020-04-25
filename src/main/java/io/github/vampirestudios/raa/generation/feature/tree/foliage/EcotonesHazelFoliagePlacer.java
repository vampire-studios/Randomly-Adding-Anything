package io.github.vampirestudios.raa.generation.feature.tree.foliage;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class EcotonesHazelFoliagePlacer extends FoliagePlacer {
    private final int field_23752;

    public EcotonesHazelFoliagePlacer(int i, int j, int k, int l, int m) {
        super(i, j, k, l, FoliagePlacerType.BLOB_FOLIAGE_PLACER);
        this.field_23752 = m;
    }

    @Override
    protected void generate(ModifiableTestableWorld world, Random random, TreeFeatureConfig treeFeatureConfig, int trunkHeight, class_5208 arg, int foliageHeight, int radius, Set<BlockPos> leaves, int i) {
        int h = 0;
        int m = 1;
        int t = 3;
        for(int l = i; l >= trunkHeight; --l) {
            h++;
            if (h == t) {
                h = 0;
                t--;
            }
            this.generate(world, random, treeFeatureConfig, trunkHeight, arg, foliageHeight, radius, leaves, i);
        }
    }

    @Override
    public int getHeight(Random random, int trunkHeight, TreeFeatureConfig treeFeatureConfig) {
        return this.field_23752;
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, boolean bl) {
        return Math.abs(dx) == 0 && Math.abs(dz) == 0;
    }

    @Override
    public int getRadius(Random random, int baseHeight) {
        return this.radius + random.nextInt(this.randomRadius + 1);
    }


}
