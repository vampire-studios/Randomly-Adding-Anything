package fr.arthurbambou.randomlyaddinganything.materials;

import fr.arthurbambou.randomlyaddinganything.utils.Rands;
import net.minecraft.util.Identifier;

public class Material {
    private OreInformation oreInformation;
    private String name;
    private int color;
    private Identifier storageBlockTexture;
    private Identifier resourceItemTexture;
    private Identifier nuggetTexture;
    private boolean armor;
    private CustomArmorMaterial armorMaterial;
    private boolean tools;
    private boolean weapons;
    private CustomToolMaterial toolMaterial;
    private boolean glowing;
    private boolean oreFlower;

    public Material(OreInformation oreInformation, String name, int color, Identifier storageBlockTexture, Identifier resourceItemTexture,
                    boolean armor, boolean tools, boolean weapons, boolean glowing, boolean oreFlower) {
        this.oreInformation = oreInformation;
        this.name = name;
        this.color = color;
        this.storageBlockTexture = storageBlockTexture;
        this.resourceItemTexture = resourceItemTexture;
        this.nuggetTexture = null;
        this.armor = armor;
        this.tools = tools;
        this.weapons = weapons;
        this.glowing = glowing;
        this.oreFlower = oreFlower;
        this.armorMaterial = null;
        this.toolMaterial = null;

        if (this.tools || this.weapons) this.toolMaterial = new CustomToolMaterial(this.name, this.oreInformation.getOreType(),
                Rands.randInt(1562), Rands.randFloat(12.0F), Rands.randFloat(3.0F),
                Rands.randInt(4), Rands.randInt(23), Rands.randFloat(3.0F),
                Rands.randFloat(2.0F), Rands.randFloat(0.2F), Rands.randInt(6), Rands.randFloat(4.0F));

        if (this.armor) this.armorMaterial = new CustomArmorMaterial(
                this.name, this.oreInformation.getOreType(), Rands.randInt(34),
                new int[]{Rands.randInt(4),Rands.randInt(7),
                        Rands.randInt(9), Rands.randInt(4)},
                Rands.randInt(26),
                Rands.randFloat(2.0F),
                Rands.randInt(30)
        );
    }

    public Material(OreInformation oreInformation, String name, int color, Identifier storageBlockTexture, Identifier resourceItemTexture, Identifier nuggetTexture,
                    boolean armor, boolean tools, boolean weapons, boolean glowing, boolean oreFlower) {
        this.oreInformation = oreInformation;
        this.name = name;
        this.color = color;
        this.storageBlockTexture = storageBlockTexture;
        this.resourceItemTexture = resourceItemTexture;
        this.nuggetTexture = nuggetTexture;
        this.armor = armor;
        this.tools = tools;
        this.weapons = weapons;
        this.glowing = glowing;
        this.oreFlower = oreFlower;
        this.armorMaterial = null;
        this.toolMaterial = null;

        if (this.tools || this.weapons) this.toolMaterial = new CustomToolMaterial(this.name, this.oreInformation.getOreType(),
                Rands.randInt(1562), Rands.randFloat(12.0F), Rands.randFloat(3.0F),
                Rands.randInt(4), Rands.randInt(23), Rands.randFloat(3.0F),
                Rands.randFloat(2.0F), Rands.randFloat(0.2F), Rands.randInt(6), Rands.randFloat(4.0F));

        if (this.armor) this.armorMaterial = new CustomArmorMaterial(
                this.name, this.oreInformation.getOreType(), Rands.randInt(34),
                new int[]{Rands.randInt(4),Rands.randInt(7),
                        Rands.randInt(9), Rands.randInt(4)},
                Rands.randInt(26),
                Rands.randFloat(2.0F),
                Rands.randInt(30)
        );
    }

    public Material(OreInformation oreInformation, String name, int color, Identifier storageBlockTexture, Identifier resourceItemTexture,
                    boolean armor, CustomArmorMaterial armorMaterial, boolean tools, boolean weapons, CustomToolMaterial toolMaterial, boolean glowing, boolean oreFlower) {
        this.oreInformation = oreInformation;
        this.name = name;
        this.color = color;
        this.storageBlockTexture = storageBlockTexture;
        this.resourceItemTexture = resourceItemTexture;
        this.nuggetTexture = null;
        this.armor = armor;
        this.armorMaterial = armorMaterial;
        this.tools = tools;
        this.weapons = weapons;
        this.toolMaterial = toolMaterial;
        this.glowing = glowing;
        this.oreFlower = oreFlower;
    }

    public Material(OreInformation oreInformation, String name, int color, Identifier storageBlockTexture, Identifier resourceItemTexture, Identifier nuggetTexture,
                    boolean armor, CustomArmorMaterial armorMaterial, boolean tools, boolean weapons, CustomToolMaterial toolMaterial, boolean glowing, boolean oreFlower) {
        this.oreInformation = oreInformation;
        this.name = name;
        this.color = color;
        this.storageBlockTexture = storageBlockTexture;
        this.resourceItemTexture = resourceItemTexture;
        this.nuggetTexture = nuggetTexture;
        this.armor = armor;
        this.armorMaterial = armorMaterial;
        this.tools = tools;
        this.weapons = weapons;
        this.toolMaterial = toolMaterial;
        this.glowing = glowing;
        this.oreFlower = oreFlower;
    }

    public OreInformation getOreInformation() {
        return oreInformation;
    }

    public String getName() {
        return name;
    }

    public int getRGBColor() {
        return color;
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

    public boolean hasOreFlower() {
        return oreFlower;
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