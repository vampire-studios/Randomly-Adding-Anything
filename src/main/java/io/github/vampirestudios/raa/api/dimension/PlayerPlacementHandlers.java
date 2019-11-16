package io.github.vampirestudios.raa.api.dimension;

import net.fabricmc.fabric.api.dimension.v1.EntityPlacer;
import net.minecraft.block.AirBlock;
import net.minecraft.block.pattern.BlockPattern;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

public enum PlayerPlacementHandlers {
    SURFACE_WORLD((teleported, destination, portalDir, horizontalOffset, verticalOffset) -> {
        BlockPos blockPos = getSurfacePos(destination, teleported);
        return new BlockPattern.TeleportTarget(new Vec3d(blockPos), Vec3d.ZERO, 0);
    });

    private EntityPlacer entityPlacer;

    PlayerPlacementHandlers(EntityPlacer entityPlacer) {
        this.entityPlacer = entityPlacer;
    }

    public EntityPlacer getEntityPlacer() {
        return entityPlacer;
    }

    private static BlockPos getSurfacePos(ServerWorld serverWorld, Entity entity) {
        for (int i = 255; i > 0; i--) {
            BlockPos pos = new BlockPos(entity.getPos().x, i, entity.getPos().z);
            if (!(serverWorld.getBlockState(pos.down(1)).getBlock() instanceof AirBlock)) {
                return pos.up();
            }
        }
        return new BlockPos(entity.getPos()).up();
    }
}
