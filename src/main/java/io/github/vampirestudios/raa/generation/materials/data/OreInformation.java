package io.github.vampirestudios.raa.generation.materials.data;

import io.github.vampirestudios.raa.api.enums.OreType;
import net.minecraft.util.Identifier;

public class OreInformation {

    private OreType oreType;
    private Identifier targetId;
    private int oreCount;
    private int minXPAmount;
    private int maxXPAmount;
    private int oreClusterSize;

    public OreInformation(OreType oreType, Identifier targetId, int oreCount, int minXPAmount, int maxXPAmount, int oreClusterSize) {
        this.oreType = oreType;
        this.targetId = targetId;
        this.oreCount = oreCount;
        this.minXPAmount = minXPAmount;
        this.maxXPAmount = maxXPAmount;
        this.oreClusterSize = oreClusterSize;
    }

    public OreType getOreType() {
        return oreType;
    }

    public Identifier getTargetId() {
        return targetId;
    }

    public int getOreCount() {
        return oreCount;
    }

    public int getMinXPAmount() {
        return minXPAmount;
    }

    public int getMaxXPAmount() {
        return maxXPAmount;
    }

    public int getOreClusterSize() {
        return oreClusterSize;
    }

}