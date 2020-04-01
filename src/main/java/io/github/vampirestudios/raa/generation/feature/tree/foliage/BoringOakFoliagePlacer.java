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

public class BoringOakFoliagePlacer extends FoliagePlacer {

    public BoringOakFoliagePlacer(int radius, int randomRadius) {
        super(radius, randomRadius, FoliagePlacers.BORING_OAK);
    }

    public <T> BoringOakFoliagePlacer(Dynamic<T> dynamic_1) {
        this(dynamic_1.get("radius").asInt(0), dynamic_1.get("radius_random").asInt(0));
    }

    @Override
    public void generate(ModifiableTestableWorld testableWorld, Random random, BranchedTreeFeatureConfig config, int baseHeight, int trunkHeight, int radius, BlockPos blockPos, Set<BlockPos> leaves) {
        int range = Rands.randIntRange(1, 2); //replace with foliage size
        for (int height = baseHeight; height >= trunkHeight + 2; --height) {
            if (height == trunkHeight + 2) range = 1; //smooth out bigger trees
            this.generate(testableWorld, random, config, baseHeight, blockPos, height, range, leaves);
        }
    }

    @Override
    public int getRadius(Random random, int baseHeight, int trunkHeight, BranchedTreeFeatureConfig config) {
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

    public static BoringOakFoliagePlacer method_26653(Random random) {
        return new BoringOakFoliagePlacer(random.nextInt(10) + 1, random.nextInt(5));
    }
}
