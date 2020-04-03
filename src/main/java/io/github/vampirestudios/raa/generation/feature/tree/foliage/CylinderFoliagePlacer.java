/*
package io.github.vampirestudios.raa.generation.feature.tree.foliage;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.registries.FoliagePlacers;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

import java.util.Random;
import java.util.Set;

public class CylinderFoliagePlacer extends FoliagePlacer {
    public CylinderFoliagePlacer(int radius, int randomRadius) {
        super(radius, randomRadius, FoliagePlacers.CYLINDER);
    }

    public <T> CylinderFoliagePlacer(Dynamic<T> dynamic_1) {
        this(dynamic_1.get("radius").asInt(0), dynamic_1.get("radius_random").asInt(0));
    }

    @Override
    public void generate(ModifiableTestableWorld modifiableTestableWorld, Random random, BranchedTreeFeatureConfig branchedTreeFeatureConfig, int i, int j, int k, BlockPos blockPos, Set<BlockPos> set) {
        for (int int_4 = i; int_4 >= j; --int_4) {
            this.generate(modifiableTestableWorld, random, branchedTreeFeatureConfig, i, blockPos, int_4, Rands.randIntRange(0, 5), set);
        }
    }

    @Override
    public int getRadius(Random random, int i, int j, BranchedTreeFeatureConfig branchedTreeFeatureConfig) {
        return this.radius + random.nextInt(this.randomRadius + 1);
    }

    @Override
    protected boolean isInvalidForLeaves(Random random, int baseHeight, int x, int y, int z, int radius) {
        return Math.abs(x) == radius && Math.abs(z) == radius && radius > 0;
    }

    @Override
    public int getRadiusForPlacement(int trunkHeight, int baseHeight, int radius, int currentTreeHeight) {
        return currentTreeHeight <= 1 ? 0 : 2;
    }

}*/
