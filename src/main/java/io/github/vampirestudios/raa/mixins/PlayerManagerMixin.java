package io.github.vampirestudios.raa.mixins;

import io.github.vampirestudios.raa.impl.PlayerMaterialDiscoverProvider;
import io.github.vampirestudios.raa.impl.PlayerMaterialDiscoverState;
import io.github.vampirestudios.raa.state.PlayerDiscoverState;
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
    private void onPlayerConnect(ClientConnection clientConnection, ServerPlayerEntity player, CallbackInfo ci) {
        PlayerMaterialDiscoverState discoverState = ((PlayerMaterialDiscoverProvider)player.getServerWorld()).getMaterialDiscoverState();
        List<PlayerDiscoverState> map = discoverState.getPlayerMap();
        boolean b1 = false;
        for (PlayerDiscoverState state : map)
            if (state.getUuid() == player.getUuid()) {
                b1 = true;
                break;
            }
        if (!b1) map.add(new PlayerDiscoverState(player.getUuid()));
    }
}
