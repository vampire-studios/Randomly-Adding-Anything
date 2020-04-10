package io.github.vampirestudios.raa.generation.feature;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.dimensions.CustomDimension;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.registries.RAALootTables;
import io.github.vampirestudios.raa.utils.Rands;
import io.github.vampirestudios.raa.utils.Utils;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.StructurePiece;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CampfireFeature extends Feature<DefaultFeatureConfig> {
    public CampfireFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> function) {
        super(function);
    }

    @Override
    public boolean generate(IWorld world, StructureAccessor StructureAccessor, ChunkGenerator chunkGenerator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        if (world.getBlockState(pos.add(0, -1, 0)).isAir() || !world.getBlockState(pos.add(0, -1, 0)).isOpaque() || world.getBlockState(pos.add(0, -1, 0)).equals(Blocks.BEDROCK.getDefaultState()))
            return true;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (j == -1 || j == 1 || i == -1 || i == 1)
                    world.setBlockState(pos.add(i, -1, j), Blocks.GRASS_PATH.getDefaultState(), 2);
            }
        }
        List<Block> stairs = new ArrayList<>(
                ImmutableList.of(
                        Blocks.ACACIA_STAIRS,
                        Blocks.BIRCH_STAIRS,
                        Blocks.DARK_OAK_STAIRS,
                        Blocks.JUNGLE_STAIRS,
                        Blocks.OAK_STAIRS,
                        Blocks.SPRUCE_STAIRS,
                        Blocks.STONE_STAIRS,
                        Blocks.BRICK_STAIRS,
                        Blocks.STONE_BRICK_STAIRS
                )
        );
        Block stair = Rands.list(stairs);

        if (Rands.chance(2))
            world.setBlockState(pos.add(0, 0, 2), stair.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.SOUTH), 2);
        if (Rands.chance(2))
            world.setBlockState(pos.add(0, 0, -2), stair.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.NORTH), 2);
        if (Rands.chance(4)) {
            world.setBlockState(pos, Blocks.CAMPFIRE.getDefaultState(), 2);
        } else {
            world.setBlockState(pos, Blocks.CAMPFIRE.getDefaultState().with(Properties.LIT, false), 2);
        }

        //half of all campfires have chests
        if (Rands.chance(2)) {
            world.setBlockState(pos.add(-2, 0, 0), StructurePiece.method_14916(world, pos, Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST)), 2);
            LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(-2, 0, 0), RAALootTables.CAMPFIRE_LOOT);
        } else {
            if (Rands.chance(2))
                world.setBlockState(pos.add(-2, 0, 0), stair.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST), 2);
        }

        Block woolBlock = Rands.values(new Block[]{Blocks.WHITE_WOOL, Blocks.ORANGE_WOOL, Blocks.MAGENTA_WOOL, Blocks.LIGHT_BLUE_WOOL,
                Blocks.YELLOW_WOOL, Blocks.LIME_WOOL, Blocks.PINK_WOOL, Blocks.GRAY_WOOL,
                Blocks.LIGHT_GRAY_WOOL, Blocks.CYAN_WOOL, Blocks.PURPLE_WOOL, Blocks.BLUE_WOOL,
                Blocks.BROWN_WOOL, Blocks.GREEN_WOOL, Blocks.RED_WOOL, Blocks.BLACK_WOOL});
        Block carpetBlock = Rands.values(new Block[]{Blocks.WHITE_CARPET, Blocks.ORANGE_CARPET, Blocks.MAGENTA_CARPET, Blocks.LIGHT_BLUE_CARPET,
                Blocks.YELLOW_CARPET, Blocks.LIME_CARPET, Blocks.PINK_CARPET, Blocks.GRAY_CARPET,
                Blocks.LIGHT_GRAY_CARPET, Blocks.CYAN_CARPET, Blocks.PURPLE_CARPET, Blocks.BLUE_CARPET,
                Blocks.BROWN_CARPET, Blocks.GREEN_CARPET, Blocks.RED_CARPET, Blocks.BLACK_CARPET});

        //1/2 of all campfires have tents
        if (Rands.chance(2)) {
            List<Block> fences = new ArrayList<>(
                    ImmutableList.of(
                            Blocks.ACACIA_FENCE,
                            Blocks.BIRCH_FENCE,
                            Blocks.DARK_OAK_FENCE,
                            Blocks.JUNGLE_FENCE,
                            Blocks.OAK_FENCE,
                            Blocks.SPRUCE_FENCE,
                            Blocks.STONE_BRICK_WALL,
                            Blocks.BRICK_WALL
                    )
            );
            Block fence = Rands.list(fences);

            for (int i = -1; i <= 1; i++) {
                for (int j = 0; j < 3; j++) {
                    world.setBlockState(pos.add(5 - j, j, i), woolBlock.getDefaultState(), 2);
                }
            }
            world.setBlockState(pos.add(2, 2, 1), woolBlock.getDefaultState(), 2);
            world.setBlockState(pos.add(2, 2, 0), woolBlock.getDefaultState(), 2);
            world.setBlockState(pos.add(2, 2, -1), woolBlock.getDefaultState(), 2);

            world.setBlockState(pos.add(2, 0, -1), fence.getDefaultState(), 2);
            world.setBlockState(pos.add(2, 1, -1), fence.getDefaultState(), 2);
            world.setBlockState(pos.add(2, 0, 1), fence.getDefaultState(), 2);
            world.setBlockState(pos.add(2, 1, 1), fence.getDefaultState(), 2);

            world.setBlockState(pos.add(3, 0, -1), carpetBlock.getDefaultState(), 2);
            world.setBlockState(pos.add(4, 0, -1), carpetBlock.getDefaultState(), 2);
            world.setBlockState(pos.add(3, 0, 0), carpetBlock.getDefaultState(), 2);
            world.setBlockState(pos.add(4, 0, 0), carpetBlock.getDefaultState(), 2);
            world.setBlockState(pos.add(3, 0, 1), carpetBlock.getDefaultState(), 2);
            world.setBlockState(pos.add(4, 0, 1), carpetBlock.getDefaultState(), 2);
            world.setBlockState(pos.add(2, 0, 0), carpetBlock.getDefaultState(), 2);

            // 1/2 chance for a lantern
            if (Rands.chance(2))
                world.setBlockState(pos.add(3, 1, -1), Blocks.LANTERN.getDefaultState().with(Properties.HANGING, true), 2);

            world.setBlockState(pos.add(3, 0, 2), StructurePiece.method_14916(world, pos, Blocks.CHEST.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.WEST)), 2);


//            LootableContainerBlockEntity.setLootTable(world, Rands.getRandom(), pos.add(3, 0, 2), RAALootTables.CAMPFIRE_TENT_LOOT);

            //try to add custom dimensional loot to the chest
            if (world.getDimension() instanceof CustomDimension) {
                fillChestWithLoot(((CustomDimension)world.getDimension()).getDimensionData(), (ChestBlockEntity) world.getBlockEntity(pos.add(3, 0, 2)));
            }
        } else {
            if (Rands.chance(2))
                world.setBlockState(pos.add(2, 0, 0), stair.getDefaultState().with(Properties.HORIZONTAL_FACING, Direction.EAST), 2);
        }

        Utils.createSpawnsFile("campfire", world, pos);
        return true;
    }

    private static void fillChestWithLoot(DimensionData data, ChestBlockEntity entity) {

        //TODO: remove the > 1 when we can be sure that dimension and material names are totally unique
        String name = data.getName().toLowerCase();
        List<Identifier> swords = Registry.ITEM.getIds().stream().filter(id -> id.getPath().startsWith(name + "_") && id.getPath().endsWith("_sword") && StringUtils.countMatches(id.getPath(), "_") > 1).collect(Collectors.toList());
        if (swords.isEmpty()) {
            swords.add(new Identifier("minecraft", "stone_sword"));
        }

        List<Identifier> pickaxes = Registry.ITEM.getIds().stream().filter(id -> id.getPath().startsWith(name + "_") && id.getPath().endsWith("_pickaxe") && StringUtils.countMatches(id.getPath(), "_") > 1).collect(Collectors.toList());
        if (pickaxes.isEmpty()) {
            pickaxes.add(new Identifier("minecraft", "stone_pickaxe"));
        }

        List<Identifier> axes = Registry.ITEM.getIds().stream().filter(id -> id.getPath().startsWith(name + "_") && id.getPath().endsWith("_axe") && StringUtils.countMatches(id.getPath(), "_") > 1).collect(Collectors.toList());
        if (pickaxes.isEmpty()) {
            pickaxes.add(new Identifier("minecraft", "stone_axe"));
        }

        for (int i = 0; i < entity.size(); i++) {

            ItemStack stack = ItemStack.EMPTY;

            switch (Rands.randInt(30)) {
                case 0:
                    stack = new ItemStack(Registry.ITEM.get(Rands.list(swords)));
                    break;
                case 1:
                    stack = new ItemStack(Registry.ITEM.get(Rands.list(pickaxes)));
                    break;
                case 2:
                case 3:
                case 4:
                    stack = new ItemStack(Items.TORCH, Rands.randIntRange(6, 8));
                    break;
                case 5:
                    stack = new ItemStack(Items.FLINT_AND_STEEL);
                    break;
                case 6:
                case 7:
                    stack = new ItemStack(Items.STRING, Rands.randIntRange(3, 6));
                    break;
            }

            entity.setStack(i, stack);
        }
    }
}
