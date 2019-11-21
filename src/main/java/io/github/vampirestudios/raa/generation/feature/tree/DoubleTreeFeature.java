package io.github.vampirestudios.raa.generation.feature.tree;

import com.google.common.collect.Sets;
import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.BranchedTreeFeature;
import net.minecraft.world.gen.feature.BranchedTreeFeatureConfig;

import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

public class DoubleTreeFeature extends BranchedTreeFeature<BranchedTreeFeatureConfig> {
    public DoubleTreeFeature(Function<Dynamic<?>, ? extends BranchedTreeFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator<? extends ChunkGeneratorConfig> generator, Random random, BlockPos pos, BranchedTreeFeatureConfig config) {
        if (world.getBlockState(pos.add(0, -1, 0)).isAir() || !world.getBlockState(pos.add(0, -1, 0)).isOpaque())
            return true;
        return this.generate(world, new Random(), pos, Sets.newTreeSet(), Sets.newTreeSet(), BlockBox.empty(), config);
    }

    @Override
    protected boolean generate(ModifiableTestableWorld modifiableTestableWorld_1, Random random_1, BlockPos blockPos_1, Set<BlockPos> set_1, Set<BlockPos> set_2, BlockBox blockBox_1, BranchedTreeFeatureConfig branchedTreeFeatureConfig_1) {
        int int_1 = branchedTreeFeatureConfig_1.baseHeight + random_1.nextInt(branchedTreeFeatureConfig_1.heightRandA + 1) + random_1.nextInt(branchedTreeFeatureConfig_1.heightRandB + 1);
        int int_2 = branchedTreeFeatureConfig_1.trunkHeight >= 0 ? branchedTreeFeatureConfig_1.trunkHeight + random_1.nextInt(branchedTreeFeatureConfig_1.trunkHeightRandom + 1) : int_1 - (branchedTreeFeatureConfig_1.field_21266 + random_1.nextInt(branchedTreeFeatureConfig_1.field_21267 + 1));
        int int_3 = branchedTreeFeatureConfig_1.foliagePlacer.method_23452(random_1, int_2, int_1, branchedTreeFeatureConfig_1);
        Optional<BlockPos> optional_1 = this.method_23378(modifiableTestableWorld_1, int_1, int_2, int_3, blockPos_1, branchedTreeFeatureConfig_1);
        if (!optional_1.isPresent()) {
            return false;
        } else {
            BlockPos blockPos_2 = optional_1.get();
            this.setToDirt(modifiableTestableWorld_1, blockPos_2.method_10074());

            //stump
            this.method_23379(modifiableTestableWorld_1, random_1, 2, blockPos_2, 0, set_1, blockBox_1, branchedTreeFeatureConfig_1);

            //side 1

            int loopAmt = Rands.randIntRange(1, 4);
            int offsetTotal = 2;
            for (int i = 0; i < loopAmt; i++) {
                int offset = Rands.randIntRange(3, 6);
                int offsetX = -1;
                int offsetZ = -1;

                if (i == loopAmt - 1) {
                    this.method_23379(modifiableTestableWorld_1, random_1, int_1, blockPos_2.add(offsetX * (i + 1), (offsetTotal), offsetZ * (i + 1)), branchedTreeFeatureConfig_1.trunkTopOffsetRandom + random_1.nextInt(branchedTreeFeatureConfig_1.field_21265 + 1), set_1, blockBox_1, branchedTreeFeatureConfig_1);
                    branchedTreeFeatureConfig_1.foliagePlacer.method_23448(modifiableTestableWorld_1, random_1, branchedTreeFeatureConfig_1, int_1, int_2, int_3, blockPos_2.add(offsetX * (i + 1), (offsetTotal), offsetZ * (i + 1)), set_2);
                } else{
                    this.method_23379(modifiableTestableWorld_1, random_1, offset, blockPos_2.add(offsetX * (i + 1), offsetTotal, offsetZ * (i + 1)), 0, set_1, blockBox_1, branchedTreeFeatureConfig_1);
                }
                offsetTotal += offset;
            }

            //side 2

            loopAmt = Rands.randIntRange(1, 4);
            offsetTotal = 2;
            for (int i = 0; i < loopAmt; i++) {
                int offset = Rands.randIntRange(3, 6);
                int offsetX = 1;
                int offsetZ = 1;

                if (i == loopAmt - 1) {
                    this.method_23379(modifiableTestableWorld_1, random_1, int_1, blockPos_2.add(offsetX * (i + 1), (offsetTotal), offsetZ * (i + 1)), branchedTreeFeatureConfig_1.trunkTopOffsetRandom + random_1.nextInt(branchedTreeFeatureConfig_1.field_21265 + 1), set_1, blockBox_1, branchedTreeFeatureConfig_1);
                    branchedTreeFeatureConfig_1.foliagePlacer.method_23448(modifiableTestableWorld_1, random_1, branchedTreeFeatureConfig_1, int_1, int_2, int_3, blockPos_2.add(offsetX * (i + 1), (offsetTotal), offsetZ * (i + 1)), set_2);
                } else{
                    this.method_23379(modifiableTestableWorld_1, random_1, offset, blockPos_2.add(offsetX * (i + 1), offsetTotal, offsetZ * (i + 1)), 0, set_1, blockBox_1, branchedTreeFeatureConfig_1);
                }
                offsetTotal += offset;
            }

//            loopAmt = Rands.randIntRange(1, 4);
//            offsetTotal = 0;
//            for (int i = 0; i < loopAmt; i++) {
//                int offset = Rands.randIntRange(3, 6);
//                offsetTotal+= offset;
//                int offsetX = 1;
//                int offsetZ = 1;
//
//                this.method_23379(modifiableTestableWorld_1, random_1, offset, blockPos_2.add(offsetX, offsetTotal, offsetZ), 0, set_1, blockBox_1, branchedTreeFeatureConfig_1);
//                if (i == loopAmt - 1) {
//                    this.method_23379(modifiableTestableWorld_1, random_1, int_1, blockPos_2.add(offsetX * i, (offsetTotal), offsetZ * i), branchedTreeFeatureConfig_1.trunkTopOffsetRandom + random_1.nextInt(branchedTreeFeatureConfig_1.field_21265 + 1), set_1, blockBox_1, branchedTreeFeatureConfig_1);
//                    branchedTreeFeatureConfig_1.foliagePlacer.method_23448(modifiableTestableWorld_1, random_1, branchedTreeFeatureConfig_1, int_1, int_2, int_3, blockPos_2.add(-offsetX * 2, (offset * 2) - 2, -offsetZ * 2), set_2);
//                }
//            }
            return true;
        }
    }
}