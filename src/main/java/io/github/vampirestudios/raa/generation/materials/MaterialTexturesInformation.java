package io.github.vampirestudios.raa.generation.materials;

import net.minecraft.util.Identifier;

import java.util.Map;

public class MaterialTexturesInformation {

    private Map.Entry<Identifier, Identifier> pickaxeTexture;
    private Map.Entry<Identifier, Identifier> axeTexture;
    private Map.Entry<Identifier, Identifier> hoeTexture;
    private Map.Entry<Identifier, Identifier> swordTexture;
    private Map.Entry<Identifier, Identifier> shovelTexture;

    private Identifier helmetTexture;
    private Identifier chestplateTexture;
    private Identifier leggingsTexture;
    private Identifier bootsTexture;

    private Identifier overlayTexture;
    private Identifier resourceItemTexture;
    private Identifier storageBlockTexture;
    private Identifier nuggetTexture;
    private Identifier fruitTexture;

    public MaterialTexturesInformation(Map.Entry<Identifier, Identifier> pickaxeTexture, Map.Entry<Identifier, Identifier> axeTexture, Map.Entry<Identifier, Identifier> hoeTexture,
                                       Map.Entry<Identifier, Identifier> swordTexture, Map.Entry<Identifier, Identifier> shovelTexture, Identifier helmetTexture, Identifier chestplateTexture,
                                       Identifier leggingsTexture, Identifier bootsTexture, Identifier overlayTexture, Identifier resourceItemTexture, Identifier storageBlockTexture,
                                       Identifier nuggetTexture, Identifier fruitTexture) {
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
        this.storageBlockTexture = storageBlockTexture;
        this.nuggetTexture = nuggetTexture;
        this.fruitTexture = fruitTexture;
    }

    public Map.Entry<Identifier, Identifier> getPickaxeTexture() {
        return pickaxeTexture;
    }

    public Map.Entry<Identifier, Identifier> getAxeTexture() {
        return axeTexture;
    }

    public Map.Entry<Identifier, Identifier> getHoeTexture() {
        return hoeTexture;
    }

    public Map.Entry<Identifier, Identifier> getSwordTexture() {
        return swordTexture;
    }

    public Map.Entry<Identifier, Identifier> getShovelTexture() {
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

        private Map.Entry<Identifier, Identifier> pickaxeTexture;
        private Map.Entry<Identifier, Identifier> axeTexture;
        private Map.Entry<Identifier, Identifier> hoeTexture;
        private Map.Entry<Identifier, Identifier> swordTexture;
        private Map.Entry<Identifier, Identifier> shovelTexture;

        private Identifier helmetTexture;
        private Identifier chestplateTexture;
        private Identifier leggingsTexture;
        private Identifier bootsTexture;

        private Identifier overlayTexture;
        private Identifier resourceItemTexture;
        private Identifier storageBlockTexture;
        private Identifier nuggetTexture;
        private Identifier fruitTexture;

        public static Builder create() {
            return new Builder();
        }

        public Builder pickaxeTexture(Map.Entry<Identifier, Identifier> pickaxeTexture) {
            this.pickaxeTexture = pickaxeTexture;
            return this;
        }

        public Builder axeTexture(Map.Entry<Identifier, Identifier> axeTexture) {
            this.axeTexture = axeTexture;
            return this;
        }

        public Builder hoeTexture(Map.Entry<Identifier, Identifier> hoeTexture) {
            this.hoeTexture = hoeTexture;
            return this;
        }

        public Builder swordTexture(Map.Entry<Identifier, Identifier> swordTexture) {
            this.swordTexture = swordTexture;
            return this;
        }

        public Builder shovelTexture(Map.Entry<Identifier, Identifier> shovelTexture) {
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
            return new MaterialTexturesInformation(pickaxeTexture, axeTexture, hoeTexture, swordTexture, shovelTexture, helmetTexture, chestplateTexture, leggingsTexture, bootsTexture,
                    overlayTexture, resourceItemTexture, storageBlockTexture, nuggetTexture, fruitTexture);
        }

    }

}
