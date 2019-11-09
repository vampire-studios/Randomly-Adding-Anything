package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.impl.PlayerMaterialDiscoverProvider;
import io.github.vampirestudios.raa.impl.PlayerMaterialDiscoverState;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;
import net.minecraft.world.WorldSaveHandler;
import net.minecraft.world.chunk.ChunkManager;
import net.minecraft.world.dimension.Dimension;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.level.LevelProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.concurrent.Executor;
import java.util.function.BiFunction;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin extends World implements PlayerMaterialDiscoverProvider {
    protected ServerWorldMixin(LevelProperties levelProperties, DimensionType dimensionType, BiFunction<World, Dimension, ChunkManager> biFunction, Profiler profiler, boolean b) {
        super(levelProperties, dimensionType, biFunction, profiler, b);
    }

    @Shadow
    public abstract PersistentStateManager getPersistentStateManager();

    private PlayerMaterialDiscoverState materialDiscoverState;

    @Inject(at = @At("RETURN"), method = "<init>")
    private void init(MinecraftServer minecraftServer, Executor executor, WorldSaveHandler worldSaveHandler, LevelProperties levelProperties, DimensionType dimensionType, Profiler profiler, WorldGenerationProgressListener worldGenerationProgressListener, CallbackInfo ci) {
        materialDiscoverState = this.getPersistentStateManager().getOrCreate(PlayerMaterialDiscoverState::new, "materialDiscover");
    }

    @Override
    public PlayerMaterialDiscoverState getMaterialDiscoverState() {
        return materialDiscoverState;
    }
}
