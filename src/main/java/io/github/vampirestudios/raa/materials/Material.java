package io.github.vampirestudios.raa.materials;

import io.github.vampirestudios.raa.utils.Rands;
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
    private boolean food;

    public Material(OreInformation oreInformation, String name, int color, Identifier storageBlockTexture, Identifier resourceItemTexture,
                    boolean armor, boolean tools, boolean weapons, boolean glowing, boolean oreFlower, boolean food) {
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
        this.food = food;
        this.armorMaterial = null;
        this.toolMaterial = null;

        if (this.tools || this.weapons) this.toolMaterial = new CustomToolMaterial(this.name, this.oreInformation.getOreType(),
                Rands.randIntRange(15,2000), Rands.randFloat(12.0F)+1.5F,
                Rands.randFloat(5.0F), Rands.randInt(4),
                Rands.randIntRange(2,30), Rands.randFloat(4.0F),
                Rands.randFloat(3.0F), Rands.randFloat(0.8F),
                Rands.randFloat(5.0F));

        if (this.armor) this.armorMaterial = new CustomArmorMaterial(
                this.name, this.oreInformation.getOreType(), Rands.randIntRange(2,50),
                new int[]{Rands.randIntRange(1,6),Rands.randIntRange(1,10),
                        Rands.randIntRange(2,12), Rands.randIntRange(1,6)},
                Rands.randIntRange(7,30),
                (Rands.chance(4)?Rands.randFloat(4.0F):0.0F),
                Rands.randInt(30)
        );
    }

    public Material(OreInformation oreInformation, String name, int color, Identifier storageBlockTexture, Identifier resourceItemTexture, Identifier nuggetTexture,
                    boolean armor, boolean tools, boolean weapons, boolean glowing, boolean oreFlower, boolean food) {
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
        this.food = food;
        this.armorMaterial = null;
        this.toolMaterial = null;

        if (this.tools || this.weapons) this.toolMaterial = new CustomToolMaterial(this.name, this.oreInformation.getOreType(),
                Rands.randIntRange(15,2000), Rands.randFloat(12.0F)+1.5F,
                Rands.randFloat(5.0F), Rands.randInt(4),
                Rands.randIntRange(2,30), Rands.randFloat(4.0F),
                Rands.randFloat(3.0F), Rands.randFloat(0.8F),
                Rands.randFloat(5.0F));

        if (this.armor) this.armorMaterial = new CustomArmorMaterial(
                this.name, this.oreInformation.getOreType(), Rands.randIntRange(2,50),
                new int[]{Rands.randIntRange(1,6),Rands.randIntRange(1,10),
                        Rands.randIntRange(2,12), Rands.randIntRange(1,6)},
                Rands.randIntRange(7,30),
                Rands.randFloat(4.0F),
                Rands.randInt(30)
        );
    }

    public Material(OreInformation oreInformation, String name, int color, Identifier storageBlockTexture, Identifier resourceItemTexture,
                    boolean armor, CustomArmorMaterial armorMaterial, boolean tools, boolean weapons, CustomToolMaterial toolMaterial, boolean glowing, boolean oreFlower, boolean food) {
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
        this.food = food;
    }

    public Material(OreInformation oreInformation, String name, int color, Identifier storageBlockTexture, Identifier resourceItemTexture, Identifier nuggetTexture,
                    boolean armor, CustomArmorMaterial armorMaterial, boolean tools, boolean weapons, CustomToolMaterial toolMaterial, boolean glowing, boolean oreFlower, boolean food) {
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
        this.food = food;
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
    
    public boolean hasFood() {
        return food;
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