package io.github.vampirestudios.raa.generation.feature;
import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.feature.config.CorruptedFeatureConfig;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

public class CraterFeature extends Feature<CorruptedFeatureConfig> {
    public static final ArrayList<BlockState> ALLOWED_STATES = new ArrayList<BlockState>();
    public CraterFeature(Function<Dynamic<?>, ? extends CorruptedFeatureConfig> function) {
        super(function);
        ALLOWED_STATES.add(Blocks.GRASS_BLOCK.getDefaultState());
        ALLOWED_STATES.add(Blocks.STONE.getDefaultState());
        ALLOWED_STATES.add(Blocks.GRAVEL.getDefaultState());
        ALLOWED_STATES.add(Blocks.SAND.getDefaultState());
//        ALLOWED_STATES.add(Blocks.GRASS_BLOCK.getDefaultState());
    }

    @Override
    public boolean generate(IWorld world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, CorruptedFeatureConfig config) {
        if (world.getBlockState(pos.add(0, -1, 0)).isAir() || !world.getBlockState(pos.add(0, -1, 0)).isOpaque() || world.getBlockState(pos.add(0, -1, 0)).equals(Blocks.BEDROCK.getDefaultState()))
            return true;
        if (canSpawn(world, pos.add(0, -1, 0))) {
            int amtMax = Rands.randIntRange(1, 3);
            int scale = Rands.randIntRange(1, 3);
            for (int amt = 0; amt < amtMax; amt++) {
                for (int i = -(amt + scale); i <= (amt + scale); i++) {
                    for (int j = -(amt + scale); j <= (amt + scale); j++) {
                        if (i == -(amt + scale) || i == (amt + scale) || j == -(amt + scale) || j == (amt + scale)) {
                            if (!Rands.chance(3)) {
                                this.setBlockState(world, new BlockPos(pos.getX() + i, pos.getY() - (amtMax - amt), pos.getZ() + j), Blocks.AIR.getDefaultState());
                            }
                            if (config.corrupted && Rands.chance(3)) {
                                this.setBlockState(world, new BlockPos(pos.getX() + i, pos.getY() - (amtMax - amt), pos.getZ() + j), Blocks.NETHERRACK.getDefaultState());
                                if (Rands.chance(2)) {
                                    this.setBlockState(world, new BlockPos(pos.getX() + i, pos.getY() - (amtMax - amt) + 1, pos.getZ() + j), Blocks.FIRE.getDefaultState());
                                }
                            }
                        } else {
                            this.setBlockState(world, new BlockPos(pos.getX() + i, pos.getY() - (amtMax - amt), pos.getZ() + j), Blocks.AIR.getDefaultState());
                        }
                    }
                }
            }
        }

        return true;
    }

    private static boolean canSpawn(IWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        if (state == Blocks.GRASS_BLOCK.getDefaultState() || state == Blocks.PODZOL.getDefaultState() || state == Blocks.COARSE_DIRT.getDefaultState()) return true;
        if (state == Blocks.GRAVEL.getDefaultState() || state == Blocks.SAND.getDefaultState() || state == Blocks.STONE.getDefaultState()) return true;
        return false;
    }
}
