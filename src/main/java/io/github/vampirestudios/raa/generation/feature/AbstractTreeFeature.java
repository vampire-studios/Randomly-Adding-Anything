package io.github.vampirestudios.raa.generation.feature;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.datafixers.Dynamic;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Material;
import net.minecraft.state.property.Properties;
import net.minecraft.structure.Structure;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.Mutable;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import net.minecraft.util.shape.BitSetVoxelSet;
import net.minecraft.util.shape.VoxelSet;
import net.minecraft.world.*;
import net.minecraft.world.Heightmap.Type;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.foliage.FoliagePlacer;

import java.util.*;
import java.util.function.Function;

public class AbstractTreeFeature extends Feature<TreeFeatureConfig> {
    public AbstractTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> function) {
        super(function);
    }

    public static boolean canTreeReplace(TestableWorld world, BlockPos pos) {
        return !world.testBlockState(pos, (state) -> {
            Block block = state.getBlock();
            return state.isAir() || state.isIn(BlockTags.LEAVES) || isDirt(block) || block.isIn(BlockTags.LOGS) || block.isIn(BlockTags.SAPLINGS) || block == Blocks.VINE || block == Blocks.WATER;
        });
    }

    private static boolean isVine(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> state.getBlock() == Blocks.VINE);
    }

    private static boolean isWater(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> state.getBlock() == Blocks.WATER);
    }

    public static boolean isAirOrLeaves(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> state.isAir() || state.isIn(BlockTags.LEAVES));
    }

    public static boolean isDirtOrGrass(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> {
            Block block = state.getBlock();
            return isDirt(block) || block == Blocks.FARMLAND;
        });
    }

    public static boolean isReplaceablePlant(TestableWorld world, BlockPos pos) {
        return world.testBlockState(pos, (state) -> {
            Material material = state.getMaterial();
            return material == Material.REPLACEABLE_PLANT;
        });
    }

    public static void setBlockStateWithoutUpdatingNeighbors(ModifiableWorld world, BlockPos pos, BlockState state) {
        world.setBlockState(pos, state, 19);
    }

    public static boolean method_27371(ModifiableTestableWorld modifiableTestableWorld, BlockPos blockPos) {
        return isAirOrLeaves(modifiableTestableWorld, blockPos) || isReplaceablePlant(modifiableTestableWorld, blockPos) || isWater(modifiableTestableWorld, blockPos);
    }

    public boolean generate(ModifiableTestableWorld world, Random random, BlockPos pos, Set<BlockPos> logPositions, Set<BlockPos> leavesPositions, BlockBox box, TreeFeatureConfig config) {
        int i = config.trunkPlacer.getHeight(random);
        int j = config.foliagePlacer.getHeight(random, i, config);
        int k = i - j;
        int l = config.foliagePlacer.getRadius(random, k);
        BlockPos blockPos2;
        int r;
        if (!config.skipFluidCheck) {
            int m = world.getTopPosition(Type.OCEAN_FLOOR, pos).getY();
            int n = world.getTopPosition(Type.WORLD_SURFACE, pos).getY();
            if (n - m > config.baseHeight) {
                return false;
            }

            if (config.heightmap == Type.OCEAN_FLOOR) {
                r = m;
            } else if (config.heightmap == Type.WORLD_SURFACE) {
                r = n;
            } else {
                r = world.getTopPosition(config.heightmap, pos).getY();
            }

            blockPos2 = new BlockPos(pos.getX(), r, pos.getZ());
        } else {
            blockPos2 = pos;
        }

        if (blockPos2.getY() >= 1 && blockPos2.getY() + i + 1 <= 256) {
            if (!isDirtOrGrass(world, blockPos2.down())) {
                return false;
            } else {
                Mutable mutable = new Mutable();
                OptionalInt optionalInt = config.featureSize.getMinClippedHeight();
                r = i;

                for(int s = 0; s <= i + 1; ++s) {
                    int t = config.featureSize.method_27378(i, s);

                    for(int u = -t; u <= t; ++u) {
                        for(int v = -t; v <= t; ++v) {
                            mutable.set(blockPos2, u, s, v);
                            if (canTreeReplace(world, mutable) || !config.ignoreVines && isVine(world, mutable)) {
                                if (!optionalInt.isPresent() || s - 1 < optionalInt.getAsInt() + 1) {
                                    return false;
                                }

                                r = s - 2;
                                break;
                            }
                        }
                    }
                }

                List<FoliagePlacer.TreeNode> list = config.trunkPlacer.generate(world, random, r, blockPos2, logPositions, box, config);
                int finalR = r;
                list.forEach((arg) -> config.foliagePlacer.generate(world, random, config, finalR, arg, j, l, leavesPositions));
                return true;
            }
        } else {
            return false;
        }
    }

    protected void setBlockState(ModifiableWorld world, BlockPos pos, BlockState state) {
        setBlockStateWithoutUpdatingNeighbors(world, pos, state);
    }

    public final boolean generate(ServerWorldAccess iWorld, StructureAccessor structureAccessor, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, TreeFeatureConfig treeFeatureConfig) {
        Set<BlockPos> set = Sets.newHashSet();
        Set<BlockPos> set2 = Sets.newHashSet();
        Set<BlockPos> set3 = Sets.newHashSet();
        BlockBox blockBox = BlockBox.empty();
        boolean bl = this.generate(iWorld, random, blockPos, set, set2, blockBox, treeFeatureConfig);
        if (blockBox.minX <= blockBox.maxX && bl && !set.isEmpty()) {
            if (!treeFeatureConfig.decorators.isEmpty()) {
                List<BlockPos> list = Lists.newArrayList(set);
                List<BlockPos> list2 = Lists.newArrayList(set2);
                list.sort(Comparator.comparingInt(Vec3i::getY));
                list2.sort(Comparator.comparingInt(Vec3i::getY));
                treeFeatureConfig.decorators.forEach((decorator) -> decorator.generate(iWorld, random, list, list2, set3, blockBox));
            }

            VoxelSet voxelSet = this.placeLogsAndLeaves(iWorld, blockBox, set, set3);
            Structure.updateCorner(iWorld, 3, voxelSet, blockBox.minX, blockBox.minY, blockBox.minZ);
            return true;
        } else {
            return false;
        }
    }

    private VoxelSet placeLogsAndLeaves(WorldAccess world, BlockBox box, Set<BlockPos> logs, Set<BlockPos> leaves) {
        List<Set<BlockPos>> list = Lists.newArrayList();
        VoxelSet voxelSet = new BitSetVoxelSet(box.getBlockCountX(), box.getBlockCountY(), box.getBlockCountZ());

        for(int j = 0; j < 6; ++j) {
            list.add(Sets.newHashSet());
        }

        Mutable mutable = new Mutable();
        Iterator var9 = Lists.newArrayList(leaves).iterator();

        BlockPos blockPos2;
        while(var9.hasNext()) {
            blockPos2 = (BlockPos)var9.next();
            if (box.contains(blockPos2)) {
                voxelSet.set(blockPos2.getX() - box.minX, blockPos2.getY() - box.minY, blockPos2.getZ() - box.minZ, true, true);
            }
        }

        var9 = Lists.newArrayList(logs).iterator();

        while(var9.hasNext()) {
            blockPos2 = (BlockPos)var9.next();
            if (box.contains(blockPos2)) {
                voxelSet.set(blockPos2.getX() - box.minX, blockPos2.getY() - box.minY, blockPos2.getZ() - box.minZ, true, true);
            }

            Direction[] var11 = Direction.values();

            for (Direction direction : var11) {
                mutable.set(blockPos2, direction);
                if (!logs.contains(mutable)) {
                    BlockState blockState = world.getBlockState(mutable);
                    if (blockState.contains(Properties.DISTANCE_1_7)) {
                        list.get(0).add(mutable.toImmutable());
                        setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState.with(Properties.DISTANCE_1_7, 1));
                        if (box.contains(mutable)) {
                            voxelSet.set(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
                        }
                    }
                }
            }
        }

        for(int k = 1; k < 6; ++k) {
            Set<BlockPos> set = list.get(k - 1);
            Set<BlockPos> set2 = list.get(k);

            for (BlockPos blockPos3 : set) {
                if (box.contains(blockPos3)) {
                    voxelSet.set(blockPos3.getX() - box.minX, blockPos3.getY() - box.minY, blockPos3.getZ() - box.minZ, true, true);
                }

                Direction[] var27 = Direction.values();

                for (Direction direction2 : var27) {
                    mutable.set(blockPos3, direction2);
                    if (!set.contains(mutable) && !set2.contains(mutable)) {
                        BlockState blockState2 = world.getBlockState(mutable);
                        if (blockState2.contains(Properties.DISTANCE_1_7)) {
                            int l = blockState2.get(Properties.DISTANCE_1_7);
                            if (l > k + 1) {
                                BlockState blockState3 = blockState2.with(Properties.DISTANCE_1_7, k + 1);
                                setBlockStateWithoutUpdatingNeighbors(world, mutable, blockState3);
                                if (box.contains(mutable)) {
                                    voxelSet.set(mutable.getX() - box.minX, mutable.getY() - box.minY, mutable.getZ() - box.minZ, true, true);
                                }

                                set2.add(mutable.toImmutable());
                            }
                        }
                    }
                }
            }
        }

        return voxelSet;
    }
}
