package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.world.gen.feature.OreFeatureConfig;

public class OreInformation {

    private OreType oreType;
    private OreFeatureConfig.Target generatesIn;
    private int oreCount;
    private int minXPAmount;
    private int maxXPAmount;
    private int oreClusterSize;

    public OreInformation(OreType oreType, OreFeatureConfig.Target generatesIn, int oreCount, int minXPAmount, int maxXPAmount, int oreClusterSize) {
        this.oreType = oreType;
        this.generatesIn = generatesIn;
        this.oreCount = oreCount;
        this.minXPAmount = minXPAmount;
        this.maxXPAmount = maxXPAmount;
        this.oreClusterSize = oreClusterSize;
    }

    public OreType getOreType() {
        return oreType;
    }

    public void setOreType(OreType oreType) {
        this.oreType = oreType;
    }

    public OreFeatureConfig.Target getGeneratesIn() {
        return generatesIn;
    }

    public void setGeneratesIn(OreFeatureConfig.Target generatesIn) {
        this.generatesIn = generatesIn;
    }

    public int getOreCount() {
        return oreCount;
    }

    public void setOreCount(int oreCount) {
        this.oreCount = oreCount;
    }

    public int getMinXPAmount() {
        return minXPAmount;
    }

    public void setMinXPAmount(int minXPAmount) {
        this.minXPAmount = minXPAmount;
    }

    public int getMaxXPAmount() {
        return maxXPAmount;
    }

    public void setMaxXPAmount(int maxXPAmount) {
        this.maxXPAmount = maxXPAmount;
    }

    public int getOreClusterSize() {
        return oreClusterSize;
    }

    public void setOreClusterSize(int oreClusterSize) {
        this.oreClusterSize = oreClusterSize;
    }

}