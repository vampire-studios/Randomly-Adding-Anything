package io.github.vampirestudios.raa.generation.feature;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class CraterFeature extends Feature<DefaultFeatureConfig> {
    public CraterFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        for (int i = -2; i <= 3; i++) {
            for (int j = -2; j <= 3; j++) {
                this.setBlockState(world, new BlockPos(pos.getX()+i, pos.getY()-1, pos.getZ()+j), Blocks.AIR.getDefaultState());
            }
        }

        for (int i = -1; i <= 2; i++) {
            for (int j = -1; j <= 2; j++) {
                this.setBlockState(world, new BlockPos(pos.getX()+i, pos.getY()-2, pos.getZ()+j), Blocks.AIR.getDefaultState());
            }
        }

        return true;
    }
}
