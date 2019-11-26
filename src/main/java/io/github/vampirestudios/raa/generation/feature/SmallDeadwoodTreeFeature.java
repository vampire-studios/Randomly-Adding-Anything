package io.github.vampirestudios.raa.generation.feature;

import com.google.common.collect.Sets;
import com.mojang.datafixers.Dynamic;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.IWorld;
import net.minecraft.world.ModifiableTestableWorld;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import net.minecraft.world.gen.chunk.ChunkGeneratorConfig;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.TreeFeatureConfig;

import java.util.Random;
import java.util.Set;
import java.util.function.Function;

//Code kindly taken from The Hallow, thanks to everyone who is working on it!
public class SmallDeadwoodTreeFeature extends AbstractTreeFeature<TreeFeatureConfig> {
    private static BlockState LOG;
    private static BlockState LEAVES;

    public SmallDeadwoodTreeFeature(Function<Dynamic<?>, ? extends TreeFeatureConfig> function) {
        super(function);
        int leafType = Rands.randInt(4);
        switch (leafType) {
            case 1:
                LOG = Blocks.BIRCH_LOG.getDefaultState();
                LEAVES = Blocks.BIRCH_LEAVES.getDefaultState();
                break;
            case 2:
                LOG = Blocks.SPRUCE_LOG.getDefaultState();
                LEAVES = Blocks.SPRUCE_LEAVES.getDefaultState();
                break;
            case 3:
                LOG = Blocks.JUNGLE_LOG.getDefaultState();
                LEAVES = Blocks.JUNGLE_LEAVES.getDefaultState();
                break;
            case 0:
            default:
                LOG = Blocks.OAK_LOG.getDefaultState();
                LEAVES = Blocks.OAK_LEAVES.getDefaultState();
                break;
        }
    }

    @Override
    public boolean generate(ModifiableTestableWorld modifiableTestableWorld, Random random, BlockPos blockPos, Set<BlockPos> set, Set<BlockPos> set1, BlockBox blockBox, TreeFeatureConfig treeFeatureConfig) {
        int height = Rands.randInt(4) + 5;
        blockPos = modifiableTestableWorld.getTopPosition(Heightmap.Type.OCEAN_FLOOR, blockPos);
        boolean bool = true;
        if (blockPos.getY() >= 1 && blockPos.getY() + height + 1 <= 256) {
            int genY;
            int genX;
            int genZ;
            for (genY = blockPos.getY(); genY <= blockPos.getY() + 1 + height; ++genY) {
                int h = 1;
                if (genY == blockPos.getY()) {
                    h = 0;
                }

                if (genY >= blockPos.getY() + 1 + height - 2) {
                    h = 3;
                }

                BlockPos.Mutable mutablePos = new BlockPos.Mutable();

                for (genX = blockPos.getX() - h; genX <= blockPos.getX() + h && bool; ++genX) {
                    for (genZ = blockPos.getZ() - h; genZ <= blockPos.getZ() + h && bool; ++genZ) {
                        if (genY >= 0 && genY < 256) {
                            mutablePos.set(genX, genY, genZ);
                            if (!isAirOrLeaves(modifiableTestableWorld, mutablePos)) {
                                if (isWater(modifiableTestableWorld, mutablePos)) {
                                    if (genY > blockPos.getY()) {
                                        bool = false;
                                    }
                                } else {
                                    bool = false;
                                }
                            }
                        } else {
                            bool = false;
                        }
                    }
                }
            }

            if (!bool) {
                return false;
            } else if (isNaturalDirtOrGrass(modifiableTestableWorld, blockPos.down(1)) && blockPos.getY() < 256 - height - 1) {
                this.setToDirt(modifiableTestableWorld, blockPos.down(1));

                int curZ;
                BlockPos genPos;
                int localY;
                int leafSize;
                for (genY = blockPos.getY() - 3 + height; genY <= blockPos.getY() + height; ++genY) {
                    localY = genY - (blockPos.getY() + height);
                    leafSize = 2 - localY / 2;

                    for (genX = blockPos.getX() - leafSize; genX <= blockPos.getX() + leafSize; ++genX) {
                        genZ = genX - blockPos.getX();

                        for (curZ = blockPos.getZ() - leafSize; curZ <= blockPos.getZ() + leafSize; ++curZ) {
                            int offsetZ = curZ - blockPos.getZ();
                            if (Math.abs(genZ) != leafSize || Math.abs(offsetZ) != leafSize || Rands.randInt(2) != 0 && localY != 0) {
                                genPos = new BlockPos(genX, genY, curZ);
                                if (isAirOrLeaves(modifiableTestableWorld, genPos) || isReplaceablePlant(modifiableTestableWorld, genPos)) {
                                    this.setBlockState(modifiableTestableWorld, genPos, LEAVES, blockBox);
                                }
                            }
                        }
                    }
                }

                for (genY = 0; genY < height; ++genY) {
                    BlockPos upPos = blockPos.up(genY);
                    if (isAirOrLeaves(modifiableTestableWorld, upPos) || isWater(modifiableTestableWorld, upPos)) {
                        this.setBlockState(modifiableTestableWorld, upPos, LOG, blockBox);
                    }
                }

                for (genY = blockPos.getY() - 3 + height; genY <= blockPos.getY() + height; ++genY) {
                    localY = genY - (blockPos.getY() + height);
                    leafSize = 2 - localY / 2;
                    BlockPos.Mutable mutablePos2 = new BlockPos.Mutable();

                    for (genZ = blockPos.getX() - leafSize; genZ <= blockPos.getX() + leafSize; ++genZ) {
                        for (curZ = blockPos.getZ() - leafSize; curZ <= blockPos.getZ() + leafSize; ++curZ) {
                            mutablePos2.set(genZ, genY, curZ);
                            if (isLeaves(modifiableTestableWorld, mutablePos2)) {
                                BlockPos vinePosWest = mutablePos2.west();
                                BlockPos vinePosEast = mutablePos2.east();
                                BlockPos vinePosNorth = mutablePos2.north();
                                BlockPos vinePosSouth = mutablePos2.south();
                                if (Rands.randInt(4) == 0 && isAir(modifiableTestableWorld, vinePosWest)) {
                                    this.makeVines(modifiableTestableWorld, vinePosWest, VineBlock.EAST);
                                }

                                if (Rands.randInt(4) == 0 && isAir(modifiableTestableWorld, vinePosEast)) {
                                    this.makeVines(modifiableTestableWorld, vinePosEast, VineBlock.WEST);
                                }

                                if (Rands.randInt(4) == 0 && isAir(modifiableTestableWorld, vinePosNorth)) {
                                    this.makeVines(modifiableTestableWorld, vinePosNorth, VineBlock.SOUTH);
                                }

                                if (Rands.randInt(4) == 0 && isAir(modifiableTestableWorld, vinePosSouth)) {
                                    this.makeVines(modifiableTestableWorld, vinePosSouth, VineBlock.NORTH);
                                }
                            }
                        }
                    }
                }
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    private void makeVines(ModifiableTestableWorld modifiableTestableWorld, BlockPos blockPos, BooleanProperty boolProp) {
        BlockState state = Blocks.VINE.getDefaultState().with(boolProp, true);
        this.setBlockState(modifiableTestableWorld, blockPos, state);
        int yOffset = 4;

        for (blockPos = blockPos.down(1); isAir(modifiableTestableWorld, blockPos) && yOffset > 0; --yOffset) {
            this.setBlockState(modifiableTestableWorld, blockPos, state);
            blockPos = blockPos.down(1);
        }
    }

}