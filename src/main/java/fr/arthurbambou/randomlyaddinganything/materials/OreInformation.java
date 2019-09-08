package fr.arthurbambou.randomlyaddinganything.materials;

import fr.arthurbambou.randomlyaddinganything.api.enums.AppearsIn;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import net.minecraft.util.Identifier;

public class OreInformation {

    private OreTypes oreType;
    private AppearsIn generateIn;
    private Identifier overlayTexture;
    private int oreCount;
    private int minXPAmount;
    private int maxXPAmount;

    public OreInformation(OreTypes oreType, AppearsIn generateIn, Identifier overlayTexture, int oreCount, int minXPAmount, int maxXPAmount) {
        this.oreType = oreType;
        this.generateIn = generateIn;
        this.overlayTexture = overlayTexture;
        this.oreCount = oreCount;
        this.minXPAmount = minXPAmount;
        this.maxXPAmount = maxXPAmount;
    }

    public OreTypes getOreType() {
        return oreType;
    }

    public AppearsIn getGenerateIn() {
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
}