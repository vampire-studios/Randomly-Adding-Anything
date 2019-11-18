package io.github.vampirestudios.raa.generation.dimensions;

import net.minecraft.util.Identifier;

public class DimensionBiomeData {
    private Identifier id;
    private String biomeName;
    private int surfaceBuilderVariantChance;
    private float depth;
    private float scale;
    private float temperature;
    private float downfall;
    private int waterColor;
    private int grassColor;
    private int foliageColor;

    DimensionBiomeData(Identifier id, String biomeName, int surfaceBuilderVariantChance, float depth, float scale, float temperature, float downfall, int waterColor, int grassColor, int foliageColor) {
        this.id = id;
        this.biomeName = biomeName;
        this.surfaceBuilderVariantChance = surfaceBuilderVariantChance;
        this.depth = depth;
        this.scale = scale;
        this.temperature = temperature;
        this.downfall = downfall;
        this.waterColor = waterColor;
        this.grassColor = grassColor;
        this.foliageColor = foliageColor;
    }

    public Identifier getId() {
        return id;
    }

    public void setId(Identifier id) {
        this.id = id;
    }

    public String getName() {
        return biomeName;
    }

    public void setName(String biomeName) {
        this.biomeName = biomeName;
    }

    public int getSurfaceBuilderVariantChance() {
        return surfaceBuilderVariantChance;
    }

    public void setSurfaceBuilderVariantChance(int surfaceBuilderVariantChance) {
        this.surfaceBuilderVariantChance = surfaceBuilderVariantChance;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getDownfall() {
        return downfall;
    }

    public void setDownfall(float downfall) {
        this.downfall = downfall;
    }

    public int getWaterColor() {
        return waterColor;
    }

    public void setWaterColor(int waterColor) {
        this.waterColor = waterColor;
    }

    public int getGrassColor() {
        return grassColor;
    }

    public int getFoliageColor() {
        return foliageColor;
    }

    public void setFoliageColor(int foliageColor) {
        this.foliageColor = foliageColor;
    }

    public static class Builder {
        private Identifier id;
        private String name;
        private int surfaceBuilderVariantChance;
        private float depth;
        private float scale;
        private float temperature;
        private float downfall;
        private int waterColor;
        private int grassColor;
        private int foliageColor;

        private Builder() {

        }

        public static Builder create(Identifier id, String name) {
            Builder builder = new Builder();
            builder.id = id;
            builder.name = name;
            return builder;
        }

        @Deprecated
        public static Builder create() {
            return new Builder();
        }

        public Builder id(Identifier id) {
            this.id = id;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder surfaceBuilderVariantChance(int surfaceBuilderVariantChance) {
            this.surfaceBuilderVariantChance = surfaceBuilderVariantChance;
            return this;
        }

        public Builder depth(float depth) {
            this.depth = depth;
            return this;
        }

        public Builder scale(float scale) {
            this.scale = scale;
            return this;
        }

        public Builder temperature(float temperature) {
            this.temperature = temperature;
            return this;
        }

        public Builder downfall(float downfall) {
            this.downfall = downfall;
            return this;
        }

        public Builder waterColor(int waterColor) {
            this.waterColor = waterColor;
            return this;
        }

        public Builder grassColor(int grassColor) {
            this.grassColor = grassColor;
            return this;
        }

        public Builder setFoliageColor(int foliageColor) {
            this.foliageColor = foliageColor;
            return this;
        }

        public DimensionBiomeData build() {
            return new DimensionBiomeData(id, name, surfaceBuilderVariantChance, depth, scale, temperature, downfall, waterColor, grassColor, foliageColor);
        }
    }
}