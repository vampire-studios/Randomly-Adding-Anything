package io.github.vampirestudios.raa.world.player;

public interface PlayerDiscoveryProvider {

    PlayerDiscoveryState getDiscoveryState();

    void setDiscoveryState(PlayerDiscoveryState playerMaterialDiscoverState);
}
