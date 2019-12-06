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
    public void generate(ModifiableTestableWorld modifiableTestableWorld, Random random, BranchedTreeFeatureConfig branchedTreeFeatureConfig, int i, int j, int k, BlockPos blockPos, Set<BlockPos> set) {
        //long oak

        for(int int_4 = i; int_4 >= j; --int_4) {
            int int_5 = Math.max(k - 1 - (int_4 - i) / Math.max(i / 3, 1), 0)+1;
            this.generate(modifiableTestableWorld, random, branchedTreeFeatureConfig, i, blockPos, int_4, Math.min(int_5, 4), set);
        }
    }

    @Override
    public int getRadius(Random random, int i, int j, BranchedTreeFeatureConfig branchedTreeFeatureConfig) {
        return this.radius + random.nextInt(this.randomRadius + 1);
    }

    @Override
    protected boolean method_23451(Random random, int i, int j, int k, int l, int m) {
        return Math.abs(j) == m && Math.abs(l) == m && m > 0;
    }

    @Override
    public int method_23447(int i, int j, int k, int l) {
        return l <= 1 ? 0 : 2;
    }
}
