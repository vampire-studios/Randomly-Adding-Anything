package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.api.enums.OreTypes;
import net.minecraft.util.Identifier;

public class OreInformation {

    private OreTypes oreType;
    private GeneratesIn generateIn;
    private Identifier overlayTexture;
    private int oreCount;
    private int minXPAmount;
    private int maxXPAmount;
    private int oreClusterSize;

    public OreInformation(OreTypes oreType, GeneratesIn generateIn, Identifier overlayTexture, int oreCount, int minXPAmount, int maxXPAmount, int oreClusterSize) {
        this.oreType = oreType;
        this.generateIn = generateIn;
        this.overlayTexture = overlayTexture;
        this.oreCount = oreCount;
        this.minXPAmount = minXPAmount;
        this.maxXPAmount = maxXPAmount;
        this.oreClusterSize = oreClusterSize;
    }

    public OreTypes getOreType() {
        return oreType;
    }

    public GeneratesIn getGeneratesIn() {
        return generateIn;
    }

    public Identifier getOverlayTexture() {
        return overlayTexture;
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