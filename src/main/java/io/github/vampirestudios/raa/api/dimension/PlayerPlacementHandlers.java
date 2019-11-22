package io.github.vampirestudios.raa.api.dimension;

import io.github.vampirestudios.raa.utils.Utils;
import net.fabricmc.fabric.api.dimension.v1.EntityPlacer;
import net.minecraft.block.AirBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.Registry;

public enum PlayerPlacementHandlers {
    SURFACE_WORLD((teleported, destination, portalDir, horizontalOffset, verticalOffset) -> {
        BlockPos blockPos = getSurfacePos(destination, teleported, 255);
        destination.setBlockState(blockPos.down(1), Registry.BLOCK.get(Utils.appendToPath(Registry.DIMENSION.getId(destination.getDimension().getType()), "_portal")).getDefaultState());
        return new BlockPattern.TeleportTarget(new Vec3d(blockPos), Vec3d.ZERO, 0);
    }),
    CAVE_WORLD((teleported, destination, portalDir, horizontalOffset, verticalOffset) -> {
        BlockPos blockPos = getSurfacePos(destination, teleported, 127);
        return new BlockPattern.TeleportTarget(new Vec3d(blockPos), Vec3d.ZERO, 0);
    });

    private EntityPlacer entityPlacer;

    PlayerPlacementHandlers(EntityPlacer entityPlacer) {
        this.entityPlacer = entityPlacer;
    }

    private static BlockPos getSurfacePos(ServerWorld serverWorld, Entity entity, int maxHeight) {
        for (int i = maxHeight; i > 0; i--) {
            BlockPos pos = new BlockPos(entity.getPos().x, i, entity.getPos().z);
            if (!(serverWorld.getBlockState(pos.down(1)).getBlock() instanceof AirBlock) && (serverWorld.getBlockState(pos.up(1)).getBlock() instanceof AirBlock) && (serverWorld.getBlockState(pos).getBlock() instanceof AirBlock)) {
                return pos.up();
            }
        }
        return new BlockPos(entity.getPos().x, maxHeight + 1, entity.getPos().z);
    }

    public EntityPlacer getEntityPlacer() {
        return entityPlacer;
    }
}
