package io.github.vampirestudios.raa.mixins;

import com.mojang.authlib.GameProfile;
import io.github.vampirestudios.raa.world.player.PlayerDiscoveryProvider;
import io.github.vampirestudios.raa.world.player.PlayerDiscoveryState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements PlayerDiscoveryProvider {

    private PlayerDiscoveryState playerDiscoveryState;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void init(MinecraftServer server, ServerWorld world, GameProfile profile, ServerPlayerInteractionManager interactionManager, CallbackInfo ci) {
        this.setDiscoveryState(new PlayerDiscoveryState());
    }

    @Inject(method = "readCustomDataFromTag", at = @At("RETURN"))
    public void readDiscoveryData(CompoundTag compoundTag_1, CallbackInfo ci) {
        this.playerDiscoveryState.fromTag(compoundTag_1);
    }

    @Inject(method = "writeCustomDataToTag", at = @At("RETURN"))
    public void writeDiscoveryData(CompoundTag compoundTag_1, CallbackInfo ci) {
        this.playerDiscoveryState.toTag(compoundTag_1);
    }

    @Inject(method = "copyFrom", at = @At("RETURN"))
    public void copyFromPlayer(ServerPlayerEntity oldPlayer, boolean alive, CallbackInfo ci) {
        this.setDiscoveryState(((PlayerDiscoveryProvider)oldPlayer).getDiscoveryState());
    }


    @Override
    public PlayerDiscoveryState getDiscoveryState() {
        return playerDiscoveryState;
    }

    @Override
    public void setDiscoveryState(PlayerDiscoveryState playerDiscoverState) {
        this.playerDiscoveryState = playerDiscoverState;
    }

}