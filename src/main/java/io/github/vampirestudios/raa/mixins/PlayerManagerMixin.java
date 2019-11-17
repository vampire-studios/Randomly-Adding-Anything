package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.impl.PlayerMaterialDiscoverProvider;
import io.github.vampirestudios.raa.impl.PlayerMaterialDiscoverState;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.state.OreDiscoverState;
import net.minecraft.network.ClientConnection;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Objects;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Inject(at = @At("RETURN"), method = "onPlayerConnect")
    private void onPlayerConnect(ClientConnection clientConnection, ServerPlayerEntity player, CallbackInfo ci) {
        PlayerMaterialDiscoverState discoverState = ((PlayerMaterialDiscoverProvider) player).getMaterialDiscoverState();
        if (discoverState == null) {
            ((PlayerMaterialDiscoverProvider) player).setMaterialDiscoverState(new PlayerMaterialDiscoverState());
            discoverState = ((PlayerMaterialDiscoverProvider) player).getMaterialDiscoverState();
        }
        List<OreDiscoverState> materialDiscoveryState = discoverState.getMaterialDiscoveryState();
        if (discoverState.isFirstConnect() || (materialDiscoveryState == null || materialDiscoveryState.isEmpty())) {
            discoverState.setFirstConnect(true);
            for (Material material : Materials.MATERIALS) {
                Objects.requireNonNull(materialDiscoveryState).add(new OreDiscoverState(material));
            }
        }

        List<OreDiscoverState> dimensionMaterialDiscoveryState = discoverState.getDimensionMaterialDiscoveryState();
        if (discoverState.isFirstConnect() || (dimensionMaterialDiscoveryState == null || dimensionMaterialDiscoveryState.isEmpty())) {
            discoverState.setFirstConnect(true);
            for (Material material : Materials.DIMENSION_MATERIALS) {
                Objects.requireNonNull(dimensionMaterialDiscoveryState).add(new OreDiscoverState(material));
            }
        }
    }
}
