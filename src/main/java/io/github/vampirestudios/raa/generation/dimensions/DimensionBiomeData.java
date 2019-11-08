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

    DimensionBiomeData(Identifier id, String biomeName, int surfaceBuilderVariantChance, float depth, float scale, float temperature, float downfall, int waterColor) {
        this.id = id;
        this.biomeName = biomeName;
        this.surfaceBuilderVariantChance = surfaceBuilderVariantChance;
        this.depth = depth;
        this.scale = scale;
        this.temperature = temperature;
        this.downfall = downfall;
        this.waterColor = waterColor;
    }

    public Identifier getId() {
        return id;
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