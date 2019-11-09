package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.registries.RAALootTables;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.Random;
import java.util.function.Function;

public class TowerFeature extends Feature<DefaultFeatureConfig> {
    public TowerFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
        super(function);
    }

    //Generates offensive/storage towers

    @Override
    public boolean generate(IWorld world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        if (world.getBlockState(pos.add(0, -1, 0)).isAir() || !world.getBlockState(pos.add(0, -1, 0)).isOpaque())
            return true;
        int tier = 0;
        if (Rands.chance(10)) tier = 1;
        int height = Rands.randIntRange(6, 15);
        for (int i = 0; i < height; i++) {
            for (int j = -2; j <= 2; j++) {
                for (int k = -2; k <= 2; k++) {
                    if (j == -2 || j == 2 || k == -2 || k == 2) {
                        placeBlockAt(world, pos.add(j, i, k), tier);
                    }
                }
            }
        }
        int heightTotal = height + Rands.randIntRange(3, 5);
        for (int i = height - 2; i < heightTotal; i++) {
            for (int j = -3; j <= 3; j++) {
                for (int k = -3; k <= 3; k++) {
                    if (j == -3 || j == 3 || k == -3 || k == 3) {
                        placeBlockAt(world, pos.add(j, i, k), tier);
                    }
                    if (j == -2 && k == -2 && i == height) {
                        world.setBlockState(pos.add(j, i, k), StructurePiece.method_14916(world, pos, Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH)), 2);
                        LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(j, i, k), (tier == 1) ? LootTables.SIMPLE_DUNGEON_CHEST : RAALootTables.TOWER_LOOT);
                    }
                    if (tier == 1) {
                        if (j == 2 && k == 2 && i == height) {
                            world.setBlockState(pos.add(j, i, k), StructurePiece.method_14916(world, pos, Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH)), 2);
                            LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(j, i, k), LootTables.SIMPLE_DUNGEON_CHEST);
                        }
                    }
                }
            }
        }
        if (Rands.chance(2) || tier == 1) {
            for (int j = -3; j <= 3; j++) {
                for (int k = -3; k <= 3; k++) {
                    placeBlockAt(world, pos.add(j, heightTotal-1, k), tier);
                }
            }
        }
        return true;
    }

    public static void placeBlockAt(IWorld world, BlockPos pos, int tier) {
        if (tier == 0) {
            int rand = Rands.randInt(5);
            switch (rand) {
                case 0:
                case 4:
                    world.setBlockState(pos, Blocks.STONE_BRICKS.getDefaultState(), 2);
                    break;
                case 1:
                    world.setBlockState(pos, Blocks.CRACKED_STONE_BRICKS.getDefaultState(), 2);
                    break;
                case 2:
                    world.setBlockState(pos, Blocks.MOSSY_STONE_BRICKS.getDefaultState(), 2);
                    break;
                case 3:
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                    break;
            }
        } else {
            int rand = Rands.randInt(8);
            switch (rand) {
                case 4:
                    world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                    break;
                default:
                    world.setBlockState(pos, Blocks.BRICKS.getDefaultState(), 2);
            }
        }
    }
}
