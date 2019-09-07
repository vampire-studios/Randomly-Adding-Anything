package fr.arthurbambou.randomlyaddinganything.materials;

import fr.arthurbambou.randomlyaddinganything.api.enums.AppearsIn;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import fr.arthurbambou.randomlyaddinganything.helpers.Rands;
import net.minecraft.util.Identifier;

public class MaterialBuilder {

    private OreTypes oreType;
    private String name;
    private int RGB;
    private AppearsIn generateIn;
    private Identifier overlayTexture;
    private Identifier resourceItemTexture;
    private boolean armor;
    private boolean tools;
    private boolean weapons;
    private boolean glowing;
    private int minXPAmount = 0;
    private int maxXPAmount = 10;

    public static MaterialBuilder create() {
        return new MaterialBuilder();
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

    public MaterialBuilder generatesIn(AppearsIn generateIn) {
        this.generateIn = generateIn;
        return this;
    }

    public MaterialBuilder overlayTexture() {
        this.overlayTexture = oreType == OreTypes.METAL ? Rands.list(OreTypes.METAL_BLOCK_TEXTURES) : Rands.list(OreTypes.GEM_BLOCK_TEXTURES);
        return this;
    }

    public MaterialBuilder resourceItemTexture() {
        this.resourceItemTexture = oreType == OreTypes.METAL ? Rands.list(OreTypes.METAL_ITEM_TEXTURES) : Rands.list(OreTypes.GEM_ITEM_TEXTURES);
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

    public MaterialBuilder minXPAmount(int minXPAmount) {
        this.minXPAmount = minXPAmount;
        return this;
    }

    public MaterialBuilder maxXPAmount(int maxXPAmount) {
        this.maxXPAmount = maxXPAmount;
        return this;
    }

    public Material build() {
        return new Material(new OreInformation(oreType, generateIn, overlayTexture, minXPAmount, maxXPAmount), name, RGB, resourceItemTexture, armor, tools, weapons, glowing);
    }

}
