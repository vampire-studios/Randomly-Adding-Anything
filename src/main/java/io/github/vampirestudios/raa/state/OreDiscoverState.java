package io.github.vampirestudios.raa.state;

import io.github.vampirestudios.raa.materials.Material;

public class OreDiscoverState {
    private Material material;
    private int discoverTimes;
    private boolean discovered;

    public OreDiscoverState(Material material) {
        this.material = material;
        this.discovered = false;
        this.discoverTimes = 0;
    }

    public OreDiscoverState(Material material, int discoverTimes, boolean discovered) {
        this.material = material;
        this.discovered = discovered;
        this.discoverTimes = discoverTimes;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }

    public void setDiscoverTimes(int discoverTimes) {
        this.discoverTimes = discoverTimes;
    }

    public int getDiscoverTimes() {
        return discoverTimes;
    }

    public Material getMaterial() {
        return material;
    }

    public boolean isDiscovered() {
        return discovered;
    }
}
