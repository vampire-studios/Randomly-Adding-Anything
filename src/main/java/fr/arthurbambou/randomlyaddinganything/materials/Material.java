package fr.arthurbambou.randomlyaddinganything.materials;

import net.minecraft.util.Identifier;

public class Material {
    private OreInformation oreInformation;
    private String name;
    private int RGB;
    private Identifier storageBlockTexture;
    private Identifier resourceItemTexture;
    private Identifier ingotTexture;
    private Identifier nuggetTexture;
    private Identifier gemTexture;
    private boolean armor;
    private boolean tools;
    private boolean weapons;
    private boolean glowing;

    public Material(OreInformation oreInformation, String name, int RGB, Identifier storageBlockTexture, Identifier resourceItemTexture,
                    boolean armor, boolean tools, boolean weapons, boolean glowing) {
        this.oreInformation = oreInformation;
        this.name = name;
        this.RGB = RGB;
        this.storageBlockTexture = storageBlockTexture;
        this.resourceItemTexture = resourceItemTexture;
        this.nuggetTexture = null;
        this.armor = armor;
        this.tools = tools;
        this.weapons = weapons;
        this.glowing = glowing;
    }

    public Material(OreInformation oreInformation, String name, int RGB, Identifier storageBlockTexture, Identifier resourceItemTexture, Identifier nuggetTexture,
                    boolean armor, boolean tools, boolean weapons, boolean glowing) {
        this.oreInformation = oreInformation;
        this.name = name;
        this.RGB = RGB;
        this.storageBlockTexture = storageBlockTexture;
        this.resourceItemTexture = resourceItemTexture;
        this.nuggetTexture = nuggetTexture;
        this.armor = armor;
        this.tools = tools;
        this.weapons = weapons;
        this.glowing = glowing;
    }

    public OreInformation getOreInformation() {
        return oreInformation;
    }

    public String getName() {
        return name;
    }

    public int getRGBColor() {
        return RGB;
    }

    public Identifier getResourceItemTexture() {
        return resourceItemTexture;
    }

    public Identifier getStorageBlockTexture() {
        return storageBlockTexture;
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

    public Identifier getNuggetTexture() {
        return nuggetTexture;
    }
}