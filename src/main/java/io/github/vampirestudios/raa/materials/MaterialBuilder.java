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
    private boolean armor;
    private boolean tools;
    private boolean weapons;
    private boolean glowing;
    private boolean oreFlower;
    private boolean food;
    private int minXPAmount = 0;
    private int maxXPAmount = 10;
    private int oreClusterSize = 9;

    public static MaterialBuilder create() {
        MaterialBuilder materialBuilder = new MaterialBuilder();
        materialBuilder.oreCount = Rands.randInt(19) + 1;
        return materialBuilder;
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

    public MaterialBuilder armor(boolean armor) {
        this.armor = armor;
        return this;
    }

    public MaterialBuilder tools(boolean tools) {
        this.tools = tools;
        return this;
    }

    public MaterialBuilder weapons(boolean weapons) {
        this.weapons = weapons;
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
                new Material(new OreInformation(oreType, generateIn, overlayTexture, oreCount, minXPAmount, maxXPAmount, oreClusterSize), name, RGB, Rands.randInt(4), storageBlockTexture, resourceItemTexture, Rands.list(OreTypes.METAL_NUGGET_TEXTURES), armor, tools, weapons, glowing, oreFlower, food)
                : new Material(new OreInformation(oreType, generateIn, overlayTexture, oreCount, minXPAmount, maxXPAmount, oreClusterSize), name, RGB, Rands.randInt(4), storageBlockTexture, resourceItemTexture, armor, tools, weapons, glowing, oreFlower, food);
    }

}
