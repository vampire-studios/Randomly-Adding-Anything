package fr.arthurbambou.randomlyaddinganything.materials;

import fr.arthurbambou.randomlyaddinganything.api.enums.GeneratesIn;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import fr.arthurbambou.randomlyaddinganything.api.enums.TextureType;
import fr.arthurbambou.randomlyaddinganything.utils.Rands;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.Map;

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
    private int minXPAmount = 0;
    private int maxXPAmount = 10;
    private Map<TextureType, Identifier> textureMap;

    public static MaterialBuilder create() {
        MaterialBuilder materialBuilder = new MaterialBuilder();
        materialBuilder.oreCount = Rands.randInt(19) + 1;
        materialBuilder.textureMap = new HashMap<>();
        return materialBuilder;
    }

    public MaterialBuilder oreType(OreTypes oreType) {
        this.oreType = oreType;
        if (this.oreType == OreTypes.METAL) this.textureMap.put(TextureType.NUGGET, Rands.list(OreTypes.METAL_NUGGET_TEXTURES));
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
            this.textureMap.put(TextureType.ORE_OVERLAY, Rands.list(OreTypes.METAL_ORE_TEXTURES));
        } else if (oreType == OreTypes.GEM) {
            this.textureMap.put(TextureType.ORE_OVERLAY, Rands.list(OreTypes.GEM_ORE_TEXTURES));
        } else {
            this.textureMap.put(TextureType.ORE_OVERLAY, Rands.list(OreTypes.CRYSTAL_ORE_TEXTURES));
        }
        return this;
    }

    public MaterialBuilder storageBlockTexture() {
        if (oreType == OreTypes.METAL) {
            this.textureMap.put(TextureType.STORAGE_BLOCK, Rands.list(OreTypes.METAL_BLOCK_TEXTURES));
        } else if (oreType == OreTypes.GEM) {
            this.textureMap.put(TextureType.STORAGE_BLOCK, Rands.list(OreTypes.GEM_BLOCK_TEXTURES));
        } else {
            this.textureMap.put(TextureType.STORAGE_BLOCK, Rands.list(OreTypes.CRYSTAL_BLOCK_TEXTURES));
        }
        return this;
    }

    public MaterialBuilder resourceItemTexture() {
        if (oreType == OreTypes.METAL) {
            this.textureMap.put(TextureType.RESOURCE_ITEM, Rands.list(OreTypes.METAL_ITEM_TEXTURES));
        } else if (oreType == OreTypes.GEM) {
            this.textureMap.put(TextureType.RESOURCE_ITEM, Rands.list(OreTypes.GEM_ITEM_TEXTURES));
        } else {
            this.textureMap.put(TextureType.RESOURCE_ITEM, Rands.list(OreTypes.CRYSTAL_ITEM_TEXTURES));
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

    public MaterialBuilder minXPAmount(int minXPAmount) {
        this.minXPAmount = minXPAmount;
        return this;
    }

    public MaterialBuilder maxXPAmount(int maxXPAmount) {
        this.maxXPAmount = maxXPAmount;
        return this;
    }

    public Material build() {
        return oreType == OreTypes.METAL ?
                new Material(new OreInformation(oreType, generateIn, oreCount, minXPAmount, maxXPAmount), name, RGB, textureMap, armor, tools, weapons, glowing, oreFlower)
                : new Material(new OreInformation(oreType, generateIn, oreCount, minXPAmount, maxXPAmount), name, RGB, textureMap, armor, tools, weapons, glowing, oreFlower);
    }

}
