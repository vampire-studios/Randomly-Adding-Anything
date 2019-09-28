package io.github.vampirestudios.raa.generation.dimensions;

public class DimensionBiomeBuilder {

    private String name;
    private int surfaceBuilderVariantChance;
    private float depth;
    private float scale;
    private float temperature;
    private float downfall;
    private int waterColor;

    public static DimensionBiomeBuilder create() {
        return new DimensionBiomeBuilder();
    }

    public DimensionBiomeBuilder name(String name) {
        this.name = name;
        return this;
    }

    public DimensionBiomeBuilder surfaceBuilderVariantChance(int surfaceBuilderVariantChance) {
        this.surfaceBuilderVariantChance = surfaceBuilderVariantChance;
        return this;
    }

    public DimensionBiomeBuilder depth(float depth) {
        this.depth = depth;
        return this;
    }

    public DimensionBiomeBuilder scale(float scale) {
        this.scale = scale;
        return this;
    }

    public DimensionBiomeBuilder temperature(float temperature) {
        this.temperature = temperature;
        return this;
    }

    public DimensionBiomeBuilder downfall(float downfall) {
        this.downfall = downfall;
        return this;
    }

    public DimensionBiomeBuilder waterColor(int waterColor) {
        this.waterColor = waterColor;
        return this;
    }

    public DimensionBiomeData build() {
        return new DimensionBiomeData(name, surfaceBuilderVariantChance, depth, scale, temperature, downfall, waterColor);
    }

}