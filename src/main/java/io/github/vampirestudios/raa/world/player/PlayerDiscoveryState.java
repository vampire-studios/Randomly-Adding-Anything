package io.github.vampirestudios.raa.world.player;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.generation.materials.Material;
import io.github.vampirestudios.raa.registries.Materials;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PlayerDiscoveryState {
    private List<OreDiscoverState> materialDiscoveryState;
    private List<OreDiscoverState> dimensionMaterialDiscoveryState;
    private boolean firstConnect;

    public PlayerDiscoveryState() {
        this.materialDiscoveryState = new ArrayList<>(Materials.MATERIALS.getIds().size() + 1);
        for (Material material : Materials.MATERIALS) {
            Objects.requireNonNull(this.materialDiscoveryState).add(new OreDiscoverState(material));
        }
        this.dimensionMaterialDiscoveryState = new ArrayList<>(Materials.DIMENSION_MATERIALS.getIds().size() + 1);
        for (Material material : Materials.DIMENSION_MATERIALS) {
            Objects.requireNonNull(this.dimensionMaterialDiscoveryState).add(new OreDiscoverState(material));
        }
    }

    public void fromTag(CompoundTag compoundTag) {
        List<OreDiscoverState> oreDiscoverStates = new ArrayList<>(Materials.MATERIALS.getIds().size() + 1);
        CompoundTag discoverList = compoundTag.getCompound("materialDiscoverList");
        if (discoverList.isEmpty()) return;
        for (String string_1 : discoverList.getKeys()) {
            CompoundTag compoundTag1 = discoverList.getCompound(string_1);
            Material material = Materials.MATERIALS.get(new Identifier(RandomlyAddingAnything.MOD_ID, compoundTag1.getString("name")));
            if (material == null) {
                System.out.println("Could not find this material : " + compoundTag1.getString("name"));
                continue;
            }
            int discoveredTimes = compoundTag1.getInt("discoverTimes");
            boolean discovered = compoundTag1.getBoolean("discovered");
            oreDiscoverStates.add(new OreDiscoverState(material, discoveredTimes, discovered));
        }
        this.materialDiscoveryState = oreDiscoverStates;

        List<OreDiscoverState> dimensionOreDiscoverStates = new ArrayList<>(Materials.DIMENSION_MATERIALS.getIds().size() + 1);
        CompoundTag dimensionDiscoverList = compoundTag.getCompound("dimensionMaterialDiscoverList");
        if (dimensionDiscoverList.isEmpty()) return;
        for (String string_1 : dimensionDiscoverList.getKeys()) {
            CompoundTag compoundTag1 = dimensionDiscoverList.getCompound(string_1);
            Material material = Materials.DIMENSION_MATERIALS.get(new Identifier(RandomlyAddingAnything.MOD_ID, compoundTag1.getString("name")));
            if (material == null) {
                System.out.println("Could not find this dimension material : " + compoundTag1.getString("name"));
                continue;
            }
            int discoveredTimes = compoundTag1.getInt("discoverTimes");
            boolean discovered = compoundTag1.getBoolean("discovered");
            dimensionOreDiscoverStates.add(new OreDiscoverState(material, discoveredTimes, discovered));
        }
        this.dimensionMaterialDiscoveryState = dimensionOreDiscoverStates;
    }

    public CompoundTag toTag(CompoundTag compoundTag) {
        CompoundTag discoverListCompound = new CompoundTag();
        CompoundTag discoverListInformation;
        for (int c = 0; c < materialDiscoveryState.size(); c++) {
            if (materialDiscoveryState.get(c) == null) continue;
            discoverListInformation = new CompoundTag();
            discoverListInformation.putString("name", materialDiscoveryState.get(c).getMaterial().getName());
            discoverListInformation.putInt("discoverTimes", materialDiscoveryState.get(c).getDiscoverTimes());
            discoverListInformation.putBoolean("discovered", materialDiscoveryState.get(c).isDiscovered());
            discoverListCompound.put("" + c + "", discoverListInformation.copy());
        }
        compoundTag.put("materialDiscoverList", discoverListCompound);

        CompoundTag dimensionDiscoverListCompound = new CompoundTag();
        CompoundTag dimensionDiscoverListInformation;
        for (int c = 0; c < dimensionMaterialDiscoveryState.size(); c++) {
            if (dimensionMaterialDiscoveryState.get(c) == null) continue;
            dimensionDiscoverListInformation = new CompoundTag();
            dimensionDiscoverListInformation.putString("name", dimensionMaterialDiscoveryState.get(c).getMaterial().getName());
            dimensionDiscoverListInformation.putInt("discoverTimes", dimensionMaterialDiscoveryState.get(c).getDiscoverTimes());
            dimensionDiscoverListInformation.putBoolean("discovered", dimensionMaterialDiscoveryState.get(c).isDiscovered());
            dimensionDiscoverListCompound.put("" + c + "", dimensionDiscoverListInformation.copy());
        }
        compoundTag.put("dimensionMaterialDiscoverList", dimensionDiscoverListCompound);
        return compoundTag;
    }

    public List<OreDiscoverState> getMaterialDiscoveryState() {
        return materialDiscoveryState;
    }

    public List<OreDiscoverState> getDimensionMaterialDiscoveryState() {
        return dimensionMaterialDiscoveryState;
    }

    public boolean isFirstConnect() {
        return firstConnect;
    }

    public void setFirstConnect(boolean firstConnect) {
        this.firstConnect = firstConnect;
    }

}