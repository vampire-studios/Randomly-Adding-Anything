package io.github.vampirestudios.raa.generation.materials.data;

import java.util.ArrayList;
import java.util.List;

public class MaterialFoodData {

    private int hunger;
    private float saturationModifier;
    private boolean meat;
    private boolean alwaysEdible;
    private boolean snack;
    private List<MaterialFoodEffectData> effects = new ArrayList<>();

    public MaterialFoodData(int hunger, float saturationModifier, boolean meat, boolean alwaysEdible, boolean snack, List<MaterialFoodEffectData> effects) {
        this.hunger = hunger;
        this.saturationModifier = saturationModifier;
        this.meat = meat;
        this.alwaysEdible = alwaysEdible;
        this.snack = snack;
    }

    public int getHunger() {
        return hunger;
    }

    public float getSaturationModifier() {
        return saturationModifier;
    }

    public boolean isMeat() {
        return meat;
    }

    public boolean isAlwaysEdible() {
        return alwaysEdible;
    }

    public boolean isSnack() {
        return snack;
    }

    public List<MaterialFoodEffectData> getEffects() {
        return effects;
    }

    public static class Builder {

        private int hunger;
        private float saturationModifier;
        private boolean meat;
        private boolean alwaysEdible;
        private boolean snack;
        private List<MaterialFoodEffectData> effects;

        public static Builder create() {
            return new Builder();
        }

        public Builder hunger(int hunger) {
            this.hunger = hunger;
            return this;
        }

        public Builder saturationModifier(float saturationModifier) {
            this.saturationModifier = saturationModifier;
            return this;
        }

        public Builder meat(boolean meat) {
            this.meat = meat;
            return this;
        }

        public Builder alwaysEdible(boolean alwaysEdible) {
            this.alwaysEdible = alwaysEdible;
            return this;
        }

        public Builder snack(boolean snack) {
            this.snack = snack;
            return this;
        }

        public Builder effects(List<MaterialFoodEffectData> effects) {
            this.effects = effects;
            return this;
        }

        public MaterialFoodData build() {
            return new MaterialFoodData(hunger, saturationModifier, meat, alwaysEdible, snack, effects);
        }

    }

}