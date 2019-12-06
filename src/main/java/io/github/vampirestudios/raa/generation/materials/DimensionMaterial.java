package io.github.vampirestudios.raa.generation.materials;

import io.github.vampirestudios.raa.api.enums.GeneratesIn;
import io.github.vampirestudios.raa.api.enums.OreType;
import io.github.vampirestudios.raa.api.enums.TextureTypes;
import io.github.vampirestudios.raa.generation.dimensions.DimensionData;
import io.github.vampirestudios.raa.utils.Rands;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;

import java.util.Map;

public class DimensionMaterial extends Material {

    private DimensionData dimensionData;

    DimensionMaterial(OreInformation oreInformation, Identifier id, String name, MaterialTexturesInformation texturesInformation, int color, int miningLevel, boolean armor,
                      boolean tools, boolean weapons, boolean glowing, boolean oreFlower, boolean food, DimensionData dimensionData) {
        super(oreInformation, id, name, texturesInformation, color, miningLevel, armor, tools, weapons, glowing, oreFlower, food);
        this.dimensionData = dimensionData;
    }

    DimensionMaterial(OreInformation oreInformation, Identifier id, String name, MaterialTexturesInformation texturesInformation, int color, int miningLevel, boolean armor,
                              CustomArmorMaterial armorMaterial, boolean tools, boolean weapons, CustomToolMaterial toolMaterial, boolean glowing, boolean oreFlower, boolean food,
                      DimensionData dimensionData) {
        super(oreInformation, id, name, texturesInformation, color, miningLevel, armor, armorMaterial, tools, weapons, toolMaterial, glowing, oreFlower, food);
        this.dimensionData = dimensionData;
    }

    public DimensionData getDimensionData() {
        return dimensionData;
    }

    public static class Builder {

        private OreType oreType;
        private Identifier id;
        private String name;
        private int RGB = -1;
        private GeneratesIn generatesIn;
        private int oreCount;
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
        private DimensionData dimensionData;

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

        public Builder dimensionData(DimensionData dimensionData) {
            this.dimensionData = dimensionData;
            return this;
        }

        public DimensionMaterial build() {
            if (id == null || name == null) {
                throw new IllegalStateException("A Material must not have a null name or identifier");
            }

            if (armor && armorMaterial == null) {
                this.armorMaterial = CustomArmorMaterial.generate(id, oreType);
            }
            if ((tools || weapons) && toolMaterial == null) {
                this.toolMaterial = CustomToolMaterial.generate(id, oreType, miningLevel);
            }

            Identifier overlayTexture;
            if (oreType == OreType.METAL) overlayTexture = Rands.list(TextureTypes.METAL_ORE_TEXTURES);
            else if (oreType == OreType.GEM) overlayTexture = Rands.list(TextureTypes.GEM_ORE_TEXTURES);
            else overlayTexture = Rands.list(TextureTypes.CRYSTAL_ORE_TEXTURES);

            Identifier storageBlockTexture;
            if (oreType == OreType.METAL) storageBlockTexture = Rands.list(TextureTypes.METAL_BLOCK_TEXTURES);
            else if (oreType == OreType.GEM) storageBlockTexture = Rands.list(TextureTypes.GEM_BLOCK_TEXTURES);
            else storageBlockTexture = Rands.list(TextureTypes.CRYSTAL_BLOCK_TEXTURES);

            Identifier resourceItemTexture;
            if (oreType == OreType.METAL) resourceItemTexture = Rands.list(TextureTypes.INGOT_TEXTURES);
            else if (oreType == OreType.GEM) resourceItemTexture = Rands.list(TextureTypes.GEM_ITEM_TEXTURES);
            else resourceItemTexture = Rands.list(TextureTypes.CRYSTAL_ITEM_TEXTURES);

            Identifier nuggetTexture;
            if (oreType == OreType.METAL) nuggetTexture = Rands.list(TextureTypes.INGOT_TEXTURES);
            else nuggetTexture = null;

            Map.Entry<Identifier, Identifier> pickaxe = Rands.map(TextureTypes.PICKAXES);
            Map.Entry<Identifier, Identifier> axe = Rands.map(TextureTypes.AXES);
            Map.Entry<Identifier, Identifier> hoe = Rands.map(TextureTypes.HOES);
            Map.Entry<Identifier, Identifier> sword = Rands.map(TextureTypes.SWORDS);
            Map.Entry<Identifier, Identifier> shovel = Rands.map(TextureTypes.SHOVELS);

            MaterialTexturesInformation texturesInformation = MaterialTexturesInformation.Builder.create()
                    .pickaxeTexture(new Pair<>(pickaxe.getKey(), pickaxe.getValue()))
                    .axeTexture(new Pair<>(axe.getKey(), axe.getValue()))
                    .hoeTexture(new Pair<>(hoe.getKey(), hoe.getValue()))
                    .swordTexture(new Pair<>(sword.getKey(), sword.getValue()))
                    .shovelTexture(new Pair<>(shovel.getKey(), shovel.getValue()))
                    .helmetTexture(Rands.list(TextureTypes.HELMET_TEXTURES))
                    .chestplateTexture(Rands.list(TextureTypes.CHESTPLATE_TEXTURES))
                    .leggingsTexture(Rands.list(TextureTypes.LEGGINGS_TEXTURES))
                    .bootsTexture(Rands.list(TextureTypes.BOOTS_TEXTURES))
                    .overlayTexture(overlayTexture)
                    .storageBlockTexture(storageBlockTexture)
                    .resourceItemTexture(resourceItemTexture)
                    .nuggetTexture(nuggetTexture)
                    .fruitTexture(Rands.list(TextureTypes.FRUIT_TEXTURES))
                    .build();

            OreInformation oreInformation = new OreInformation(oreType, generatesIn, oreCount, minXPAmount, maxXPAmount, oreClusterSize);

            return new DimensionMaterial(oreInformation, id, name, texturesInformation, RGB, miningLevel, armor, armorMaterial, tools, weapons, toolMaterial, glowing, oreFlower, food, dimensionData);
        }
    }

}