package io.github.vampirestudios.raa.generation.feature;

import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.generation.dimensions.CustomDimension;
import io.github.vampirestudios.raa.registries.Entities;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.entity.EntityType;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.DefaultFeatureConfig;
import net.minecraft.world.gen.feature.Feature;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AbovegroundStorageFeature extends Feature<DefaultFeatureConfig> {
    private static AreaDecorator RARE_BLOCKS = new RareBlockAreaDecorator();
    private static AreaDecorator LIBRARY = new LibraryAreaDecorator();
    private static AreaDecorator PILLARS = new PillarsAreaDecorator();
    private static AreaDecorator MOBS = new MonsterSpawnerAreaDecorator();
    private static AreaDecorator COBWEBS = new CobwebAreaDecorator();

    public AbovegroundStorageFeature(Function<Dynamic<?>, ? extends DefaultFeatureConfig> configDeserializer) {
        super(configDeserializer);
    }

    @Override
    public boolean generate(ServerWorldAccess world, StructureAccessor arg, ChunkGenerator generator, Random random, BlockPos pos, DefaultFeatureConfig config) {
        int width = Rands.randIntRange(6, 10);
        int length = Rands.randIntRange(6, 10);
        int height = Rands.randIntRange(4, 8);
        boolean hasLibrary = Rands.chance(3); //33% chance
        boolean hasPillars = Rands.chance(2); //50% chance
        boolean hasMobs = !Rands.chance(3); //66% chance
        boolean hasCobwebs = !Rands.chance(3); //66% chance

        //don't generate above the height limit
        if (height + pos.getY() > 255) return false;

        pos = pos.down();

        if (!world.getBlockState(pos).isOpaque()) return false;

        Set<BlockPos> airPositions = new HashSet<>();

        for (int x = -width; x <= width; x++) {
            for (int z = -length; z <= length; z++) {
                placeBlockAt(world, pos.add(x, 0, z));
                placeBlockAt(world, pos.add(x, height, z));

                if (Math.abs(x) == width || Math.abs(z) == length) {
                    for (int i = 1; i < height; i++) {
                        placeBlockAt(world, pos.add(x, i, z));
                    }
                } else {
                    for (int i = 1; i < height; i++) {
                        world.setBlockState(pos.add(x, i, z), Blocks.AIR.getDefaultState(), 2);
                        airPositions.add(pos.add(x, i, z));
                    }
                }
            }
        }

        if (hasLibrary) {
            airPositions.removeAll(LIBRARY.decorate(world, airPositions, pos, width, length, height));
        }

        if (hasPillars) {
            airPositions.removeAll(PILLARS.decorate(world, airPositions, pos, width, length, height));
        }

        if (hasMobs) {
            airPositions.removeAll(MOBS.decorate(world, airPositions, pos, width, length, height));
        }

        airPositions.removeAll(RARE_BLOCKS.decorate(world, airPositions, pos, width, length, height));

        if (hasCobwebs) {
            airPositions.removeAll(COBWEBS.decorate(world, airPositions, pos, width, length, height));
        }

        return false;
    }

    public static void placeBlockAt(WorldAccess world, BlockPos pos) {
        int rand = Rands.randInt(4);
        switch (rand) {
            case 0:
            case 1:
                world.setBlockState(pos, Blocks.STONE_BRICKS.getDefaultState(), 2);
                break;
            case 2:
                world.setBlockState(pos, Blocks.CRACKED_STONE_BRICKS.getDefaultState(), 2);
                break;
            case 3:
                world.setBlockState(pos, Blocks.MOSSY_STONE_BRICKS.getDefaultState(), 2);
                break;
        }
    }

    //TODO: split into more classes when more area decorators are added
    private static abstract class AreaDecorator {
        public abstract Set<BlockPos> decorate(WorldAccess world, Set<BlockPos> airPositions, BlockPos basePos, int width, int length, int height);
    }

    private static class RareBlockAreaDecorator extends AreaDecorator {

        @Override
        public Set<BlockPos> decorate(WorldAccess world, Set<BlockPos> airPositions, BlockPos basePos, int width, int length, int height) {
            Set<BlockPos> decoratedPositions = new HashSet<>();

            Dimension dimension = world.getDimension();
            List<Identifier> customMaterialBlockIds = new ArrayList<>();

            //you can never be too sure
            if (dimension instanceof CustomDimension) {
                String dimName = ((CustomDimension)(dimension)).getDimensionData().getId().getPath();
                customMaterialBlockIds = Registry.BLOCK.getIds().stream().filter(id -> id.getPath().startsWith(dimName + "_") && id.getPath().endsWith("_block")).collect(Collectors.toList());
            }

            for (BlockPos pos : airPositions) {
                if (world.getBlockState(pos.down()).isOpaque()) {
                    int rand = Rands.randInt(100);
                    switch (rand) {
                        case 5:
                            decoratedPositions.add(pos);
                            world.setBlockState(pos, Blocks.GOLD_BLOCK.getDefaultState(), 2);
                            break;
                        case 7:
                            decoratedPositions.add(pos);
                            world.setBlockState(pos, Blocks.DIAMOND_BLOCK.getDefaultState(), 2);
                            break;
                        case 9:
                            decoratedPositions.add(pos);
                            world.setBlockState(pos, Blocks.EMERALD_BLOCK.getDefaultState(), 2);
                            break;
                        case 10:
                            decoratedPositions.add(pos);
                            world.setBlockState(pos, Blocks.NETHERITE_BLOCK.getDefaultState(), 2);
                            break;
                    }

                    if (rand > 90) {
                        if (customMaterialBlockIds.size() > 0) {
                            world.setBlockState(pos, Registry.BLOCK.get(Rands.list(customMaterialBlockIds)).getDefaultState(), 2);
                        }
                    }
                }
            }

            return decoratedPositions;
        }
    }

    public static class LibraryAreaDecorator extends AreaDecorator {

        @Override
        public Set<BlockPos> decorate(WorldAccess world, Set<BlockPos> airPositions, BlockPos basePos, int width, int length, int height) {
            Set<BlockPos> decoratedPositions = new HashSet<>();

            //castle transformer
            for (BlockPos pos : airPositions) {
                if (world.getBlockState(pos.add(1, 0, 0)).isOpaque() ||
                        world.getBlockState(pos.add(-1, 0, 0)).isOpaque() ||
                        world.getBlockState(pos.add(0, 0, 1)).isOpaque() ||
                        world.getBlockState(pos.add(0, 0, -1)).isOpaque()) {
                    if (!Rands.chance(4)) {
                        decoratedPositions.add(pos);
                        world.setBlockState(pos, Blocks.BOOKSHELF.getDefaultState(), 2);
                    }
                }
            }

            return decoratedPositions;
        }
    }

    public static class PillarsAreaDecorator extends AreaDecorator {

        @Override
        public Set<BlockPos> decorate(WorldAccess world, Set<BlockPos> airPositions, BlockPos basePos, int width, int length, int height) {
            Set<BlockPos> decoratedPositions = new HashSet<>();

            for (int x = -width; x <= width; x++) {
                for (int z = -length; z <= length; z++) {
                    if (Rands.chance(8)) {
                        for (int i = 1; i < height; i++) {
                            BlockPos localPos = basePos.add(x, i, z);
                            if (airPositions.contains(localPos)) {
                                decoratedPositions.add(localPos);
                                placeBlockAt(world, localPos);
                            }
                        }
                    }
                }
            }

            return decoratedPositions;
        }
    }

    private static class MonsterSpawnerAreaDecorator extends AreaDecorator {

        @Override
        public Set<BlockPos> decorate(WorldAccess world, Set<BlockPos> airPositions, BlockPos basePos, int width, int length, int height) {
            Set<BlockPos> decoratedPositions = new HashSet<>();

            for (BlockPos pos : airPositions) {
                if (world.getBlockState(pos.down()).isOpaque()) {
                    int rand = Rands.randInt(100);
                    switch (rand) {
                        case 1:
                        case 2:
                        case 3:
                            world.setBlockState(pos, Blocks.SPAWNER.getDefaultState(), 2);
                            decoratedPositions.add(pos);
                            BlockEntity blockEntity = world.getBlockEntity(pos);
                            if (blockEntity instanceof MobSpawnerBlockEntity) {
                                ((MobSpawnerBlockEntity)blockEntity).getLogic().setEntityId(getMobID());
                            }
                            break;
                    }
                }
            }

            return decoratedPositions;
        }

        private static EntityType<?> getMobID() {
            return Entities.RANDOM_ZOMBIES.getRandom(Rands.getRandom());
        }
    }

    public static class CobwebAreaDecorator extends AreaDecorator {

        @Override
        public Set<BlockPos> decorate(WorldAccess world, Set<BlockPos> airPositions, BlockPos basePos, int width, int length, int height) {
            Set<BlockPos> decoratedPositions = new HashSet<>();

            for (BlockPos pos : airPositions) {
                if (Rands.chance(8)) {
                    decoratedPositions.add(pos);
                    world.setBlockState(pos, Blocks.COBWEB.getDefaultState(), 2);
                }
            }

            return decoratedPositions;
        }
    }
}
