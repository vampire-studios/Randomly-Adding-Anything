package io.github.vampirestudios.raa.materials;

import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.api.enums.OreTypes;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.Identifier;

public class MaterialBuilder {

    private OreTypes oreType;
    private String name;
    private int RGB;
    private GeneratesIn generateIn;
    private int oreCount;
    private Identifier overlayTexture;
    private Identifier resourceItemTexture;
    private Identifier storageBlockTexture;
    private Identifier nuggetTexture;
    private CustomArmorMaterial armorMaterial;
    private CustomToolMaterial toolMaterial;
    private boolean armor;
    private boolean tools;
    private boolean weapons;
    private boolean glowing;
    private boolean oreFlower;
    private boolean food;
    private int minXPAmount = 0;
    private int maxXPAmount = 10;
    private int oreClusterSize = 9;
    private int miningLevel;

    public static MaterialBuilder create() {
        MaterialBuilder materialBuilder = new MaterialBuilder();
        materialBuilder.oreCount = Rands.randInt(19) + 1;
        materialBuilder.miningLevel = Rands.randInt(4);
        return materialBuilder;
    }

    public MaterialBuilder oreCount(int oreCount) {
        this.oreCount = oreCount;
        return this;
    }

    public MaterialBuilder miningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
        return this;
    }

    public MaterialBuilder oreType(OreTypes oreType) {
        this.oreType = oreType;
        return this;
    }

    public MaterialBuilder name(String name) {
        this.name = name;
        return this;
    }

    public MaterialBuilder color(int RGB) {
        this.RGB = RGB;
        return this;
    }

    public MaterialBuilder generatesIn(GeneratesIn generateIn) {
        this.generateIn = generateIn;
        return this;
    }

    public MaterialBuilder overlayTexture() {
        if (oreType == OreTypes.METAL) {
            this.overlayTexture = Rands.list(OreTypes.METAL_ORE_TEXTURES);
        } else if (oreType == OreTypes.GEM) {
            this.overlayTexture = Rands.list(OreTypes.GEM_ORE_TEXTURES);
        } else {
            this.overlayTexture = Rands.list(OreTypes.CRYSTAL_ORE_TEXTURES);
        }
        return this;
    }

    public MaterialBuilder overlayTexture(Identifier identifier) {
        this.overlayTexture = identifier;
        return this;
    }

    public MaterialBuilder nuggetTexture(Identifier identifier) {
        this.nuggetTexture = identifier;
        return this;
    }

    public MaterialBuilder storageBlockTexture() {
        if (oreType == OreTypes.METAL) {
            this.storageBlockTexture = Rands.list(OreTypes.METAL_BLOCK_TEXTURES);
        } else if (oreType == OreTypes.GEM) {
            this.storageBlockTexture = Rands.list(OreTypes.GEM_BLOCK_TEXTURES);
        } else {
            this.storageBlockTexture = Rands.list(OreTypes.CRYSTAL_BLOCK_TEXTURES);
        }
        return this;
    }

    public MaterialBuilder storageBlockTexture(Identifier identifier) {
        this.storageBlockTexture = identifier;
        return this;
    }

    public MaterialBuilder resourceItemTexture() {
        if (oreType == OreTypes.METAL) {
            this.resourceItemTexture = Rands.list(OreTypes.METAL_ITEM_TEXTURES);
        } else if (oreType == OreTypes.GEM) {
            this.resourceItemTexture = Rands.list(OreTypes.GEM_ITEM_TEXTURES);
        } else {
            this.resourceItemTexture = Rands.list(OreTypes.CRYSTAL_ITEM_TEXTURES);
        }
        return this;
    }

    public MaterialBuilder resourceItemTexture(Identifier identifier) {
        this.resourceItemTexture = identifier;
        return this;
    }

    public MaterialBuilder armor(boolean armor) {
        this.armor = armor;
        return this;
    }

    public MaterialBuilder armor(CustomArmorMaterial armorMaterial) {
        this.armor = true;
        this.armorMaterial = armorMaterial;
        return this;
    }

    public MaterialBuilder armor() {
        this.armor = true;
        this.armorMaterial = new CustomArmorMaterial(
                this.name, this.oreType, Rands.randIntRange(2,50),
                new int[]{Rands.randIntRange(1,6),Rands.randIntRange(1,10),
                        Rands.randIntRange(2,12), Rands.randIntRange(1,6)},
                Rands.randIntRange(7,30),
                (Rands.chance(4)?Rands.randFloat(4.0F):0.0F),
                Rands.randInt(30)
        );
        return this;
    }

    public MaterialBuilder tools(boolean tools) {
        this.tools = tools;
        return this;
    }

    public MaterialBuilder tools() {
        this.tools = true;
        this.toolMaterial = new CustomToolMaterial(this.name, this.oreType,
                Rands.randIntRange(15,2000), Rands.randFloat(4.0F)+1.5F,
                Rands.randFloat(3.0F), this.miningLevel,
                Rands.randIntRange(2,10), Rands.randFloat(4.0F),
                Rands.randFloat(3.0F), Rands.randFloat(0.8F),
                Rands.randFloat(5.0F));
        return this;
    }

    public MaterialBuilder weapons(boolean weapons) {
        this.weapons = weapons;
        return this;
    }

    public MaterialBuilder weapons() {
        this.weapons = true;
        this.toolMaterial = new CustomToolMaterial(this.name, this.oreType,
                Rands.randIntRange(15,2000), Rands.randFloat(4.0F)+1.5F,
                Rands.randFloat(3.0F), this.miningLevel,
                Rands.randIntRange(2,10), Rands.randFloat(4.0F),
                Rands.randFloat(3.0F), Rands.randFloat(0.8F),
                Rands.randFloat(5.0F));
        return this;
    }

    public MaterialBuilder glowing(boolean glowing) {
        this.glowing = glowing;
        return this;
    }

    public MaterialBuilder oreFlower(boolean oreFlower) {
        this.oreFlower = oreFlower;
        return this;
    }
    
    public MaterialBuilder food(boolean food) {
        this.food = food;
        return this;
    }

    public MaterialBuilder minXPAmount(int minXPAmount) {
        this.minXPAmount = minXPAmount;
        return this;
    }

    public MaterialBuilder maxXPAmount(int maxXPAmount) {
        this.maxXPAmount = maxXPAmount;
        return this;
    }

    public MaterialBuilder oreClusterSize(int oreClusterSize) {
        this.oreClusterSize = oreClusterSize;
        return this;
    }

    public Material build() {
        return oreType == OreTypes.METAL ?
                new Material(new OreInformation(oreType, generateIn, overlayTexture, oreCount, minXPAmount, maxXPAmount, oreClusterSize), name, RGB, this.miningLevel, storageBlockTexture, resourceItemTexture, Rands.list(OreTypes.METAL_NUGGET_TEXTURES), armor, tools, weapons, glowing, oreFlower, food)
                : new Material(new OreInformation(oreType, generateIn, overlayTexture, oreCount, minXPAmount, maxXPAmount, oreClusterSize), name, RGB, this.miningLevel, storageBlockTexture, resourceItemTexture, armor, tools, weapons, glowing, oreFlower, food);
    }

    public Material buildFromJSON() {
        return oreType == OreTypes.METAL ?
                new Material(new OreInformation(oreType, generateIn, overlayTexture, oreCount, minXPAmount, maxXPAmount, oreClusterSize),
                name, RGB, miningLevel, storageBlockTexture, resourceItemTexture, nuggetTexture, armor, armorMaterial, tools, weapons, toolMaterial, glowing, oreFlower, food) :
                new Material(new OreInformation(oreType, generateIn, overlayTexture, oreCount, minXPAmount, maxXPAmount, oreClusterSize),
                        name, RGB, miningLevel, storageBlockTexture, resourceItemTexture, armor, armorMaterial, tools, weapons, toolMaterial, glowing, oreFlower, food);
    }

}
