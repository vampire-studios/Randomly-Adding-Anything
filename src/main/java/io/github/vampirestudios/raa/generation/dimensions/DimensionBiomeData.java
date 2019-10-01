package io.github.vampirestudios.raa.generation.dimensions;

public class DimensionBiomeData {

    private String biomeName;
    private int surfaceBuilderVariantChance;
    private float depth;
    private float scale;
    private float temperature;
    private float downfall;
    private int waterColor;

    public DimensionBiomeData(String biomeName, int surfaceBuilderVariantChance, float depth, float scale, float temperature, float downfall, int waterColor) {
        this.biomeName = biomeName;
        this.surfaceBuilderVariantChance = surfaceBuilderVariantChance;
        this.depth = depth;
        this.scale = scale;
        this.temperature = temperature;
        this.downfall = downfall;
        this.waterColor = waterColor;
    }

    public String getName() {
        return biomeName;
    }

    public int getSurfaceBuilderVariantChance() {
        return surfaceBuilderVariantChance;
    }

    public float getDepth() {
        return depth;
    }

    public float getScale() {
        return scale;
    }

    public float getTemperature() {
        return temperature;
    }

    public float getDownfall() {
        return downfall;
    }

    public int getWaterColor() {
        return waterColor;
    }

}