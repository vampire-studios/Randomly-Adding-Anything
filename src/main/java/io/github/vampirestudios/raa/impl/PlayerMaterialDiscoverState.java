package io.github.vampirestudios.raa.impl;

import com.google.gson.Gson;
import io.github.vampirestudios.raa.state.PlayerDiscoverState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.PersistentState;

import java.util.ArrayList;
import java.util.List;

public class PlayerMaterialDiscoverState extends PersistentState {

    private List<PlayerDiscoverState> playerMap = new ArrayList<>();

    public PlayerMaterialDiscoverState() {
        super("materialDiscover");
    }

    @Override
    public void fromTag(CompoundTag compoundTag) {
        this.playerMap = new Gson().fromJson(compoundTag.getString("playerMap"), List.class);
    }

    @Override
    public CompoundTag toTag(CompoundTag compoundTag) {
        compoundTag.putString("playerMap", playerMap.toString());
        return compoundTag;
    }

    public List<PlayerDiscoverState> getPlayerMap() {
        return playerMap;
    }
}
