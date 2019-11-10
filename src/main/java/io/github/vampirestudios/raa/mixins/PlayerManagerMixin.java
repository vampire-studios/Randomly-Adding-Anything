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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Mixin(PlayerManager.class)
public abstract class PlayerManagerMixin {

    @Inject(at = @At("RETURN"), method = "onPlayerConnect")
    private void onPlayerConnect(ClientConnection clientConnection, ServerPlayerEntity player, CallbackInfo ci) {
        PlayerMaterialDiscoverState discoverState = ((PlayerMaterialDiscoverProvider)player.getServerWorld()).getMaterialDiscoverState();
        Map<UUID, List<OreDiscoverState>> map = discoverState.getPlayerMap();
        if (map.containsKey(player.getUuid())) {
            List<OreDiscoverState> list = new ArrayList<>();
            for (Material material : Materials.MATERIALS) {
                list.add(new OreDiscoverState(material));
            }
            map.put(player.getUuid(), list);
        }
    }
}
