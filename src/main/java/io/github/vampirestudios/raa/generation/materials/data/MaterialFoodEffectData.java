package io.github.vampirestudios.raa.generation.materials.data;

public class MaterialFoodEffectData {

    private String effectName;
    private int duration;
    private int amplifier;
    private boolean isAmbient;
    private boolean shouldShowParticles;
    private boolean shouldShowIcon;
    private float chance;

    public MaterialFoodEffectData(String effectName, int duration, int amplifier, boolean isAmbient, boolean shouldShowParticles, boolean shouldShowIcon, float chance) {
        this.effectName = effectName;
        this.duration = duration;
        this.amplifier = amplifier;
        this.isAmbient = isAmbient;
        this.shouldShowParticles = shouldShowParticles;
        this.shouldShowIcon = shouldShowIcon;
        this.chance = chance;
    }

    public String getEffectName() {
        return effectName;
    }

    public int getDuration() {
        return duration;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public boolean isAmbient() {
        return isAmbient;
    }

    public boolean isShouldShowParticles() {
        return shouldShowParticles;
    }

    public boolean isShouldShowIcon() {
        return shouldShowIcon;
    }

    public float getChance() {
        return chance;
    }

    public static class Builder {

        private String effectName;
        private int duration;
        private int amplifier;
        private boolean isAmbient;
        private boolean shouldShowParticles;
        private boolean shouldShowIcon;
        private float chance;

        public static Builder create() {
            return new Builder();
        }

        public Builder effectName(String effectName) {
            this.effectName = effectName;
            return this;
        }

        public Builder duration(int duration) {
            this.duration = duration;
            return this;
        }

        public Builder amplifier(int amplifier) {
            this.amplifier = amplifier;
            return this;
        }

        public Builder ambient(boolean ambient) {
            isAmbient = ambient;
            return this;
        }

        public Builder shouldShowParticles(boolean shouldShowParticles) {
            this.shouldShowParticles = shouldShowParticles;
            return this;
        }

        public Builder shouldShowIcon(boolean shouldShowIcon) {
            this.shouldShowIcon = shouldShowIcon;
            return this;
        }

        public Builder chance(float chance) {
            this.chance = chance;
            return this;
        }

        public MaterialFoodEffectData build() {
            return new MaterialFoodEffectData(effectName, duration, amplifier, isAmbient, shouldShowParticles, shouldShowIcon, chance);
        }

    }

}