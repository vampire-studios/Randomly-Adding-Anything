package fr.arthurbambou.randomlyaddinganything.materials;

import fr.arthurbambou.randomlyaddinganything.api.enums.GeneratesIn;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import fr.arthurbambou.randomlyaddinganything.api.enums.TextureType;
import net.minecraft.util.Identifier;

public class OreInformation {

    private OreTypes oreType;
    private GeneratesIn generateIn;
    private int oreCount;
    private int minXPAmount;
    private int maxXPAmount;

    public OreInformation(OreTypes oreType, GeneratesIn generateIn, int oreCount, int minXPAmount, int maxXPAmount) {
        this.oreType = oreType;
        this.generateIn = generateIn;
        this.oreCount = oreCount;
        this.minXPAmount = minXPAmount;
        this.maxXPAmount = maxXPAmount;
    }

    public OreTypes getOreType() {
        return oreType;
    }

    public GeneratesIn getGenerateIn() {
        return generateIn;
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