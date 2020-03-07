package io.github.vampirestudios.raa.api.dimension;

import io.github.vampirestudios.raa.blocks.PortalBlock;
import io.github.vampirestudios.raa.utils.Utils;
import net.fabricmc.fabric.api.dimension.v1.EntityPlacer;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Blocks;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

import java.util.Objects;

public enum PlayerPlacementHandlers {
    SURFACE_WORLD((teleported, destination, portalDir, horizontalOffset, verticalOffset) -> {
        BlockPos blockPos = getSurfacePos(destination, teleported, 255);
        destination.setBlockState(blockPos.down(1), Registry.BLOCK.get(Utils.addSuffixToPath(Objects.requireNonNull(Registry.DIMENSION_TYPE.getId(destination.getDimension().getType())), "_portal")).getDefaultState());
        return new BlockPattern.TeleportTarget(new Vec3d(new Vector3f(blockPos.getX(), blockPos.getY(), blockPos.getZ())), Vec3d.ZERO, 0);
    }),
    CAVE_WORLD((teleported, destination, portalDir, horizontalOffset, verticalOffset) -> {
        BlockPos blockPos = getSurfacePos(destination, teleported, 126);
        destination.setBlockState(blockPos.down(1), Registry.BLOCK.get(Utils.addSuffixToPath(Objects.requireNonNull(Registry.DIMENSION_TYPE.getId(destination.getDimension().getType())), "_portal")).getDefaultState());
        return new BlockPattern.TeleportTarget(new Vec3d(new Vector3f(blockPos.getX(), blockPos.getY(), blockPos.getZ())), Vec3d.ZERO, 0);
    }),
    OVERWORLD((teleported, destination, portalDir, horizontalOffset, verticalOffset) -> {
        BlockPos blockPos = getSurfacePos(destination, teleported, 255);
        destination.setBlockState(blockPos.down(1), Registry.BLOCK.get(Utils.addSuffixToPath(Objects.requireNonNull(Registry.DIMENSION_TYPE.getId(teleported.getEntityWorld().dimension.getType())), "_portal")).getDefaultState());
        return new BlockPattern.TeleportTarget(new Vec3d(new Vector3f(blockPos.getX(), blockPos.getY(), blockPos.getZ())), Vec3d.ZERO, 0);
    });

    private EntityPlacer entityPlacer;

    PlayerPlacementHandlers(EntityPlacer entityPlacer) {
        this.entityPlacer = entityPlacer;
    }

    private static BlockPos getSurfacePos(ServerWorld serverWorld, Entity entity, int maxHeight) {
        BlockPos portalPos = getPortalPos(serverWorld, entity, maxHeight);
        if (portalPos != null) return portalPos.up();
        for (int i = maxHeight; i > 0; i--) {
            BlockPos pos = new BlockPos(entity.getSenseCenterPos().getX(), i, entity.getSenseCenterPos().getZ());
            if (!(serverWorld.getBlockState(pos.down(1)).getBlock() instanceof AirBlock) && (serverWorld.getBlockState(pos.up()).getBlock() instanceof AirBlock) && (serverWorld.getBlockState(pos).getBlock() instanceof AirBlock)) {
                return pos.up();
            }
        }
        portalPos = new BlockPos(entity.getSenseCenterPos().getX(), maxHeight + 1, entity.getSenseCenterPos().getZ());
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
