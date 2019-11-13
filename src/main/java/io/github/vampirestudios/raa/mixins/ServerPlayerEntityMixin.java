package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.impl.PlayerMaterialDiscoverProvider;
import io.github.vampirestudios.raa.impl.PlayerMaterialDiscoverState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayerEntity.class)
public class ServerPlayerEntityMixin implements PlayerMaterialDiscoverProvider {

    private PlayerMaterialDiscoverState playerMaterialDiscoverState;

    @Inject(method = "readCustomDataFromTag", at = @At("RETURN"))
    public void readDiscoveryData(CompoundTag compoundTag_1, CallbackInfo ci) {
        if (this.playerMaterialDiscoverState == null) this.setMaterialDiscoverState(new PlayerMaterialDiscoverState());
        this.playerMaterialDiscoverState.fromTag(compoundTag_1);
    }

    @Inject(method = "writeCustomDataToTag", at = @At("RETURN"))
    public void writeDiscoveryData(CompoundTag compoundTag_1, CallbackInfo ci) {
        this.playerMaterialDiscoverState.toTag(compoundTag_1);
    }


    @Override
    public PlayerMaterialDiscoverState getMaterialDiscoverState() {
        return playerMaterialDiscoverState;
    }

    @Override
    public void setMaterialDiscoverState(PlayerMaterialDiscoverState playerMaterialDiscoverState) {
        this.playerMaterialDiscoverState = playerMaterialDiscoverState;
    }
}
