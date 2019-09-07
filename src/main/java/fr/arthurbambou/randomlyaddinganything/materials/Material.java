package fr.arthurbambou.randomlyaddinganything.materials;

import fr.arthurbambou.randomlyaddinganything.api.enums.AppearsIn;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import net.minecraft.util.Identifier;

public class Material {
    private OreTypes oreType;
    private String name;
    private int[] RGB;
    private AppearsIn generateIn;
    private Identifier overlayTexture;
    private Identifier resourceItemTexture;
    private boolean armor;
    private boolean tools;
    private boolean weapons;
    private boolean glowing;

    public Material(OreTypes oreType, String name, int[] RGB, AppearsIn generateIn, Identifier overlayTexture, Identifier resourceItemTexture, boolean armor, boolean tools, boolean weapons,
                    boolean glowing) {
        this.oreType = oreType;
        this.name = name;
        this.RGB = RGB;
        this.generateIn = generateIn;
        this.overlayTexture = overlayTexture;
        this.resourceItemTexture = resourceItemTexture;
        this.armor = armor;
        this.tools = tools;
        this.weapons = weapons;
        this.glowing = glowing;
    }

    public OreTypes getOreType() {
        return oreType;
    }

    public String getName() {
        return name;
    }

    public int[] getRGB() {
        return RGB;
    }

    public AppearsIn getGenerateIn() {
        return generateIn;
    }

    public Identifier getOverlayTexture() {
        return overlayTexture;
    }

    public Identifier getResourceItemTexture() {
        return resourceItemTexture;
    }

    public boolean hasArmor() {
        return armor;
    }

    public boolean hasTools() {
        return tools;
    }

    public boolean hasWeapons() {
        return weapons;
    }

    public boolean isGlowing() {
        return glowing;
    }

}