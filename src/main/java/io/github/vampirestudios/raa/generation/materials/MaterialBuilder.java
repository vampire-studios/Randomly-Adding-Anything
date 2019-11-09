package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.RandomlyAddingAnything;
import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.api.enums.OreTypes;
import io.github.vampirestudios.raa.api.enums.TextureTypes;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.Identifier;

public class MaterialBuilder {

    private OreTypes oreType;
    private Identifier id;
    private String name;
    private int RGB = -1;
    private GeneratesIn generateIn;
    private int oreCount;
    private Identifier overlayTexture;
    private Identifier resourceItemTexture;
    private Identifier storageBlockTexture;
    private Identifier nuggetTexture;
    private CustomArmorMaterial armorMaterial;
    private CustomToolMaterial toolMaterial;
    private boolean armor = false;
    private boolean tools = false;
    private boolean weapons = false;
    private boolean glowing = false;
    private boolean oreFlower = false;
    private boolean food = false;
    private int minXPAmount = 0;
    private int maxXPAmount = 10;
    private int oreClusterSize = 9;
    private int miningLevel;

    @Deprecated
    public static MaterialBuilder create() {
    	return new MaterialBuilder();
    }

    public static MaterialBuilder create(Identifier id, String name) {
        MaterialBuilder materialBuilder = new MaterialBuilder();
        materialBuilder.id = id;
        materialBuilder.name = name;
        return materialBuilder;
    }

    protected MaterialBuilder() {
        oreCount = Rands.randInt(19) + 1;
        miningLevel = Rands.randInt(4);
    }

    public MaterialBuilder oreCount(int oreCount) {
        this.oreCount = oreCount;
        return this;
    }

    public MaterialBuilder miningLevel(int miningLevel) {
        this.miningLevel = miningLevel;
        return this;
    }

    public MaterialBuilder id(Identifier id) {
        this.id = id;
        return this;
    }

    public MaterialBuilder name(String name) {
        this.name = name.toLowerCase();
        if (id == null) {
            this.id = new Identifier(RandomlyAddingAnything.MOD_ID, name.toLowerCase());
        }
        return this;
    }

    public MaterialBuilder oreType(OreTypes oreType) {
        this.oreType = oreType;
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

    public MaterialBuilder overlayTexture(Identifier identifier) {
        this.overlayTexture = identifier;
        return this;
    }

    public MaterialBuilder nuggetTexture(Identifier identifier) {
        this.nuggetTexture = identifier;
        return this;
    }

    public MaterialBuilder storageBlockTexture(Identifier identifier) {
        this.storageBlockTexture = identifier;
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

    public MaterialBuilder tools(boolean tools) {
        this.tools = tools;
        return this;
    }

    public MaterialBuilder tools(CustomToolMaterial toolMaterial) {
        this.tools = true;
        this.toolMaterial = toolMaterial;
        return this;
    }

    public MaterialBuilder weapons(boolean weapons) {
        this.weapons = weapons;
        return this;
    }

    public MaterialBuilder weapons(CustomToolMaterial toolMaterial) {
        this.weapons = true;
        this.toolMaterial = toolMaterial;
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
        if(id == null || name == null) {
            throw new IllegalStateException("A Material must not have a null name or identifier");
        }

        if(armor && armorMaterial == null) {
            this.armorMaterial = new CustomArmorMaterial(
                this.name, this.oreType, Rands.randIntRange(2,50),
                new int[]{Rands.randIntRange(1,6),Rands.randIntRange(1,10),
                    Rands.randIntRange(2,12), Rands.randIntRange(1,6)},
                Rands.randIntRange(7,30),
                (Rands.chance(4)?Rands.randFloat(4.0F):0.0F),
                Rands.randInt(30)
            );
        }
        if((tools || weapons) && toolMaterial == null) {
            this.toolMaterial = new CustomToolMaterial(this.name, this.oreType,
                Rands.randIntRange(15,2000), Rands.randFloat(4.0F)+1.5F,
                Rands.randFloat(3.0F), this.miningLevel,
                Rands.randIntRange(2,10), Rands.randFloat(4.0F),
                Rands.randFloat(3.0F), Rands.randFloat(0.8F),
                Rands.randFloat(5.0F));
        }

        if(overlayTexture == null) {
            if (oreType == OreTypes.METAL) {
                this.overlayTexture = Rands.list(TextureTypes.METAL_ORE_TEXTURES);
            } else if (oreType == OreTypes.GEM) {
                this.overlayTexture = Rands.list(TextureTypes.GEM_ORE_TEXTURES);
            } else {
                this.overlayTexture = Rands.list(TextureTypes.CRYSTAL_ORE_TEXTURES);
            }
        }
        if(storageBlockTexture == null) {
            if (oreType == OreTypes.METAL) {
                this.storageBlockTexture = Rands.list(TextureTypes.METAL_BLOCK_TEXTURES);
            } else if (oreType == OreTypes.GEM) {
                this.storageBlockTexture = Rands.list(TextureTypes.GEM_BLOCK_TEXTURES);
            } else {
                this.storageBlockTexture = Rands.list(TextureTypes.CRYSTAL_BLOCK_TEXTURES);
            }
        }
        if(resourceItemTexture == null) {
            if (oreType == OreTypes.METAL) {
                this.resourceItemTexture = Rands.list(TextureTypes.INGOT_TEXTURES);
            } else if (oreType == OreTypes.GEM) {
                this.resourceItemTexture = Rands.list(TextureTypes.GEM_ITEM_TEXTURES);
            } else {
                this.resourceItemTexture = Rands.list(TextureTypes.CRYSTAL_ITEM_TEXTURES);
            }
        }

    	OreInformation oreInformation = new OreInformation(oreType, generateIn, overlayTexture, oreCount, minXPAmount, maxXPAmount, oreClusterSize);
        if(oreType == OreTypes.METAL) {
            return new Material(oreInformation, id, name, RGB, miningLevel, storageBlockTexture, resourceItemTexture, nuggetTexture == null ? Rands.list(TextureTypes.METAL_NUGGET_TEXTURES) : nuggetTexture, armor, tools, weapons, glowing, oreFlower, food);
        } else {
            return new Material(oreInformation, id, name, RGB, miningLevel, storageBlockTexture, resourceItemTexture, nuggetTexture, armor, tools, weapons, glowing, oreFlower, food);
        }
    }
}
