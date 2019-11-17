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

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Inject(at = @At("RETURN"), method = "onPlayerConnect")
    private void onPlayerConnecte(ClientConnection clientConnection, ServerPlayerEntity player, CallbackInfo ci) {
        PlayerMaterialDiscoverState discoverState = ((PlayerMaterialDiscoverProvider) player).getMaterialDiscoverState();
        if (discoverState == null) {
            ((PlayerMaterialDiscoverProvider) player).setMaterialDiscoverState(new PlayerMaterialDiscoverState());
            discoverState = ((PlayerMaterialDiscoverProvider) player).getMaterialDiscoverState();
        }
        List<OreDiscoverState> map = discoverState.getList();
        if (discoverState.isFirstConnect() || (map == null || map.isEmpty())) {
            discoverState.setFirstConnect(true);
            for (Material material : Materials.MATERIALS) {
                map.add(new OreDiscoverState(material));
            }
        }
    }
}
