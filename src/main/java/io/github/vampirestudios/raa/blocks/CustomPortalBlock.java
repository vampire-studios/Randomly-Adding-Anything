package io.github.vampirestudios.raa.blocks;

import com.google.common.cache.LoadingCache;
import io.github.vampirestudios.raa.generation.dimensions.data.DimensionData;
import io.github.vampirestudios.raa.utils.Utils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.*;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.EnumProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

import java.util.Objects;
import java.util.Random;

public class CustomPortalBlock extends Block {

    private final DimensionData dimensionData;
    private final DimensionType dimensionType;
    public static final EnumProperty<Direction.Axis> AXIS;
    protected static final VoxelShape X_SHAPE;
    protected static final VoxelShape Z_SHAPE;

    public CustomPortalBlock(DimensionData dimensionData, DimensionType dimensionType) {
        super(AbstractBlock.Settings.copy(Blocks.NETHER_PORTAL));
        this.dimensionData = dimensionData;
        this.dimensionType = dimensionType;
        this.setDefaultState(this.stateManager.getDefaultState().with(AXIS, Direction.Axis.X));
    }

    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch(Objects.requireNonNull(state).get(AXIS)) {
            case Z:
                return Z_SHAPE;
            case X:
            default:
                return X_SHAPE;
        }
    }

    /*public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.dimension.hasVisibleSky() && world.getGameRules().getBoolean(GameRules.DO_MOB_SPAWNING) && random.nextInt(2000) < world.getDifficulty().getId()) {
            while(world.getBlockState(pos).getBlock() == this) {
                pos = pos.down();
            }

            if (world.getBlockState(pos).allowsSpawning(world, pos, EntityType.ZOMBIFIED_PIGLIN)) {
                Entity entity = EntityType.ZOMBIFIED_PIGLIN.spawn(world, null, null, null, pos.up(), SpawnType.STRUCTURE, false, false);
                if (entity != null) {
                    entity.netherPortalCooldown = entity.getDefaultNetherPortalCooldown();
                }
            }
        }

    }*/

    public static boolean createPortalAt(DimensionData dimensionData, IWorld iWorld, BlockPos blockPos) {
        CustomPortalBlock.AreaHelper areaHelper = createAreaHelper(dimensionData, iWorld, blockPos);
        if (areaHelper != null) {
            areaHelper.createPortal();
            return true;
        } else {
            return false;
        }
    }

    public static CustomPortalBlock.AreaHelper createAreaHelper(DimensionData dimensionData, IWorld iWorld, BlockPos blockPos) {
        CustomPortalBlock.AreaHelper areaHelper = new CustomPortalBlock.AreaHelper(dimensionData, iWorld, blockPos, Direction.Axis.X);
        if (areaHelper.isValid() && areaHelper.foundPortalBlocks == 0) {
            return areaHelper;
        } else {
            CustomPortalBlock.AreaHelper areaHelper2 = new CustomPortalBlock.AreaHelper(dimensionData, iWorld, blockPos, Direction.Axis.Z);
            return areaHelper2.isValid() && areaHelper2.foundPortalBlocks == 0 ? areaHelper2 : null;
        }
    }

    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState newState, IWorld world, BlockPos pos, BlockPos posFrom) {
        Direction.Axis axis = direction.getAxis();
        Direction.Axis axis2 = state.get(AXIS);
        boolean bl = axis2 != axis && axis.isHorizontal();
        return !bl && newState.getBlock() != this && !(new NetherPortalBlock.AreaHelper(world, pos, axis2)).wasAlreadyValid() ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, newState, world, pos, posFrom);
    }

    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!entity.hasVehicle() && !entity.hasPassengers() && entity.canUsePortals()) {
            if(entity.dimension == Registry.DIMENSION_TYPE.get(dimensionData.getId())) entity.changeDimension(DimensionType.OVERWORLD);
            else entity.changeDimension(Registry.DIMENSION_TYPE.get(dimensionData.getId()));
        }
    }

    @Environment(EnvType.CLIENT)
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (random.nextInt(100) == 0) {
            world.playSound((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 0.5F, random.nextFloat() * 0.4F + 0.8F, false);
        }

        for(int i = 0; i < 4; ++i) {
            double d = (double)pos.getX() + (double)random.nextFloat();
            double e = (double)pos.getY() + (double)random.nextFloat();
            double f = (double)pos.getZ() + (double)random.nextFloat();
            double g = ((double)random.nextFloat() - 0.5D) * 0.5D;
            double h = ((double)random.nextFloat() - 0.5D) * 0.5D;
            double j = ((double)random.nextFloat() - 0.5D) * 0.5D;
            int k = random.nextInt(2) * 2 - 1;
            if (world.getBlockState(pos.west()).getBlock() != this && world.getBlockState(pos.east()).getBlock() != this) {
                d = (double)pos.getX() + 0.5D + 0.25D * (double)k;
                g = random.nextFloat() * 2.0F * (float)k;
            } else {
                f = (double)pos.getZ() + 0.5D + 0.25D * (double)k;
                j = random.nextFloat() * 2.0F * (float)k;
            }

            world.addParticle(ParticleTypes.PORTAL, d, e, f, g, h, j);
        }

    }

    @Environment(EnvType.CLIENT)
    public ItemStack getPickStack(BlockView world, BlockPos pos, BlockState state) {
        return ItemStack.EMPTY;
    }

    public BlockState rotate(BlockState state, BlockRotation rotation) {
        switch(rotation) {
            case COUNTERCLOCKWISE_90:
            case CLOCKWISE_90:
                switch(state.get(AXIS)) {
                    case Z:
                        return state.with(AXIS, Direction.Axis.X);
                    case X:
                        return state.with(AXIS, Direction.Axis.Z);
                    default:
                        return state;
                }
            default:
                return state;
        }
    }

    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(AXIS);
    }

    public static BlockPattern.Result findPortal(DimensionData dimensionData, IWorld iWorld, BlockPos world) {
        Direction.Axis axis = Direction.Axis.Z;
        CustomPortalBlock.AreaHelper areaHelper = new CustomPortalBlock.AreaHelper(dimensionData, iWorld, world, Direction.Axis.X);
        LoadingCache<BlockPos, CachedBlockPosition> loadingCache = BlockPattern.makeCache(iWorld, true);
        if (!areaHelper.isValid()) {
            axis = Direction.Axis.X;
            areaHelper = new CustomPortalBlock.AreaHelper(dimensionData, iWorld, world, Direction.Axis.Z);
        }

        if (!areaHelper.isValid()) {
            return new BlockPattern.Result(world, Direction.NORTH, Direction.UP, loadingCache, 1, 1, 1);
        } else {
            int[] is = new int[Direction.AxisDirection.values().length];
            Direction direction = areaHelper.negativeDir.rotateYCounterclockwise();
            BlockPos blockPos = areaHelper.lowerCorner.up(areaHelper.getHeight() - 1);
            Direction.AxisDirection[] var8 = Direction.AxisDirection.values();
            int var9 = var8.length;

            int var10;
            for(var10 = 0; var10 < var9; ++var10) {
                Direction.AxisDirection axisDirection = var8[var10];
                BlockPattern.Result result = new BlockPattern.Result(direction.getDirection() == axisDirection ? blockPos : blockPos.offset(areaHelper.negativeDir, areaHelper.getWidth() - 1), Direction.get(axisDirection, axis), Direction.UP, loadingCache, areaHelper.getWidth(), areaHelper.getHeight(), 1);

                for(int i = 0; i < areaHelper.getWidth(); ++i) {
                    for(int j = 0; j < areaHelper.getHeight(); ++j) {
                        CachedBlockPosition cachedBlockPosition = result.translate(i, j, 1);
                        if (!cachedBlockPosition.getBlockState().isAir()) {
                            ++is[axisDirection.ordinal()];
                        }
                    }
                }
            }

            Direction.AxisDirection axisDirection2 = Direction.AxisDirection.POSITIVE;
            Direction.AxisDirection[] var17 = Direction.AxisDirection.values();
            var10 = var17.length;

            for(int var18 = 0; var18 < var10; ++var18) {
                Direction.AxisDirection axisDirection3 = var17[var18];
                if (is[axisDirection3.ordinal()] < is[axisDirection2.ordinal()]) {
                    axisDirection2 = axisDirection3;
                }
            }

            return new BlockPattern.Result(direction.getDirection() == axisDirection2 ? blockPos : blockPos.offset(areaHelper.negativeDir, areaHelper.getWidth() - 1), Direction.get(axisDirection2, axis), Direction.UP, loadingCache, areaHelper.getWidth(), areaHelper.getHeight(), 1);
        }
    }

    static {
        AXIS = Properties.HORIZONTAL_AXIS;
        X_SHAPE = Block.createCuboidShape(0.0D, 0.0D, 6.0D, 16.0D, 16.0D, 10.0D);
        Z_SHAPE = Block.createCuboidShape(6.0D, 0.0D, 0.0D, 10.0D, 16.0D, 16.0D);
    }

    public static class AreaHelper {
        private final IWorld world;
        private final Direction.Axis axis;
        private final Direction negativeDir;
        private final Direction positiveDir;
        private int foundPortalBlocks;
        private BlockPos lowerCorner;
        private int height;
        private int width;
        private final DimensionData dimensionData;

        public AreaHelper(DimensionData dimensionData, IWorld world, BlockPos pos, Direction.Axis axis) {
            this.world = world;
            this.axis = axis;
            this.dimensionData = dimensionData;
            if (axis == Direction.Axis.X) {
                this.positiveDir = Direction.EAST;
                this.negativeDir = Direction.WEST;
            } else {
                this.positiveDir = Direction.NORTH;
                this.negativeDir = Direction.SOUTH;
            }

            for(BlockPos blockPos = pos; pos.getY() > blockPos.getY() - 21 && pos.getY() > 0 && this.validStateInsidePortal(world.getBlockState(pos.down())); pos = pos.down()) {
            }

            int i = this.distanceToPortalEdge(pos, this.positiveDir) - 1;
            if (i >= 0) {
                this.lowerCorner = pos.offset(this.positiveDir, i);
                this.width = this.distanceToPortalEdge(this.lowerCorner, this.negativeDir);
                if (this.width < 2 || this.width > 21) {
                    this.lowerCorner = null;
                    this.width = 0;
                }
            }

            if (this.lowerCorner != null) {
                this.height = this.findHeight();
            }

        }

        protected int distanceToPortalEdge(BlockPos pos, Direction dir) {
            int i;
            for(i = 0; i < 22; ++i) {
                BlockPos blockPos = pos.offset(dir, i);
                if (!this.validStateInsidePortal(this.world.getBlockState(blockPos)) || this.world.getBlockState(blockPos.down()).getBlock() != Blocks.OBSIDIAN) {
                    break;
                }
            }

            Block block = this.world.getBlockState(pos.offset(dir, i)).getBlock();
            return block == Blocks.OBSIDIAN ? i : 0;
        }

        public int getHeight() {
            return this.height;
        }

        public int getWidth() {
            return this.width;
        }

        protected int findHeight() {
            int i;
            label56:
            for(this.height = 0; this.height < 21; ++this.height) {
                for(i = 0; i < this.width; ++i) {
                    BlockPos blockPos = this.lowerCorner.offset(this.negativeDir, i).up(this.height);
                    BlockState blockState = this.world.getBlockState(blockPos);
                    if (!this.validStateInsidePortal(blockState)) {
                        break label56;
                    }

                    Block block = blockState.getBlock();
                    if (block == Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_custom_portal"))) {
                        ++this.foundPortalBlocks;
                    }

                    if (i == 0) {
                        block = this.world.getBlockState(blockPos.offset(this.positiveDir)).getBlock();
                        if (block != Blocks.OBSIDIAN) {
                            break label56;
                        }
                    } else if (i == this.width - 1) {
                        block = this.world.getBlockState(blockPos.offset(this.negativeDir)).getBlock();
                        if (block != Blocks.OBSIDIAN) {
                            break label56;
                        }
                    }
                }
            }

            for(i = 0; i < this.width; ++i) {
                if (this.world.getBlockState(this.lowerCorner.offset(this.negativeDir, i).up(this.height)).getBlock() != Blocks.OBSIDIAN) {
                    this.height = 0;
                    break;
                }
            }

            if (this.height <= 21 && this.height >= 3) {
                return this.height;
            } else {
                this.lowerCorner = null;
                this.width = 0;
                this.height = 0;
                return 0;
            }
        }

        protected boolean validStateInsidePortal(BlockState state) {
            Block block = state.getBlock();
            return state.isAir() || state.isIn(BlockTags.FIRE) || block == Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_custom_portal"));
        }

        public boolean isValid() {
            return this.lowerCorner != null && this.width >= 2 && this.width <= 21 && this.height >= 3 && this.height <= 21;
        }

        public void createPortal() {
            for(int i = 0; i < this.width; ++i) {
                BlockPos blockPos = this.lowerCorner.offset(this.negativeDir, i);

                for(int j = 0; j < this.height; ++j) {
                    this.world.setBlockState(blockPos.up(j), Registry.BLOCK.get(Utils.addSuffixToPath(dimensionData.getId(), "_custom_portal")).getDefaultState().with(NetherPortalBlock.AXIS, this.axis), 18);
                }
            }

        }

        private boolean portalAlreadyExisted() {
            return this.foundPortalBlocks >= this.width * this.height;
        }

        public boolean wasAlreadyValid() {
            return this.isValid() && this.portalAlreadyExisted();
        }
    }
}