package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.world.player.PlayerDiscoveryProvider;
import io.github.vampirestudios.raa.world.player.PlayerDiscoveryState;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.world.player.OreDiscoverState;
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
        PlayerDiscoveryState discoveryState = ((PlayerDiscoveryProvider) player).getDiscoveryState();
        if (discoveryState == null) {
            ((PlayerDiscoveryProvider) player).setDiscoveryState(new PlayerDiscoveryState());
            discoveryState = ((PlayerDiscoveryProvider) player).getDiscoveryState();
        }
        List<OreDiscoverState> materialDiscoveryState = discoveryState.getMaterialDiscoveryState();
        if (discoveryState.isFirstConnect() || (materialDiscoveryState == null || materialDiscoveryState.isEmpty())) {
            discoveryState.setFirstConnect(true);
            for (Material material : Materials.MATERIALS) {
                Objects.requireNonNull(materialDiscoveryState).add(new OreDiscoverState(material));
            }
        }

        List<OreDiscoverState> dimensionMaterialDiscoveryState = discoveryState.getDimensionMaterialDiscoveryState();
        if (discoveryState.isFirstConnect() || (dimensionMaterialDiscoveryState == null || dimensionMaterialDiscoveryState.isEmpty())) {
            discoveryState.setFirstConnect(true);
            for (Material material : Materials.DIMENSION_MATERIALS) {
                Objects.requireNonNull(dimensionMaterialDiscoveryState).add(new OreDiscoverState(material));
            }
        }
    }
}
