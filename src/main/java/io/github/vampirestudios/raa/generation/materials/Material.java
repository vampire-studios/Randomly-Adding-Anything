package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.api.enums.TextureTypes;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.Identifier;

public class Material {
    private OreInformation oreInformation;
    private Identifier id;
    private String name;
    private int color;
    private int miningLevel;
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

    private Material(OreInformation oreInformation, Identifier id, String name, int color, int miningLevel, Identifier storageBlockTexture, Identifier resourceItemTexture,
                     boolean armor, boolean tools, boolean weapons, boolean glowing, boolean oreFlower, boolean food) {
        this(oreInformation, id, name, color, miningLevel, storageBlockTexture, resourceItemTexture, null, armor, tools, weapons, glowing, oreFlower, food);
    }

    private Material(OreInformation oreInformation, Identifier id, String name, int color, int miningLevel, Identifier storageBlockTexture, Identifier resourceItemTexture, Identifier nuggetTexture,
                     boolean armor, boolean tools, boolean weapons, boolean glowing, boolean oreFlower, boolean food) {
        this(oreInformation, id, name, color, miningLevel, storageBlockTexture, resourceItemTexture, nuggetTexture, armor, null, tools, weapons, null, glowing, oreFlower, food);

        if (this.tools || this.weapons) {
            this.toolMaterial = CustomToolMaterial.generate(id, getOreInformation().getOreType(), miningLevel);
        }
        if (this.armor) {
            this.armorMaterial = CustomArmorMaterial.generate(id, getOreInformation().getOreType());
        }
    }

    private Material(OreInformation oreInformation, Identifier id, String name, int color, int miningLevel, Identifier storageBlockTexture, Identifier resourceItemTexture,
                     boolean armor, CustomArmorMaterial armorMaterial, boolean tools, boolean weapons, CustomToolMaterial toolMaterial, boolean glowing, boolean oreFlower, boolean food) {
        this(oreInformation, id, name, color, miningLevel, storageBlockTexture, null, resourceItemTexture, armor, armorMaterial, tools, weapons, toolMaterial, glowing, oreFlower, food);
    }

    private Material(OreInformation oreInformation, Identifier id, String name, int color, int miningLevel, Identifier storageBlockTexture, Identifier resourceItemTexture, Identifier nuggetTexture,
                     boolean armor, CustomArmorMaterial armorMaterial, boolean tools, boolean weapons, CustomToolMaterial toolMaterial, boolean glowing, boolean oreFlower, boolean food) {
        this.oreInformation = oreInformation;
        this.id = id;
        this.name = name;
        this.color = color;
        this.miningLevel = miningLevel;
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

    public Identifier getId() {
        return id;
    }

    public String getName() {
        return name.toLowerCase();
    }

    @Deprecated
    public void setName(String name) {
        this.name = name;
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

    public int getMiningLevel() {
        return miningLevel;
    }

    public static class Builder {

        private OreType oreType;
        private Identifier id;
        private String name;
        private int RGB = -1;
        private GeneratesIn generatesIn;
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

        protected Builder() {
            oreCount = Rands.randInt(19) + 1;
            miningLevel = Rands.randInt(4);
        }

        @Deprecated
        public static Builder create() {
            return new Builder();
        }

        public static Builder create(Identifier id, String name) {
            Builder builder = new Builder();
            builder.id = id;
            builder.name = name;
            return builder;
        }

        public Builder oreCount(int oreCount) {
            this.oreCount = oreCount;
            return this;
        }

        public Builder miningLevel(int miningLevel) {
            this.miningLevel = miningLevel;
            return this;
        }

        public Builder id(Identifier id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder oreType(OreType oreType) {
            this.oreType = oreType;
            return this;
        }

        public Builder color(int RGB) {
            this.RGB = RGB;
            return this;
        }

        public Builder generatesIn(GeneratesIn generatesIn) {
            this.generatesIn = generatesIn;
            return this;
        }

        public Builder overlayTexture(Identifier identifier) {
            this.overlayTexture = identifier;
            return this;
        }

        public Builder nuggetTexture(Identifier identifier) {
            this.nuggetTexture = identifier;
            return this;
        }

        public Builder storageBlockTexture(Identifier identifier) {
            this.storageBlockTexture = identifier;
            return this;
        }

        public Builder resourceItemTexture(Identifier identifier) {
            this.resourceItemTexture = identifier;
            return this;
        }

        public Builder armor(boolean armor) {
            this.armor = armor;
            return this;
        }

        public Builder armor(CustomArmorMaterial armorMaterial) {
            this.armor = true;
            this.armorMaterial = armorMaterial;
            return this;
        }

        public Builder tools(boolean tools) {
            this.tools = tools;
            return this;
        }

        public Builder tools(CustomToolMaterial toolMaterial) {
            this.tools = true;
            this.toolMaterial = toolMaterial;
            return this;
        }

        public Builder weapons(boolean weapons) {
            this.weapons = weapons;
            return this;
        }

        public Builder weapons(CustomToolMaterial toolMaterial) {
            this.weapons = true;
            this.toolMaterial = toolMaterial;
            return this;
        }

        public Builder glowing(boolean glowing) {
            this.glowing = glowing;
            return this;
        }

        public Builder oreFlower(boolean oreFlower) {
            this.oreFlower = oreFlower;
            return this;
        }

        public Builder food(boolean food) {
            this.food = food;
            return this;
        }

        public Builder minXPAmount(int minXPAmount) {
            this.minXPAmount = minXPAmount;
            return this;
        }

        public Builder maxXPAmount(int maxXPAmount) {
            this.maxXPAmount = maxXPAmount;
            return this;
        }

        public Builder oreClusterSize(int oreClusterSize) {
            this.oreClusterSize = oreClusterSize;
            return this;
        }

        public Material build() {
            if (id == null || name == null) {
                throw new IllegalStateException("A Material must not have a null name or identifier");
            }

            if (armor && armorMaterial == null) {
                this.armorMaterial = CustomArmorMaterial.generate(id, oreType);
            }
            if ((tools || weapons) && toolMaterial == null) {
                this.toolMaterial = CustomToolMaterial.generate(id, oreType, miningLevel);
            }

            if (overlayTexture == null) {
                if (oreType == OreType.METAL) {
                    this.overlayTexture = Rands.list(TextureTypes.METAL_ORE_TEXTURES);
                } else if (oreType == OreType.GEM) {
                    this.overlayTexture = Rands.list(TextureTypes.GEM_ORE_TEXTURES);
                } else {
                    this.overlayTexture = Rands.list(TextureTypes.CRYSTAL_ORE_TEXTURES);
                }
            }
            if (storageBlockTexture == null) {
                if (oreType == OreType.METAL) {
                    this.storageBlockTexture = Rands.list(TextureTypes.METAL_BLOCK_TEXTURES);
                } else if (oreType == OreType.GEM) {
                    this.storageBlockTexture = Rands.list(TextureTypes.GEM_BLOCK_TEXTURES);
                } else {
                    this.storageBlockTexture = Rands.list(TextureTypes.CRYSTAL_BLOCK_TEXTURES);
                }
            }
            if (resourceItemTexture == null) {
                if (oreType == OreType.METAL) {
                    this.resourceItemTexture = Rands.list(TextureTypes.INGOT_TEXTURES);
                } else if (oreType == OreType.GEM) {
                    this.resourceItemTexture = Rands.list(TextureTypes.GEM_ITEM_TEXTURES);
                } else {
                    this.resourceItemTexture = Rands.list(TextureTypes.CRYSTAL_ITEM_TEXTURES);
                }
            }

            OreInformation oreInformation = new OreInformation(oreType, generatesIn, overlayTexture, oreCount, minXPAmount, maxXPAmount, oreClusterSize);
            if (oreType == OreType.METAL) {
                return new Material(oreInformation, id, name, RGB, miningLevel, storageBlockTexture, resourceItemTexture, nuggetTexture == null ? Rands.list(TextureTypes.METAL_NUGGET_TEXTURES) : nuggetTexture, armor, tools, weapons, glowing, oreFlower, food);
            } else {
                return new Material(oreInformation, id, name, RGB, miningLevel, storageBlockTexture, resourceItemTexture, nuggetTexture, armor, tools, weapons, glowing, oreFlower, food);
            }
        }
    }

}