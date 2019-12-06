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
        // cylinder spruce
//        int int_4 = random.nextInt(3);
//        int int_5 = 2;
//        int int_6 = 1;
//        for(int int_7 = i; int_7 >= j; --int_7) {
//            this.placeLeaves(modifiableTestableWorld, random, branchedTreeFeatureConfig, i, blockPos, int_7+1, Math.max(int_4, 1)+1, set);
//            if (int_4 >= int_5) {
//                int_4 = int_6;
//                int_6 = 1;
//                int_5 = Math.min(int_5 + 1, k);
//            } else {
//                ++int_4;
//            }
//        }
        for(int int_4 = i; int_4 >= j; --int_4) {
            this.generate(modifiableTestableWorld, random, branchedTreeFeatureConfig, i, blockPos, int_4, Rands.randIntRange(0, 5), set);
        }
    }

    public void placeLeaves(ModifiableTestableWorld modifiableTestableWorld_1, Random random_1, BranchedTreeFeatureConfig branchedTreeFeatureConfig_1, int int_1, BlockPos blockPos_1, int int_2, int int_3, Set<BlockPos> set_1) {
        BlockPos.Mutable blockPos$Mutable_1 = new BlockPos.Mutable();

        for(int int_4 = -int_3; int_4 <= int_3; ++int_4) {
            //trunk checking here
            for(int int_5 = -int_3; int_5 <= int_3; ++int_5) {
                if (!this.method_23451(random_1, int_1, int_4, int_2, int_5, int_3)) {
                    blockPos$Mutable_1.set(int_4 + blockPos_1.getX(), int_2 + blockPos_1.getY(), int_5 + blockPos_1.getZ());
//                    if (!modifiableTestableWorld_1.testBlockState(blockPos$Mutable_1, i -> i == Blocks.OAK_LOG.getDefaultState() || i == Blocks.SPRUCE_LOG.getDefaultState() || i == Blocks.ACACIA_LOG.getDefaultState() || i == Blocks.BIRCH_LOG.getDefaultState() || i == Blocks.JUNGLE_LOG.getDefaultState() || i == Blocks.DARK_OAK_LOG.getDefaultState()))
//                    if (!(int_4 == 0 && int_5 == 0) && int_2 < int_1)
                        this.method_23450(modifiableTestableWorld_1, random_1, blockPos$Mutable_1, branchedTreeFeatureConfig_1, set_1);
                }
            }
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
