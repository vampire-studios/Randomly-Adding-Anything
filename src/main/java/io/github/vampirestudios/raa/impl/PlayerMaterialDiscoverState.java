package io.github.vampirestudios.raa.impl;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import io.github.vampirestudios.raa.state.OreDiscoverState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class PlayerMaterialDiscoverState {
    private List<OreDiscoverState> materialDiscoveryState;
    private boolean firstConnect;

    public PlayerMaterialDiscoverState() {
        this.materialDiscoveryState = new ArrayList<>(Materials.MATERIALS.getIds().size() + 1);
    }

    public void fromTag(CompoundTag compoundTag) {
        List<OreDiscoverState> oreDiscoverStates = new ArrayList<>(Materials.MATERIALS.getIds().size() + 1);
        CompoundTag discoverList = compoundTag.getCompound("discoverList");
        if (discoverList.isEmpty()) return;
        for (String string_1 : discoverList.getKeys()) {
            CompoundTag compoundTag1 = discoverList.getCompound(string_1);
            Material material = Materials.MATERIALS.get(new Identifier(RandomlyAddingAnything.MOD_ID, compoundTag1.getString("name")));
            if (material == null) {
                System.out.println("Could not find this material : " + compoundTag1.getString("name"));
            }
            int discoveredTimes = compoundTag1.getInt("discoverTimes");
            boolean discovered = compoundTag1.getBoolean("discovered");
            oreDiscoverStates.add(new OreDiscoverState(material, discoveredTimes, discovered));
        }
        this.materialDiscoveryState = oreDiscoverStates;
    }

    public CompoundTag toTag(CompoundTag compoundTag) {
        CompoundTag discoverListCompound = new CompoundTag();
        CompoundTag discoverListInformation;
        for (int c = 0; c < materialDiscoveryState.size(); c++) {
            discoverListInformation = new CompoundTag();
            discoverListInformation.putString("name", materialDiscoveryState.get(c).getMaterial().getName());
            discoverListInformation.putInt("discoverTimes", materialDiscoveryState.get(c).getDiscoverTimes());
            discoverListInformation.putBoolean("discovered", materialDiscoveryState.get(c).isDiscovered());
            discoverListCompound.put("" + c + "", discoverListInformation.method_10553());
        }
        compoundTag.put("discoverList", discoverListCompound);
        return compoundTag;
    }

    public List<OreDiscoverState> getMaterialDiscoveryState() {
        return materialDiscoveryState;
    }

    public boolean isFirstConnect() {
        return firstConnect;
    }

    public void setFirstConnect(boolean firstConnect) {
        this.firstConnect = firstConnect;
    }

}