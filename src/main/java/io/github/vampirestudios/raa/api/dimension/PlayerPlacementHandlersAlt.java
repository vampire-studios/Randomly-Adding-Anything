package io.github.vampirestudios.raa.api.dimension;

import io.github.vampirestudios.raa.blocks.PortalBlock;
import io.github.vampirestudios.raa.utils.Utils;
import net.fabricmc.fabric.api.dimension.v1.EntityPlacer;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

public enum PlayerPlacementHandlersAlt {
    SURFACE_WORLD((teleported, destination, portalDir, horizontalOffset, verticalOffset) -> {
        BlockPos blockPos = getSurfacePos(destination, teleported);
        Block block = Registry.BLOCK.get(Utils.addSuffixToPath(Objects.requireNonNull(Registry.DIMENSION_TYPE.getId(destination.getDimension().getType())), "_custom_portal"));

        destination.setBlockState(blockPos, block.getDefaultState());
        destination.setBlockState(blockPos.east(), block.getDefaultState());

        destination.setBlockState(blockPos.down().east(2).up(2), block.getDefaultState());
        destination.setBlockState(blockPos.down().east(2).up(3), block.getDefaultState());
        destination.setBlockState(blockPos.down().east(2).up(4), block.getDefaultState());

        destination.setBlockState(blockPos.down().west().up(2), block.getDefaultState());
        destination.setBlockState(blockPos.down().west().up(3), block.getDefaultState());
        destination.setBlockState(blockPos.down().west().up(4), block.getDefaultState());

        destination.setBlockState(blockPos.up(4), block.getDefaultState());
        destination.setBlockState(blockPos.up(4).east(), block.getDefaultState());

        for (int x = 0; x < 2; x++)
        {
            for (int y = 0; y < 3; y++)
            {
                destination.setBlockState(blockPos.up(y).east(x).up(), Registry.BLOCK.get(Utils.addSuffixToPath(Objects.requireNonNull(Registry.DIMENSION_TYPE.getId(destination.getDimension().getType())), "_custom_portal")).getDefaultState());
            }
        }

        return new BlockPattern.TeleportTarget(new Vec3d(new Vector3f(blockPos.getX(), blockPos.getY(), blockPos.getZ())), Vec3d.ZERO, 0);
    }),
    CAVE_WORLD((teleported, destination, portalDir, horizontalOffset, verticalOffset) -> {
        BlockPos blockPos = getSurfacePos(destination, teleported);
        Block block = Registry.BLOCK.get(Utils.addSuffixToPath(Objects.requireNonNull(Registry.DIMENSION_TYPE.getId(destination.getDimension().getType())), "_custom_portal"));

        destination.setBlockState(blockPos, block.getDefaultState());
        destination.setBlockState(blockPos.east(), block.getDefaultState());

        destination.setBlockState(blockPos.down().east(2).up(2), block.getDefaultState());
        destination.setBlockState(blockPos.down().east(2).up(3), block.getDefaultState());
        destination.setBlockState(blockPos.down().east(2).up(4), block.getDefaultState());

        destination.setBlockState(blockPos.down().west().up(2), block.getDefaultState());
        destination.setBlockState(blockPos.down().west().up(3), block.getDefaultState());
        destination.setBlockState(blockPos.down().west().up(4), block.getDefaultState());

        destination.setBlockState(blockPos.up(4), block.getDefaultState());
        destination.setBlockState(blockPos.up(4).east(), block.getDefaultState());

        for (int x = 0; x < 2; x++)
        {
            for (int y = 0; y < 3; y++)
            {
                destination.setBlockState(blockPos.up(y).east(x).up(), Registry.BLOCK.get(Utils.addSuffixToPath(Objects.requireNonNull(Registry.DIMENSION_TYPE.getId(destination.getDimension().getType())), "_custom_portal")).getDefaultState());
            }
        }

        return new BlockPattern.TeleportTarget(new Vec3d(new Vector3f(blockPos.getX(), blockPos.getY(), blockPos.getZ())), Vec3d.ZERO, 0);
    }),
    OVERWORLD((teleported, destination, portalDir, horizontalOffset, verticalOffset) -> {
        BlockPos blockPos = getSurfacePos(destination, teleported);
        Block block = Registry.BLOCK.get(Utils.addSuffixToPath(Objects.requireNonNull(Registry.DIMENSION_TYPE.getId(destination.getDimension().getType())), "_custom_portal"));

        destination.setBlockState(blockPos, block.getDefaultState());
        destination.setBlockState(blockPos.east(), block.getDefaultState());

        destination.setBlockState(blockPos.down().east(2).up(2), block.getDefaultState());
        destination.setBlockState(blockPos.down().east(2).up(3), block.getDefaultState());
        destination.setBlockState(blockPos.down().east(2).up(4), block.getDefaultState());

        destination.setBlockState(blockPos.down().west().up(2), block.getDefaultState());
        destination.setBlockState(blockPos.down().west().up(3), block.getDefaultState());
        destination.setBlockState(blockPos.down().west().up(4), block.getDefaultState());

        destination.setBlockState(blockPos.up(4), block.getDefaultState());
        destination.setBlockState(blockPos.up(4).east(), block.getDefaultState());

        for (int x = 0; x < 2; x++)
        {
            for (int y = 0; y < 3; y++)
            {
                destination.setBlockState(blockPos.up(y).east(x).up(), Registry.BLOCK.get(Utils.addSuffixToPath(Objects.requireNonNull(Registry.DIMENSION_TYPE.getId(destination.getDimension().getType())), "_custom_portal")).getDefaultState());
            }
        }

        return new BlockPattern.TeleportTarget(new Vec3d(new Vector3f(blockPos.getX(), blockPos.getY(), blockPos.getZ())), Vec3d.ZERO, 0);
    }),
    FLOATING_WORLD((teleported, destination, portalDir, horizontalOffset, verticalOffset) -> {
        BlockPos blockPos = getSurfacePos(destination, teleported);
        if (blockPos.getY() == 255 || blockPos.getY() == 256) {
            blockPos = new BlockPos(blockPos.getX(), teleported.getY(), blockPos.getZ());
            Block block = Registry.BLOCK.get(Utils.addSuffixToPath(Objects.requireNonNull(Registry.DIMENSION_TYPE.getId(destination.getDimension().getType())), "_custom_portal"));

            destination.setBlockState(blockPos, block.getDefaultState());
            destination.setBlockState(blockPos.east(), block.getDefaultState());

            destination.setBlockState(blockPos.down().east(2).up(2), block.getDefaultState());
            destination.setBlockState(blockPos.down().east(2).up(3), block.getDefaultState());
            destination.setBlockState(blockPos.down().east(2).up(4), block.getDefaultState());

            destination.setBlockState(blockPos.down().west().up(2), block.getDefaultState());
            destination.setBlockState(blockPos.down().west().up(3), block.getDefaultState());
            destination.setBlockState(blockPos.down().west().up(4), block.getDefaultState());

            destination.setBlockState(blockPos.up(4), block.getDefaultState());
            destination.setBlockState(blockPos.up(4).east(), block.getDefaultState());

            for (int x = 0; x < 2; x++)
            {
                for (int y = 0; y < 3; y++)
                {
                    destination.setBlockState(blockPos.up(y).east(x).up(), Registry.BLOCK.get(Utils.addSuffixToPath(Objects.requireNonNull(Registry.DIMENSION_TYPE.getId(destination.getDimension().getType())), "_custom_portal")).getDefaultState());
                }
            }

            return new BlockPattern.TeleportTarget(new Vec3d(new Vector3f(blockPos.up(2).getX(), blockPos.up(2).getY(), blockPos.up(2).getZ())), Vec3d.ZERO, 0);
        } else {
            Block block = Registry.BLOCK.get(Utils.addSuffixToPath(Objects.requireNonNull(Registry.DIMENSION_TYPE.getId(destination.getDimension().getType())), "_custom_portal"));

            destination.setBlockState(blockPos, block.getDefaultState());
            destination.setBlockState(blockPos.east(), block.getDefaultState());

            destination.setBlockState(blockPos.down().east(2).up(2), block.getDefaultState());
            destination.setBlockState(blockPos.down().east(2).up(3), block.getDefaultState());
            destination.setBlockState(blockPos.down().east(2).up(4), block.getDefaultState());

            destination.setBlockState(blockPos.down().west().up(2), block.getDefaultState());
            destination.setBlockState(blockPos.down().west().up(3), block.getDefaultState());
            destination.setBlockState(blockPos.down().west().up(4), block.getDefaultState());

            destination.setBlockState(blockPos.up(4), block.getDefaultState());
            destination.setBlockState(blockPos.up(4).east(), block.getDefaultState());

            for (int x = 0; x < 2; x++)
            {
                for (int y = 0; y < 3; y++)
                {
                    destination.setBlockState(blockPos.up(y).east(x).up(), Registry.BLOCK.get(Utils.addSuffixToPath(Objects.requireNonNull(Registry.DIMENSION_TYPE.getId(destination.getDimension().getType())), "_custom_portal")).getDefaultState());
                }
            }

            return new BlockPattern.TeleportTarget(new Vec3d(new Vector3f(blockPos.getX(), blockPos.getY(), blockPos.getZ())), Vec3d.ZERO, 0);
        }
    });

    private final EntityPlacer entityPlacer;

    PlayerPlacementHandlersAlt(EntityPlacer entityPlacer) {
        this.entityPlacer = entityPlacer;
    }

    private static BlockPos getSurfacePos(ServerWorld serverWorld, Entity entity) {
        BlockPos portalPos = getPortalPos(serverWorld, entity, 255);
        if (portalPos != null) return portalPos.up();
        for (int i = 255; i > 0; i--) {
            BlockPos pos = new BlockPos(entity.getBlockPos().getX(), i, entity.getBlockPos().getZ());
            if (!(serverWorld.getBlockState(pos.down(1)).getBlock() instanceof AirBlock) && (serverWorld.getBlockState(pos.up()).getBlock() instanceof AirBlock) && (serverWorld.getBlockState(pos).getBlock() instanceof AirBlock)) {
                return pos.up();
            }
        }
        portalPos = new BlockPos(entity.getBlockPos().getX(), 255 + 1, entity.getBlockPos().getZ());
        serverWorld.setBlockState(portalPos.up(), Blocks.AIR.getDefaultState());
        serverWorld.setBlockState(portalPos, Blocks.AIR.getDefaultState());
        return portalPos;
    }

    private static BlockPos getPortalPos(ServerWorld serverWorld, Entity entity, int maxHeight) {
        for (int i = maxHeight; i > 0; i--) {
            BlockPos pos = new BlockPos(entity.getPos().x, i, entity.getPos().z);
            if (serverWorld.getBlockState(pos).getBlock() instanceof PortalBlock) {
                return pos;
            }
        }
        return null;
    }

    public EntityPlacer getEntityPlacer() {
        return entityPlacer;
    }
}
