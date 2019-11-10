package io.github.vampirestudios.raa.state;

import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerDiscoverState {
    private UUID uuid;
    private List<OreDiscoverState> discoverStateList;

    public PlayerDiscoverState(UUID uuid, List<OreDiscoverState> discoverStates) {
        this.uuid = uuid;
        this.discoverStateList = discoverStates;
    }

    public PlayerDiscoverState(UUID uuid) {
        this.uuid = uuid;
        List<OreDiscoverState> discoverStates = new ArrayList<>();
        for (Material material : Materials.MATERIALS)
            discoverStates.add(new OreDiscoverState(material));
        this.discoverStateList = discoverStates;
    }

    public List<OreDiscoverState> getDiscoverStateList() {
        return discoverStateList;
    }

    public UUID getUuid() {
        return uuid;
    }
}
