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

    public OreFeatureConfig.Target getGeneratesIn() {
        return generatesIn;
    }

    public int getMinXPAmount() {
        return minXPAmount;
    }

    public int getMaxXPAmount() {
        return maxXPAmount;
    }

    public int getOreCount() {
        return oreCount;
    }

    public int getOreClusterSize() {
        return oreClusterSize;
    }

}