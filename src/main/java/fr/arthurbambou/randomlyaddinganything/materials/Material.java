package fr.arthurbambou.randomlyaddinganything.materials;

import fr.arthurbambou.randomlyaddinganything.helpers.Rands;
import net.minecraft.sound.SoundEvents;
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
    private CustomArmorMaterial armorMaterial;
    private boolean tools;
    private boolean weapons;
    private CustomToolMaterial toolMaterial;
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
        this.armorMaterial = null;
        this.toolMaterial = null;

        if (this.tools || this.weapons) this.toolMaterial = new CustomToolMaterial(this.name, this.oreInformation.getOreType(),
                Rands.randInt(1562), Rands.randFloat(12.0F), Rands.randFloat(3.0F),
                Rands.randInt(4), Rands.randInt(23), Rands.randFloat(3.0F),
                Rands.randFloat(2.0F), Rands.randFloat(0.2F));

        if (this.armor) this.armorMaterial = new CustomArmorMaterial(
                this.name, this.oreInformation.getOreType(), Rands.randInt(34),
                new int[]{Rands.randInt(4),Rands.randInt(7),
                        Rands.randInt(9), Rands.randInt(4)},
                Rands.randInt(26),
                Rands.randFloat(2.0F)
        );
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
        this.armorMaterial = null;
        this.toolMaterial = null;

        if (this.tools || this.weapons) this.toolMaterial = new CustomToolMaterial(this.name, this.oreInformation.getOreType(),
                Rands.randInt(1562), Rands.randFloat(12.0F), Rands.randFloat(3.0F),
                Rands.randInt(4), Rands.randInt(23), Rands.randFloat(3.0F),
                Rands.randFloat(2.0F), Rands.randFloat(0.2F));

        if (this.armor) this.armorMaterial = new CustomArmorMaterial(
                this.name, this.oreInformation.getOreType(), Rands.randInt(34),
                new int[]{Rands.randInt(4),Rands.randInt(7),
                        Rands.randInt(9), Rands.randInt(4)},
                Rands.randInt(26),
                Rands.randFloat(2.0F)
        );
    }

    public Material(OreInformation oreInformation, String name, int RGB, Identifier storageBlockTexture, Identifier resourceItemTexture,
                    boolean armor,CustomArmorMaterial armorMaterial, boolean tools, boolean weapons, CustomToolMaterial toolMaterial, boolean glowing) {
        this.oreInformation = oreInformation;
        this.name = name;
        this.RGB = RGB;
        this.storageBlockTexture = storageBlockTexture;
        this.resourceItemTexture = resourceItemTexture;
        this.nuggetTexture = null;
        this.armor = armor;
        this.armorMaterial = armorMaterial;
        this.tools = tools;
        this.weapons = weapons;
        this.toolMaterial = toolMaterial;
        this.glowing = glowing;
    }

    public Material(OreInformation oreInformation, String name, int RGB, Identifier storageBlockTexture, Identifier resourceItemTexture, Identifier nuggetTexture,
                    boolean armor, CustomArmorMaterial armorMaterial, boolean tools, boolean weapons, CustomToolMaterial toolMaterial, boolean glowing) {
        this.oreInformation = oreInformation;
        this.name = name;
        this.RGB = RGB;
        this.storageBlockTexture = storageBlockTexture;
        this.resourceItemTexture = resourceItemTexture;
        this.nuggetTexture = nuggetTexture;
        this.armor = armor;
        this.armorMaterial = armorMaterial;
        this.tools = tools;
        this.weapons = weapons;
        this.toolMaterial = toolMaterial;
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

    public CustomToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public CustomArmorMaterial getArmorMaterial() {
        return armorMaterial;
    }
}