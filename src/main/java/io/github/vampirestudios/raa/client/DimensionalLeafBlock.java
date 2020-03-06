package io.github.vampirestudios.raa.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

import java.util.Random;

public class DimensionalLeafBlock extends Block {

    public static final IntProperty DISTANCE = IntProperty.of("distance", 1, 15);
    public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;

    public DimensionalLeafBlock() {
        super(FabricBlockSettings.of(Material.LEAVES).hardness(0.2F).ticksRandomly().sounds(BlockSoundGroup.GRASS).build());
        this.setDefaultState(this.getStateManager().getDefaultState().with(DISTANCE, 15).with(PERSISTENT, false));
    }

    public boolean hasRandomTicks(BlockState state) {
        return state.get(DISTANCE) == 15 && !state.get(PERSISTENT);
    }

    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (!state.get(PERSISTENT) && state.get(DISTANCE) == 15) {
            dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }

    }

    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        world.setBlockState(pos, updateDistanceFromLogs(state, world, pos), 3);
    }

    public int getOpacity(BlockState state, BlockView world, BlockPos pos) {
        return 1;
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction facing, BlockState neighborState, IWorld world, BlockPos pos, BlockPos neighborPos) {
        int i = getDistanceFromLog(neighborState) + 1;
        if (i != 1 || state.get(DISTANCE) != i) {
            world.getBlockTickScheduler().schedule(pos, this, 1);
        }

        return state;
    }

    private static BlockState updateDistanceFromLogs(BlockState state, IWorld world, BlockPos pos) {
        int distance = 15;
        BlockPos.Mutable mutable = new BlockPos.Mutable();
        Direction[] directions = Direction.values();

        for (Direction direction : directions) {
            mutable.method_25505(pos, direction);
            distance = Math.min(distance, getDistanceFromLog(world.getBlockState(mutable)) + 1);
            if (distance == 1) {
                break;
            }
        }

        return state.with(DISTANCE, distance);
    }

    private static int getDistanceFromLog(BlockState state) {
        if (BlockTags.LOGS.contains(state.getBlock())) {
            return 0;
        } else {
            return state.getBlock() instanceof DimensionalLeafBlock ? state.get(DISTANCE) : 15;
        }
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (world.hasRain(pos.up())) {
            if (random.nextInt(15) == 1) {
                BlockPos blockPos = pos.down();
                BlockState blockState = world.getBlockState(blockPos);
                if (!blockState.isOpaque() || !blockState.isSideSolidFullSquare(world, blockPos, Direction.UP)) {
                    double d = (float)pos.getX() + random.nextFloat();
                    double e = (double)pos.getY() - 0.05D;
                    double f = (float)pos.getZ() + random.nextFloat();
                    world.addParticle(ParticleTypes.DRIPPING_WATER, d, e, f, 0.0D, 0.0D, 0.0D);
                }
            }
        }
    }

    public boolean canSuffocate(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    public boolean allowsSpawning(BlockState state, BlockView world, BlockPos pos, EntityType<?> type) {
        return type == EntityType.OCELOT || type == EntityType.PARROT;
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(DISTANCE, PERSISTENT);
    }

    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return updateDistanceFromLogs(this.getDefaultState().with(PERSISTENT, true), ctx.getWorld(), ctx.getBlockPos());
    }

}