package io.github.vampirestudios.raa.generation.materials.data;

import net.minecraft.util.Identifier;

public class MaterialTexturesInformation {

    private TextureData pickaxeTexture;
    private TextureData axeTexture;
    private TextureData hoeTexture;
    private SwordTextureData swordTexture;
    private TextureData shovelTexture;

    private Identifier helmetTexture;
    private Identifier chestplateTexture;
    private Identifier leggingsTexture;
    private Identifier bootsTexture;

    private Identifier overlayTexture;
    private Identifier resourceItemTexture;
    private Identifier dustTexture;
    private Identifier smallDustTexture;
    private Identifier gearTexture;
    private Identifier plateTexture;
    private Identifier storageBlockTexture;
    private Identifier nuggetTexture;
    private Identifier fruitTexture;

    public MaterialTexturesInformation(TextureData pickaxeTexture, TextureData axeTexture, TextureData hoeTexture, SwordTextureData swordTexture,
                                       TextureData shovelTexture, Identifier helmetTexture, Identifier chestplateTexture, Identifier leggingsTexture, Identifier bootsTexture,
                                       Identifier overlayTexture, Identifier resourceItemTexture, Identifier dustTexture, Identifier smallDustTexture, Identifier gearTexture,
                                       Identifier plateTexture, Identifier storageBlockTexture, Identifier nuggetTexture, Identifier fruitTexture) {
        this.pickaxeTexture = pickaxeTexture;
        this.axeTexture = axeTexture;
        this.hoeTexture = hoeTexture;
        this.swordTexture = swordTexture;
        this.shovelTexture = shovelTexture;
        this.helmetTexture = helmetTexture;
        this.chestplateTexture = chestplateTexture;
        this.leggingsTexture = leggingsTexture;
        this.bootsTexture = bootsTexture;
        this.overlayTexture = overlayTexture;
        this.resourceItemTexture = resourceItemTexture;
        this.dustTexture = dustTexture;
        this.smallDustTexture = smallDustTexture;
        this.gearTexture = gearTexture;
        this.plateTexture = plateTexture;
        this.storageBlockTexture = storageBlockTexture;
        this.nuggetTexture = nuggetTexture;
        this.fruitTexture = fruitTexture;
    }

    public TextureData getPickaxeTexture() {
        return pickaxeTexture;
    }

    public TextureData getAxeTexture() {
        return axeTexture;
    }

    public TextureData getHoeTexture() {
        return hoeTexture;
    }

    public SwordTextureData getSwordTexture() {
        return swordTexture;
    }

    public TextureData getShovelTexture() {
        return shovelTexture;
    }

    public Identifier getHelmetTexture() {
        return helmetTexture;
    }

    public Identifier getChestplateTexture() {
        return chestplateTexture;
    }

    public Identifier getLeggingsTexture() {
        return leggingsTexture;
    }

    public Identifier getBootsTexture() {
        return bootsTexture;
    }

    public Identifier getOverlayTexture() {
        return overlayTexture;
    }

    public Identifier getResourceItemTexture() {
        return resourceItemTexture;
    }

    public Identifier getDustTexture() {
        return dustTexture;
    }

    public Identifier getSmallDustTexture() {
        return smallDustTexture;
    }

    public Identifier getGearTexture() {
        return gearTexture;
    }

    public Identifier getPlateTexture() {
        return plateTexture;
    }

    public Identifier getStorageBlockTexture() {
        return storageBlockTexture;
    }

    public Identifier getNuggetTexture() {
        return nuggetTexture;
    }

    public Identifier getFruitTexture() {
        return fruitTexture;
    }

    public static class Builder {

        private TextureData pickaxeTexture;
        private TextureData axeTexture;
        private TextureData hoeTexture;
        private SwordTextureData swordTexture;
        private TextureData shovelTexture;

        private Identifier helmetTexture;
        private Identifier chestplateTexture;
        private Identifier leggingsTexture;
        private Identifier bootsTexture;

        private Identifier overlayTexture;
        private Identifier resourceItemTexture;
        private Identifier dustTexture;
        private Identifier smallDustTexture;
        private Identifier gearTexture;
        private Identifier plateTexture;
        private Identifier storageBlockTexture;
        private Identifier nuggetTexture;
        private Identifier fruitTexture;

        public static Builder create() {
            return new Builder();
        }

        public Builder pickaxeTexture(TextureData pickaxeTexture) {
            this.pickaxeTexture = pickaxeTexture;
            return this;
        }

        public Builder axeTexture(TextureData axeTexture) {
            this.axeTexture = axeTexture;
            return this;
        }

        public Builder hoeTexture(TextureData hoeTexture) {
            this.hoeTexture = hoeTexture;
            return this;
        }

        public Builder swordTexture(SwordTextureData swordTexture) {
            this.swordTexture = swordTexture;
            return this;
        }

        public Builder shovelTexture(TextureData shovelTexture) {
            this.shovelTexture = shovelTexture;
            return this;
        }

        public Builder helmetTexture(Identifier helmetTexture) {
            this.helmetTexture = helmetTexture;
            return this;
        }

        public Builder chestplateTexture(Identifier chestplateTexture) {
            this.chestplateTexture = chestplateTexture;
            return this;
        }

        public Builder leggingsTexture(Identifier leggingsTexture) {
            this.leggingsTexture = leggingsTexture;
            return this;
        }

        public Builder bootsTexture(Identifier bootsTexture) {
            this.bootsTexture = bootsTexture;
            return this;
        }

        public Builder overlayTexture(Identifier overlayTexture) {
            this.overlayTexture = overlayTexture;
            return this;
        }

        public Builder resourceItemTexture(Identifier resourceItemTexture) {
            this.resourceItemTexture = resourceItemTexture;
            return this;
        }

        public Builder dustTexture(Identifier dustTexture) {
            this.dustTexture = dustTexture;
            return this;
        }

        public Builder smallDustTexture(Identifier smallDustTexture) {
            this.smallDustTexture = smallDustTexture;
            return this;
        }

        public Builder gearTexture(Identifier gearTexture) {
            this.gearTexture = gearTexture;
            return this;
        }

        public Builder plateTexture(Identifier plateTexture) {
            this.plateTexture = plateTexture;
            return this;
        }

        public Builder storageBlockTexture(Identifier storageBlockTexture) {
            this.storageBlockTexture = storageBlockTexture;
            return this;
        }

        public Builder nuggetTexture(Identifier nuggetTexture) {
            this.nuggetTexture = nuggetTexture;
            return this;
        }

        public Builder fruitTexture(Identifier fruitTexture) {
            this.fruitTexture = fruitTexture;
            return this;
        }

        public MaterialTexturesInformation build() {
            if (this.axeTexture == null) throw new RuntimeException("bad");
            return new MaterialTexturesInformation(pickaxeTexture, axeTexture, hoeTexture, swordTexture, shovelTexture, helmetTexture, chestplateTexture, leggingsTexture, bootsTexture,
                    overlayTexture, resourceItemTexture, dustTexture, smallDustTexture, gearTexture, plateTexture, storageBlockTexture, nuggetTexture, fruitTexture);
        }

    }

}
