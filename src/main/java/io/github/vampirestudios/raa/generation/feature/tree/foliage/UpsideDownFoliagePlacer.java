package io.github.vampirestudios.raa.generation.feature.tree.foliage;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;
import net.minecraft.world.gen.foliage.FoliagePlacerType;

import java.util.Random;
import java.util.Set;

public class UpsideDownFoliagePlacer extends FoliagePlacer {
    private final int height;

    public UpsideDownFoliagePlacer(int radius, int randomRadius, int offset, int randomOffset, int height) {
        super(radius, randomRadius, offset, randomOffset, FoliagePlacerType.BLOB_FOLIAGE_PLACER);
        this.height = height;
    }

    @Override
    public void generate(ModifiableTestableWorld world, Random random, BranchedTreeFeatureConfig config, int trunkHeight, BlockPos pos, int foliageHeight, int radius, Set<BlockPos> leaves) {
        int j = this.radius;
        int i = 2;

        for(int m = foliageHeight; m >= 0; --m) {
            if (i == 0) {
                j--;
                i = 2;
                if (j == 0) break;
            }
            this.generate(world, random, config, pos, foliageHeight, m, j, leaves);
            i--;
        }
    }

    @Override
    public int getHeight(Random random, int trunkHeight) {
        return this.height;
    }

    @Override
    public int getRadius(Random random, int baseHeight, BranchedTreeFeatureConfig config) {
        return this.radius + random.nextInt(this.randomRadius + 1);
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int baseHeight, int dx, int dy, int dz, int radius) {
        return Math.abs(dx) == radius && Math.abs(dz) == radius && radius > 0;
    }

    @Override
    public int getRadiusForPlacement(int trunkHeight, int baseHeight, int radius) {
        return radius <= 1 ? 0 : 2;
    }
}
