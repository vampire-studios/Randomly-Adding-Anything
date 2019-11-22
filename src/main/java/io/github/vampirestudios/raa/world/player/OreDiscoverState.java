package io.github.vampirestudios.raa.world.player;

import io.github.vampirestudios.raa.generation.materials.Material;

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

    public OreDiscoverState discover() {
        this.setDiscovered(true);
        this.discoverTimes++;
        return this;
    }

    public OreDiscoverState alreadyDiscovered() {
        this.discoverTimes++;
        return this;
    }

    public int getDiscoverTimes() {
        return discoverTimes;
    }

    public void setDiscoverTimes(int discoverTimes) {
        this.discoverTimes = discoverTimes;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public boolean isDiscovered() {
        return discovered;
    }

    public void setDiscovered(boolean discovered) {
        this.discovered = discovered;
    }
}
