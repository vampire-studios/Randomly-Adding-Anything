package fr.arthurbambou.randomlyaddinganything.materials;

import fr.arthurbambou.randomlyaddinganything.api.enums.AppearsIn;
import fr.arthurbambou.randomlyaddinganything.api.enums.OreTypes;
import net.minecraft.util.Identifier;

public class MaterialBuilder {

    private OreTypes oreType;
    private String name;
    private int[] RGB = new int[3];
    private AppearsIn generateIn;
    private Identifier overlayTexture;
    private Identifier resourceItemTexture;
    private boolean armor;
    private boolean tools;
    private boolean weapons;

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

    public MaterialBuilder color(int[] RGB) {
        this.RGB = RGB;
        return this;
    }

    public MaterialBuilder generatesIn(AppearsIn generateIn) {
        this.generateIn = generateIn;
        return this;
    }

    public MaterialBuilder overlayTexture(Identifier overlayTexture) {
        this.overlayTexture = overlayTexture;
        return this;
    }

    public MaterialBuilder resourceItemTexture(Identifier resourceItemTexture) {
        this.resourceItemTexture = resourceItemTexture;
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

    public Material build() {
        return new Material(oreType, name, RGB, generateIn, overlayTexture, resourceItemTexture, armor, tools, weapons);
    }

}
