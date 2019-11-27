package io.github.vampirestudios.raa.generation.feature.tree.foliage;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.registries.FoliagePlacers;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

import java.util.Random;
import java.util.Set;

public class UpsideDownOakFoliagePlacer extends FoliagePlacer {
    public UpsideDownOakFoliagePlacer(int radius, int randomRadius) {
        super(radius, randomRadius, FoliagePlacers.UPSIDE_DOWN);
    }

    public <T> UpsideDownOakFoliagePlacer(Dynamic<T> dynamic_1) {
        this(dynamic_1.get("radius").asInt(0), dynamic_1.get("radius_random").asInt(0));
    }

    @Override
    public void generate(ModifiableTestableWorld modifiableTestableWorld, Random random, BranchedTreeFeatureConfig branchedTreeFeatureConfig, int i, int j, int k, BlockPos blockPos, Set<BlockPos> set) {
        //cylinder spruce
//        int int_4 = random.nextInt(3);
//        int int_5 = 2;
//        int int_6 = 1;
//        for(int int_7 = i; int_7 >= j; --int_7) {
//            this.placeLeaves(modifiableTestableWorld, random, branchedTreeFeatureConfig, i, blockPos, int_7+1, int_4, set);
//            if (int_4 >= int_5) {
//                int_4 = int_6;
//                int_6 = 1;
//                int_5 = Math.min(int_5 + 1, k);
//            } else {
//                ++int_4;
//            }
//        }

        //hat spruce
//        int int_4 = random.nextInt(3);
//        int int_5 = 2;
//        int int_6 = 1;
//        for(int int_7 = i; int_7 >= j; --int_7) {
//            this.placeLeaves(modifiableTestableWorld, random, branchedTreeFeatureConfig, i, blockPos, int_7+1, int_4, set);
//            if (int_4 >= int_5) {
//                int_4 = int_6;
//                int_6 = 1;
//                int_5 = Math.min(int_5 + 1, k);
//            } else {
//                ++int_4;
//            }
//        }

        //long oak
//        for(int int_4 = i; int_4 >= j; --int_4) {
//            int int_5 = Math.max(k - 1 - (int_4 - i) / 6, 0);
//            this.placeLeaves(modifiableTestableWorld, random, branchedTreeFeatureConfig, i, blockPos, int_4, int_5, set);
//        }

        //upside down oak
        int int_4 = 3; //replace with foliage size
        for(int int_5 = i; int_5 >= j; --int_5) {
            this.generate(modifiableTestableWorld, random, branchedTreeFeatureConfig, i, blockPos, int_5, int_4, set);
            if (int_5%2 == 0) --int_4;
            if (int_4 == 0) break;
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
