package io.github.vampirestudios.raa.impl;

import com.google.gson.Gson;
import io.github.vampirestudios.raa.state.OreDiscoverState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.PersistentState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PlayerMaterialDiscoverState extends PersistentState {

    private Map<UUID, List<OreDiscoverState>> playerMap = new HashMap();

    public PlayerMaterialDiscoverState() {
        super("materialDiscover");
    }

    @Override
    public void fromTag(CompoundTag compoundTag) {
        this.playerMap = new Gson().fromJson(compoundTag.getString("playerMap"), Map.class);
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        compoundTag.putString("playerMap", playerMap.toString());
        return compoundTag;
    }

    public Map<UUID, List<OreDiscoverState>> getPlayerMap() {
        return playerMap;
    }
}
