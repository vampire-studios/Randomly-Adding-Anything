package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.registries.RAALootTables;
import io.github.vampirestudios.raa.utils.Rands;
import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.loot.LootTables;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.function.Function;

public class OutpostFeature extends Feature<DefaultFeatureConfig> {
    public OutpostFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
        super(function);
    }

    //Generates tiered outposts
    // T0 = stone brick
    // T1 = brick
    // T2 = obsidian
    // T-1 = cobblestone

    @Override
    public boolean generate(IWorld world, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        if (world.getBlockState(pos.add(0, -1, 0)).isAir() || !world.getBlockState(pos.add(0, -1, 0)).isOpaque()  || world.getBlockState(pos.add(0, -1, 0)).equals(Blocks.BEDROCK.getDefaultState()))
            return true;
        int tier = 0;
        if (Rands.chance(4)) tier = -1; //ugly hack to make cobblestone work
        if (Rands.chance(10)) tier = 1;
        if (tier == 1) if (Rands.chance(10)) tier = 2;
        boolean hasRoof = Rands.chance(2);

        //height modification
        int height = Rands.randIntRange(6, 15);
        if (tier == -1) height = Rands.randIntRange(6, 12);
        if (tier == 1) height = Rands.randIntRange(9, 18);
        if (tier == 2) height = Rands.randIntRange(12, 21);
        //floor
        for (int j = -2; j <= 2; j++) {
            for (int k = -2; k <= 2; k++) {
                placeBlockAt(world, pos.add(j, 0, k), tier);
            }
        }
        //"staircase" area
        for (int i = 0; i < height; i++) {
            for (int j = -2; j <= 2; j++) {
                for (int k = -2; k <= 2; k++) {
                    if (j == -2 || j == 2 || k == -2 || k == 2) {
                        placeBlockAt(world, pos.add(j, i, k), tier);
                    }
                    else {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                    }
                }
            }
        }
        //room area
        int heightTotal = height + Rands.randIntRange(3, 5);
        if (tier == -1)  {
            if (hasRoof) {
                heightTotal = height + Rands.randIntRange(2, 3);
            } else {
                heightTotal = height + Rands.randIntRange(3, 4);
            }
        }
        if (tier == 1) heightTotal = height + Rands.randIntRange(4, 6);
        if (tier == 2) heightTotal = height + Rands.randIntRange(5, 7);
        for (int i = height - 2; i < heightTotal; i++) {
            for (int j = -3; j <= 3; j++) {
                for (int k = -3; k <= 3; k++) {
                    if (j == -3 || j == 3 || k == -3 || k == 3) {
                        placeBlockAt(world, pos.add(j, i, k), tier);
                    }
                    else {
                        world.setBlockState(pos, Blocks.AIR.getDefaultState(), 2);
                    }
                    if (j == -2 && k == -2 && i == height) {
                        world.setBlockState(pos.add(j, i, k), StructurePiece.method_14916(world, pos, Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH)), 2);
                        LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(j, i, k), (tier >= 1) ? (tier >= 2) ? LootTables.END_CITY_TREASURE_CHEST : LootTables.SIMPLE_DUNGEON_CHEST : RAALootTables.OUTPOST_LOOT);
                    }
                    if (tier >= 1) {
                        if (j == 2 && k == 2 && i == height) {
                            world.setBlockState(pos.add(j, i, k), StructurePiece.method_14916(world, pos, Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH)), 2);
                            LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(j, i, k), (tier >= 2) ? LootTables.END_CITY_TREASURE_CHEST : LootTables.SIMPLE_DUNGEON_CHEST);
                        }
                    }
                    if (tier >= 2) {
                        if (j == -2 && k == 2 && i == height) {
                            world.setBlockState(pos.add(j, i, k), StructurePiece.method_14916(world, pos, Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH)), 2);
                            LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(j, i, k), LootTables.END_CITY_TREASURE_CHEST);
                        }
                        if (j == 2 && k == -2 && i == height) {
                            world.setBlockState(pos.add(j, i, k), StructurePiece.method_14916(world, pos, Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH)), 2);
                            LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(j, i, k), LootTables.END_CITY_TREASURE_CHEST);
                        }
                    }
                }
            }
        }
        //roof
        if (hasRoof || tier >= 1) {
            for (int j = -3; j <= 3; j++) {
                for (int k = -3; k <= 3; k++) {
                    placeBlockAt(world, pos.add(j, heightTotal-1, k), tier);
                }
            }
        }

        try {
            String path;
            World world2 = world.getWorld();
            if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) path = "saves/" + ((ServerWorld) world2).getSaveHandler().getWorldDir().getName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/outpost_spawns.txt";
            else path = world.getLevelProperties().getLevelName() + "/DIM_raa_" + world.getDimension().getType().getSuffix().substring(4) + "/data/outpost_spawns.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.append(pos.getX() + "," + pos.getY() + "," + pos.getZ() + "\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void placeBlockAt(IWorld world, BlockPos pos, int tier) {
        switch (tier) {
            case -1:
                int randneg1 = Rands.randInt(4);
                switch (randneg1) {
                    case 0:
                    case 1:
                        world.setBlockState(pos, Blocks.COBBLESTONE.getDefaultState(), 2);
                        break;
                    case 2:
                        world.setBlockState(pos, Blocks.MOSSY_COBBLESTONE.getDefaultState(), 2);
                        break;
                    case 3:
                        world.setBlockState(pos, (Rands.chance(3)) ? Blocks.COBWEB.getDefaultState() : Blocks.AIR.getDefaultState(), 2);
                        break;
                }
                break;
            case 0:
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
                        world.setBlockState(pos, (Rands.chance(4)) ? Blocks.COBWEB.getDefaultState() : Blocks.AIR.getDefaultState(), 2);
                        break;
                }
            break;
            case 1:
                int rand1 = Rands.randInt(8);
                if (rand1 == 0) {
                    world.setBlockState(pos, (Rands.chance(4)) ? Blocks.COBWEB.getDefaultState() : Blocks.AIR.getDefaultState(), 2);
                } else {
                    world.setBlockState(pos, Blocks.BRICKS.getDefaultState(), 2);
                }
                break;
            case 2:
                int rand2 = Rands.randInt(20);
                if (rand2 == 0) {
                    world.setBlockState(pos, (Rands.chance(4)) ? Blocks.COBWEB.getDefaultState() : Blocks.AIR.getDefaultState(), 2);
                } else {
                    world.setBlockState(pos, Blocks.OBSIDIAN.getDefaultState(), 2);
                }
                break;
        }
    }
}
