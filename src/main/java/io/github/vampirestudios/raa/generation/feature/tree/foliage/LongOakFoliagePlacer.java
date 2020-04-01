package io.github.vampirestudios.raa.generation.feature.tree.foliage;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.registries.FoliagePlacers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

import java.util.Random;
import java.util.Set;

public class LongOakFoliagePlacer extends FoliagePlacer {
    public LongOakFoliagePlacer(int radius, int randomRadius) {
        super(radius, randomRadius, FoliagePlacers.LONG_OAK);
    }

    public <T> LongOakFoliagePlacer(Dynamic<T> dynamic_1) {
        this(dynamic_1.get("radius").asInt(0), dynamic_1.get("radius_random").asInt(0));
    }

    @Override
    public void generate(ModifiableTestableWorld modifiableTestableWorld, Random random, BranchedTreeFeatureConfig branchedTreeFeatureConfig, int baseHeight, int trunkHeight, int radius, BlockPos blockPos, Set<BlockPos> set) {
        for (int height = baseHeight; height >= trunkHeight; --height) {
            int i = Math.max(radius - 1 - (height - baseHeight) / Math.max(baseHeight / 3, 1), 0) + 1;
            this.generate(modifiableTestableWorld, random, branchedTreeFeatureConfig, baseHeight, blockPos, height, Math.min(i, 4), set);
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

    public static LongOakFoliagePlacer method_26653(Random random) {
        return new LongOakFoliagePlacer(random.nextInt(10) + 1, random.nextInt(5));
    }
}
