package fr.arthurbambou.randomlyaddinganything.materials;

import fr.arthurbambou.randomlyaddinganything.api.enums.AppearsIn;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import net.minecraft.util.Identifier;

public class Material {
    private OreTypes oreType;
    private String name;
    private int[] RGB = new int[3];
    private AppearsIn generateIn;
    private Identifier overlayTexture;
    private Identifier ressourceItemTexture;

    public Material() {}

    public OreTypes getOreType() {
        return oreType;
    }

    public void setOreType(OreTypes oreType) {
        this.oreType = oreType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRGB(int[] RGB) {
        this.RGB = RGB;
    }

    public int[] getRGB() {
        return RGB;
    }

    public AppearsIn getGenerateIn() {
        return generateIn;
    }

    public void setGenerateIn(AppearsIn generateIn) {
        this.generateIn = generateIn;
    }

    public Identifier getOverlayTexture() {
        return overlayTexture;
    }

    public void setOverlayTexture(Identifier overlayTexture) {
        this.overlayTexture = overlayTexture;
    }

    public Identifier getRessourceItemTexture() {
        return ressourceItemTexture;
    }

    public void setRessourceItemTexture(Identifier ressourceItemTexture) {
        this.ressourceItemTexture = ressourceItemTexture;
    }
}
